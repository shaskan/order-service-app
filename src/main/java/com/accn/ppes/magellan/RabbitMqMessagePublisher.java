/**
 * 
 */
package com.accn.ppes.magellan;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.accn.ppes.magellan.api.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jai.balaji.sukumar
 *
 */
@Component
@Profile("rmq")
public class RabbitMqMessagePublisher implements MessagePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${confirm.order.request.queue}")
	//TODO: Remove if not required
	private String confirmOrderQueue;

	@Value("${cancel.order.request.queue}")
	//TODO: Remove if not required
	private String cancelOrderQueue;
	
	@Value("${exchange.name}")
	private String exchangeName;

	@Value("${confirm.order.request.routingKey}")
	private String confirmOrderRoutingKey;

	@Value("${cancel.order.request.routingKey}")
	private String cancelOrderRoutingKey;
	
	/**
	 * 
	 */
	public RabbitMqMessagePublisher() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.accn.ppes.magellan.api.MessagePublisher#postInPlaceOrderQueue(com.accn.ppes.magellan.OrderItems)
	 */
	@Override
	public void postInPlaceOrderQueue(OrderItems orderItems) {
		ObjectMapper objectMapper = new ObjectMapper();
		String message;
		try {
			message = objectMapper.writeValueAsString(orderItems);
			MessageProperties properties = new MessageProperties();
			properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			Message textMessage = new Message(message.getBytes(), properties);
			rabbitTemplate.send(exchangeName, confirmOrderRoutingKey, textMessage);
			System.out.println("Message posted:" + textMessage);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void postInCancelOrderQueue(OrderItems orderDetails) {
		ObjectMapper objectMapper = new ObjectMapper();
		String message;
		try {
			message = objectMapper.writeValueAsString(orderDetails);
			MessageProperties properties = new MessageProperties();
			properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			Message textMessage = new Message(message.getBytes(), properties);
			rabbitTemplate.send(exchangeName, cancelOrderRoutingKey, textMessage);
			System.out.println("Message posted:" + textMessage);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
