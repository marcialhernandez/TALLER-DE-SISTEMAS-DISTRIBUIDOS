package cliente;

import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.ConexionCliente;
import rmi_interface.*;
import tablero.*;
import static tablero.PaginaPrincipal.vacio;

/**
 *
 * @author Daniel Wladdimiro Cottet
 * @title Taller de sistemas distribuidos - Clase 1
 */

class escuchaChat extends Thread{
    
    MessengerInterface sesionActual;
    int banderaClase;
    
        escuchaChat(MessengerInterface mess,int banderaGlobal){
            sesionActual=(MessengerInterface)mess;
            banderaClase=(int)banderaGlobal;
        }
        
    @Override
        public void run(){
            try {
            while (banderaClase==0){
               if(sesionActual.comprobarMensajes()==true){
               System.out.println(sesionActual.imprimirMensaje());
               sleep(3000);  // 3second
                    }
               }
	    //WithThread.showElapsedTime("ThreadedPseudoIO finishes
	} catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(escuchaChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

//aqui

class hiloChat extends Thread{
    
    static volatile GroupChatInterface chat;
    Scanner entradaChat;
    static volatile MessengerInterface sesionCliente;
    boolean ingresoUsername;
    String username;
    String mensaje="";
    static volatile escuchaChat escuchandoServidor;
    static volatile int banderaRecepcionChat;
    //int banderaClase;
    String[] separadoEspacio;
    String[] usuarioDestino;
    String[] mensajeEfectivo;
    //String mensajeInterGrafico;
    private ArrayList mensajeInterGrafico;
    
        hiloChat(ConexionCliente conexionActual){
            
            chat=conexionActual.getServidorChat();
            entradaChat=new Scanner(System.in);
            sesionCliente = null;
            ingresoUsername=false;
            username="";
            mensaje="";
            banderaRecepcionChat=0;
        }
        
        
    @Override
        public void run(){
         
             try {
		      System.out.println("[System] Client Messenger is running");
                      System.out.println("[System] Ingrese su Nick");
                      while(ingresoUsername==false){
                      synchronized(this) {
                      username = entradaChat.nextLine();
                      }
                      sesionCliente=new Messenger(username,chat);
                      ingresoUsername=chat.login(sesionCliente);
                      if(ingresoUsername==false){
                          System.out.println("[System] Ya existe el usuario ingreso, intentelo de nuevo");
                          sesionCliente=null;
                      }
                      
                      }
		      chat.sendToAll("Just Connected,\n",sesionCliente);
                      //Revisa el buzon del usuario e imprime el mensaje
                      escuchandoServidor = new escuchaChat(sesionCliente,banderaRecepcionChat);
                      escuchandoServidor.start();
                      
                      while(! mensaje.equals("@exit")){
                          
                          mensaje = entradaChat.nextLine();
                          if( mensaje.equals("@exit")){
                              chat.sendToAll("Ha salido del chat",sesionCliente);
                              banderaRecepcionChat=1;
                              chat.unlogin(sesionCliente); //hay que parar el escucha
                          }
                          //forma de mandar mensaje
                          //@to Marcial:mensaje
                          else if (mensaje.startsWith("@to"))
                                  {
                                      separadoEspacio=mensaje.split(" ");
                                      usuarioDestino=separadoEspacio[1].split("::");
                                      if (chat.existe(usuarioDestino[0])){
                                          mensajeEfectivo =mensaje.split("::");
                                          chat.sendTo(mensajeEfectivo [1], sesionCliente, usuarioDestino[0]);
                                           //mandar mensaje
                                      }
                                      else{
                                          System.out.println("[System] No existe el usuario ingresado, intentelo de nuevo");
                                      }
                                  
                                  }
                          else{
                          chat.sendToAll(mensaje,sesionCliente);
                          sleep(1000);
                          }

	    	  }
                      
       }catch (RemoteException e) {
          System.out.println("Hello Client exception: " + e);
       } catch (InterruptedException ex) {
            Logger.getLogger(hiloChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }


public class Cliente {

    public static int Puerto = 2014;                                 //Número del puerto que está alojado el servidor
    public static String IPServer = "Localhost";                    //Dirección IP del servidor, la cual podría utilizarse por defecto el localhost
    public static String nombreReferenciaRemota = "Ejemplo-RMI";
    public static String nombreReferenciaRemotaChat = "rmi://localhost/ABCD";// Nombre del objeto subido
    public static String nombreReferenciaRemotaEco="Eco";
    public static int banderaEscucha=0; //con 0 Escucha, con 1 no
    public static int banderaEscrituraChat=0; //con 0 escribe, con 1 no
    hiloChat chatCliente=null;
    static volatile GroupChatInterface objetoRemotoChat;
    static boolean banderaChat=true;


    public static void main(String[] args) {

        Interface objetoRemoto; //Se crea un nuevo objeto llamado objetoRemoto
        objetoRemotoChat=null;
        ServicioEco objetoRemotoEco;

        //Se instancia el objeto que conecta con el servidor
        ConexionCliente conexion = new ConexionCliente();
        try {
            //Se conecta con el servidor
            if (conexion.iniciarRegistro(IPServer, Puerto, nombreReferenciaRemota) && conexion.iniciarRegistroChat(IPServer, Puerto, nombreReferenciaRemotaChat )  && conexion.iniciarRegistroEco(IPServer, Puerto, nombreReferenciaRemotaEco )) {

                //Se obtiene la referencia al objeto remoto
                objetoRemoto = conexion.getServidor();
                //objetoRemotoChat=conexion.getServidorChat();
                objetoRemotoEco=conexion.getServidorEco();

                int opcion = 0;
                while (opcion != 5) {

                    //Escoger alguna opción del menú
                    System.out.println("Menú RMI\n1. Ingresar un usuario al servidor\n2. Ver usuarios del servidor\n3. Ingresar al Chat\n4. Capitalizar \n5.Salir ");
                    Scanner sc = new Scanner(System.in);
                    opcion = Integer.parseInt(sc.next());

                    if (opcion == 1) {

                        System.out.println("Ingrese el nombre del usuario: ");
                        sc = new Scanner(System.in);
                        String usuario = sc.next();

                        //Llama a un método del objeto remoto, y se le ingresa un parámetro a éste método
                        boolean ingreso = objetoRemoto.ingresarUsuario(usuario);
                        if (ingreso) {
                            System.out.println("¡Felicitaciones, ha sido agregado el usuario!");
                        } else {
                            System.out.println("Lamentablemente no ha sido ingresado el usuario, pruebe con otro nombre...");
                        }

                    } else if (opcion == 2) {
                        //Llama a un método del objeto remoto
                        ArrayList<String> usuarios = objetoRemoto.verUsuarios();

                        for (String usuario : usuarios) {
                            System.out.println("Usuario: " + usuario);
                        }
                        
                    }else if (opcion == 3) {
                        
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                            new PaginaPrincipal(vacio).setVisible(true);                          
                            }
                        });
                        
                        hiloChat chatActual = new hiloChat(conexion);
                        chatActual.start();
                        while (banderaChat==true){
                            if (chatActual.isAlive()==false){
                                banderaChat=false;
                                }                            
                        }
    }    
                    else if (opcion == 4) {
                        //Llama a un método del objeto remoto
                        Scanner consola = new Scanner(System.in);
                        String palabra="";
                        while(! palabra.equals("@exit")){
                        System.out.println("Ingrese la palabra a capitalizar,\npara salir ingrese @exit");
                        palabra = consola.nextLine();
                        String salida=objetoRemotoEco.eco(palabra);
                        System.out.println("La palabra capitalizada es : " + salida);
                        }              
                    }
                      
                       else if (opcion != 5) {
                        System.out.println("Ingrese un número válido por favor...");
                    }
                }
            }

        } catch (NumberFormatException | RemoteException e) {
            System.out.println("Ha ocurrido un error");
        }

        System.exit(0);
    }
}
