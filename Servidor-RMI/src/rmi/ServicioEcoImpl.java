/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;
import java.rmi.*;
import java.rmi.server.*;
import rmi_interface.*;

/**
 *
 * @author admin
 */
public class ServicioEcoImpl extends UnicastRemoteObject implements ServicioEco {

    public ServicioEcoImpl() throws RemoteException {
        super();
    }
    
    @Override
    public String eco(String s) throws RemoteException {
        return s.toUpperCase();
    }
    
}