package com.accn.ppes.magellan;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderResponseListener {
	
	@Autowired
	OrderUtility orderUtility;
	
	public void confirmOrderResponseMessage(Object text) throws JsonParseException, JsonMappingException, IOException{
		try{
			String message = (String) text;
			System.out.println("Message fetched:" + message);
			OrderItems order = new OrderItems();
			ObjectMapper mapper = new ObjectMapper();
			order = mapper.readValue(message, OrderItems.class);
			//order.setStatus("Confirmed");
			System.out.println("\n order in response listener :"+order+"\n");
			OrderItems orderDb = orderUtility.getOrderItemById(order.getOrder_ID());
			orderDb.setStatus(order.getStatus());
			orderUtility.updateOrderItemsFromListener(orderDb);
			System.out.println(message);
		} catch (Exception e) {
			//TODO: Need log the message
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}

}
