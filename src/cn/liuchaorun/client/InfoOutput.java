/*
 * InfoOutput
 *
 * @author lcr
 * @date 18-7-16
 */
package cn.liuchaorun.client;

import javax.swing.*;

public class InfoOutput {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private int i = 0;

    public InfoOutput(){
        setAllNumber(0);
        textArea1.setEditable(false);
        textField1.setEditable(false);
        textField2.setEditable(false);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setAllNumber(int n){
        textField1.setText(Integer.toString(n));
    }

    public synchronized void setFinishedNumber(){
        i++;
        textField2.setText(Integer.toString(i));
    }

    public void setInfo(String text){
        textArea1.append(text+'\n');
        textArea1.setCaretPosition(textArea1.getText().length());
    }
}
