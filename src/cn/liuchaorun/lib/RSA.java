/*
 * RSA
 *
 * @author lcr
 * @date 18-7-12
 */

package cn.liuchaorun.lib;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.security.Key;
import java.util.Properties;

public class RSA implements RSADecrypt,RSAEncrypt {
    private Key publicKey = null;
    private Key privateKey = null;
    private Cipher cipher;

    public RSA() {
        Properties properties = new Properties();
        try {
            cipher = Cipher.getInstance("RSA");
            String configPath = RSA.class.getResource("../config.properties").toString();
            properties.load(new FileReader(configPath.substring(5,configPath.length())));
            String libPath = RSA.class.getResource("./").toString();
            publicKey =(Key) new ObjectInputStream(new FileInputStream(libPath.substring(5,libPath.length())+properties.getProperty("publicKey"))).readObject();
            privateKey =(Key) new ObjectInputStream(new FileInputStream(libPath.substring(5,libPath.length())+properties.getProperty("privateKey"))).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] privateKeyEncrypt(byte[] data){
        try {
            cipher.init(Cipher.ENCRYPT_MODE,this.privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] publicKeyEncrypt(byte[] data){
        try {
            cipher.init(Cipher.ENCRYPT_MODE,this.publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] privateKeyDecrypt(byte[] data){
        try {
            cipher.init(Cipher.DECRYPT_MODE,this.privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] publicKeyDecrypt(byte[] data){
        try {
            cipher.init(Cipher.DECRYPT_MODE,this.publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

//package cn.liuchaorun.lib;
//
//import javax.crypto.Cipher;
//import javax.crypto.CipherInputStream;
//import javax.crypto.CipherOutputStream;
//import java.io.*;
//import java.security.Key;
//import java.util.Properties;
//
//public class RSA implements RSADecrypt,RSAEncrypt {
//    private Key publicKey = null;
//    private Key privateKey = null;
//    private Cipher cipher;
//
//    public RSA() {
//        Properties properties = new Properties();
//        try {
//            cipher = Cipher.getInstance("RSA");
//            String configPath = RSA.class.getResource("../config.properties").toString();
//            properties.load(new FileReader(configPath.substring(5,configPath.length())));
//            String libPath = RSA.class.getResource("./").toString();
//            publicKey =(Key) new ObjectInputStream(new FileInputStream(libPath.substring(5,libPath.length())+properties.getProperty("publicKey"))).readObject();
//            privateKey =(Key) new ObjectInputStream(new FileInputStream(libPath.substring(5,libPath.length())+properties.getProperty("privateKey"))).readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public CipherOutputStream privateKeyEncrypt(OutputStream outputStream){
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE,this.privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new CipherOutputStream(outputStream,cipher);
//    }
//
//    @Override
//    public CipherOutputStream publicKeyEncrypt(OutputStream outputStream){
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE,this.publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new CipherOutputStream(outputStream,cipher);
//    }
//
//    @Override
//    public CipherInputStream privateKeyDecrypt(InputStream inputStream) {
//        try {
//            cipher.init(Cipher.DECRYPT_MODE,this.privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new CipherInputStream(inputStream,cipher);
//    }
//
//    @Override
//    public CipherInputStream publicKeyDecrypt(InputStream inputStream) {
//        try {
//            cipher.init(Cipher.DECRYPT_MODE,this.publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new CipherInputStream(inputStream,cipher);
//    }
//
//    public static void main(String[] args) {
//        try{
//            RSA rsa = new RSA();
//            Key key = AES.generatorKey();
//            FileOutputStream fileOutputStream = new FileOutputStream("/home/lcr/1.txt");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(rsa.privateKeyEncrypt(new BufferedOutputStream(fileOutputStream)));
//            objectOutputStream.writeObject(key);
//            objectOutputStream.close();
//
//            FileInputStream fileInputStream = new FileInputStream("/home/lcr/1.txt");
////            CipherInputStream cipherInputStream = rsa.publicKeyDecrypt(fileInputStream);
////            byte[] b =new byte[1];
////            while(cipherInputStream.read(b) == 1){
////                System.out.println(b[0]);
////            }
//            ObjectInputStream objectInputStream = new ObjectInputStream(rsa.publicKeyDecrypt(new BufferedInputStream(fileInputStream)));
//            System.out.println(objectInputStream.readObject());
//
//        }catch (Exception err){
//            err.printStackTrace();
//        }
//    }
//}
