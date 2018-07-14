package cn.liuchaorun.client.upload;

import java.net.Socket;

public interface UploadService {
    void service(Socket s, String absolutePath, String filePath);
}
