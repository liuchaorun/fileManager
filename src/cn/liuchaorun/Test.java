/*
 * Test
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun;

import cn.liuchaorun.client.InfoOutput;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.*;

public class Test {
    private static final int KEYSIZE = 1024;

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
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}