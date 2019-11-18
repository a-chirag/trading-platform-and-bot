package com.inautix.automation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inautix.utils.OrderBook;
import com.inautix.utils.UserData;
import com.inautix.utils.Wrapper;
import com.inautix.utils.WrapperImplFound;

public class TradingUtils {

	public static ArrayList<LinkedHashMap<String, Object>> listOfAllUsers() {
		try {
			URL url = new URL("http://localhost:8080/book/v1/users");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			return (ArrayList<LinkedHashMap<String, Object>>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static LinkedHashMap<String, Object> getUser(int userId) {
		try {
			URL url = new URL("http://localhost:8080/book/v1/users/" + userId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			// UserData user= new
			// ObjectMapper().readValue(data.getData().toString(),
			// UserData.class);
			conn.disconnect();
			return (LinkedHashMap<String, Object>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static LinkedHashMap<String, Object> ordersUser(int userId) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL("http://localhost:8080/book/v1/users/" + userId + "/orders");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			return (LinkedHashMap<String, Object>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static LinkedHashMap<String, Object> addOrder(OrderData d, int userId) {
		try {
			URL url = new URL("http://localhost:8080/book/v1/users/" + userId + "/orders");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{ \"instrument\": \""+d.getInstrument()+"\", \"size\": "+d.getSize()+", \"side\": \""+d.getSide()+"\", \"price\": "+d.getPrice()+" }";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			return (LinkedHashMap<String, Object>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ResponseEntity<Wrapper> deleteOrder(int orderId, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<LinkedHashMap<String, Object>> listOfOrderBook() {
		try {
			URL url = new URL("http://localhost:8080/book/v1/orderbooks/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			return (ArrayList<LinkedHashMap<String, Object>>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static LinkedHashMap<String, LinkedHashMap<String, Object>> getOrderBook(String inst) {
		try {
			URL url = new URL("http://localhost:8080/book/v1/orderbooks/" +inst);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			conn.disconnect();
			return (LinkedHashMap<String, LinkedHashMap<String, Object>>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static LinkedHashMap<String, Object> getOrderBookPrice(String inst) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL("http://localhost:8080/book/v1/orderbooks/" +inst +"/prices");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			return (LinkedHashMap<String, Object>) data.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public static Double getOrderBookCurrentPrice(String inst) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL("http://localhost:8080/book/v1/orderbooks/" +inst +"/prices");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			conn.getContent();
			String output;
			WrapperImplFound data = null;
			while ((output = br.readLine()) != null) {
				data = new ObjectMapper().readValue(output, WrapperImplFound.class);
			}
			System.out.println(data.getMetaData());
			conn.disconnect();
			Double curPrice = null;
			for( String s :((LinkedHashMap<String, Object>) data.getData()).keySet()){
				curPrice=(Double) ((LinkedHashMap<String, Object>) data.getData()).get(s);
			}
			return curPrice;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
