package com.ibm.order.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.order.exception.OrderNotFoundException;
import com.ibm.order.model.OrderDetails;
import com.ibm.order.service.OrderServiceImpl;

/**
 * This is order controller class
 * @author ManojKumar
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/orderservice")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderService;
	
	@PostMapping("/createorder") 
	private ResponseEntity<?> createOrder(@RequestBody OrderDetails order) {
		ResponseEntity<?> responseEntity;
		try {
			
			// Check if any value is empty or null
			if (StringUtils.isEmpty(order.getCustomerName()) 
					|| StringUtils.isEmpty(order.getShippingAddress()) 
					|| StringUtils.isEmpty(order.getTotals())
					|| StringUtils.isEmpty(order.getOrderItems())) {
				responseEntity = new ResponseEntity<String>("Customer name or Shipping Address or Totals should not be empty / null",
						HttpStatus.BAD_REQUEST);
				return responseEntity;
			}
			
			if (null == order.getDate()) {
				order.setDate(new Date());
			}
			//Save Order into DB
			orderService.createOrder(order);
			responseEntity = new ResponseEntity<OrderDetails>(order, HttpStatus.CREATED);
			
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@GetMapping("/getorderinfo/{custname}")  
	private ResponseEntity<?> getOrderInfo(@PathVariable("custname") String custname)   
	{ 
		ResponseEntity<?> responseEntity;
		try {
			List<OrderDetails> order = orderService.retrieveOrderByCustomerName(custname);
			if (null == order || order.isEmpty()) {
				throw new OrderNotFoundException("Order doesn't exist for customer " + custname);
			}
			return new ResponseEntity<List<OrderDetails>>(orderService.retrieveOrderByCustomerName(custname), HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	} 
}
