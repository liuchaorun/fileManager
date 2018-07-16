package cn.liuchaorun.client.upload;

public interface UploadService {
    void service(String host, int port, String absolutePath, String filePath) throws Exception;
}
