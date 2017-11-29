package com.accn.ppes.magellan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accn.ppes.magellan.api.MessagePublisher;
import com.accn.ppes.magellan.api.Orderservice;

@Service
public class OrderUtility implements Orderservice {

	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	OrderRespository orderRespository;

	public OrderUtility() {
		
	}



	@Override
	public OrderItems getOrderItemById(long id) {
		// TODO Auto-generated method stub
		OrderItems productcount = orderRespository.findByOrderID(id);
		if (productcount == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException(id);
		else {
			return productcount;
		}
	}

	@Override
	public OrderItems saveOrderItems(OrderItems product) {
		OrderItems created = orderRespository.save(product);
		System.out.println("\n created" + created.toString() + "\n");
		messagePublisher.postInPlaceOrderQueue(created);
		return created;
	}

	@Override
	public OrderItems updateOrderItems(OrderItems orderItems) {
		// TODO Auto-generated method stub
		try {
			//OrderItems confirmedOrder = getOrderItemById(entity.getOrder_ID());
			orderRespository.save(orderItems);
			this.messagePublisher.postInCancelOrderQueue(orderItems);
		} catch (Exception e) {
			throw new com.accn.ppes.magellan.exception.OrderItemsException(orderItems.getOrder_ID());
		}
		return orderItems;
	}

	@Override
	public Collection<OrderItems> getAllOrderItems() {
		// TODO Auto-generated method stub
		Collection<OrderItems> orderItems = orderRespository.findAll();
		return orderItems;
	}

	@Override
	public String deleteOrder(Long id) {
		// TODO Auto-generated method stub
		try {
			orderRespository.delete(id);
		} catch (Exception e) {
			throw new com.accn.ppes.magellan.exception.OrderItemsException(id);
		}
		return "OrderItem deleted!";
	}

	

	@Override
	public OrderItems getOrderByProductName(String productName) {
		// TODO Auto-generated method stub
		return orderRespository.findByProductName(productName);
	}

	@Override
	public OrderItems getOrderByLocationName(String locationName) {
		// TODO Auto-generated method stub
		return orderRespository.findByLocationName(locationName);
	}

	@Override
	public OrderItems getOrderByStatus(String status) {
		// TODO Auto-generated method stub
		return orderRespository.findByStatus(status);
	}

	@Override
	public OrderItems getOrderByExternalRef(String externalRef) {
		// TODO Auto-generated method stub
		return orderRespository.findByExternalRef(externalRef);
	}

	@Override
	public OrderItems getOrderByQuantity(long quantity) {
		// TODO Auto-generated method stub
		return orderRespository.findByQuantity(quantity);
	}

	@Override
	public OrderItems updateOrderByExternalRef(OrderItems orderItems, String externalRef) {
		// TODO Auto-generated method stub
		OrderItems oldOrderItems = orderRespository.findByExternalRef(externalRef);
		orderItems.setOrder_ID(oldOrderItems.getOrder_ID());
		orderItems.setExternal_ref(externalRef);
		return orderRespository.save(orderItems);
	}

	@Override
	public String deleteOrderByExternalRef(String externalRef) {
		// TODO Auto-generated method stub
		try {
			OrderItems orderItem = orderRespository.findByExternalRef(externalRef);
			orderRespository.delete(orderItem);
		} catch (Exception e) {
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		}
		return "order deleted!";

	}
	
	public OrderItems updateOrderItemsFromListener(OrderItems orderItems) {
		try {
			// OrderItems confirmedOrder =
			// getOrderItemById(entity.getOrder_ID());
			orderRespository.save(orderItems);
		} catch (Exception e) {
			throw new com.accn.ppes.magellan.exception.OrderItemsException(orderItems.getOrder_ID());
		}
		return orderItems;
	}

}
