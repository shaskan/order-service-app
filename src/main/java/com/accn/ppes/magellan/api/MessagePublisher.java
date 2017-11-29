package com.accn.ppes.magellan.api;

import com.accn.ppes.magellan.OrderItems;

public interface MessagePublisher {

	void postInPlaceOrderQueue(OrderItems orderItems);

	void postInCancelOrderQueue(OrderItems orderDetails);
	
}
