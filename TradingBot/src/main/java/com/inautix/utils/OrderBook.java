package com.inautix.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBook {
	String instrument;
	String desc;
	String winCase;
	public String getWinCase() {
		return winCase;
	}
	public void setWinCase(String winCase) {
		this.winCase = winCase;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	Date endDate;
	TreeMap<Double, PriceLevel> buy;
	TreeMap<Double, PriceLevel> sell;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	TreeMap<Date, Double> prices;
	@JsonIgnore
	OrderBookListner listner;

	public OrderBook() {
		this("IndVsPak","A match between India and Pakistan",new Date("10 may 2017"),"India Wins");
	}
	@JsonCreator
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OrderBook(@JsonProperty("instrument")String instrument,@JsonProperty("desc")String desc, @JsonProperty("endDate")Date endDate,@JsonProperty("wincase")String winCase) {
		System.out.println("OrderBook Created: "+instrument);
		buy = new TreeMap(new Comparator<Double>() {

			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}

		});
		sell = new TreeMap();
		this.instrument = instrument;
		this.endDate=endDate;
		this.desc=desc;
		this.winCase=winCase;
		listner = null;
		prices= new TreeMap();
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OrderBook(String instrument, OrderBookListner listner) {
		super();
		buy = new TreeMap(new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}

		});
		sell = new TreeMap();
		this.instrument = instrument;
		this.listner = listner;
		prices= new TreeMap();
	}

	public TreeMap<Date,Double> getPrices() {
		return prices;
	}
	public void setPrices(Date time, Double prices) {
		this.prices.put(time, prices);
	}

	public void buy(Order bo) {
		bo.getListner().add(bo);
		double remainingQuantity = bo.getSize();

		PriceLevel bestLevel = getBestLevel(sell);

		while (remainingQuantity > 0 && bestLevel != null && bestLevel.getPrice() <= bo.getPrice()) {
			remainingQuantity = bestLevel.match(bo);

			if (bestLevel.isEmpty())
				sell.remove(bestLevel.getPrice());

			bestLevel = getBestLevel(sell);
		}

		if (remainingQuantity > 0) {
			System.out.println("adding new buy level");
			PriceLevel level = buy.get(bo.getPrice());
			if(level==null)
				buy.put(bo.getPrice(), new PriceLevel(bo.getPrice(),Side.BUY));
			level=buy.get(bo.getPrice());
			level.add(bo);
			// listener.add(orderId, Side.BUY, price, remainingQuantity);
		}
	}

	private PriceLevel getBestLevel(TreeMap<Double, PriceLevel> levels) {
		if (levels.isEmpty())
			return null;

		return levels.get(levels.firstKey());
	}

	public void sell(Order so) {
		so.getListner().add(so);
		double remainingQuantity = so.getSize();

		PriceLevel bestLevel = getBestLevel(buy);

		while (remainingQuantity > 0 && bestLevel != null && bestLevel.getPrice() >= so.getPrice()) {
			remainingQuantity = bestLevel.match(so);
			System.out.println("check"+bestLevel.isEmpty());
			System.out.println("Quantity remaining"+remainingQuantity);
			if (bestLevel.isEmpty())
				buy.remove(bestLevel.getPrice());
			bestLevel = getBestLevel(buy);
		}

		if (remainingQuantity > 0) {
			PriceLevel level = sell.get(so.getPrice());
			if(level==null)
				level=sell.put(so.getPrice(), new PriceLevel(so.getPrice(),Side.SELL));
			level=sell.get(so.getPrice());
			level.add(so);
			// listener.add(orderId, Side.SELL, price, remainingQuantity);
		}
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public TreeMap<Double, PriceLevel> getBuy() {
		return buy;
	}

	public void setBuy(TreeMap<Double, PriceLevel> buy) {
		this.buy = buy;
	}

	public TreeMap<Double, PriceLevel> getSell() {
		return sell;
	}

	public void setSell(TreeMap<Double, PriceLevel> sell) {
		this.sell = sell;
	}

	public OrderBookListner getListner() {
		return listner;
	}

	public void setListner(OrderBookListner listner) {
		this.listner = listner;
	}

	public boolean removeOrder(Order order) {
		PriceLevel level = buy.get(order.getPrice());
		if(level==null)
			level=sell.get(order.getPrice());
		return level.remove(order);
	}

}
