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
public class GroupChat extends UnicastRemoteObject implements GroupChatInterface {

    private Hashtable l;
    private Vector chatGlobal;
    private int turnoActual;
    private int posicionX,posicionY;

    //private Vector buzonGlobal;
    /**
     *
     * @throws RemoteException
     */
    public GroupChat() throws RemoteException {
        super();
        l = new Hashtable();
        chatGlobal = new Vector();
        turnoActual = 1;
        this.posicionX=0;
        this.posicionY=0;
    }
    
    @Override
    public void setUbicacionRaton(int x,int y) throws RemoteException {
     this.posicionX=x;
     this.posicionY=y;
    }
    
    @Override
    public int getUbicacionRatonX() throws RemoteException {
     return this.posicionX;
    }
    
    @Override
    public int getUbicacionRatonY() throws RemoteException {
     return this.posicionY;
    }

    @Override
    public int getTurnoServ() throws RemoteException {
        return this.turnoActual;
    }

    @Override
    public void cambiaTurno() throws RemoteException {
        if (this.turnoActual < l.size()) {
            this.turnoActual++;
        } else {
            this.turnoActual = 1;
        }
    }

    @Override
    public void setChatGlobal(String entrada) throws RemoteException {
        this.chatGlobal.add(entrada);

    }

    @Override
    public String getChatGlobal() throws RemoteException {
        String mensaje = "";
        if (!this.chatGlobal.isEmpty()) {
            mensaje = (String) this.chatGlobal.get(0);
            this.chatGlobal.remove(0);
            return mensaje;
        }
        return mensaje;
    }

    @Override
    public int cantidadUsuarios() throws RemoteException {
        return this.l.size();
    }

    @Override
    public boolean login(MessengerInterface m) throws RemoteException {
        //Si no contiene la llave nombre de usuario
        if (!l.containsKey(m.getUsername())) {
            l.put(m.getUsername(), m);
            m.setTurnoCliente(l.size());
            m.tell("[Server] Welcome Client " + m.getUsername());
            return true;
        } //no se pudo logear, ya existia el usuario
        else {
            return false;
        }
    }

    public synchronized boolean existe(String usuario) throws RemoteException {
        //Si no contiene la llave nombre de usuario
        if (l.containsKey(usuario)) {
            return true;
        } //no se pudo logear, ya existia el usuario
        else {
            return false;
        }
    }

    @Override
    public synchronized boolean unlogin(MessengerInterface m) throws RemoteException {
        //Si no contiene la llave nombre de usuario
        String usuarioEliminado = m.getUsername();
        if (l.containsKey(usuarioEliminado)) {
            l.remove(m.getUsername(), m);
            m.tell("[Server] EL usuario" + usuarioEliminado + "ha salido de la sesion");

            m.tell("[Server] EL usuario " + usuarioEliminado + " ha salido de la sesion");
            return true;
        } //no se pudo logear, ya existia el usuario
        else {
            m.tell("[Server] EL usuario " + usuarioEliminado + " no existe");
            return false;
        }

    }

    @Override
    public String sendToAll(String s, MessengerInterface from) throws RemoteException {
        //System.out.println("\n["+from.getUsername()+"] "+s);
        String mensajeRetorno = "\n[" + from.getUsername() + "] " + s;
        Enumeration usernames = l.keys();
        while (usernames.hasMoreElements()) {
            String user = (String) usernames.nextElement();
            MessengerInterface m = (MessengerInterface) l.get(user);
            if (user.equals(from.getUsername())) {
                continue;
            }

            try {
                m.tell("\n[" + from.getUsername() + "] " + s);
            } catch (RemoteException e) {
            }
        }
        return mensajeRetorno;

    }

    @Override
//       public void sendTo(String s, MessengerInterface from, String to, GroupChatInterface server) throws RemoteException {

    public void sendTo(String s, MessengerInterface from, String to) throws RemoteException {
        //if (!l.containsKey(server.getMessenger(to))){
        //MessengerInterface destiny=l.getMessenger(to);
        String mensaje = "MP from [" + from.getUsername() + "]" + " " + s;
        MessengerInterface m = (MessengerInterface) l.get(to);
        m.agregarMensaje(mensaje);
        //significa que no existe
    }

    @Override
    public MessengerInterface getMessenger(String username) throws RemoteException {
        MessengerInterface m = (MessengerInterface) l.get(username);
        return m;
    }
}
