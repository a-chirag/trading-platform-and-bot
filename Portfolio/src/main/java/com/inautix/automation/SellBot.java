package com.inautix.automation;

public class SellBot extends TradeBot {
	static String side = "sell";
	public SellBot(int user, String inst, double probs) {
		super(user, inst, probs, side);
		// TODO Auto-generated constructor stub
	}

}
