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
import java.util.Scanner;
import java.util.logging.Logger;

public class FileManagerProtocol implements SocketService {
    private Controller controller = new Controller();

    @Override
    public void service(Socket s) {
        try{
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Scanner g = new Scanner(dis);
            while (true){
                String operator = g.nextLine();
                if(operator.equals("UPLOAD")){
                    Logger.getGlobal().info("正在准备上传");

                }
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
