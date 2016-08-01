package next.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.jdbc.DataAccessException;
import core.mvc.ModelAndView;
import next.CannotDeleteException;
import next.LoginUser;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

@RestController
@RequestMapping("/api/qna")
public class ApiQnAController {
	private static final Logger log = LoggerFactory.getLogger(ApiQnAController.class);
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	private QnaService qnaService = QnaService.getInstance();
	
	@RequestMapping("/list")
	public List<Question> list() {
		return questionDao.findAll();
	}
	
	@RequestMapping(value="/deleteQuestion", method = RequestMethod.POST)
	public Map<String, Object> deleteQuestion(@LoginUser User user, @RequestParam(value="questionId") String id, Model model) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (user == null) {
			ret.put("result", Result.fail("Login is required"));
			return ret;
		}
		
		long questionId = Long.parseLong(id);
		try {
			qnaService.deleteQuestion(questionId, user);
			ret.put("result", Result.ok());
		} catch (CannotDeleteException e) {
			ret.put("result", Result.fail(e.getMessage()));
		}
		return ret;
	}
	
	@RequestMapping(value="/addAnswer", method = RequestMethod.POST)
	public Map<String, Object> addAnswer(@LoginUser User user, @RequestParam(value="questionId") String id, @RequestParam(value="contents") String contents) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (user == null) {
			ret.put("result", Result.fail("Login is required"));
			return ret;
		}
    	
		Answer answer = new Answer(user.getUserId(), 
									contents, 
									Long.parseLong(id));
		log.debug("answer : {}", answer);
		
		Answer savedAnswer = answerDao.insert(answer);
		questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());
		
		ret.put("answer", savedAnswer);
		ret.put("result", Result.ok());
		return ret;
	}
	
	@RequestMapping(value="/deleteAnswer", method = RequestMethod.POST)
	public Map<String, Object> deleteAnswer(@RequestParam(value="answerId") String id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Long answerId = Long.parseLong(id);
        
		try {
			answerDao.delete(answerId);
			ret.put("result", Result.ok());
		} catch (DataAccessException e) {
			ret.put("result", Result.fail(e.getMessage()));
		}
		return ret;
	}
}
