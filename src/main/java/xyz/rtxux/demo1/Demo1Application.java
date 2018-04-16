package xyz.rtxux.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.rtxux.demo1.DAO.UserRepository;

@SpringBootApplication
@EntityScan("xyz.rtxux.demo1.Model")
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }
}
