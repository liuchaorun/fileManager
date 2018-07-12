/*
 * UploadManager
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

import java.io.*;
import java.util.logging.Logger;

/**
 * 接受上传文件数据
 */
public class UploadManager {
    private int fileLength;
    private String name;
    private String path;

    public UploadManager(String name, String path, int fileLength){
        this.name = name;
        this.path = path;
        this.fileLength = fileLength;
    }

    /**
     * 文件夹上传函数
     * @param dis
     * @param dos
     */
    public void uploadDir(DataInputStream dis, DataOutputStream dos){

    }

    /**
     * 文件上传处理函数
     * @param dis
     * @param decrypt
     * @param dos
     */
    public void uploadFile(DataInputStream dis, DataOutputStream dos, RSADecrypt decrypt){
        File f = new File(path+name);
        if(!f.exists()){
            if(f.mkdirs()){
                try {
                    if(f.createNewFile()){
                        Logger.getGlobal().info(path+name+"文件创建成功！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int currentLength = 0;
        try {
            FileOutputStream fos = new FileOutputStream(f,true);
            while (currentLength <fileLength){
                int length = dis.readInt();
                byte[] data = new byte[length];
                if(dis.read(data)==length){
                    data = decrypt.privateKeyDecrypt(data);
                    currentLength += data.length;
                    fos.write(data);
                    dos.writeChars("OK");
                    dos.flush();
                }else {
                    dos.writeChars("Retry");
                    dos.flush();
                }
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getGlobal().info("文件上传中断，记录此次信息！");
            try{
                FileWriter fw = new FileWriter(path+name+".lcrbak");
                fw.write(this.name);
                fw.write(this.path);
                fw.write(Integer.toString(currentLength));
                fw.write(Integer.toString(this.fileLength));
                fw.close();
            }catch (Exception err){
                err.printStackTrace();
            }
        }
    }
}
