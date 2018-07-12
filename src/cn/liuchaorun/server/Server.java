/*
 * Server
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.server;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Logger;

public class Server {
    private Properties config;
    private SocketService threadsPool = new ProtocolThreadPool(new FileManagerProtocol());

    public Server() {
        this.config = new Properties();
        try {
            FileReader fr = new FileReader("src/cn/liuchaorun/config.properties");
            this.config.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(Integer.parseInt(config.getProperty("port")));
            Logger.getGlobal().info("this is a test log");
            System.out.println("Server start in port " + config.getProperty("port"));
            while (true) {
                Socket s = ss.accept();
                threadsPool.service(s);
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
