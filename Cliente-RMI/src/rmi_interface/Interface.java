
package rmi_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * Esta inteface indicará los métodos que están a dispoción del cliente y servidor
 * para que puedan interactuar remotamente.
 * Todos estos métodos deben poseer como mínimo la excepción RemoteException
 */

public interface Interface extends Remote {
    
    public boolean ingresarUsuario(String usuario) throws RemoteException;
    public ArrayList<String> verUsuarios() throws RemoteException;
}