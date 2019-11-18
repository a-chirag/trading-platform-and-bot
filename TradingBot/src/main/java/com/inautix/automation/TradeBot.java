package com.inautix.automation;

import java.util.Scanner;

public class TradeBot extends Thread {
	Multi prob = new Multi();
	int user;
	String inst;
	String side;

	public TradeBot(int user, String inst, double probs, String side) {
		super();
		this.prob.probs = probs;
		this.user = user;
		this.prob.uid = user;
		this.inst = inst;
		this.side = side;
	}
	
	public void run() {
		prob.start();
		while (true) {
			try {
				double dPrice = Math.random();
				double dSize = Math.random();
				if (side.equals("buy"))
					makeOrder(dPrice * prob.probs, Math.ceil(dSize * 10));
				else
					makeOrder(prob.probs + dPrice * (1 - prob.probs), Math.ceil(dSize * 10));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void makeOrder(double price, double size) {
		TradingUtils.addOrder(new OrderData(inst, side, price, size), user);
	}
}
 
class Multi extends Thread {
	double probs;
	int uid;

	public void run() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			probs = sc.nextDouble();

		}
	}
}