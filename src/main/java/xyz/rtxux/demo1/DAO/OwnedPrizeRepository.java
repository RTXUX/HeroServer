package xyz.rtxux.demo1.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.rtxux.demo1.Model.OwnedPrize;

import java.util.List;

@Repository
public interface OwnedPrizeRepository extends CrudRepository<OwnedPrize, Integer> {

    List<OwnedPrize> findOwnedPrizeByUid(int uid);
}
