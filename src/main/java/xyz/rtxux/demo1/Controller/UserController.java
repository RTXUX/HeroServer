package xyz.rtxux.demo1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.rtxux.demo1.DAO.UserRepository;
import xyz.rtxux.demo1.Model.User;
import xyz.rtxux.demo1.ReturnModel.JsonUser;
import xyz.rtxux.demo1.ReturnModel.Login;
import xyz.rtxux.demo1.ReturnModel.UserInfo;
import xyz.rtxux.demo1.Utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public Login login() {
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            return new Login(1, 0, "");
        }
        User user = new User(UUID.randomUUID().toString(), null);
        user = userRepository.save(user);
        Login login = new Login(0, user.getId(), user.getToken());
        return login;
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Login login(@RequestBody UserInfo userInfo) {
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            return new Login(1, 0, "");
        }
        Optional<User> userOptional = userRepository.findUserByUsernameIgnoreCase(userInfo.getUsername());
        if (!userOptional.isPresent()) {
            return new Login(2, 0, "");
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(userInfo.getPassword())) {
            return new Login(2, 0, "");
        }
        user.setToken(UUID.randomUUID().toString());
        user = userRepository.save(user);
        return new Login(0, user.getId(), user.getToken());
    }
    @RequestMapping("/get_time")
    public Map<String, Object> getTime() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("time", Utils.getTimeBound());
        return response;
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody UserInfo userInfo) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findUserByUsernameIgnoreCase(userInfo.getUsername());
        if (userOptional.isPresent()) {
            response.put("status", 7);
            return response;
        }
        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user = userRepository.save(user);
        response.put("status", 0);
        return response;
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    public Map<String, Object> logout(@RequestBody JsonUser jsonUser) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findById(jsonUser.getUid());
        if (!userOptional.isPresent()) {
            response.put("status", 2);
            return response;
        }
        User user = userOptional.get();
        if (!user.getToken().equals(jsonUser.getToken())) {
            response.put("status", 2);
            return response;
        }
        user.setToken(null);
        user = userRepository.save(user);
        response.put("status", 0);
        return response;
    }

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.POST)
    public Login refresh(@RequestBody JsonUser jsonUser) {

        Optional<User> userOptional = userRepository.findById(jsonUser.getUid());
        if (!userOptional.isPresent()) {
            return new Login(2, 0, "");
        }
        User user = userOptional.get();
        if (!user.getToken().equals(jsonUser.getToken())) {
            return new Login(2, 0, "");
        }
        user.setToken(UUID.randomUUID().toString());
        user = userRepository.save(user);
        return new Login(0, user.getId(), user.getToken());

    }

}
