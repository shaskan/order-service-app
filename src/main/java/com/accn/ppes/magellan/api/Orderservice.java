package com.accn.ppes.magellan.api;

import java.util.Collection;

import com.accn.ppes.magellan.OrderItems;

public interface Orderservice {
		public OrderItems getOrderItemById(long id);
		public OrderItems getOrderByProductName(String productName);		
		public OrderItems getOrderByLocationName(String locationName);
		public OrderItems getOrderByStatus(String status);
		public OrderItems getOrderByExternalRef(String externalRef);
		public OrderItems getOrderByQuantity(long quantity);
		public OrderItems saveOrderItems(OrderItems commodity);
		public OrderItems updateOrderItems(OrderItems commodity);
		public Collection<OrderItems> getAllOrderItems();
		public String deleteOrder(Long id);
		public String deleteOrderByExternalRef(String externalRef);
		/*public void postInConfirmedOrderQueue(OrderItems orderDetails);
		public void postInCancelOrderQueue(OrderItems orderDetails);*/
		public OrderItems updateOrderByExternalRef(OrderItems orderItems,String externalRef);
		
}
