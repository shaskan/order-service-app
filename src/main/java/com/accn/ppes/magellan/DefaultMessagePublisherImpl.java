/**
 * 
 */
package com.accn.ppes.magellan;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.accn.ppes.magellan.api.MessagePublisher;

/**
 * @author jai.balaji.sukumar
 *
 */
@Component
@Profile("!rmq")
public class DefaultMessagePublisherImpl implements MessagePublisher {

	/**
	 * 
	 */
	public DefaultMessagePublisherImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.accn.ppes.magellan.api.MessagePublisher#postInPlaceOrderQueue()
	 */
	@Override
	public void postInPlaceOrderQueue(OrderItems orderItems) {
		System.out.println("Message is recevied but will not be published " + orderItems);
	}

	@Override
	public void postInCancelOrderQueue(OrderItems orderDetails) {
		System.out.println("Message is recevied but will not be published " + orderDetails);
		
	}

}
