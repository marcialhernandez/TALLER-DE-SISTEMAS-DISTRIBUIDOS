package rmi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
    import java.rmi.*;
    import java.rmi.server.*;
    import java.util.*;
    import rmi_interface.GroupChatInterface;
    import rmi_interface.MessengerInterface;

public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{
    
    private Hashtable l=new Hashtable();
    
    public GroupChat() throws RemoteException{ }
    
    @Override
    public boolean login(MessengerInterface m) throws RemoteException{
        
    l.put(m.getUsername(), m);
    
    m.tell("[Server] Welcome Client "+m.getUsername());
    
    return true;
    
    }	
    
    @Override
    public void sendToAll(String s,MessengerInterface from) throws RemoteException{
        
    System.out.println("\n["+from.getUsername()+"] "+s);
    
    Enumeration usernames = l.keys();
    
    while(usernames.hasMoreElements()){
           
        String user=(String) usernames.nextElement();
        MessengerInterface m=(MessengerInterface)l.get(user);
        if (user.equals(from.getUsername())){continue;}
            try{
                m.tell("\n["+from.getUsername()+"] "+s);
            }catch(Exception e){e.printStackTrace();}
        }
    }	
    @Override
    public MessengerInterface getMessenger(String username) throws RemoteException{
        MessengerInterface m=(MessengerInterface)l.get(username);
        return m;
        }	
}