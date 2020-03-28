package com.cgm.qanda.service;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataaccessobject.QuestionRepository;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionAnswerServiceImplTest {

	@Autowired
	QuestionAnswerService service;

	@Mock
	QuestionRepository repo;

	@Before
	public void setup() {
		Question question = createQuestionEntity();
		repo.save(question);
	}

	private Question createQuestionEntity() {
		Question question = new Question();
		question.setQuestion("question1");
		Answer answer = new Answer();
		answer.setAnswer("answer1");
		Set<Answer> set = new HashSet<>();
		set.add(answer);
		return question;
	}

	// Method to add question and answer in repo --Mayank
	private Question createUserEntity(String qus, String[] arr) {
		Question question = new Question();
		question.setQuestion(qus);
		Set<Answer> set = new HashSet<>();
		// add answers to this qus
		for (String ans : arr) {
			Answer answer = new Answer();
			answer.setAnswer(ans);
			set.add(answer);
		}
		question.setAnswers(set);
		return question;
	}

	@Test
	public void testGetAnswers() {
		Question q = createQuestionEntity();
		Mockito.when(repo.findByQuestion("question1")).thenReturn(Optional.ofNullable(q));
		List<String> answers = service.getAnswers("question1");
		assertNotNull(answers);
		assertEquals(1, answers.size());
	}

	// Method to add multiple answers --Mayank
	@Test
	public void addQuestionWithMultipleAnswersTest() {
		String qus = "question";
		String[] input = { "answer1", "answer2", "answer3", "answer4", "answer5", "answer6" };
		Question q = createUserEntity(qus, input);
		q.setQuestion(qus);
		Mockito.when(repo.save(q)).thenReturn(q);
		Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
		Set<String> expectedSet = new HashSet<>(Arrays.asList(input));
		StringBuilder answersCombined = new StringBuilder();
		for (String ans : expectedSet) {
			answersCombined.append(ans).append("\"");
		}
		service.addQuestion(qus, answersCombined.toString());
		List<String> answers = service.getAnswers(qus);
		assertNotNull(answers);

		// validate answers size
		assertEquals(expectedSet.size(), answers.size());
		// validate individual answers
		for (String ans : answers) {
			assertTrue(expectedSet.contains(ans));
		}
	}

	// method to add duplicate answer ---Mayank
	@Test
	public void addQuestionWithMultipleAnswersWithDuplicatesTest() {
		String qus = "question";
		String[] input = { "answer1", "answer1", "answer2", "answer2", "answer3", "answer4", "answer5", "answer6" };
		Question q = createUserEntity(qus, input);
		q.setQuestion(qus);
		Mockito.when(repo.save(q)).thenReturn(q);
		Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
		Set<String> expectedSet = new HashSet<>(Arrays.asList(input));
		StringBuilder answersCombined = new StringBuilder();
		for (String ans : expectedSet) {
			answersCombined.append(ans).append("\"");
		}
		service.addQuestion(qus, answersCombined.toString());
		List<String> answers = service.getAnswers(qus);
		assertNotNull(answers);

		// validate answers size
		assertEquals(expectedSet.size(), answers.size());
		// validate individual answers
		for (String ans : answers) {
			assertTrue(expectedSet.contains(ans));
		}
	}
}
