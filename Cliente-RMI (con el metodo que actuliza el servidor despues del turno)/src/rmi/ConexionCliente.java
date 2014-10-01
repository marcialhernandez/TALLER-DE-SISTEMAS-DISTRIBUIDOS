package rmi;

import rmi_interface.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConexionCliente {

    private Registry registry;  //Registro de la conexión del usuario con el servidor
    private boolean conectado;  //Estado de conexión del usuario con el servidor
    private Interface servidor; //Interface necesaria para la comunición con el objecto del servidor
    private GroupChatInterface servidorChat;
    private InterfazTableroServer servidorTablero;
    
    public ConexionCliente() {
        this.conectado = false;
        this.registry = null;
        this.servidor = null;
        this.servidorChat=null;
        this.servidorTablero=null;
    }

    public boolean iniciarRegistro(String IP, int Puerto, String nombreObjetoRemoto) throws RemoteException {
        try {
            
            //Se le otorga el permiso necesario para poder ejecutar las funciones correspondientes
            java.security.AllPermission allPermision = new java.security.AllPermission();          
            System.setProperty("java.security.policy", "rmi.policy");

            //Se inicia RMI-Registry para el registro del objeto
            try {
                //Obtenemos el Registry del servidor RMI
                registry = LocateRegistry.getRegistry(IP, Puerto);

            //De existir algún error con el registro que se desea obtener del servidor, se mostrará un mensaje
            } catch (RemoteException e) {
                System.out.println(IP + ":" + Puerto);
                System.out.println(e.getMessage());
                System.out.println(e.toString());
            }

            //Vamos al Registry y miramos el Objeto "nombreObjRemoto" para poder usarlo.
            servidor = (Interface) registry.lookup(nombreObjetoRemoto);

            this.conectado = true;
            return true;
            
        //De existir algún error con la conexión al servidor, se mostrará un mensaje
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: No se posee conexión");
            
            this.conectado = false;
            return false;
        }

    }
    
        public boolean iniciarRegistroChat(String IP, int Puerto, String nombreObjetoRemoto) throws RemoteException {
        try {
            
            //Se le otorga el permiso necesario para poder ejecutar las funciones correspondientes
            java.security.AllPermission allPermision = new java.security.AllPermission();          
            System.setProperty("java.security.policy", "rmi.policy");

            //Se inicia RMI-Registry para el registro del objeto
            try {
                //Obtenemos el Registry del servidor RMI
                registry = LocateRegistry.getRegistry(IP, Puerto);

            //De existir algún error con el registro que se desea obtener del servidor, se mostrará un mensaje
            } catch (RemoteException e) {
                System.out.println(IP + ":" + Puerto);
                System.out.println(e.getMessage());
                System.out.println(e.toString());
            }

            //Vamos al Registry y miramos el Objeto "nombreObjRemoto" para poder usarlo.
            servidorChat = (GroupChatInterface) registry.lookup(nombreObjetoRemoto);

            this.conectado = true;
            return true;
            
        //De existir algún error con la conexión al servidor, se mostrará un mensaje
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: No se posee conexión Chat");
            
            this.conectado = false;
            return false;
        }

    }
        
    public boolean iniciarRegistroTablero(String IP, int Puerto, String nombreObjetoRemoto) throws RemoteException {
        try {
            
            //Se le otorga el permiso necesario para poder ejecutar las funciones correspondientes
            java.security.AllPermission allPermision = new java.security.AllPermission();          
            System.setProperty("java.security.policy", "rmi.policy");

            //Se inicia RMI-Registry para el registro del objeto
            try {
                //Obtenemos el Registry del servidor RMI
                registry = LocateRegistry.getRegistry(IP, Puerto);

            //De existir algún error con el registro que se desea obtener del servidor, se mostrará un mensaje
            } catch (RemoteException e) {
                System.out.println(IP + ":" + Puerto);
                System.out.println(e.getMessage());
                System.out.println(e.toString());
            }

            //Vamos al Registry y miramos el Objeto "nombreObjRemoto" para poder usarlo.
            servidorTablero = (InterfazTableroServer) registry.lookup(nombreObjetoRemoto);

            this.conectado = true;
            return true;
            
        //De existir algún error con la conexión al servidor, se mostrará un mensaje
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: No se posee conexión Chat");
            
            this.conectado = false;
            return false;
        }

    }
    
    //Getting y Setting de los atributos de ConexionCliente

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public boolean getConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public Interface getServidor() {
        return servidor;
    }
    
    public GroupChatInterface getServidorChat() {
        return servidorChat;
    }
    
    public InterfazTableroServer getServidorTablero() {
        return servidorTablero;
    }
        

    public void setServidor(Interface servidor) {
        this.servidor = servidor;
    }
    
    public void setServidor(GroupChatInterface servidorEntrada) {
        this.servidorChat = servidorEntrada;
    }
    
    public void setServidor(InterfazTableroServer servidorEntrada) {
        this.servidorTablero = servidorEntrada;
    }
}