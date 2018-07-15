/*
 * AES
 *
 * @author lcr
 * @date 18-7-15
 */
package cn.liuchaorun.lib;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements AESDecrypt,AESEncrypt {
    private Key key;
    private Cipher cipher;
    private IvParameterSpec ivParameterSpec;

    public AES(Key key,IvParameterSpec ivParameterSpec){
        try{
            this.key = key;
            cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            this.ivParameterSpec = ivParameterSpec;
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static IvParameterSpec generateIvParamterApec(){
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static Key generatorKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }

    @Override
    public CipherInputStream decrypt(InputStream is) {
        try {
            cipher.init(Cipher.DECRYPT_MODE,key,ivParameterSpec);
//            byte[] b = new byte[cipher.getBlockSize()];
//            int length = cipherInputStream.read(b);
//            while (length != -1){
//                fileOutputStream.write(b,0,length);
//                length = cipherInputStream.read(b);
//            }
//            cipherInputStream.close();
//            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CipherInputStream(is,cipher);
    }

    @Override
    public CipherOutputStream encrypt(OutputStream os) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE,key,ivParameterSpec);
//            byte[] b = new byte[cipher.getBlockSize()];
//            int length  = fileInputStream.read(b);
//            while(length!=-1){
//                cos.write(b);
//                cos.flush();
//                if(fileInputStream.available() == 0){
//                    break;
//                }
//                if(fileInputStream.available() < 16){
//                    b = new byte[fileInputStream.available()];
//                }
//                length = fileInputStream.read(b);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CipherOutputStream(os,cipher);
    }
}
