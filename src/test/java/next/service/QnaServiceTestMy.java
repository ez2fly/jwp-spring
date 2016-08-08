package next.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import next.dao.AnswerDao;
import next.dao.QuestionDao;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTestMy {
	@Mock
	private QuestionDao questionDao;
	@Mock
	private AnswerDao answerDao;
	@InjectMocks
	private QnaService qnaService;
	
	@Test (expected = EmptyResultDataAccessException.class)
	public void deleteQuestion_없는_질문() throws Exception {
		
	}
	
	
}
