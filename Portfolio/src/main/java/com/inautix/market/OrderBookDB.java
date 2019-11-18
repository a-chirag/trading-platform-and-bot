package com.inautix.market;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inautix.utils.OrderBook;
@Repository
public class OrderBookDB {
Map<String, OrderBook> orderBooks;
public OrderBookDB(){
	System.out.println("OrderBookDB initialised");
	orderBooks= new HashMap<String, OrderBook>();
	//OrderBook test = new OrderBook("test","test",new Date("10 may 2017"));
	//orderBooks.put("test", test);
}
@Autowired
public void setOrderBook(OrderBook orderBook) {
	orderBooks.put(orderBook.getInstrument(), orderBook);
}

public OrderBook getOrderBook(String instrument){
	return orderBooks.get(instrument);
}
public Map<String, OrderBook> getOrderBooks() {
	return orderBooks;
}
public void setOrderBooks(Map<String, OrderBook> orderBooks) {
	this.orderBooks = orderBooks;
}

}
