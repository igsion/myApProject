package Controllers;

import Models.States.LoginState;
import Models.States.State;
import Views.Display;
import Views.LoginPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        if(jButton.getText().equals("Sign In")){
            LoginState state = (LoginState) State.getState();
            String msg = state.login(LoginPanel.getLoginPanel().getUsername(),LoginPanel.getLoginPanel().getPassword());
            if(msg.equals("Successful")){
                Display.getDisplay().changePage("menu");
            }
        }
        if(jButton.getText().equals("Sign Up")){
            LoginState state = (LoginState) State.getState();
//            System.out.println(LoginPanel.getLoginPanel().getUsername());
//            System.out.println(LoginPanel.getLoginPanel().getPassword());
            String msg = state.signUp(LoginPanel.getLoginPanel().getUsername(),LoginPanel.getLoginPanel().getPassword());
//            System.out.println(msg);
            if(msg.equals("Successful")){
                Display.getDisplay().changePage("menu");
            }
        }
    }
}
