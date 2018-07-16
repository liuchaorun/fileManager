/*
 * client
 *
 * @author lcr
 * @date 18-7-12
 */
package cn.liuchaorun.client;

import cn.liuchaorun.client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Controller controller;
    private Pattern pattern;
    private InfoOutput infoOutput;

    public Client(){
        infoOutput = new InfoOutput();
        this.controller = new Controller(this.infoOutput);
        this.pattern = Pattern.compile("^([a-z]{1,10}) ([^ ].*)");
        EventQueue.invokeLater(()->{
            JFrame frame = new JFrame("InfoOutput");
            JPanel jPanel = infoOutput.getPanel1();
            frame.setContentPane(jPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public void start() {
        while (true) {
            try {
                System.out.println("文件管理系统：");
                System.out.println("请输入指令");
                Scanner scanner = new Scanner(System.in);
                String operator = scanner.nextLine();
                Matcher matcher = pattern.matcher(operator);
                if (matcher.find()) {
                    switch (matcher.group(1)) {
                        case "upload":
                            controller.upload(matcher.group(2));
                            break;
                        default:
                            System.out.println("错误的指令！");
                            break;
                    }
                }
                else {
                    System.out.println("错误的指令格式！");
                }
            } catch (Exception err) {
                err.printStackTrace();
                break;
            }
        }
    }
}
