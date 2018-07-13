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
