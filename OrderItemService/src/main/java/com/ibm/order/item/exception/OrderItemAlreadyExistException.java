package com.ibm.order.item.exception;

/**
 * This is Exception class, if order item already exist.
 * @author ManojKumar
 *
 */
public class OrderItemAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public OrderItemAlreadyExistException (String msg) {
		super(msg);
	}
}
