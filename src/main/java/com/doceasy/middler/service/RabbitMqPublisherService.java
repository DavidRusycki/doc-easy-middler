package com.doceasy.middler.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Service
public class RabbitMqPublisherService {

	private final RabbitTemplate rabbitTemplate;
	private Queue queue;
	
	public void publish(String content) {
		rabbitTemplate.convertAndSend(queue.getName(), content);	
	}
	
}
