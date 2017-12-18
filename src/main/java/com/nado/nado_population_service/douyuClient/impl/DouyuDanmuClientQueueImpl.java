package com.nado.nado_population_service.douyuClient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;

@Service
public class DouyuDanmuClientQueueImpl implements DouyuDanmuClient {
	private Long messageId = 0l;
	private int BUFFER_SIZE = 0xA00000;
	private char[] buffer = new char[BUFFER_SIZE];
	BufferedReader reader = null;
	private BlockingQueue<String> messages = new LinkedTransferQueue<>();
	Socket clientSocket;
	// used to reconnect

	private SocketAddress address;

	@PostConstruct
	public void init() {
		System.out.println("\n\nInit!!!\n\n");
		clientSocket = douyuSocket();
		// register(2020877+"");
	}

	public Socket douyuSocket() {
		Socket result = new Socket();
		try {
			Socket clientSocket = new Socket("openbarrage.douyutv.com", 8601);
			address = clientSocket.getRemoteSocketAddress();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] send(String msg) {
		// TODO Auto-generated method stub
		byte[] message = msg.getBytes(StandardCharsets.UTF_8);
		int length = message.length;
		byte[] result = new byte[length + 13];
		ByteBuffer buffer = ByteBuffer.allocate(length + 13);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(result.length - 4);
		buffer.putInt(result.length - 4);
		buffer.putShort((short) 689);
		buffer.put((byte) 0);
		buffer.put((byte) 0);
		buffer.put(message, 0, length);
		buffer.put((byte) '\0');
		buffer.rewind();
		buffer.get(result);
		try {
			clientSocket.getOutputStream().write(result);
			System.out.println(Arrays.toString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void register(String room_id) {
		System.out.println("\n\nRegistering!!!\n\n");
		if (clientSocket.isClosed()) {
			clientSocket = douyuSocket();
		}
		if (clientSocket.isConnected()) {

		} else {
			try {
				clientSocket.connect(address);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()), BUFFER_SIZE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send("type@=loginreq/roomid@=" + room_id + "/");
			String message = "";
			do {
				message = take();
				if (message.contains("loginres")) {
					break;
				}
			} while (true);
			send("type@=joingroup/rid@=" + room_id + "/gid@=-9999/");
			System.out.println("\n\nRegistered!!!\n\n");
		}
	}

	@Scheduled(cron = "0/45 * * * * *")
	public void heartbeat() {
		send("type@=mrkl/");
	}

	@Override
	public void logout() {
		System.out.println("\n\nLogout!!!\n\n");
		// TODO Auto-generated method stub
		if (clientSocket.isConnected()) {
			send("type@=logout/");
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "* * * * * *")
	public void getMessage() {
		try {
			reader.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spliteAndDecorateMessages(new String(buffer)).forEach(str -> {
			messages.offer(str);
		});
		Arrays.fill(buffer, (char) 0);
	}

	@Override
	public String take() {
		String message = null;
		try {
			message = messages.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		messages.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	private Long newMessageId() {
		Long result = messageId;
		messageId++;
		return messageId;
	}

	private List<String> spliteAndDecorateMessages(String rawMessage) {
		String[] splitedRawMessage = (rawMessage.trim()).split(new String(new char[] {(char)0}));
		return Arrays.asList(splitedRawMessage).stream().filter(str -> str.contains("type"))
				.map(str -> str + "timestamp@=" + new Date().getTime() + "/messageId@=" + newMessageId() + "/")
				.collect(Collectors.toList());
	}
}
