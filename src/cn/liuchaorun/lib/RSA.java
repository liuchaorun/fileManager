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

    public RSA() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/cn/liuchaorun/config.properties"));
            publicKey =(Key) new ObjectInputStream(new FileInputStream(properties.getProperty("publicKey"))).readObject();
            privateKey =(Key) new ObjectInputStream(new FileInputStream(properties.getProperty("privateKey"))).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] privateKeyEncrypt(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,this.privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] publicKeyEncrypt(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,this.publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] privateKeyDecrypt(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,this.privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] publicKeyDecrypt(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,this.publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
