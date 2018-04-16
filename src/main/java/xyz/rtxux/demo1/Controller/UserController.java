package xyz.rtxux.demo1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.rtxux.demo1.DAO.UserRepository;
import xyz.rtxux.demo1.Model.User;
import xyz.rtxux.demo1.ReturnModel.Login;
import xyz.rtxux.demo1.Utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/auth/login")
    public Login login() {
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            return new Login(1, 0, "");
        }
        User user = new User(UUID.randomUUID().toString(), null);
        user = userRepository.save(user);
        Login login = new Login(0, user.getId(), user.getToken());
        return login;
    }

    @RequestMapping("/get_time")
    public Map<String, Object> getTime() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("time", Utils.getTimeBound());
        return response;
    }
}
