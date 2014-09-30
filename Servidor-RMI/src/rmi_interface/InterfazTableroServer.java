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

public interface ServicioEco extends Remote {
    String eco (String s) throws RemoteException;
}
