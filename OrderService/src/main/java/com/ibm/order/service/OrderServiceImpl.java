package com.ibm.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.order.model.OrderDetails;
import com.ibm.order.repository.OrderRepository;

@Service
public class OrderServiceImpl {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * This method create the order
	 * @param order
	 */
	public void createOrder(OrderDetails order) {
		orderRepository.save(order);
	}
	
	public OrderDetails retrieveOrderByOrderId(Integer orderId) {
		return orderRepository.findById(orderId).get();
	}
	
	public List<OrderDetails> retrieveOrderByCustomerName(String custName) {
		return orderRepository.findByCustomerName(custName);
	}
}
