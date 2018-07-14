/*
 * Controller
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.controller;

import cn.liuchaorun.client.FindFile.FileInfo;
import cn.liuchaorun.client.FindFile.FindFiles;
import cn.liuchaorun.client.upload.UploadManager;

import java.io.File;
import java.io.FileReader;
import java.lang.Exception;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;

public class Controller {
    private Properties config;
    private UploadManager uploadManager;

    public Controller() {
        this.config = new Properties();
        try {
            String configPath = Controller.class.getResource("../../config.properties").toString();
            FileReader fr = new FileReader(configPath.substring(5, configPath.length()));
            this.config.load(fr);
            this.uploadManager = new UploadManager(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload(String filePath){
        try{
            File f = new File(filePath);
            if(f.exists()){
                if(f.isFile()){
                    Socket s = new Socket(this.config.getProperty("host"),Integer.parseInt(this.config.getProperty("port")));
                    String[] strings = filePath.split("/");
                    String name = strings[strings.length -1];
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < strings.length - 1; i++){
                        stringBuilder.append(strings[i]);
                        stringBuilder.append("/");
                    }
                    uploadManager.service(s,stringBuilder.toString(),name);
                }
                if (f.isDirectory()){
                    String[] strings = filePath.split("/");
                    String name = strings[strings.length -1];
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < strings.length - 1; i++){
                        stringBuilder.append(strings[i]);
                        stringBuilder.append("/");
                    }
                    FindFiles findFiles = new FindFiles();
                    LinkedList<FileInfo> list = findFiles.getFiles(filePath);
                    for (int i = 0; i < list.size(); i++){
                        Socket s = new Socket(this.config.getProperty("host"),Integer.parseInt(this.config.getProperty("port")));
                        System.out.println(name+list.get(i).getPath()+list.get(i).getName()+"开始上传");
                        uploadManager.service(s,stringBuilder.toString(),name+list.get(i).getPath()+list.get(i).getName());
                    }
                }
            }else {
                System.out.println("文件不存在！");
            }
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }
}
