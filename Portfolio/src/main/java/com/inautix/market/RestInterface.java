package com.inautix.market;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inautix.utils.FormOrderBean;
import com.inautix.utils.OrderBook;
import com.inautix.wrapper.Wrapper;

public interface RestInterface {

	ResponseEntity<Wrapper> listOfAllUsers();

	ResponseEntity<Wrapper> getUser(int userId);

	ResponseEntity<Wrapper> ordersUser(int userId);

	ResponseEntity<Wrapper> addOrder(FormOrderBean bean, int userId);

	ResponseEntity<Wrapper> deleteOrder(int orderId, int userId);

	ResponseEntity<Wrapper> listOfOrderBook();

	ResponseEntity<Wrapper> getOrderBook(String inst);

	ResponseEntity<Wrapper> putOrderBook(OrderBook book);

	ResponseEntity<Wrapper> changeOrderBook(OrderBook book);

	ResponseEntity<Wrapper> getOrderBookPrice(String inst);

}