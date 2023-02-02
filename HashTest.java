package com.mistarpeo.java_hash;

import javax.net.ssl.SNIHostName;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
       String raw = "dlrmfdkdlv3@";
       String hex = "";

       // SHA1PRNG 알고리즘
       SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
       byte[] bytes = new byte[16];
       random.nextBytes(bytes);

       String salt = new String(Base64.getEncoder().encode(bytes));
       String rawAndSalt = raw+"66cf33a7010d20e2520487434a619679";

        System.out.println("raw : "+ raw);
        System.out.println("salt : "+ salt);

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(raw.getBytes(StandardCharsets.UTF_8));
        byte[] hash2 = md.digest(rawAndSalt.getBytes(StandardCharsets.UTF_8));

        extracted(hash);
        extracted(hash2);


        // 평문 암호화
        md.update(raw.getBytes(StandardCharsets.UTF_8));
        hex = String.format("%064x", new BigInteger(1, md.digest()));
        System.out.println("raw의 해시값 : "+hex);

        // 평문+salt 암호화
        System.out.println("rawAndSalt:  "+rawAndSalt);
        md.update(rawAndSalt.getBytes());
        hex = String.format("%064x", new BigInteger(1, md.digest()));
        System.out.println("raw+salt의 해시값 : "+hex);
    }

    private static void extracted(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i< hash.length; i++) {
            String hex1 = Integer.toHexString(0xff & hash[i]);
            if(hex1.length() == 1) hexString.append('0');
            hexString.append(hex1);
        }
        System.out.println("hexString: "+ hexString.toString());
    }
}

