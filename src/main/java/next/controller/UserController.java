package next.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import core.mvc.JstlView;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.User;

@Controller
@RequestMapping(value="/users/*")
public class UserController {
	private UserDao userDao = UserDao.getInstance();
	//private QuestionDao questionDao = QuestionDao.getInstance();

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form() throws Exception {
		return "/user/form";
	}
	
	@RequestMapping(value = "loginForm", method = RequestMethod.GET)
	public String loginForm() throws Exception {
		return "/user/login";
	}
	
	@RequestMapping(value = "listUser", method = RequestMethod.GET)
	public ModelAndView listUser(HttpSession session) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return new ModelAndView("redirect:/users/loginForm");
		}
		
		ModelAndView mav = new ModelAndView("/user/list");
		mav.addObject("users", userDao.findAll());
		return mav;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam String userId, @RequestParam String password) throws Exception {
		User user = userDao.findByUserId(userId);
		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}
		
		if (user.matchPassword(password)) {
			//  세션 저장해야함
			return "redirect:/";
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다."); 
		}
	}
	
}
