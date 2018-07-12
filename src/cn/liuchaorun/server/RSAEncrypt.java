package cn.liuchaorun.server;

public interface RSAEncrypt {
    public byte[] publicKeyEncrypt(byte[] data);
    public byte[] privateKeyEncrypt(byte[] data);
}
