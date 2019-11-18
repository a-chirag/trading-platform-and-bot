package com.inautix.automation;

public class OrderData {
String instrument;
String side;
double price;
double size;
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
public double getPrice() {
	return price;
}
public OrderData(String instrument, String side, double price, double size) {
	super();
	this.instrument = instrument;
	this.side = side;
	this.price = price;
	this.size = size;
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
@Override
public String toString() {
	return "OrderData [instrument=" + instrument + ", side=" + side + ", price=" + price + ", size=" + size + "]";
}
}
