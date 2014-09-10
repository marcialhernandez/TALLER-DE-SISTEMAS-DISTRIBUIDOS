/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi_interface;
import java.rmi.Remote;
import java.rmi.RemoteException;
     
    public interface GroupChatInterface extends Remote {
     
    public boolean login (MessengerInterface m) throws RemoteException;
     
    public void sendToAll(String s, MessengerInterface from) throws RemoteException;
     
    public MessengerInterface getMessenger(String username) throws RemoteException;
     
    }