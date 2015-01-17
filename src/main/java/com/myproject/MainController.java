package com.myproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@Controller
public class MainController{

	@Autowired
	DaoInterface dao;

	@RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
	public String getMain(Model model){
		//model.addAttribute("list", dao.findByLogin("roman"));
		model.addAttribute("list", new User());
		return "index";
	}

	@RequestMapping(value="/admin**", method = RequestMethod.GET)
	public String getAdmin(Model model){
		model.addAttribute("title", "Spring Security + Hibernate Example");
		model.addAttribute("message", "This page is for ROLE_ADMIN only!");
		return "admin";
	}

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getAllUsers(Model model, @RequestParam(value ="user", required = false)String user){
		model.addAttribute("all", dao.list());
		return "list";
	}

	@RequestMapping(value="/list/{user}", method = RequestMethod.GET)
	public String getCertainUser(Model model, @PathVariable String user){
		model.addAttribute("user", dao.findByLogin(user));
		return "profile";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
    public String getLogin(Model model, @RequestParam(value="login_error",required = false)String login_error, HttpServletRequest request){
        String message = "";
        
        if(login_error != null){
			model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        
        model.addAttribute("message", message);
        return "login";
    }

    private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	@RequestMapping(value="/rest/{id}", method = RequestMethod.GET)
	public String getRest(@PathVariable String id, Model model){
		if(id.equals(null))
			dao.updateUser(new User("vualia"));
		else
			model.addAttribute("user",dao.findById(id));
		return "rest";
	}

	@RequestMapping(value="/rest/create/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody User createUser(@RequestBody User user){
		dao.saveUser(user);
		return user;
	}

	@RequestMapping(value="/rest/update/{id}", method = RequestMethod.PUT)
	public @ResponseBody User putUser(@RequestBody User user, @PathVariable String id){
		dao.updateUser(user);
		return user;
	}

}