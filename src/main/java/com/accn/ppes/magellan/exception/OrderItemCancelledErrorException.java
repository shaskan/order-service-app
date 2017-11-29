package com.accn.ppes.magellan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemCancelledErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderItemCancelledErrorException()
	{
		super("Order already cancelled ");

	}
	public OrderItemCancelledErrorException(String status)
	{
		super("Order status is Error");

	}
}
