/*
 * UploadManager
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.client.FindFile.FileInfo;
import cn.liuchaorun.client.controller.Controller;
import cn.liuchaorun.lib.RSA;
import cn.liuchaorun.lib.RSAEncrypt;

import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;


public class UploadManager extends Thread implements UploadService {
    private LinkedList treads = new LinkedList();
    private final int MAX_THREAD;
    private RSAEncrypt encrypt;
    private LinkedList<FileInfo> uploadFiles;
    private Properties config;

    public UploadManager(int number, LinkedList<FileInfo> uploadFiles){
        this.config = new Properties();
        try{
            String configPath = Controller.class.getResource("../../config.properties").toString();
            FileReader fr = new FileReader(configPath.substring(5, configPath.length()));
            this.config.load(fr);
        }catch (Exception err){
            err.printStackTrace();
        }
        this.MAX_THREAD = number;
        this.uploadFiles = uploadFiles;
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
    public synchronized void service(String host, int port, String absolutePath, String filePath) throws Exception {
        System.out.println(1);
        int flag = -1;
        while (flag == -1){
            for (int i = 0; i < MAX_THREAD; i++){
                UploadThread one = ((UploadThread)treads.get(i));
                if(one.isIdle()){
                    flag = i;
                    Socket s = new Socket(host,port);
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

    @Override
    public void run() {
        try {
            while (true){
                if(uploadFiles.size() != 0) {
                    FileInfo fileInfo = uploadFiles.getFirst();
                    service(this.config.getProperty("host"), Integer.parseInt(this.config.getProperty("port")), fileInfo.getName(), fileInfo.getPath());
                    uploadFiles.removeFirst();
                }else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
