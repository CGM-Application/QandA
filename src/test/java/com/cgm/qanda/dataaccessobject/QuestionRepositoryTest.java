package com.cgm.qanda.dataaccessobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionRepositoryTest {

	@Mock
	QuestionRepository repository;

	@Test
	public void TestRepositoryInjected() {
		assertNotNull(repository);
	}
	//Modified test to validate single answer  --Mayank
	@Test
	public void testSimpleQnAEntitySave() {
		String qus = "question1";
		Question question = createUserEntity(qus, new String[] { "A single answer." });
		repository.save(question);
		Optional<Question> mocked = Optional.of(question);
		when(repository.findByQuestion(qus)).thenReturn(mocked);
		Optional<Question> q = repository.findByQuestion(qus);
		Question qq = q.get();
		assertNotNull(qq);
		assertEquals("question1", qq.getQuestion());
		repository.flush();
	}
	//Added new Test to verify the multiple answers --Mayank
	@Test
	public void testEntitySaveWithMultipleAnswer() {
		String qus = "question2";
		String[] input = { "answer1", "answer2", "answer3", "answer4", "answer5", "answer6" };
		Question question = createUserEntity(qus, input);
		repository.save(question);
		Optional<Question> mocked = Optional.of(question);
		when(repository.findByQuestion(qus)).thenReturn(mocked);
		Optional<Question> qusOptional = repository.findByQuestion(qus);
		assertNotNull(qusOptional);
		List<String> answers = getAnswers(qusOptional);
		assertNotNull(answers);
		// validate answers size
		assertEquals(input.length, answers.size());
		Set<String> expectedSet = new HashSet<>(Arrays.asList(input));
		// validate individual answers
		for (String ans : answers) {
			assertTrue(expectedSet.contains(ans));
		}
		repository.flush();
	}
	//Added new test to verify multiple answer with duplicate answer --Mayank
	@Test
	public void testEntitySaveWithMultipleAnswerWithDuplicate() {
		String qus = "question3";
		// add few duplicate answers
		String[] input = { "answer1", "answer1", "answer2", "answer2", "answer3", "answer4", "answer5", "answer6" };
		Question question = createUserEntity(qus, input);
		repository.save(question);
		Optional<Question> mocked = Optional.of(question);
		when(repository.findByQuestion(qus)).thenReturn(mocked);
		Optional<Question> qusOptional = repository.findByQuestion(qus);
		assertNotNull(qusOptional);
		List<String> answers = getAnswers(qusOptional);
		assertNotNull(answers);
		Set<String> expectedSet = new HashSet<>(Arrays.asList(input));
		// validate answers size
		assertEquals(expectedSet.size(), 6);
		// validate individual answers
		for (String ans : answers) {
			assertTrue(expectedSet.contains(ans));
		}
		repository.flush();
	}

	private Question createUserEntity(String qus, String[] arr) {
		Question question = new Question();
		question.setQuestion(qus);
		Set<Answer> set = new HashSet<>();
		// add 4 answers to this qus
		for (String ans : arr) {
			Answer answer = new Answer();
			answer.setAnswer(ans);
			set.add(answer);
		}
		question.setAnswers(set);
		return question;
	}
	//Method to add multiple answers --Mayank	
	List<String> getAnswers(Optional<Question> question) {
		List<String> list = new ArrayList<>();
		if (question.isPresent()) {
			Question q = question.get();
			List<Answer> answers = q.getAnswers().stream().collect(Collectors.toList());
			for (Answer answer : answers) {
				list.add(answer.getAnswer());
			}
		}
		return list;
	}
}
