package com.accn.ppes.magellan;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.accn.ppes.magellan.api.MessagePublisher;
import com.accn.ppes.magellan.api.Orderservice;
import com.accn.ppes.magellan.exception.OrderItemCancelledErrorException;
import com.accn.ppes.magellan.exception.OrderItemsException;

@RestController
@RequestMapping("/api")
public class OrderController {

	 @Autowired
	 Environment environment;
	 
	protected Orderservice orderservice;
	
	MessagePublisher orderMessage;

/*	@Value("${inventory.host}")
	private String inventoryHost;

	@Value("${inventory.port}")
	private String inventoryPort;*/

	@Autowired
	public OrderController(Orderservice orderservice) {
		this.orderservice = orderservice;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String index() {
		return "Welcome to APP PES Order API";

	}

	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byNumber(@PathVariable("id") Long id) {
		OrderItems productRes = orderservice.getOrderItemById(id);

		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException(id);
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public @ResponseBody OrderItems placeOrder(@RequestBody OrderItems orderSample) {
		// System.out.println("client_id:" + client_id);
		//RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		if (orderSample.getQuantity() > 0) {

			params.put("productName", orderSample.getProductName());
			params.put("locationName", orderSample.getLocationName());
			/*StringBuilder uri = new StringBuilder("http://").append(inventoryHost).append(":").append(inventoryPort)
					.append("/api/inventory/getQuantity/{productName}/{locationName}");
			Long quantity = (Long) restTemplate.getForObject(uri.toString(), Long.class, params);
			System.out.println("invetory:" + quantity);*/
			orderSample.setClient_ID(orderSample.getClient_ID());
			orderSample.setStatus("B");
			System.out.println(orderSample.toString());
			OrderItems created = null;
			
			// if (quantity > 0) {
				 created = orderservice.saveOrderItems(orderSample);
			// }
			// OrderItems created = orderitems.saveOrderItems(orderSample);
			// else{
			//	 throw new OrderItemsException(orderSample.getProductName(), orderSample.getProductName());
			// }
			 
			return created;
		} else {
			throw new OrderItemsException(orderSample.getQuantity());
		}
	}

	@RequestMapping(value = "/order/getByProductName/{productName}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byProductName(@PathVariable("productName") String productName) {
		OrderItems productRes = orderservice.getOrderByProductName(productName);
		System.out.println("environment:"+environment);
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}

	}

	@RequestMapping(value = "/order/getByLocationName/{locationName}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byLocationName(@PathVariable("locationName") String locationName) {
		OrderItems productRes = orderservice.getOrderByLocationName(locationName);
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order/getByStatus/{status}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byStatus(@PathVariable("status") String status) {
		OrderItems productRes = orderservice.getOrderByStatus(status);
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order/getByExternalRef/{externalRef}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byExternalRef(@PathVariable("externalRef") String externalRef) {
		OrderItems productRes = orderservice.getOrderByExternalRef(externalRef);
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order/getByQuantity/{quantity}", method = RequestMethod.GET)
	public @ResponseBody OrderItems byQuantity(@PathVariable("quantity") long quantity) {
		OrderItems productRes = orderservice.getOrderByQuantity(quantity);
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order/updateByExternalRef", method = RequestMethod.PUT)
	public @ResponseBody OrderItems updateByExternalRef(@RequestBody OrderItems orderItems) {
		OrderItems productRes = orderservice.updateOrderByExternalRef(orderItems, orderItems.getExternal_ref());
		if (productRes == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return productRes;
		}
	}

	@RequestMapping(value = "/order/deleteByExternalRef/{externalRef}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteByExternalRef(@PathVariable("externalRef") String externalRef) {
		String result = orderservice.deleteOrderByExternalRef(externalRef);

		if (result == null)
			throw new com.accn.ppes.magellan.exception.OrderItemsException();
		else {
			return result;
		}
	}

	/*
	 * @RequestMapping(value = "/{client_id}/{randomnumber}", method =
	 * RequestMethod.PUT) public @ResponseBody OrderItems
	 * cancelOrder(@PathVariable("client_id") Long client_id,
	 * 
	 * @PathVariable("randomnumber") Long randomnumber) { OrderItems orderSample
	 * = orderitems.getOrderItemById(randomnumber); orderSample.setStatus("C");
	 * orderitems.postInCancelOrderQueue(orderSample); OrderItems created =
	 * orderitems.updateOrderItems(orderSample); return created; }
	 */
	@RequestMapping(value = "/order/cancelByOrderId", method = RequestMethod.PUT)
	public @ResponseBody OrderItems cancelOrderbyOrderId(@RequestBody OrderItems orderItems) {
		OrderItems orderSample = orderservice.getOrderItemById(orderItems.getOrder_ID());
		OrderItems created = null;
		System.out.println("\n" + orderSample.getStatus() + "\n");
		if (orderSample.getStatus().equalsIgnoreCase("B") || orderSample.getStatus().equalsIgnoreCase("S")) {
			orderSample.setStatus("C");
			created = orderservice.updateOrderItems(orderSample);

		} else if (orderSample.getStatus().equalsIgnoreCase("E")) {
			throw new OrderItemCancelledErrorException(orderItems.getStatus());
		} else {
			throw new OrderItemCancelledErrorException();
		}
		return created;
	}

}
