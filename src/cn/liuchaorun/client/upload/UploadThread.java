/*
 * UploadThread
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.lib.RSAEncrypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class UploadThread extends Thread {
    private Socket s;
    private Uploader uploader;
    private boolean isFile;
    private String filePath;
    private RSAEncrypt encrypt;
    private String absolutePath;

    public UploadThread(RSAEncrypt encrypt){
        this.s = null;
        this.uploader = null;
        this.isFile = true;
        this.filePath = "";
        this.encrypt = encrypt;
        this.absolutePath = "";
    }

    public synchronized void setSocketAndUploader(Socket s, Uploader uploader){
        this.s = s;
        this.uploader = uploader;
        notify();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isIdle(){
        return this.s == null || this.uploader == null;
    }

    @Override
    public synchronized void run() {
        try{
            while (true){
                wait();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeChars("UPLOAD\n");
                dos.flush();
                if(isFile){
                    File f = new File(absolutePath+filePath);
                    dos.writeLong(f.length());
                    dos.flush();
                    FileInputStream fis = new FileInputStream(f);
                    uploader.uploadFile(fis,dis,dos,encrypt,f.length());
                    fis.close();
                }else {
                    uploader.uploadDir(dis,dos);
                }
                dis.close();
                dos.close();
                s.close();
                this.s = null;
                this.uploader = null;
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
