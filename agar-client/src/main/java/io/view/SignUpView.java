package io.view;

import java.io.FileWriter;
import java.io.PrintWriter;

import io.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpView {

    @FXML
    private Button SignUpButton;

    @FXML
    private Label TitleEmail;

    @FXML
    private Label TitlePassword;

    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    void SignUpBut(ActionEvent event) throws Exception {
    	
    	Controller controller = new Controller();
    	Escribir(emailTxt.getText() + "," + passwordTxt.getText());
    	controller.open(emailTxt.getText(), passwordTxt.getText());
    	
    	

    }
    
    public void Escribir(String data) {
		 FileWriter fichero = null;
	        PrintWriter pw = null;
	        try
	        {
	            fichero = new FileWriter("../Data/Usuarios.txt",true);
	            pw = new PrintWriter(fichero);

	           
	                pw.println(data);

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	        
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
       
	}
	

}