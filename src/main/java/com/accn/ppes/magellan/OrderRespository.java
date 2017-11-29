package com.accn.ppes.magellan;


import java.util.Collection;

import org.springframework.data.repository.Repository;





public interface OrderRespository extends Repository<OrderItems, Long> {
	/**
	 * Find an account with the specified commodity number.
	 *
	 * @param commodity
	 * @return The commodity if found, null otherwise.
	 */
	
	public OrderItems findByOrderID(Long id);
	
	public OrderItems save(OrderItems saved);
	
	public String delete(Long id);
	
	public String delete(OrderItems orderItem);

	public Collection<OrderItems> findAll();
	
	public OrderItems findByProductName(String productName);
	
	public OrderItems findByLocationName(String locationName);
	
	public OrderItems findByStatus(String status);
	
	public OrderItems findByExternalRef(String externalRef);
	
	public OrderItems findByQuantity(long quantity);
	
}