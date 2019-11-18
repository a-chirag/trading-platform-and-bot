package com.inautix.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class test {
public static void main(String[] args) {
	
	TradeBot bot = new BuyBot(4,"IndVsPak",0.43);
	TradeBot bot2 = new BuyBot(3,"IndVsPak",0.54);
	TradeBot bot3 = new SellBot(6,"IndVsPak",0.4);
	TradeBot bot4 = new SellBot(5,"IndVsPak",0.46);
	bot.start();
	bot2.start();
	bot3.start();
	bot4.start();
}
}
