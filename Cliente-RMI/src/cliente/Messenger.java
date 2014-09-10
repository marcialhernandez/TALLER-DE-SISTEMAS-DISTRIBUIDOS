/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

/**
 *
 * @author admin
 */
    import java.rmi.*;
    import java.rmi.server.*;
    import rmi_interface.GroupChatInterface;
    import rmi_interface.MessengerInterface;
     
    public class Messenger extends UnicastRemoteObject implements MessengerInterface{
     
    private String username;
    
    private GroupChatInterface server;
    
    public Messenger(String u, GroupChatInterface s) throws RemoteException {
    username=u;
    server=s;
    }
    public String getUsername() throws RemoteException{
    return username;	
    }
    public void tell(String s) throws RemoteException{	
    System.out.println(s);	
    }
 }