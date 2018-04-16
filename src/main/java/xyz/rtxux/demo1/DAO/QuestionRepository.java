package xyz.rtxux.demo1.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import xyz.rtxux.demo1.Model.Question;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query(nativeQuery = true, value = "select * from question ORDER BY RAND() limit ?1")
    List<Question> getRandomQuestion(int size);
}
