/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_interface;
import java.rmi.*;

/**
 *
 * @author admin
 */
 
public interface MessengerInterface  extends Remote{
    
	public String getUsername() throws RemoteException;
 
	public void tell(String s) throws RemoteException; 
        
        public boolean comprobarMensajes() throws RemoteException;
        
        public void agregarMensaje(String s) throws RemoteException;
        
        public String imprimirMensaje() throws RemoteException;


 
}