package com.inautix.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class test {
public static void main(String[] args) {
	
	TradeBot bot = new BuyBot(4,"AAPL",43);
	TradeBot bot2 = new BuyBot(3,"AAPL",54);
	TradeBot bot3 = new SellBot(6,"AAPL",44);
	TradeBot bot4 = new SellBot(5,"AAPL",46);
	bot.start();
	bot2.start();
	bot3.start();
	bot4.start();
}
}
