/*
 * Controller
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

import cn.liuchaorun.lib.RSA;

import java.io.*;
import java.util.logging.Logger;

public class Controller {
    private RSA rsa = new RSA();
    private final String prefix = "/home/lcr/file/";
    public void upload(DataInputStream dis, DataOutputStream dos){
        try{
            long fileLength = dis.readLong();
            //Logger.getGlobal().info("fileLength = " + fileLength);
            StringBuilder stringBuilder = new StringBuilder();
            char c = 0;
            while ((c = dis.readChar())!='\n'){
                stringBuilder.append(c);
            }
            String path = stringBuilder.toString();
            //Logger.getGlobal().info("path = "+path);
            UploadManager uploadManager = new UploadManager(prefix+path,fileLength);
            if(fileLength == (long)-1){
                uploadManager.uploadDir(dis,dos);
            }else {
                boolean flag = false;
                if(uploadManager.readSchedule()){
                    flag = true;
                    //Logger.getGlobal().info("开始加载之前上传的文件");
                }else {
                    //Logger.getGlobal().info("开始上传");
                }
                uploadManager.uploadFile(dis,dos,rsa,flag);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
