/*
 * Uploader
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.lib.RSAEncrypt;

import java.io.*;

/**
 * 上传工作的类，主要作用为控制上传的进度
 */
public class Uploader {
    private String path;
    private String name;

    public Uploader(String filePath){
        String[] s = filePath.split("/");
        this.name = s[s.length -1];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length - 1; i++){
            stringBuilder.append(s[i]);
            stringBuilder.append("/");
        }
        this.path = stringBuilder.toString();
        System.out.println(this.name);
        System.out.println(this.path);
    }

    /**
     * 上传文件夹函数
     * @param dis
     * @param dos
     */
    public void uploadDir(DataInputStream dis, DataOutputStream dos){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            dos.writeLong((long)-1);
            dos.flush();
            dos.writeChars(path + name+'\n');
            dos.flush();
            char c = 0;
            while ((c = dis.readChar())!='\n'){
                stringBuilder.append(c);
            }
            if(stringBuilder.toString().equals("OK")){
                System.out.println("文件夹上传成功!");
            }
            else {
                System.out.println("文件夹上传失败！");
            }
            dos.writeChars("CLOSE\n");
            dos.flush();
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * 上传文件函数
     * @param dis
     * @param dos
     */
    public void uploadFile(BufferedInputStream bufferedInputStream, DataInputStream dis, DataOutputStream dos, RSAEncrypt encrypt,long length){
        try{
            dos.writeChars(path+name+'\n');
            dos.flush();
            long currentLength = dis.readLong();
            long actuallySkip = bufferedInputStream.skip(currentLength);
            while (actuallySkip < currentLength){
                actuallySkip = bufferedInputStream.skip(currentLength - actuallySkip);
            }
            byte[] b = new byte[117];
            long fileLength = length;
            int l = 0;
            dos.writeInt(128);
            dos.flush();
            while (currentLength < fileLength){
                if(fileLength - currentLength >=117){
                    l = bufferedInputStream.read(b);
                }
                else {
                    b = new byte[(int)(fileLength - currentLength)];
                    l = bufferedInputStream.read(b);
                }
                dos.write(encrypt.publicKeyEncrypt(b));
                dos.flush();
                currentLength += l;
            }
            System.out.println(path + name + " success!");
            dos.writeChars("CLOSE\n");
            dos.flush();
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
