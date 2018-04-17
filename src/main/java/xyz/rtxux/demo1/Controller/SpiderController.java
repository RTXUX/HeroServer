package xyz.rtxux.demo1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.rtxux.demo1.DAO.AnswerRespository;
import xyz.rtxux.demo1.DAO.QuestionRepository;
import xyz.rtxux.demo1.Model.Answer;
import xyz.rtxux.demo1.Model.Question;
import xyz.rtxux.demo1.Utils.KXSpider;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class SpiderController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRespository answerRespository;

    //@RequestMapping(value = "/internal/spider", method = RequestMethod.POST)
    public String spider(@RequestBody String str) throws Exception {
        Pattern pattern = Pattern.compile("([0-9]+)、(.*)\\s*A：(.*)\\s*B：(.*)\\s*C：(.*)\\s*D：(.*)\\s*答案：(.*)", Pattern.MULTILINE);

        FileInputStream fis = new FileInputStream("/home/rtxux/Q.txt");
        FileChannel channel = fis.getChannel();
        ByteBuffer bbuf = channel.map(FileChannel.MapMode.READ_ONLY, 0, (int) channel.size());
        CharBuffer cbuf = Charset.forName("UTF-8").newDecoder().decode(bbuf);

        Matcher matcher = pattern.matcher(cbuf);

        while (matcher.find()) {
            Question question = new Question();
            question.setId(Integer.parseInt(matcher.group(1)));
            question.setDescription(matcher.group(2));
            String ans = matcher.group(7);
            int ansIndex = 0;
            if (ans.equals("A")) {
                ansIndex = 1;
            } else if (ans.equals("B")) {
                ansIndex = 2;
            } else if (ans.equals("C")) {
                ansIndex = 3;
            } else if (ans.equals("D")) {
                ansIndex = 4;
            } else {
                continue;
            }
            questionRepository.save(question);
            List<Answer> answers = new LinkedList<>();
            for (int i = 1; i <= 4; i++) {
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setDescription(matcher.group(i + 2));
                if (i == ansIndex) {
                    question.setCorrectAnswer(answer);
                }
                answers.add(answer);
                answerRespository.save(answer);
            }
            questionRepository.save(question);
            for (Answer answer : answers) {
                answer.setQuestion(question);
                answerRespository.save(answer);
            }
        }
        return "Fuck";
    }

    //@RequestMapping(value = "/internal/spider2", method = RequestMethod.POST)
    public String spider2(@RequestBody String str) {
        KXSpider kxSpider = new KXSpider();
        for (int i = 1; i < 7; i++) {
            kxSpider.getPage(i);
        }
        Pattern pattern1 = Pattern.compile("([0-9]+)、(.*)【\\s(.)\\s】\\s*(.*)");
        Pattern pattern2 = Pattern.compile("[A-Z]\\s、(.*?)\\s");
        Matcher matcher = pattern1.matcher(kxSpider.sb);
        while (matcher.find()) {
            Question question = new Question();
            question.setDescription(matcher.group(2));
            String ans = matcher.group(3);
            int ansIndex = 0, index = 0;
            if (ans.equals("A")) {
                ansIndex = 1;
            } else if (ans.equals("B")) {
                ansIndex = 2;
            } else if (ans.equals("C")) {
                ansIndex = 3;
            } else if (ans.equals("D")) {
                ansIndex = 4;
            } else {
                continue;
            }
            question.setId(Integer.parseInt(matcher.group(1)) + 10000);
            question = questionRepository.save(question);
            String answerString = matcher.group(4);
            Matcher matcher1 = pattern2.matcher(answerString);
            while (matcher1.find()) {
                index++;
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setDescription(matcher1.group(1));
                if (index == ansIndex) {
                    question.setCorrectAnswer(answer);
                }
                answer = answerRespository.save(answer);
            }
            question = questionRepository.save(question);
        }
        return "Shit";
    }
}
