/*
 * client
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class client {
    public static void main(String[] args){
        try{
            Socket s = new Socket("127.0.0.1",3000);
            Timer t =new Timer(1000,new TimerPrinter(s));
            t.start();
            JOptionPane.showMessageDialog(null,"quit?");
            System.exit(0);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}

class TimerPrinter implements ActionListener{
    private Socket s = null;
    public TimerPrinter(Socket s){
        this.s = s;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            PrintWriter p = new PrintWriter(dos);
            p.println("UPLOAD");
            dos.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
