package com.doceasy.middler.service;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.doceasy.middler.dto.FileProcessQueueDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@Service
public class FileProcessQueueService {

	@Value("${mq.queues.files-to-process}")
	private String fileProcessQueue;
	
	private FileProcessQueueDTO queueDto;
	
	@Autowired
	private RabbitMqPublisherService publisherService;
	
	public Boolean publish(FileProcessQueueDTO queueDto) throws JsonProcessingException {
		String content = this.getContent(queueDto);
		
		publisherService.setQueue(this.getFileProcessQueue());
		publisherService.publish(content);
		
		return true;
	}
	
	private String getContent(FileProcessQueueDTO queueDto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(queueDto);
		
		return content;
	}
	
	/**
	 * Retorna a fila.
	 * @return
	 */
	public Queue getFileProcessQueue() {
		return new Queue(fileProcessQueue, true);
	}
	
}
