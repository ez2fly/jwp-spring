package next.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import next.CannotDeleteException;
import next.LoginUser;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

@Controller
@RequestMapping("/qna")
public class QnAController {
	private static final Logger log = LoggerFactory.getLogger(QnAController.class);
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	private QnaService qnaService = QnaService.getInstance();
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show(@PathVariable String id, Model model) {
		long questionId = Long.parseLong(id);
		
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "/qna/show";
	}
	
	@RequestMapping(value="/new", method = RequestMethod.GET)
	public String form(@LoginUser User user, Model model) {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		model.addAttribute("question", new Question());
		return "/qna/form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@LoginUser User user, @RequestParam String title, @RequestParam String contents) {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
    	Question question = new Question(user.getUserId(), title, contents);
    	questionDao.insert(question);
		return "redirect:/";
	}
	
	@RequestMapping(value="/{id}/edit", method = RequestMethod.GET)
	public String edit(@LoginUser User user, @PathVariable String id, Model model) {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		
		long questionId = Long.parseLong(id);
		Question question = questionDao.findById(questionId);
		if (!question.isSameUser(user)) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		model.addAttribute("question", question);
		return "/qna/update";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@LoginUser User user, @PathVariable String id, @RequestParam String title, @RequestParam String contents) {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		
		long questionId = Long.parseLong(id);
		Question question = questionDao.findById(questionId);
		if (!question.isSameUser(user)) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		
		Question newQuestion = new Question(question.getWriter(), title, contents);
		question.update(newQuestion);
		questionDao.update(question);
		return "redirect:/";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public String destroy(@LoginUser User user, @PathVariable String id, Model model) {
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		
		long questionId = Long.parseLong(id);
		try {
			qnaService.deleteQuestion(questionId, user);
			return "redirect:/";
		} catch (CannotDeleteException e) {
			model.addAttribute("question", qnaService.findById(questionId));
			model.addAttribute("answers", qnaService.findAllByQuestionId(questionId));
			model.addAttribute("errorMessage", e.getMessage());
			return "/qna/show";
		}
	}
	
}
