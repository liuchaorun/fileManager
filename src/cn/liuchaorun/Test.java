/*
 * Test
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun;

import cn.liuchaorun.lib.RSA;

import java.io.*;
import java.security.*;
import java.util.Arrays;

public class Test {
    private static final int KEYSIZE = 4096;

    public static void main(String[] args) throws IOException, GeneralSecurityException, ClassCastException {
//        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
//        SecureRandom random = new SecureRandom();
//        pairGenerator.initialize(KEYSIZE,random);
//        KeyPair keyPair = pairGenerator.generateKeyPair();
//        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("public.key"))){
//            out.writeObject(keyPair.getPublic());
//        }
//        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("private.key"))){
//            out.writeObject(keyPair.getPrivate());
//        }
        RSA rsa = new RSA();
        byte[] b = {1,2,3,4,5};
        System.out.println(Arrays.toString(rsa.privateKeyDecrypt(b)));
    }
}
