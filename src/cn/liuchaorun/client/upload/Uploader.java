/*
 * Uploader
 *
 * @author lcr
 * @date 18-7-14
 */
package cn.liuchaorun.client.upload;

import cn.liuchaorun.lib.AES;
import cn.liuchaorun.lib.AESEncrypt;
import cn.liuchaorun.lib.ObjectAndBytes;
import cn.liuchaorun.lib.RSAEncrypt;

import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.Key;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 上传工作的类，主要作用为控制上传的进度
 */
public class Uploader {
    private String path;
    private String name;
    private AESEncrypt aesEncrypt;

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
    public void uploadFile(BufferedInputStream fileBufferedInputStream, DataInputStream dis, DataOutputStream dos, RSAEncrypt encrypt,long length){
        try{
            dos.writeChars(path+name+'\n');
            dos.flush();
            long currentLength = dis.readLong();
            long actuallySkip = fileBufferedInputStream.skip(currentLength);
            while (actuallySkip < currentLength){
                actuallySkip = fileBufferedInputStream.skip(currentLength - actuallySkip);
            }
            Key key = AES.generatorKey();
            byte[] iv = AES.generateIvParamterApec();
            dos.write(encrypt.publicKeyEncrypt(iv));
            dos.flush();
            byte[] o = ObjectAndBytes.toByteArray(key);
            dos.writeInt(o.length);
            byte[] bytes = new byte[117];
            int c = 0;
            while (c < o.length){
                if(o.length - c>=117){
                    System.arraycopy(o,c,bytes,0,bytes.length);
                }
                else {
                    bytes = new byte[o.length - c];
                    System.arraycopy(o,c,bytes,0,bytes.length);
                }
                dos.write(encrypt.publicKeyEncrypt(bytes));
                c+=bytes.length;
            }

            this.aesEncrypt = new AES(key,iv);

            CipherOutputStream cipherOutputStream = aesEncrypt.encrypt(dos);
            byte[] b = new byte[16];
            long fileLength = length;
            int l = 0;
            while (currentLength < fileLength){
                if(fileLength - currentLength >=16){
                    l = fileBufferedInputStream.read(b);
                }
                else {
                    b = new byte[(int)(fileLength - currentLength)];
                    l = fileBufferedInputStream.read(b);
                }
                cipherOutputStream.write(b);
                cipherOutputStream.flush();
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
