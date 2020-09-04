package com.ibm.order.exception;

/**
 * This exception class deals with if there is no order found
 * @author ManojKumar
 *
 */
public class OrderNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String msg) {
		super(msg);
	}
}
