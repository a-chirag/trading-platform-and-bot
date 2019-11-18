package com.inautix.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData implements Orderlistner {
	int userId;
	double balance;
	Map<Integer, Order> pendingOrders = new HashMap<Integer, Order>();
	Map<String, Double> portfolio = new HashMap<String, Double>();
	public Map<Integer, Order> getPendingOrders() {
		return pendingOrders;
	}

	public void setPendingOrders(Map<Integer, Order> pendingOrders) {
		this.pendingOrders = pendingOrders;
	}

	public Map<Integer, Order> getExecutedOrders() {
		return executedOrders;
	}

	public void setExecutedOrders(Map<Integer, Order> executedOrders) {
		this.executedOrders = executedOrders;
	}

	Map<Integer, Order> executedOrders = new HashMap<Integer, Order>();

	public void add(Order o) {
		System.out.println("Order Added" + o + "\n User:" + this.userId);
		pendingOrders.put(o.getOrderId(), o);
	}

	private void addToPort(String inst, double d) {
		if (portfolio.get(inst) == null) {
			portfolio.put(inst, 0.0);
		}
		portfolio.put(inst, portfolio.get(inst) + d);
	}

	public void match(Order bo, Order so, Order o) {
		if (pendingOrders.get(bo.getOrderId()) != null) {
			System.out.println("Buy order executed:" + bo + "\n User:" + this.userId);
			addToPort(bo.getInstrument(), o.getSize());
			executedOrders.put(bo.getOrderId(), bo);
			if (o.getSize() == bo.getSize())
				pendingOrders.remove(bo.getOrderId());
			balance -= so.getPrice() * o.getSize();
			bo.getBook().setPrices(new java.util.Date(),so.getPrice());
		} else if (pendingOrders.get(so.getOrderId()) != null) {
			System.out.println("Sell order executed:" + so + "\n User:" + this.userId);
			addToPort(bo.getInstrument(), -o.getSize());
			executedOrders.put(so.getOrderId(), so);
			if (o.getSize() == so.getSize())
				pendingOrders.remove(so.getOrderId());
			balance += so.getPrice() * o.getSize();
		}
	}

	public Map<String, Double> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Map<String, Double> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "UserData [userId=" + userId + ", balance=" + balance + "]";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public UserData() {
		super();
	}
	public Order removeOrder(int id){
		Order order = pendingOrders.get(id);
		if(order==null)
			return null;
		pendingOrders.remove(id);
		if(order.getBook().removeOrder(order))
			return order;
		else
			return null;
	}
}
