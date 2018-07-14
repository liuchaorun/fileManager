/*
 * UploadManager
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

import cn.liuchaorun.lib.RSADecrypt;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 接受上传文件数据
 */
public class UploadManager {
    private long fileLength;
    private String name;
    private String path;
    private long currentLength = 0;

    public UploadManager(String pathName, long fileLength){
        String[] s = pathName.split("/");
        this.name = s[s.length -1];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length - 1; i++){
            stringBuilder.append(s[i]);
            stringBuilder.append("/");
        }
        this.path = stringBuilder.toString();
        this.fileLength = fileLength;
    }

    /**
     * 文件夹上传函数
     * @param dis
     * @param dos
     */
    public void uploadDir(DataInputStream dis, DataOutputStream dos){
        try{
            if(fileLength == 0){
                File f = new File(path+name);
                if(f.exists()){
                    dos.writeChars("OK");
                }
                else {
                    if(f.mkdirs()){
                        dos.writeChars("OK");
                    }else {
                        dos.writeChars("FAILED");
                    }
                }
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * 查看是否该文件之前有过传输记录
     * @return boolean
     */
    public boolean readSchedule(){
        try{
            File f = new File(path+name+".lcrbak");
            if(f.exists()){
                RandomAccessFile raf = new RandomAccessFile(f,"r");
                String n = raf.readLine();
                String p = raf.readLine();
                long c = Long.parseLong(raf.readLine());
                long l = Long.parseLong(raf.readLine());
                if(n.equals(this.name)&&p.equals(this.path)&&l==this.fileLength){
                    this.currentLength = c;
                }
                return true;
            }else {
                return false;
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return false;
    }

    /**
     * 文件上传处理函数
     * @param dis
     * @param decrypt
     * @param dos
     */
    public void uploadFile(DataInputStream dis, DataOutputStream dos, RSADecrypt decrypt){
        File f = new File(path+name);
        Logger.getGlobal().info(path);
        if(!f.exists()){
            if(new File(path).mkdirs()){
                try {
                    if(f.createNewFile()){
                        Logger.getGlobal().info(path+name+"文件创建成功！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(f,true);
            while (currentLength <fileLength){
                int length = dis.readInt();
                Logger.getGlobal().info("test "+length);
                byte[] data = new byte[length];
                if(dis.read(data)==length){
                    Logger.getGlobal().info(Arrays.toString(data));
                    data = decrypt.privateKeyDecrypt(data);
                    currentLength += data.length;
                    fos.write(data);
                    dos.writeChars("OK\n");
                    dos.flush();
                }else {
                    dos.writeChars("RETRY\n");
                    dos.flush();
                }
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getGlobal().info("文件上传中断，记录此次信息！");
            try{
                FileWriter fw = new FileWriter(path+name+".lcrbak");
                fw.write(this.name+'\n');
                fw.write(this.path+'\n');
                fw.write(Long.toString(currentLength)+'\n');
                fw.write(Long.toString(this.fileLength)+'\n');
                fw.close();
            }catch (Exception err){
                err.printStackTrace();
            }
        }
    }
}
