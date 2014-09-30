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
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;
import rmi_interface.*;

 
public class Messenger extends UnicastRemoteObject implements MessengerInterface{
 
	private String username;
	//private GroupChatInterface server;
        private Vector buzon;
        
        public Messenger(String u/*, GroupChatInterface s*/) throws RemoteException {
		username=u;
		//server=s;
                buzon=new Vector();
	}
        
        @Override
        public boolean comprobarMensajes() throws RemoteException{
            if(0==this.buzon.size()){
                return false;
            }
            else{
                return true;
            }
        }
        
        @Override
        public void agregarMensaje(String mensaje) throws RemoteException{
            this.buzon.add(mensaje);
        }
        
        @Override
        public String imprimirMensaje() throws RemoteException{
            String mensaje=(String) this.buzon.get(0);
            this.buzon.remove(0);
            return mensaje;
        }
        
        @Override
	public String getUsername() throws RemoteException {
		return username;	
	}
        @Override
	public void tell(String s) throws RemoteException{		
		System.out.println(s);		
	}
        
}
