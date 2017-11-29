package com.accn.ppes.magellan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.accn.ppes.magellan.OrderItems;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public OrderItemsException() {
		super("No such Order: ");
	}
	public OrderItemsException(Long orderNumber) {
		super("No such Order: " + orderNumber);
	}
	public OrderItemsException(OrderItems orderItem) {
		super("Product quantity should not be zero: " + orderItem.getQuantity());
	}
	public OrderItemsException(String productName,String locationName) {
		super("INVALID ORDER -> No such product or location name or Quantity may be zero");
	}
}
