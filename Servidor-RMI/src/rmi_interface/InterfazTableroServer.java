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

public interface InterfazTableroServer extends Remote {

    public int getPosicion5Y() throws RemoteException;

    public int getPosicion5X() throws RemoteException;

    public int getPosicion8X() throws RemoteException;

    public int getPosicion8Y() throws RemoteException;

    public void setPosicion8(int x, int y) throws RemoteException;

    public int[][] getTablero(GroupChatInterface infoServer) throws RemoteException;

    public void setTablero(GroupChatInterface infoServer, int[][] tableroRecivido) throws RemoteException;

    public void setTurnoActual(String turno) throws RemoteException;

    public String getTurnoActual() throws RemoteException;

    public void llenatablero10() throws RemoteException;

    public void llenatablero5() throws RemoteException;

    public void llenatablero4() throws RemoteException;

    public void llenatablero2() throws RemoteException;

    public int[][] generaRandom(int i, int j, int nTrampas) throws RemoteException;

    public int[][] colocaSalida(int matriz[][], int inicio) throws RemoteException;

    public int[][] colocaRaton(int matriz[][]) throws RemoteException;

    public int numeroAzar() throws RemoteException;

    boolean validaSalida(int inicio, int fila, int columna) throws RemoteException;

    boolean verificacionStndar(int matriz[][]) throws RemoteException;

    public int[][] getTablero2() throws RemoteException;

    public int[][] getTablero4() throws RemoteException;

    public int[][] getTablero5() throws RemoteException;

    public int[][] getTablero10() throws RemoteException;

    public void setTablero2(int matriz[][]) throws RemoteException;

    public void setTablero4(int matriz[][]) throws RemoteException;

    public void setTablero5(int matriz[][]) throws RemoteException;

    public void setTablero10(int matriz[][]) throws RemoteException;
//String eco (String s) throws RemoteException
}
