/*
 * FileManagerProtocol
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

import java.io.*;
import java.lang.Exception;
import java.net.Socket;
import java.util.logging.Logger;

public class FileManagerProtocol implements SocketService {
    private Controller controller = new Controller();

    @Override
    public void service(Socket s) {
        try{
            DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            while (true){
                StringBuilder stringBuilder = new StringBuilder();
                char c = 0;
                while ((c = dis.readChar())!='\n'){
                    stringBuilder.append(c);
                }
                if(stringBuilder.toString().equals("UPLOAD")){
                    Logger.getGlobal().info("正在准备上传");
                    controller.upload(dis,dos);
                }else if(stringBuilder.toString().equals("CLOSE")){
                    s.close();
                    Logger.getGlobal().info("SOCKET CLOSE");
                    break;
                }
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
