package cn.liuchaorun.lib;

import javax.crypto.CipherOutputStream;
import java.io.OutputStream;

public interface AESEncrypt {
    CipherOutputStream encrypt(OutputStream os);
}
