package cn.liuchaorun.lib;

import javax.crypto.CipherInputStream;
import java.io.InputStream;

public interface AESDecrypt {
    CipherInputStream decrypt(InputStream is);
}
