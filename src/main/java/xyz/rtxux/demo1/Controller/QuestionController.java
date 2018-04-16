package xyz.rtxux.demo1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.rtxux.demo1.DAO.AnswerRespository;
import xyz.rtxux.demo1.DAO.PrizeRepository;
import xyz.rtxux.demo1.DAO.QuestionRepository;
import xyz.rtxux.demo1.DAO.UserRepository;
import xyz.rtxux.demo1.Model.Question;
import xyz.rtxux.demo1.Model.User;
import xyz.rtxux.demo1.ReturnModel.*;
import xyz.rtxux.demo1.Utils.Utils;

import java.util.*;

@RestController
public class QuestionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRespository answerRespository;
    @Autowired
    private PrizeRepository prizeRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/get_question")
    public Map<String, Object> getQuestion(@RequestBody JsonUser juser) {
        Map<String, Object> response = new HashMap<>();
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            response.put("status", 1);
            return response;
        }
        Optional<User> userOptional = userRepository.findById(juser.getUid());
        User user;
        if (!userOptional.isPresent()) {
            response.put("status", 2);
            return response;
        }
        user = userOptional.get();
        if (!user.getToken().equals(juser.getToken())) {
            response.put("status", 2);
            return response;
        }
        if (prizeRepository.findAllAvailablePrize().size() == 0) {
            response.put("status", 5);
            return response;
        }
        List<Question> questions;
        if (Utils.getCurrentTimestamp() - user.getLastTimestamp() > 60) {
            user.getCurrentQuestion().clear();
            user = userRepository.save(user);
        }
        if (!user.getCurrentQuestion().isEmpty()) {
            questions = user.getCurrentQuestion();

        } else {
            questions = questionRepository.getRandomQuestion(5);
            questions.add(questionRepository.findById(175).get());
            /*for (Question question:questions) {
                if (user.getAnsweredQuestions().contains(question)) {
                    questions.remove(question);
                }
            }*/
            ListIterator<Question> iter = questions.listIterator();
            while (iter.hasNext()) {
                if (user.getAnsweredQuestions().contains(iter.next())) {
                    iter.remove();
                }
            }
            while (questions.size() != 6) {
                List<Question> pendingQuestions = questionRepository.getRandomQuestion(6 - questions.size());
                for (Question question : pendingQuestions) {
                    if (!user.getAnsweredQuestions().contains(question)) {
                        questions.add(question);
                    }
                }

            }
            user.setCurrentQuestion(questions);
            user.setLastTimestamp(Utils.getCurrentTimestamp());
        }
        List<JsonQuestion> jsonQuestions = new LinkedList<>();
        for (Question question : questions) {
            JsonQuestion jsonQuestion = new JsonQuestion();
            jsonQuestion.setQid(question.getId());
            jsonQuestion.setDescription(question.getDescription());
            jsonQuestion.setAnswers(question.getAnswers());
            jsonQuestions.add(jsonQuestion);
        }

        user = userRepository.save(user);
        response.put("status", 0);
        response.put("timestamp", user.getLastTimestamp());
        response.put("question", jsonQuestions);
        return response;
    }

    @RequestMapping(value = "/post_answer", method = RequestMethod.POST)
    Map<String, Object> answer(@RequestBody JsonAnswer jsonAnswer) {
        Map<String, Object> response = new HashMap<>();
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            response.put("status", 1);
            return response;
        }
        Optional<User> userOptional = userRepository.findById(jsonAnswer.getUid());
        User user;
        if (!userOptional.isPresent()) {
            response.put("status", 2);
            return response;
        }
        user = userOptional.get();
        if (!user.getToken().equals(jsonAnswer.getToken())) {
            response.put("status", 2);
            return response;
        }
        List<Question> questions = user.getCurrentQuestion();
        if (questions.isEmpty()) {
            response.put("status", 6);
            return response;
        }
        if (jsonAnswer.getAnswers().size() != 6) {
            response.put("status", 6);
            return response;
        }
        if (Utils.getCurrentTimestamp() - user.getLastTimestamp() > 60) {
            response.put("status", 6);
            user.getCurrentQuestion().clear();
            user = userRepository.save(user);
            return response;
        }
        List<Integer> correct = new LinkedList<>();
        List<IncorrectAnswer> incorrect = new LinkedList<>();
        for (AnswerToQuestion a2q : jsonAnswer.getAnswers()) {
            int flag = 0;
            for (Question question : questions) {
                if (question.getId() == a2q.getQuestion_id()) {
                    flag = 1;
                    if (a2q.getAnswer_id() == question.getCorrectAnswer().getAid()) {
                        correct.add(a2q.getQuestion_id());
                        user.getAnsweredQuestions().add(question);
                    } else {
                        incorrect.add(new IncorrectAnswer(a2q.getQuestion_id(), question.getCorrectAnswer().getAid()));
                    }
                    break;
                }
            }
            if (flag == 0) {
                response.put("status", 6);
                return response;
            }
        }
        if (incorrect.isEmpty()) {
            user.setCapableOfPrize(user.getCapableOfPrize() + 1);
        }
        questions.clear();
        userRepository.save(user);
        response.put("status", 0);
        response.put("correct", correct);
        response.put("incorrect", incorrect);
        return response;
    }


}
