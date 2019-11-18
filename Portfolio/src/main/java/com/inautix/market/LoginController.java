package com.inautix.market;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.inautix.utils.FormOrderBean;
import com.inautix.utils.UserData;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller
@SessionAttributes({ "user", "userdata" })
public class LoginController {
	@Autowired
	LoginService ls;
	@Autowired
	MarketService ms;
	private boolean isLoggedIn(HttpSession session){
		if(session.getAttribute("user")==null)
			return false;
		else
			return true;
	}
	@PostConstruct
	void init(){
		System.out.println("hello");
		ms.putUsers(ls.getUsers());
	}
	@RequestMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login", "command", new User());
	}

	@RequestMapping("registeruser")
	public String register(@RequestParam("username")String username, @RequestParam("password")String password){
		ms.addUser(ls.register(username,password));
		return "registered";
		
	}
	@RequestMapping(value = "valid", method = RequestMethod.POST)
	public String valid(@ModelAttribute("user2") User user, Model model) {
		model.addAttribute("user", user);
		int uid = ls.valid(user);
		if (uid == 0)
			return "invalid";
		else {
			model.addAttribute("userdata", ms.getUserData(uid));
			model.addAttribute("user", user);
			System.out.println(ms.getUserData(uid));
			return "home";
		}
	}

	@RequestMapping("home")
	public String home(HttpSession session) {
		return "home";
	}
	@RequestMapping("order")
	public String order(HttpSession session) {
		if(!isLoggedIn(session))
			return "notLogin";
			else
		return "order";
	}

	@RequestMapping(value = "addorder")
	public String addOrder(@RequestParam("size")String size,@RequestParam("price")String price,@RequestParam("side")String side,@RequestParam("instrument")String inst,
			@ModelAttribute("userdata") UserData userData) {
		System.out.println("in addorder");
		FormOrderBean order= new FormOrderBean();
		order.setPrice(Double.parseDouble(price));
		order.setSide(side);
		order.setSize(Double.parseDouble(size));
		order.setInstrument(inst);
		System.out.println(order);
		ms.addOrder(order, userData);
		return "redirect:order.do";
	}

	@RequestMapping("pending")
	public String viewOrder(HttpSession session) {
		if(!isLoggedIn(session))
		return "notLogin";
		else
			return "pending";
	}

	@RequestMapping("portfolio")
	public String portfolioOrder(HttpSession session) {
		if(!isLoggedIn(session))
			return "notLogin";
			else
		return "portfolio";
	}

	@RequestMapping("orderbook")
	public String orderBook(@RequestParam("inst") String instrument, Model model) {
		model.addAttribute("orderbook", ms.getOrderBook(instrument));
		return "orderbook";
	}
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,SessionStatus status) {
		status.setComplete();
		return "notLogin";
	}
	@RequestMapping(value = "loginuser", method = RequestMethod.GET)
	public String loginuser(@RequestParam("username") String username,@RequestParam("password")String password, Model model) {
		User user = new User(username,password);
		int uid = ls.valid(user);
		if (uid == 0)
			return "invalid";
		else {
			model.addAttribute("userdata", ms.getUserData(uid));
			model.addAttribute("user", user);
			System.out.println(ms.getUserData(uid));
			return "home";
		}
	}
	@RequestMapping(value="validAdmin/{inst}",method= RequestMethod.GET)
	public String validAdmin(Model model,@PathVariable("inst")String inst){
		model.addAttribute("instrument", ms.getOrderBook(inst));
		return "admin";
	}
	
@RequestMapping(value="postResult",method=RequestMethod.POST)
public String postResult(@RequestParam("username")String username,@RequestParam("password")String password, @RequestParam("result")String result, @RequestParam("instrument")String inst, Model model){
	User user = new User(username,password);
	model.addAttribute("user", user);
	int uid = ls.valid(user);
	if(uid==1){
		System.out.println(result);
		ms.postResult(result,inst);
		return "posted";
	}
	else
		return "invalid";
}
}
