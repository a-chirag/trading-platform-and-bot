package com.inautix.market;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inautix.utils.FormOrderBean;
import com.inautix.utils.Order;
import com.inautix.utils.OrderBook;
import com.inautix.utils.UserData;
import com.inautix.wrapper.BookError;
import com.inautix.wrapper.Data;
import com.inautix.wrapper.MetaData;
import com.inautix.wrapper.Wrapper;
import com.inautix.wrapper.WrapperImplError;
import com.inautix.wrapper.WrapperImplFound;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	MarketService ms;

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> listOfAllUsers() {

		HttpHeaders headers = new HttpHeaders();
		List<UserData> users = ms.getUsers();
//		List<UserData> users = null;

		if (users == null) {
			
			return new ResponseEntity<Wrapper>(fetchFailed("No Users Found",BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Number Of Records Found", String.valueOf(users.size()));
		
		return new ResponseEntity<Wrapper>(found("Users Found:"+users.size(),users), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> getUser(@PathVariable("id") int userId) {
		UserData user= ms.getUserData(userId);
		if (user == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("No such User Exists", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Wrapper>(found("User Found",user), HttpStatus.OK);
	}
	@RequestMapping(value = "/users/{id}/orders", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> ordersUser(@PathVariable("id") int userId) {
		UserData user= ms.getUserData(userId);
		if (user == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("No such user Found", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Wrapper>(found("User's Orders:",new ArrayList(user.getPendingOrders().values())), HttpStatus.OK);
	}
	@RequestMapping(value = "/users/{id}/orders", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Wrapper> addOrder(@RequestBody FormOrderBean bean,@PathVariable("id") int userId) {
		System.out.println(bean.getPrice());
		UserData user= ms.getUserData(userId);
		if(user==null){
			return new ResponseEntity<Wrapper>(fetchFailed("No Such user exists", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		Order order = ms.addOrder(bean, user);
		if (order == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("Cannot place the order", BookError.INVALIDDATA),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Wrapper>(found("Order Placed",order), HttpStatus.OK);
	}
	@RequestMapping(value = "/users/{id}/orders/{oid}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Wrapper> deleteOrder(@PathVariable("oid")int orderId,@PathVariable("id") int userId) {
		UserData user= ms.getUserData(userId);
		if(user==null){
			return new ResponseEntity<Wrapper>(fetchFailed("No Such user exists", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		Order order = user.removeOrder(orderId);
		if (order == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("Cannot Delete the order", BookError.INVALIDDATA),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Wrapper>(found("Order Deleted",order), HttpStatus.OK);
	}
	@RequestMapping(value = "/orderbooks", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> listOfOrderBook() {
		HttpHeaders headers = new HttpHeaders();
		List<OrderBook> orderbooks = ms.getOrderBooks();

		if (orderbooks == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("No OrderBooks there", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Number Of Records Found", String.valueOf(orderbooks.size()));
		return new ResponseEntity<Wrapper>(found("Orderbooks fetched",orderbooks), headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/orderbooks/{instrument}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> getOrderBook(@PathVariable("instrument") String inst) {
		HttpHeaders headers = new HttpHeaders();
		OrderBook orderbook = ms.getOrderBook(inst);

		if (orderbook == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("No OrderBook found", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Record Found","True");
		return new ResponseEntity<Wrapper>(found("Orderbook fetched",orderbook),  headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/orderbooks", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Wrapper> putOrderBook(@RequestBody OrderBook book) {
		HttpHeaders headers = new HttpHeaders();

		OrderBook boo = ms.addBook(book);
		if (boo== null) {
			return new ResponseEntity<Wrapper>(fetchFailed("Cannot Create Orderbook", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Book Created","True");
		return new ResponseEntity<Wrapper>(found("Orderbook Created",boo), headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/orderbooks", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Wrapper> changeOrderBook(@RequestBody OrderBook book) {
		HttpHeaders headers = new HttpHeaders();

		OrderBook boo = ms.changeBook(book);
		if (boo== null) {
			return new ResponseEntity<Wrapper>(fetchFailed("Cannot Change Orderbook", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Book Modified","True");
		return new ResponseEntity<Wrapper>(found("Orderbook Modified",boo), headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/orderbooks/{instrument}/prices", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Wrapper> getOrderBookPrice(@PathVariable("instrument") String inst) {
		HttpHeaders headers = new HttpHeaders();
		OrderBook orderbook = ms.getOrderBook(inst);

		if (orderbook == null) {
			return new ResponseEntity<Wrapper>(fetchFailed("Cannot find Orderbook", BookError.NOTFOUND),HttpStatus.NOT_FOUND);
		}
		headers.add("Record Found","True");
		return new ResponseEntity<Wrapper>(found("Orderbook Prices",orderbook.getPrices()), headers, HttpStatus.OK);
	}
	
	private Wrapper fetchFailed(String msg, BookError error){
		Wrapper result = new WrapperImplError();
		MetaData meta = new MetaData();
		meta.setMessage(msg);
		meta.setStatus(false);
		result.setMetaData(meta);
		result.setError(error);
		return result;
	}
	private Wrapper found(String msg,Object object){
		Wrapper result = new WrapperImplFound();
		MetaData meta = new MetaData();
		meta.setMessage(msg);
		meta.setStatus(true);
		result.setMetaData(meta);
		Data data = new Data();
		data.setData(object);
		result.setData(object);
		return result;
	}
}
