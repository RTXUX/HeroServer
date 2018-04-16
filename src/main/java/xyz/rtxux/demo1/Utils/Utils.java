package xyz.rtxux.demo1.Utils;

import xyz.rtxux.demo1.Model.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static long timeBound = 1524268800;

    public static long getTimeBound() {
        return timeBound;
    }

    public static Prize shotPrize(List<Prize> prizes) {
        ArrayList<Prize> pool = new ArrayList<>(100);
        int index = 0;
        for (Prize prize : prizes) {
            for (int i = 0; i < prize.getProbability(); i++) {
                pool.add(index++, prize);
            }
        }
        Random random = new Random();
        int shot = random.nextInt(100);
        return pool.get(shot);
    }

}
