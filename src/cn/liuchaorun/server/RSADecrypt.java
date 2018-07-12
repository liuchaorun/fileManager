/*
 * RSADecrypt
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

public interface RSADecrypt {
    public byte[] publicKeyDecrypt(byte[] data);
    public byte[] privateKeyDecrypt(byte[] data);
}
