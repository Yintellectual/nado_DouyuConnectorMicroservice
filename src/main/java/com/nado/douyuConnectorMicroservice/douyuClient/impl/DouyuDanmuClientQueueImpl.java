package com.nado.douyuConnectorMicroservice.douyuClient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuClient;
import com.nado.douyuConnectorMicroservice.util.CommonUtil;

@Service
public class DouyuDanmuClientQueueImpl implements DouyuDanmuClient {
	private static final Logger logger = LoggerFactory.getLogger(DouyuDanmuClientQueueImpl.class);	
	private Long messageId = 0l;
	private int BUFFER_SIZE = 0xA00000;
	private char[] buffer = new char[BUFFER_SIZE];
	BufferedReader reader = null;
	private BlockingQueue<String> messages = new LinkedTransferQueue<>();
	private BlockingQueue<String> heartBeat = new LinkedTransferQueue<>();
	Socket clientSocket;
	// used to reconnect
	private String room_id;
	private SocketAddress address;

	@PostConstruct
	public void init() {
		logger.info("\n\nInit!!!\n\n");
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
			logger.error(e.toString());
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
			clientSocket.getOutputStream().flush();
			logger.info(Arrays.toString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				clientSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			register(room_id);
			logger.error(e.toString());
			return send(msg);
		}
		return result;
	}

	@Override
	public void register(String room_id) {
		logout = false;
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.room_id = room_id;
		logger.info("\n\nRegistering!!!\n\n");
		if (clientSocket.isClosed()) {
			logger.info("Error when send and closed");
			clientSocket = douyuSocket();
		}
		if (clientSocket.isConnected()) {
			logger.info("Error when send but connected");
		} else {
			logger.info("Error when send and disconnected");
			try {
				clientSocket.connect(address);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
			}
			try {
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()), BUFFER_SIZE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
			}
			send("type@=loginreq/roomid@=" + room_id + "/");
			String message = "";
			List<String> temp = new ArrayList<>();
			do {
				try {
					message = messages.poll(10, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
				} // take();
				if(message!=null){
					temp.add(message);
					if(message.contains("loginres")){
						temp.forEach(m->{
							messages.offer(m);
						});
						break;
					}else{
						logger.error(message);
					}
				}else{
					
				}

			} while (true);
			send("type@=joingroup/rid@=" + room_id + "/gid@=-9999/");
			logger.info("\n\nRegistered!!!\n\n");
		}
	}

	@Scheduled(cron = "0/30 * * * * *")
	public void heartbeat() {
		if (!logout) {
			logger.info(Thread.currentThread().getName() + ":heartbeat");
			heartBeat.clear();
			send("type@=mrkl/");
			String message = "";
			try {
				message = heartBeat.poll(5, TimeUnit.SECONDS);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				try {
					clientSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
				}
				register(room_id);
			}
		}
	}

	private boolean logout = true;

	@Override
	public void logout() {
		logger.info("\n\nLogout!!!\n\n");
		logout = true;
		// TODO Auto-generated method stub
		if (clientSocket.isConnected()) {
			send("type@=logout/");
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}
	}

	@Scheduled(cron = "* * * * * *")
	public void getMessage() {
		if (!logout) {
			//logger.info(Thread.currentThread().getName() + ": getMessage");
			try {
				reader.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
			}
			spliteAndDecorateMessages(new String(buffer)).forEach(str -> {
				messages.offer(str);
			});
			Arrays.fill(buffer, (char) 0);
		}
	}

	@Override
	public String take() {
		String message = null;
		try {
			message = messages.poll(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}
		if (message == null) {
			return take();
		} else {
			return message;
		}
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
		//logger.info(rawMessage.trim());
		if (rawMessage.contains("type@=mrkl/")) {
			try {
				heartBeat.put("/type@=mrkl/");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
			}
		}
		String[] splitedRawMessage = (rawMessage.trim()).split(new String(new char[] { (char) 0 }));
		return Arrays.asList(splitedRawMessage).stream().filter(str -> str.contains("type"))
				.map(str -> "/timestamp@=" + new Date().getTime() + "/messageId@=" + newMessageId() + "/" + str)
				.collect(Collectors.toList());
	}
}
