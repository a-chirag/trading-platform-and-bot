package com.inautix.utils;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceLevel {
Double price;
Side side;
@JsonIgnore
ArrayList<Order> orders;
Double size;
public Double getPrice() {
	return price;
}
public Side getSide() {
	return side;
}
public PriceLevel(double d, Side side) {
	super();
	orders= new ArrayList<Order>();
	this.price = d;
	this.side = side;
	this.size=0.0;
}
public boolean isEmpty(){
	return size==0;
}
public Order add(Order o){
	orders.add(o);
	increaseSize(o.getSize());
	return o;
}
public void delete(Order o){
	orders.remove(o);
	increaseSize(-o.getSize());
}
public double match(Order o){
	Double size = o.getSize();
	 while (size> 0 && !orders.isEmpty()) {
         Order order = orders.get(0);

         double orderQuantity = order.getSize();

         if (orderQuantity > size) {
             
             if(this.side==Side.BUY){
             order.getListner().match(order,o,o);
             o.getListner().match(order,o,o);
             }
             else{
            	 order.getListner().match(o,order,o);
            	 o.getListner().match(o,order,o);
             }
             order.reduce(o.getSize());
             increaseSize(-o.getSize());
             size=(double) 0;
         } else {
             orders.remove(0);
             if(this.side==Side.BUY){
                 order.getListner().match(order,o,order);
                 o.getListner().match(order,o,order);
             }
                 else{
                	 order.getListner().match(o,order,order);
                	 o.getListner().match(o,order,order);
                 }
             o.setSize(o.getSize()-orderQuantity);
             increaseSize(-orderQuantity);
             size=o.getSize();
         }
     }

     return size;
}
public ArrayList<Order> getOrders() {
	return orders;
}
public void setOrders(ArrayList<Order> orders) {
	this.orders = orders;
}
public Double getSize() {
	return size;
}
public void setSize(Double size) {
	this.size = size;
}
public void increaseSize(Double size) {
	this.size += size;
}
public boolean remove(Order order) {
	if(orders.remove(order)){
		increaseSize(-order.getSize());
		return true;
	}
	return false;
}
}
