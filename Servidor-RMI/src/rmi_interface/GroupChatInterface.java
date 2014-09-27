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
public interface GroupChatInterface extends Remote {
    
    	  public boolean login (MessengerInterface m) throws RemoteException;
          
          public boolean unlogin(MessengerInterface m) throws RemoteException;
          
          public boolean existe(String usuario) throws RemoteException;
 
	  public void sendToAll(String s, MessengerInterface from) throws RemoteException;
          
          public void sendTo(String s, MessengerInterface from, String to) throws RemoteException;            

	  public MessengerInterface getMessenger(String username)  throws RemoteException;
}