/*
 * client
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.client;

import cn.liuchaorun.lib.RSA;

import java.io.*;
import java.net.Socket;

public class Client {
    public void start(){
        try{
            Socket s = new Socket("127.0.0.1",3000);
            InputStream sis = s.getInputStream();
            OutputStream sos = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(sos);
            DataInputStream dis = new DataInputStream(sis);
            File f = new File("/home/lcr/file/1/temp.txt");
            FileInputStream fis = new FileInputStream(f);
            dos.writeChars("UPLOAD\n");
            dos.flush();
            int i = 0;
            dos.writeLong(f.length());
            dos.flush();
            dos.writeChars("new/"+f.getName()+'\n');
            dos.flush();
            RSA rsa = new RSA();
            int l = 0;
            byte[] b = new byte[117];
            StringBuilder stringBuilder = new StringBuilder();
            while((l=fis.read(b))==117){
                do{
                    stringBuilder = new StringBuilder();
                    System.out.println(l);
                    dos.writeInt(128);
                    dos.flush();
                    dos.write(rsa.publicKeyEncrypt(b));
                    dos.flush();
                    char c = 0;
                    while ((c = dis.readChar())!='\n'){
                        stringBuilder.append(c);
                    }
                }while (stringBuilder.toString().equals("RETRY"));
            }
            if(l > 0){
                stringBuilder = new StringBuilder();
                byte[] rest = new byte[l];
                System.arraycopy(b,0,rest,0,l);
                do{
                    dos.writeInt(128);
                    dos.flush();
                    dos.write(rsa.publicKeyEncrypt(rest));
                    dos.flush();
                    char c = 0;
                    while ((c = dis.readChar())!='\n'){
                        stringBuilder.append(c);
                    }
                }while (stringBuilder.toString().equals("RETRY"));
            }
            System.out.println("success!");
            dos.writeChars("CLOSE\n");
            dos.flush();
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
