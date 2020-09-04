package com.ibm.order.item.controller;

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

import com.ibm.order.item.exception.OrderItemAlreadyExistException;
import com.ibm.order.item.model.OrderItem;
import com.ibm.order.item.service.OrderItemServiceImpl;

/**
 * This is controller class
 * @author ManojKumar
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/orderitemservice")
public class OrderItemController {
	
	@Autowired
	private OrderItemServiceImpl orderItemService;

	@PostMapping("/createorderitem") 
	private ResponseEntity<?> createOrderItem(@RequestBody OrderItem orderItem) {
		ResponseEntity<?> responseEntity;
		try {
			
			// Check if any value is empty or null
			if (StringUtils.isEmpty(orderItem.getProductName()) 
					|| StringUtils.isEmpty(orderItem.getProductCode()) 
					|| StringUtils.isEmpty(orderItem.getQuantity())) {
				responseEntity = new ResponseEntity<String>("Product name or code or quantity should not be empty / null",
						HttpStatus.BAD_REQUEST);
				return responseEntity;
			}
			
			//Check if product code is already exist in DB
			OrderItem orderItemExist = orderItemService.retrieveOrderItemByProdCode(orderItem.getProductCode());
			if (orderItemExist != null) {
				throw new OrderItemAlreadyExistException(orderItem.getProductCode() + " already exist in DB");
			}
			
			//Save Order item into DB
			orderItemService.createOrderItem(orderItem);
			responseEntity = new ResponseEntity<OrderItem>(orderItem, HttpStatus.CREATED);
		} catch (OrderItemAlreadyExistException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@GetMapping("/getorderitem/{id}")  
	private ResponseEntity<?> getOrderItem(@PathVariable("id") int id)   
	{ 
		ResponseEntity<?> responseEntity;
		try {
			OrderItem orderItem = orderItemService.retrieveOrderItem(id);
			if (null == orderItem) {
				responseEntity = new ResponseEntity<String>("Order Item id doesn't exist", HttpStatus.NO_CONTENT);
				return responseEntity;
			}
			return new ResponseEntity<OrderItem>(orderItemService.retrieveOrderItem(id), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	} 
}
