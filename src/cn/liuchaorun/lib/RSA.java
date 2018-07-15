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
