/*
 * UploadThread
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.client.InfoOutput;
import cn.liuchaorun.client.controller.Controller;
import cn.liuchaorun.lib.RSAEncrypt;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class UploadThread extends Thread {
    private Uploader uploader;
    private boolean isFile;
    private String filePath;
    private RSAEncrypt encrypt;
    private String absolutePath;
    private Properties config;
    private InfoOutput infoOutput;

    public UploadThread(RSAEncrypt encrypt,InfoOutput infoOutput) {
        this.infoOutput = infoOutput;
        this.config = new Properties();
        try {
            String configPath = Controller.class.getResource("../../config.properties").toString();
            FileReader fr = new FileReader(configPath.substring(5, configPath.length()));
            this.config.load(fr);
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.uploader = null;
        this.isFile = true;
        this.filePath = "";
        this.encrypt = encrypt;
        this.absolutePath = "";
    }

    public synchronized void setUploader(Uploader uploader) {
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

    public boolean isIdle() {
        return this.uploader == null;
    }

    @Override
    public synchronized void run() {
        Socket s = null;
        try {
            while (true) {
                wait();
                s = new Socket(this.config.getProperty("host"), Integer.parseInt(this.config.getProperty("port")));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(s.getInputStream(), 512);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(s.getOutputStream(), 512);
                DataInputStream dis = new DataInputStream(bufferedInputStream);
                DataOutputStream dos = new DataOutputStream(bufferedOutputStream);
                dos.writeChars("UPLOAD\n");
                dos.flush();
                if (isFile) {
                    File f = new File(absolutePath + filePath);
                    dos.writeLong(f.length());
                    dos.flush();
                    FileInputStream fis = new FileInputStream(f);
                    BufferedInputStream fileBufferedInputStream = new BufferedInputStream(fis);
                    uploader.uploadFile(fileBufferedInputStream, dis, dos, encrypt, f.length());
                    fis.close();
                } else {
                    uploader.uploadDir(dis, dos);
                }
                dis.close();
                dos.close();
                s.close();
                s = null;
                this.uploader = null;
            }
        } catch (Exception err) {
            try {
                if (s != null) {
                    s.close();
                    s = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            err.printStackTrace();
        }
    }
}
