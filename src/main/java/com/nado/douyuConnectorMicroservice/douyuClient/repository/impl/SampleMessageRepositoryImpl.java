package com.nado.douyuConnectorMicroservice.douyuClient.repository.impl;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jboss.logging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.douyuConnectorMicroservice.douyuClient.repository.SampleMessageRepository;
import com.nado.douyuConnectorMicroservice.enums.MessageIntegrityStatuses;

@Service
public class SampleMessageRepositoryImpl implements SampleMessageRepository {

	private static final int MAXMUM_INDEX_FOR_EACH_TYPE = 99;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	SetOperations<String, String> setOperations;
	HashOperations<String, String, String> hashOperations;
	ListOperations<String, String> listOperations;

	@PostConstruct
	public void init() {
		setOperations = stringRedisTemplate.opsForSet();
		hashOperations = stringRedisTemplate.opsForHash();
		listOperations = stringRedisTemplate.opsForList();
	}

	@Override
	public int getMaximumSizeForEachType() {
		// TODO Auto-generated method stub
		return MAXMUM_INDEX_FOR_EACH_TYPE + 1;
	}

	@Override
	public void saveSample(String message, String type, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		String key = "nado:sample_" + field + "_messages:type:" + type;
		listOperations.leftPush(key, message);
		listOperations.trim(key, 0, MAXMUM_INDEX_FOR_EACH_TYPE);
		setOperations.add("nado:sample_" + MessageIntegrityStatuses.total + "_messages:types", type);
	}

	@Override
	public Set<String> retieveTypes() {
		return setOperations.members("nado:sample_" + MessageIntegrityStatuses.total + "_messages:types");
	}

	@Override
	public List<String> retieveSamplesByType(String type, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		String key = "nado:sample_" + field + "_messages:type:" + type;
		return listOperations.range(key, 0, -1);
	}

	@Override
	public Map<String, List<String>> printAsFiles() {
		Map<String, List<String>> result = new HashMap<>();
		// TODO Auto-generated method stub
		String folderName = "sampleMessageFiles";
		Path folder = Paths.get(folderName);
		try {
			Files.createDirectory(folder);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Files.walk(Paths.get(folderName)).filter(p -> {
				return p.getFileName().toString().contains(".txt");
			}).forEach(arg0 -> {
				try {
					Files.delete(arg0);
				} catch (NoSuchFileException x) {
					System.err.format("%s: no such" + " file or directory%n", arg0);
				} catch (DirectoryNotEmptyException x) {
					System.err.format("%s not empty%n", arg0);
				} catch (IOException x) {
					// File permission problems are caught here.
					System.err.println(x);
				}
			});
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", folderName);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", folderName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (MessageIntegrityStatuses status : MessageIntegrityStatuses.values()) {
			if (!status.equals(MessageIntegrityStatuses.total)) {
				Set<String> types = retieveTypes();
				if (types != null && !types.isEmpty()) {
					for (String type : types) {
						List<String> messages = retieveSamplesByType(type, status);
						if (messages != null) {
							String fileName = status + "_" + type + "_sample_messages_" + messages.size() + ".txt";
							Path path = Paths.get(folderName, fileName);
							result.put(fileName, messages);
							messages.forEach(message -> {
								try {
									Files.write(path, (message + "\n").getBytes(), StandardOpenOption.APPEND,
											StandardOpenOption.CREATE);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
						}
					}
				}
			}
		}
		return result;
	}
}
