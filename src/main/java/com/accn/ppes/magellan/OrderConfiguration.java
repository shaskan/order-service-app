package com.accn.ppes.magellan;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
@Profile("rmq")
public class OrderConfiguration implements RabbitListenerConfigurer {

	@Value("${confirm.order.request.queue}")
	private String confirmOrderQueue;
	
	@Value("${cancel.order.request.queue}")
	private String cancelOrderQueue;
	
	@Value("${exchange.name}")
	private String exchangeName;
	
	@Value("${confirm.order.request.routingKey}")
	private String confirmOrderRoutingKey;
	
	@Value("${cancel.order.request.routingKey}")
	private String cancelOrderRoutingKey;

	@Value("${confirm.order.response.queue}")
	private String CONFORM_ORDER_RESPONSE_QUEUE;
	
	@Value("${confirm.order.response.routingKey}")
	private String confirmOrderResponseRoutingKey;
	
	@Bean
	public DirectExchange appExchange() {
		return new DirectExchange(exchangeName);
	}
	
	@Bean
	public Queue confirmOrderQueue() {
		return new Queue(confirmOrderQueue);
	}
	
	@Bean
	public Queue cancelOrderQueue() {
		return new Queue(cancelOrderQueue);
	}
	
	@Bean
	public Binding confirmOrderQueueBinding() {
		return BindingBuilder.bind(confirmOrderQueue()).to(appExchange()).with(confirmOrderRoutingKey);
	}
	
	@Bean
	public Binding cancelOrderQueueBinding() {
		return BindingBuilder.bind(cancelOrderQueue()).to(appExchange()).with(cancelOrderRoutingKey);
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}
	@Bean
	public Queue confirmOrderResponseQueue() {
		return new Queue(CONFORM_ORDER_RESPONSE_QUEUE);
	}
	 
/*	@Bean
	public Binding confirmOrderResponseQueueBinding() {
		return BindingBuilder.bind(confirmOrderResponseQueue()).to(appExchange()).with(confirmOrderResponseRoutingKey);
	} 
	*/
	@Bean
	Binding confirmOrderResponseQueueBinding(Queue confirmOrderResponseQueue, DirectExchange appExchange) {
		return BindingBuilder.bind(confirmOrderResponseQueue).to(appExchange).with(confirmOrderResponseRoutingKey);
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter(OrderResponseListener receiver) {
		MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
		messageListener.setDefaultListenerMethod("confirmOrderResponseMessage");
		return messageListener;
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(CONFORM_ORDER_RESPONSE_QUEUE);
		container.setMessageListener(listenerAdapter);
		container.setPrefetchCount(1);
		return container;
	} 

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

}
