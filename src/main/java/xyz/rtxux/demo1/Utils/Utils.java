package xyz.rtxux.demo1.Utils;

import xyz.rtxux.demo1.Model.Prize;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static long timeBound = 1524268800;

    public static long getTimeBound() {
        return timeBound;
    }


    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }
    public static Prize shotPrize(List<Prize> prizes) {
        ArrayList<Prize> pool = new ArrayList<>(100);
        int totalProbability = 0;
        for (Prize prize : prizes) {
            totalProbability += prize.getProbability();
        }
        int index = 0;
        for (Prize prize : prizes) {
            for (int i = 0; i < (prize.getProbability() * 100 / totalProbability); i++) {
                pool.add(index++, prize);
            }
        }
        Random random = new Random();
        int shot = random.nextInt(pool.size());
        return pool.get(shot);
    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
