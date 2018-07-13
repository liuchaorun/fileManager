/*
 * Main
 *
 * @author lcr
 * @date 18-7-12
 */

import cn.liuchaorun.client.Client;
import cn.liuchaorun.server.Server;



public class Main {
    public static void main(String[] args){
        if(args[0].equals("client")){
            Client client = new Client();
            client.start();
        }else {
            Server app = new Server();
            app.start();
        }

    }
}