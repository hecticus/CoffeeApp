package security;

import java.security.SecureRandom;

/**
 * Created by nisa on 11/10/17.
 */
public class SecurityUtils {

    public static String generatePin(int digitLength){
        SecureRandom random = new SecureRandom();
        int num = random.nextInt((int) Math.pow(10, digitLength)); // reference: https://stackoverflow.com/questions/33847225/generating-a-random-pin-of-5-digits
        return String.format("%0" + digitLength + "d", num);
    }

    public static String encrypt(String value){
        return null;
    }
}
