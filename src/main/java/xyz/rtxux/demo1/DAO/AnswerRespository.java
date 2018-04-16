package xyz.rtxux.demo1.DAO;

import org.springframework.data.repository.CrudRepository;
import xyz.rtxux.demo1.Model.Answer;

public interface AnswerRespository extends CrudRepository<Answer, Integer> {

}
