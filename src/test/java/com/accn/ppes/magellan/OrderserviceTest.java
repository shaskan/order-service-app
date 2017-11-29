package com.accn.ppes.magellan;

import java.util.Collection;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.accn.ppes.magellan.OrderItems;
import com.accn.ppes.magellan.api.Orderservice;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class OrderserviceTest  extends AbstractTest{

	@Autowired
	private Orderservice service;
	
    @Test
    public void test1Create() {

    	OrderItems entity = new OrderItems();
    	 Long id=new Long(1);
    	 entity.setClient_ID(id);
         entity.setProductName("CELO");
         entity.setExternal_ref("local");
         entity.setLocationName("India");
         entity.setStatus("B");
         Long quantity=new Long(6);
         entity.setQuantity(quantity);
        

     	OrderItems secondentity = new OrderItems();
     	Long secondentity_id=new Long(1);
     	secondentity.setClient_ID(secondentity_id);
     	secondentity.setProductName("Montex");
     	secondentity.setExternal_ref("local1");
     	secondentity.setLocationName("India");
     	secondentity.setStatus("B");
        Long secondentity_quantity=new Long(8);
        secondentity.setQuantity(secondentity_quantity);
         
        OrderItems createdEntity = service.saveOrderItems(entity);
        service.saveOrderItems(secondentity);
        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null"+createdEntity.getOrder_ID(),
                createdEntity.getOrder_ID());
        Assert.assertEquals("failure - expected Product Name attribute match", "CELO",
                createdEntity.getProductName());
        Assert.assertEquals("failure - expected Location Name attribute match", "India" ,
        		createdEntity.getLocationName());
        Assert.assertEquals("failure - expected Order status attribute match", "B" ,
        		createdEntity.getStatus());

        Collection<OrderItems> list = service.getAllOrderItems();

        Assert.assertEquals("failure - expected size", 2, list.size());
    }
	
	  @Test
	    public void test2FindAll() {

	        Collection<OrderItems> list = service.getAllOrderItems();

	        Assert.assertNotNull("failure - expected not null", list);
	        Assert.assertEquals("failure - expected list size", 2, list.size());

	    }
	  
	@Test
    public void test3FindOne() {

        Long id = new Long(1);

        OrderItems entity = service.getOrderItemById(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id,
                entity.getOrder_ID());

    }

    
    @Test
    public void test4Update() {

        Long id = new Long(1);

        OrderItems entity = service.getOrderItemById(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = "C";
        entity.setStatus(updatedText);
        OrderItems updatedEntity = service.updateOrderItems(entity);

        Assert.assertNotNull("failure - expected not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute match", id,
                updatedEntity.getOrder_ID());
        Assert.assertEquals("failure - expected text attribute match",
                updatedText, updatedEntity.getStatus());

    }


    @Test
    public void test5Delete() {

        Long id = new Long(1);

        Collection<OrderItems> entities = service.getAllOrderItems();
        
        OrderItems entity = service.getOrderItemById(id);
        
        System.out.println(entities.toString());

        Assert.assertNotNull("failure - expected not null", entity);

        service.deleteOrder(id);

        Collection<OrderItems> list = service.getAllOrderItems();

        Assert.assertEquals("failure - expected size", 1, list.size());


    }
}
