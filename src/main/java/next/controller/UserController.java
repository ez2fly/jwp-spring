package next.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import next.LoginUser;
import next.dao.UserDao;
import next.model.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private UserDao userDao = UserDao.getInstance();

	@RequestMapping(method = RequestMethod.GET)
	public String index(@LoginUser User user, Model model) throws Exception {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		model.addAttribute("users", userDao.findAll());
		return "/user/list";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String form(Model model) throws Exception {
		model.addAttribute("user", new User());
		return "/user/form";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(User user) throws Exception {
		userDao.insert(user);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() throws Exception {
		return "/user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, @RequestParam String userId, @RequestParam String password) throws Exception {
		User user = userDao.findByUserId(userId);
		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}
		
		if (user.matchPassword(password)) {
			UserSessionUtils.setUser(session, user);
			return "redirect:/";
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다."); 
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) throws Exception {
		UserSessionUtils.remove(session);
		return "redirect:/";
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.GET)
	public String show(@PathVariable String userId, Model model) throws Exception {
        model.addAttribute("user", userDao.findByUserId(userId));
        return "/user/profile";
	}
	
	@RequestMapping(value="/{userId}/edit", method = RequestMethod.GET)
	public String edit(@LoginUser User curUser, @PathVariable String userId, Model model) throws Exception {
		User user = userDao.findByUserId(userId);
		if (!user.isSameUser(curUser)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    	model.addAttribute("user", user);
    	return "/user/updateForm";
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT)
	public String update(@LoginUser User curUser, User user) throws Exception {
		if (!user.isSameUser(curUser)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        
        log.debug("Update User : {}", user);
        userDao.update(user);
        return "redirect:/";
	}
	
}
