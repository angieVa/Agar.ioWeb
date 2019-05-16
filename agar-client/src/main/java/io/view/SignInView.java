package io.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInView {

    @FXML
    private Button SignInButton;

    @FXML
    private Label TitleEmail;

    @FXML
    private Label TitlePassword;

    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField passwordTxt;


    @FXML
    void SignInBut(ActionEvent event) throws Exception {
    	if(leer()) {
    	Controller controller = new Controller();
    	
    	controller.open(emailTxt.getText(), passwordTxt.getText());
    	Stage stageAct = (Stage) SignInButton.getScene().getWindow();
		stageAct.close();
    	}

    }
    
    public boolean leer() {
    	boolean existe = false;
    	
    	File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
          
           archivo = new File ("../Data/Usuarios.txt");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           String linea;
           while((linea=br.readLine())!=null) {
        	   
        	   String[] sep = linea.split(",");
        	   if(sep[0].equals(emailTxt.getText()) && sep[1].equals(passwordTxt.getText())) {
        		   existe = true;
        	   }
        	
           }
              
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
   
    	
    	return existe;
    }

}