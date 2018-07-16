/*
 * UploadManager
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.client.FindFile.FileInfo;
import cn.liuchaorun.client.InfoOutput;
import cn.liuchaorun.lib.RSA;
import cn.liuchaorun.lib.RSAEncrypt;

import java.io.File;
import java.util.LinkedList;


public class UploadManager extends Thread implements UploadService {
    private LinkedList treads = new LinkedList();
    private final int MAX_THREAD;
    private RSAEncrypt encrypt;
    private LinkedList<FileInfo> uploadFiles;
    private InfoOutput infoOutput;

    public UploadManager(int number, LinkedList<FileInfo> uploadFiles, InfoOutput infoOutput){
        this.infoOutput = infoOutput;
        this.MAX_THREAD = number;
        this.uploadFiles = uploadFiles;
        this.encrypt = new RSA();
        for (int i = 0; i < MAX_THREAD; i++){
            UploadThread one = new UploadThread(encrypt,infoOutput);
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
    public synchronized void service(String absolutePath, String filePath) throws Exception {
        int flag = -1;
        while (flag == -1){
            for (int i = 0; i < MAX_THREAD; i++){
                UploadThread one = ((UploadThread)treads.get(i));
                if(one.isIdle()){
                    flag = i;
                    Uploader uploader = new Uploader(filePath,infoOutput);
                    one.setAbsolutePath(absolutePath);
                    one.setFilePath(filePath);
                    one.setFile(new File(absolutePath+filePath).isFile());
                    one.setUploader(uploader);
                    break;
                }
            }
            if(flag == -1){
                Thread.sleep(100);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                infoOutput.setAllNumber(uploadFiles.size());
                if(uploadFiles.size() != 0) {
                    FileInfo fileInfo = uploadFiles.getFirst();
                    service(fileInfo.getName(), fileInfo.getPath());
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
