package xyz.rtxux.demo1.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.rtxux.demo1.Model.Prize;

import java.util.List;

@Repository
public interface PrizeRepository extends CrudRepository<Prize, Integer> {
    @Query("select p from Prize p where p.amount>0")
    List<Prize> findAllAvailablePrize();
}
