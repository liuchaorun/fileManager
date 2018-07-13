package cn.liuchaorun.lib;

public interface RSAEncrypt {
    byte[] publicKeyEncrypt(byte[] data);
    byte[] privateKeyEncrypt(byte[] data);
}
