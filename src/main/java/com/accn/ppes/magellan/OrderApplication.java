package com.accn.ppes.magellan;

/*import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
*/import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
*/
//@EnableRabbit
@SpringBootApplication
@Configuration
@EnableSwagger2
public class OrderApplication{
	
	/*@Value("${confirm.order.queue.name}")
	private String confirmOrderQueue;
	
	@Value("${cancel.order.queue.name}")
	private String cancelOrderQueue;
	
	@Value("${exchange.name}")
	private String exchangeName;
	
	@Value("${confirm.Order.RoutingKey}")
	private String confirmOrderRoutingKey;
	
	@Value("${cancel.Order.RoutingKey}")
	private String cancelOrderRoutingKey;
	@Value("${confirm.order.response.queue}")
	private String CONFORM_ORDER_RESPONSE_QUEUE;
	
	@Value("${confirm.Order.Response.RoutingKey}")
	private String confirmOrderResponseRoutingKey;*/
	
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.accn.ppes.magellan"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
	 private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Order API")
				.description("Order API reference for developers")
				.contact("ramarao.ariyaram@accenture.com").license("Order API License")
				.licenseUrl("ramarao.ariyaram@accenture.com").version("1.0").build();
	}
	
	/*@Bean
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
	 
	@Bean
	public Binding confirmOrderResponseQueueBinding() {
		return BindingBuilder.bind(confirmOrderResponseQueue()).to(appExchange()).with(confirmOrderResponseRoutingKey);
	} 
	
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
*/}
