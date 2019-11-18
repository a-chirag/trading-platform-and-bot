package com.inautix.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inautix.utils.FormOrderBean;
import com.inautix.utils.Order;
import com.inautix.utils.OrderBook;
import com.inautix.utils.UserData;

@Service
public class MarketService {
	@Autowired
	UsersDB udb;
	@Autowired
	OrderBookDB odb;

	public UserData getUserData(int id) {
		return udb.getUser(id);
	}

	public Order addOrder(FormOrderBean order, UserData userData) {
		order.getOrder().setListner(getUserData(userData.getUserId()));// set
																		// user
																		// value
		OrderBook ob = odb.getOrderBook(order.getInstrument());// get orderbook
		if (ob == null) {
			return null;
		}
		order.getOrder().setBook(ob);// set orderbook in order
		if (order.getSide().equals("buy"))
			ob.buy(order.getOrder());
		else
			ob.sell(order.getOrder());
		return order.getOrder();
	}

	public OrderBook getOrderBook(String instrument) {
		return odb.getOrderBook(instrument);
	}

	public List<UserData> getUsers() {
		return new ArrayList<UserData>(udb.getUsers().values());
	}

	public UserData addUser(int id) {
		UserData user = new UserData();
		user.setUserId(id);
		user.setBalance(100);
		return udb.getUsers().put(id, user);
	}

	public List<OrderBook> getOrderBooks() {
		return new ArrayList<OrderBook>(odb.getOrderBooks().values());
	}

	public OrderBook addBook(OrderBook book) {
		odb.setOrderBook(book);
		return book;
	}

	public void putUsers(List<User> users) {
		for (User user : users) {
			addUser(user.getUid());
		}
	}

	public OrderBook changeBook(OrderBook book) {
		OrderBook old = odb.getOrderBooks().get(book.getInstrument());
		old.setDesc(book.getDesc());
		return old;
	}

	public void postResult(String result, String inst) {
		Map<Integer, UserData> data = udb.getUsers();
		for (int i : data.keySet()) {
			UserData user = data.get(i);
			Map<Integer, Order> porders = user.getPendingOrders();
			for (int j : porders.keySet()) {
				Order o = porders.get(j);
				if (o.getInstrument().equals(inst)) {
					user.removeOrder(j);
				}
			}
		}
		for (int i : data.keySet()) {
			UserData user = data.get(i);
			Double stocks = user.getPortfolio().get(inst);
			if (result.equals("won")) {
				if (stocks != null && stocks > 0) {
					FormOrderBean order = new FormOrderBean();
					order.setInstrument(inst);
					order.setSide("sell");
					order.setPrice(1);
					order.setSize(stocks);
					addOrder(order, user);
				}
				if (stocks != null && stocks < 0) {
					FormOrderBean order = new FormOrderBean();
					order.setInstrument(inst);
					order.setSide("buy");
					order.setPrice(1);
					order.setSize(-stocks);
					addOrder(order, user);
				}
			} else {
				if (stocks > 0) {
					FormOrderBean order = new FormOrderBean();
					order.setInstrument(inst);
					order.setSide("sell");
					order.setPrice(0);
					order.setSize(stocks);
					addOrder(order, user);
				}
				if (stocks < 0) {
					FormOrderBean order = new FormOrderBean();
					order.setInstrument(inst);
					order.setSide("buy");
					order.setPrice(0);
					order.setSize(-stocks);
					addOrder(order, user);
				}
			}
		}
	}

}
