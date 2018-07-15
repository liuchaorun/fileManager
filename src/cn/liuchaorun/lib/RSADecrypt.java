/*
 * RSADecrypt
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.lib;

public interface RSADecrypt {
    byte[] publicKeyDecrypt(byte[] data);
    byte[] privateKeyDecrypt(byte[] data);
}

//package cn.liuchaorun.lib;
//
//import java.io.InputStream;
//import javax.crypto.CipherInputStream;
//
//public interface RSADecrypt {
//    CipherInputStream publicKeyDecrypt(InputStream inputStream);
//    CipherInputStream privateKeyDecrypt(InputStream inputStream);
//}
