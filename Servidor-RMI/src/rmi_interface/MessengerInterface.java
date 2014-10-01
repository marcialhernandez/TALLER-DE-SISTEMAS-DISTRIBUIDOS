/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_interface;

/**
 *
 * @author admin
 */
import java.rmi.*;

public interface MessengerInterface extends Remote {

    public String getUsername() throws RemoteException;

    public void tell(String s) throws RemoteException;

    public boolean comprobarMensajes() throws RemoteException;

    public void agregarMensaje(String s) throws RemoteException;

    public String imprimirMensaje() throws RemoteException;

    public void setTurnoCliente(int numeroTurno) throws RemoteException;

    public int getTurnoCliente() throws RemoteException;

}
