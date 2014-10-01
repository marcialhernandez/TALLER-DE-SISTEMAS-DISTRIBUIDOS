/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablero;

import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author admin
 */
public class CustomInputStream {
    
    private JTextArea textArea;
    Scanner InputFromKeyboard;
    //JButton ConfirmacionEntradaMensaje;
    //Enconlar un Arraylist con mensajes
    //Encolar a medida que pasa el tiempo
    //poner como condicion que se haga click
 
    public CustomInputStream(JTextArea textArea) {
        this.textArea = textArea;
        InputFromKeyboard = new Scanner(textArea.getText());
        
    }
    
    /*public CustomInputStream(PaginaPrincipal ventana) {
        
        this.textArea = ventana.paginaPrincipalInput();
        InputFromKeyboard = new Scanner(textArea.getText());
    }*/
    
    public Scanner inputFromChat(){
        return InputFromKeyboard;
    }
    
    
    
    
    
}
