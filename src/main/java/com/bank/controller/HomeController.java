package com.bank.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bank.dao.RoleDao;
import com.bank.domain.PrimaryAccount;
import com.bank.domain.SavingsAccount;
import com.bank.domain.User;
import com.bank.domain.security.UserRole;
import com.bank.service.LoginService;
import com.bank.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private LoginService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private RoleDao roleDao;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
    public String index() {
        return "index";
    }
	
	@RequestMapping("/back")
    public String back() {
        return "afteradmin";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user,  Model model) {

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
        	 Set<UserRole> userRoles = new HashSet<>();
             userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

            userService.createUser(user, userRoles);

            return "redirect:/";
        }
    }
	
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	@GetMapping("/admin/adminlogin")
	public String admin() {
		return "adminlogin";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	@RequestMapping("/admin/adminlogin")
	public ModelAndView adminlogin(Model model) {
	ModelAndView mav=new ModelAndView("adminlogin");
	return mav;
	}
	
	@RequestMapping(value="/admin/adminlogin" , method=RequestMethod.POST)
	public String ShowWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password)
	{
	boolean isValidUser = service.validateUser(name, password);
	if(!isValidUser) {
	model.put("errorMessage","Invalid Credentials" );
	
	return "adminlogin";
	}
	model.put("name",name);
	model.put("password",password);
	
	return "afteradmin";

	 }
	
	@RequestMapping("/afteradmin")
	public String adminloginpage() {
	
	return "afteradmin";
	}

	 @GetMapping("/userPage")
	    public String listProducts(Model model) {
	        
	        return "list_user_page";
	    }
	   
	
	
}
