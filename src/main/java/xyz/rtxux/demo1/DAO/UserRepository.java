package xyz.rtxux.demo1.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.rtxux.demo1.Model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


}
