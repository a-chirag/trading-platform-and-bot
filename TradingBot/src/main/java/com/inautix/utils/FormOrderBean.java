package com.inautix.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormOrderBean {
String instrument;
String side;
Order order;
public FormOrderBean(Order order2) {
	order=order2;
}

public FormOrderBean() {
	order=new Order(null);
}

@JsonCreator
public FormOrderBean(@JsonProperty("side")String side, @JsonProperty("instrument")String instrument, @JsonProperty("size")Double size,@JsonProperty("price")Double price){
	order=new Order(null);
	this.setPrice(price);
	this.setSide(side);
	this.setSize(size);
	this.setInstrument(instrument);
}
public String getInstrument() {
	return instrument;
}
public void setInstrument(String instrument) {
	this.instrument = instrument;
}
public String getSide() {
	return side;
}
public void setSide(String side) {
	this.side = side;
}
public Order getOrder() {
	return order;
}
public void setOrder(Order order) {
	this.order = order;
}
public void setPrice(double price) {
	this.order.setPrice(price);
}
public void setSize(double size) {
	this.order.setSize(size);
}
public double getSize() {
	return order.getSize();
}
public double getPrice() {
	return order.getPrice();
}
@Override
public String toString() {
	return "FormOrderBean [instrument=" + instrument + ", side=" + side + ", order=" + order + "]";
}
}
