package rmi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import rmi_interface.*;

/**
 *
 * @author admin
 */
public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{
	
	private Hashtable l;
	 
    /**
     *
     * @throws RemoteException
     */
        public GroupChat() throws RemoteException {
            super( );
            l = new Hashtable();
}
        
        @Override
	public boolean login(MessengerInterface m) throws RemoteException{
            //Si no contiene la llave nombre de usuario
            if (!l.containsKey(m.getUsername())){
                l.put(m.getUsername(), m);
		m.tell("[Server] Welcome Client "+m.getUsername());
		return true;
            }
            //no se pudo logear, ya existia el usuario
            else {return false;}
	}
        
        public synchronized boolean existe(String usuario) throws RemoteException{
            //Si no contiene la llave nombre de usuario
            if (l.containsKey(usuario)){
		return true;
            }
            //no se pudo logear, ya existia el usuario
            else {return false;}
	}
        
        @Override
        public synchronized boolean unlogin(MessengerInterface m) throws RemoteException{
            //Si no contiene la llave nombre de usuario
            String usuarioEliminado=m.getUsername();
            if (l.containsKey(usuarioEliminado)){
                l.remove(m.getUsername(), m);		m.tell("[Server] EL usuario"+ usuarioEliminado + "ha salido de la sesion");

		m.tell("[Server] EL usuario "+ usuarioEliminado + " ha salido de la sesion");
		return true;
            }
            //no se pudo logear, ya existia el usuario
            else {
                m.tell("[Server] EL usuario "+ usuarioEliminado + " no existe");
                return false;}
            
	}
                
        @Override
	public String sendToAll(String s,MessengerInterface from) throws RemoteException{
		//System.out.println("\n["+from.getUsername()+"] "+s);
                String mensajeRetorno="\n["+from.getUsername()+"] "+s;
		Enumeration usernames = l.keys();
        while(usernames.hasMoreElements()){
		       String user=(String) usernames.nextElement();
		       MessengerInterface m=(MessengerInterface)l.get(user);
		       if (user.equals(from.getUsername())){continue;}
		       
		       try{
		    	   m.tell("\n["+from.getUsername()+"] "+s);
		       }catch(RemoteException e){}
        }
        return mensajeRetorno;
        
	}
       
       @Override
//       public void sendTo(String s, MessengerInterface from, String to, GroupChatInterface server) throws RemoteException {
               
         public void sendTo(String s, MessengerInterface from, String to) throws RemoteException {            
            //if (!l.containsKey(server.getMessenger(to))){
            //MessengerInterface destiny=l.getMessenger(to);
           String mensaje="MP from ["+from.getUsername()+"]"+" "+ s;
            MessengerInterface m=(MessengerInterface)l.get(to);
            m.agregarMensaje(mensaje);  
                //significa que no existe
            }

        @Override
	public MessengerInterface getMessenger(String username)  throws RemoteException{
		MessengerInterface m=(MessengerInterface)l.get(username);
		return m;
	}	
}
