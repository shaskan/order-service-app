package com.accn.ppes.magellan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "order_items")
@Proxy(lazy = false)
public class OrderItems {
	
	@Override
	public String toString() {
		return "OrderItems [orderID=" + orderID + ", client_ID=" + client_ID + ", productName=" + productName
				+ ", locationName=" + locationName + ", status=" + status + ", externalRef=" + externalRef
				+ ", quantity=" + quantity + "]";
	}

	@Id
	@GeneratedValue
	Long orderID;

	@Column
	private Long client_ID;
	
	@Column
	private String productName;

	@Column
	private String  locationName;

	@Column
	private String status;
	
	@Column(unique = true)
	private String externalRef;
	
	@Column
	private Long quantity;

	public Long getOrder_ID() {
		return orderID;
	}

	public void setOrder_ID(Long order_ID) {
		orderID = order_ID;
	}

	public Long getClient_ID() {
		return client_ID;
	}

	public void setClient_ID(Long client_ID) {
		this.client_ID = client_ID;
	}

	public String getExternal_ref() {
		return externalRef;
	}

	public void setExternal_ref(String external_ref) {
		this.externalRef = external_ref;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
