package xyz.rtxux.demo1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.rtxux.demo1.DAO.OwnedPrizeRepository;
import xyz.rtxux.demo1.DAO.PrizeRepository;
import xyz.rtxux.demo1.DAO.UserRepository;
import xyz.rtxux.demo1.Model.OwnedPrize;
import xyz.rtxux.demo1.Model.Prize;
import xyz.rtxux.demo1.Model.User;
import xyz.rtxux.demo1.ReturnModel.JsonUser;
import xyz.rtxux.demo1.Utils.Utils;

import java.util.*;

@RestController
public class PrizeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrizeRepository prizeRepository;
    @Autowired
    private OwnedPrizeRepository ownedPrizeRepository;

    @RequestMapping(value = "/prize/list", method = RequestMethod.POST)
    private Map<String, Object> listPrize(@RequestBody JsonUser jsonUser) {
        Map<String, Object> response = new HashMap<>();
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            response.put("status", 1);
            return response;
        }
        Optional<User> userOptional = userRepository.findById(jsonUser.getUid());
        User user;
        if (!userOptional.isPresent()) {
            response.put("status", -1);
            return response;
        }
        user = userOptional.get();
        if (!user.getToken().equals(jsonUser.getToken())) {
            response.put("status", -1);
            return response;
        }
        response.put("status", 0);
        response.put("prize", prizeRepository.findAllAvailablePrize());
        return response;
    }

    @RequestMapping(value = "/prize/get", method = RequestMethod.POST)
    Map<String, Object> getPrize(@RequestBody JsonUser jsonUser) {
        Map<String, Object> response = new HashMap<>();
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            response.put("status", 1);
            return response;
        }
        Optional<User> userOptional = userRepository.findById(jsonUser.getUid());
        User user;
        if (!userOptional.isPresent()) {
            response.put("status", 2);
            return response;
        }
        user = userOptional.get();
        if (!user.getToken().equals(jsonUser.getToken())) {
            response.put("status", 2);
            return response;
        }
        if (!(user.getCapableOfPrize() > 0)) {
            response.put("status", 4);
            return response;
        }
        List<Prize> prizes = prizeRepository.findAllAvailablePrize();
        Prize prize = xyz.rtxux.demo1.Utils.Utils.shotPrize(prizes);
        prize.setAmount(prize.getAmount() - 1);

        user.setCapableOfPrize(user.getCapableOfPrize() - 1);
        userRepository.save(user);
        List<OwnedPrize> ownedPrizes = ownedPrizeRepository.findOwnedPrizeByUid(user.getId());
        OwnedPrize ownedPrize = null;
        for (OwnedPrize ownedPrize1 : ownedPrizes) {
            if (ownedPrize1.getPid() == prize.getId()) {
                ownedPrize = ownedPrize1;
            }
        }
        if (ownedPrize != null) {
            ownedPrize.setAmount(ownedPrize.getAmount() + 1);
        } else {
            ownedPrize = new OwnedPrize(user.getId(), prize.getId(), 1);
        }
        ownedPrizeRepository.save(ownedPrize);
        response.put("status", 0);
        response.put("id", prize.getId());
        response.put("description", prize.getDescription());
        return response;
    }


    @RequestMapping(value = "/prize/own", method = RequestMethod.POST)
    public Map<String, Object> getOwnedPrize(@RequestBody JsonUser jsonUser) {
        Map<String, Object> response = new HashMap<>();
        if (System.currentTimeMillis() / 1000L > Utils.getTimeBound()) {
            response.put("status", 1);
            return response;
        }
        Optional<User> userOptional = userRepository.findById(jsonUser.getUid());
        User user;
        if (!userOptional.isPresent()) {
            response.put("status", 2);
            return response;
        }
        user = userOptional.get();
        if (!user.getToken().equals(jsonUser.getToken())) {
            response.put("status", 2);
            return response;
        }
        List<OwnedPrize> ownedPrizes = ownedPrizeRepository.findOwnedPrizeByUid(jsonUser.getUid());
        List<Prize> prizes = new LinkedList<>();
        for (OwnedPrize ownedPrize : ownedPrizes) {
            Prize prize = prizeRepository.findById(ownedPrize.getPid()).get();
            prize.setAmount(ownedPrize.getAmount());
            prizes.add(prize);
        }
        response.put("status", 0);
        response.put("prize", prizes);
        return response;
    }
}
