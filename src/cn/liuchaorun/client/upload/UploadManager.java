/*
 * UploadManager
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.lib.RSA;
import cn.liuchaorun.lib.RSAEncrypt;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;

public class UploadManager implements UploadService {
    private LinkedList treads = new LinkedList();
    private final int MAX_THREAD;
    private RSAEncrypt encrypt;

    public UploadManager(int number){
        this.MAX_THREAD = number;
        this.encrypt = new RSA();
        for (int i = 0; i < MAX_THREAD; i++){
            UploadThread one = new UploadThread(encrypt);
            one.start();
            treads.add(one);
        }
        try{
            Thread.sleep(300);
        }catch (InterruptedException err){
            err.printStackTrace();
        }
    }

    @Override
    public synchronized void service(Socket s, String absolutePath, String filePath) {
        int flag = -1;
        UploadThread one;
        while (flag == -1){
            for (int i = 0; i < MAX_THREAD; i++){
                one = ((UploadThread)treads.get(i));
                if(one.isIdle()){
                    flag = i;
                    Uploader uploader = new Uploader(filePath);
                    one.setAbsolutePath(absolutePath);
                    one.setFilePath(filePath);
                    one.setFile(new File(absolutePath+filePath).isFile());
                    one.setSocketAndUploader(s,uploader);
                    break;
                }
            }
        }
    }
}
