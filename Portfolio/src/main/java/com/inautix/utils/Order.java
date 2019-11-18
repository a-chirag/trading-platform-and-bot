package com.inautix.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
int orderId;
double price;
double size;
@JsonIgnore
Orderlistner listner;
@JsonIgnore
OrderBook book;
public OrderBook getBook() {
	return book;
}
public void setBook(OrderBook book) {
	this.book = book;
}
static int orderIdCounter=0;
public Order(long price, double size, Orderlistner listner) {
	super();
	orderIdCounter++;
	this.orderId = orderIdCounter;
	this.price = price;
	this.size = size;
	this.listner = listner;
}
public int getOrderId() {
	return orderId;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;
}
public double getPrice() {
	return price;
}
public Order(Orderlistner listner) {
	super();
	orderIdCounter++;
	this.orderId = orderIdCounter;
	this.listner=listner;
}
@Override
public String toString() {
	return "Order [orderId=" + orderId + ", price=" + price + ", size=" + size + ", listner=" + listner + "]";
}
public void setPrice(double price) {
	this.price = price;
}
public double getSize() {
	return size;
}
public void setSize(double size) {
	this.size = size;
}
public Orderlistner getListner() {
	return listner;
}
public void setListner(Orderlistner listner) {
	this.listner = listner;
}
public String getInstrument(){
	return this.getBook().getInstrument();
}
public String getSide(){
	if(this.getBook().getBuy().containsKey(this.getPrice()))
		return "buy";
	else
		return "sell";
}
public void reduce(double size2) {
	this.size-=size2;
}
}
