package cliente;

import static cliente.Cliente.VentanaPrincipal;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;
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
class escuchaTurno extends Thread {

    ConexionCliente conexionActual;
    hiloChat hiloChatCliente;
    PaginaPrincipal ventanaCliente;

    escuchaTurno(ConexionCliente a, hiloChat b, PaginaPrincipal c) {
        conexionActual = a;
        hiloChatCliente = b;
        ventanaCliente = c;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //if(conexionActual.getServidorChat().getTurnoServ()==hiloChatCliente.turnoHilo()){
                synchronized (this) {
                    VentanaPrincipal.posicionFila=conexionActual.getServidorChat().getUbicacionRatonX();
                    VentanaPrincipal.posicionColumna=conexionActual.getServidorChat().getUbicacionRatonY();
                    VentanaPrincipal.jugadorTurno = conexionActual.getServidorChat().getTurnoServ();
                    ventanaCliente.revisaTurno();
                    //System.out.print("turCl="+hiloChatCliente.turnoHilo()+"turServ="+conexionActual.getServidorChat().getTurnoServ()+"\n");
                    // 3second
                }
                sleep(300);
                //}
            }
            //WithThread.showElapsedTime("ThreadedPseudoIO finishes
        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(escuchaChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//Hilo que se ejecuta en segundo plano cuando se ejecuta hiloChat
//Verifica cada 3 segundos si el buzon de messenger tiene mensajes
//En caso que tenga, saca el primer mensaje de la pila de mensajes
//Borra el primer mensaje de esa pila y vuelve a verificar
//En caso que no haya no retorna nada
class escuchaChat extends Thread {

    MessengerInterface sesionActual;
    int banderaClase;

    escuchaChat(MessengerInterface mess, int banderaGlobal) {
        sesionActual = (MessengerInterface) mess;
        banderaClase = (int) banderaGlobal;
    }

    @Override
    public void run() {
        try {
            while (banderaClase == 0) {
                if (sesionActual.comprobarMensajes() == true) {
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

class escuchaTablero extends Thread {

    ConexionCliente conexionActual;
    int aceptar;

    escuchaTablero(ConexionCliente entradaConexion) {
        conexionActual = entradaConexion;
        this.aceptar = 0;
    }

    escuchaTablero(ConexionCliente entradaConexion, int varGlobal) {
        conexionActual = entradaConexion;
        this.aceptar = varGlobal;
    }

    int getAceptar() {
        return this.aceptar;
    }

    @Override
    public void run() {
        try {
            while (true) {

                this.aceptar = conexionActual.getServidorChat().cantidadUsuarios();

                sleep(2000);  // 3second

            }

        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(escuchaTablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //WithThread.showElapsedTime("ThreadedPseudoIO finishes

}

//Hilo que se ejecuta en segundo plano mientras el jugador esta activo
//Toma la conexion del servidor y solicita el servicio GroupChat
//Asocia la session actual MessengerInterface con el servicio remoto
//Solicita y envia informacion al servidor de forma concurrente con la interfaz
class hiloChat extends Thread {

    static volatile GroupChatInterface chat;
    Scanner entradaChat;
    static volatile MessengerInterface sesionCliente;
    boolean ingresoUsername;
    String username;
    String mensaje = "";
    static volatile escuchaChat escuchandoServidor;
    static volatile int banderaRecepcionChat;
    //int banderaClase;
    String[] separadoEspacio;
    String[] usuarioDestino;
    String[] mensajeEfectivo;
    PaginaPrincipal ventana;
    int turno;

    //static volatile CustomInputStream messageToChat;
    hiloChat(ConexionCliente conexionActual) {

        chat = conexionActual.getServidorChat();
        entradaChat = new Scanner(System.in);
        sesionCliente = null;
        ingresoUsername = false;
        username = "";
        mensaje = "";
        banderaRecepcionChat = 0;

    }

    public int turnoHilo() throws RemoteException {
        return this.sesionCliente.getTurnoCliente();
    }

    public int cantidadJugadores() throws RemoteException {
        return this.chat.cantidadUsuarios();
    }

    public String nombreClienteActual() throws RemoteException {
        return this.username;
    }

    @Override
    public void run() {

        try {
            System.out.println("[System] Iniciando partida...");
            System.out.println("[System] Ingrese su Nick");

            while (ingresoUsername == false) {
                synchronized (this) {

                    username = entradaChat.nextLine();
                }
                this.sesionCliente = new Messenger(username, chat);
                ingresoUsername = chat.login(sesionCliente);
                if (ingresoUsername == false) {
                    System.out.println("[System] Ya existe el usuario ingreso, intentelo de nuevo");
                    sesionCliente = null;
                }

            }

            this.sesionCliente.setTurnoCliente(chat.cantidadUsuarios());
            //System.out.println(sesionCliente.getTurnoCliente());
            chat.sendToAll("Just Connected,\n", sesionCliente);

            //Revisa el buzon del usuario e imprime el mensaje
            escuchandoServidor = new escuchaChat(sesionCliente, banderaRecepcionChat);
            escuchandoServidor.start();

            while (!mensaje.equals("@exit")) {

                mensaje = entradaChat.nextLine();
                if (mensaje.equals("@exit")) {
                    chat.sendToAll("Ha salido del chat", sesionCliente);
                    banderaRecepcionChat = 1;
                    chat.unlogin(sesionCliente); //hay que parar el escucha
                } //forma de mandar mensaje
                //@to Marcial:mensaje
                else if (mensaje.startsWith("@to")) {
                    separadoEspacio = mensaje.split(" ");
                    usuarioDestino = separadoEspacio[1].split("::");
                    if (chat.existe(usuarioDestino[0]) && mensaje.contains(usuarioDestino[0] + "::")) {
                        mensajeEfectivo = mensaje.split("::");
                        chat.sendTo(mensajeEfectivo[1], sesionCliente, usuarioDestino[0]);
                        //mandar mensaje
                    } else {
                        System.out.println("[System] No existe el usuario ingresado, intentelo de nuevo");
                    }

                } else {
                    System.out.println(chat.sendToAll(mensaje, sesionCliente));
                    sleep(1000);
                }

            }

        } catch (RemoteException e) {
            System.out.println("Exception: " + e);
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
    public static String nombreReferenciaRemotaTablero = "Tablero";
    public static int banderaEscucha = 0; //con 0 Escucha, con 1 no
    public static int banderaEscrituraChat = 0; //con 0 escribe, con 1 no

    hiloChat chatCliente = null;
    static volatile GroupChatInterface objetoRemotoChat;
    static boolean banderaChat = true;
    static PrintStream salidaToChat;
    static PaginaPrincipal VentanaPrincipal;
    static CustomInputStream entradaDesdeChat;
    static public volatile boolean aceptar = true;
    static int cantidadUsuariosON = 0;

    public static int[][] sacaMatriz(int[][] matrizz, int jugador, int cantidadJugadores, int ratonx, int ratony, int salidax, int saliday) {
        int[][] matrizValida = new int[10][10];
        if (cantidadJugadores == 2) {
            if (jugador == 1) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i][j] = matrizz[i][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 2) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i][j + 5] = matrizz[i][j + 5];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
        }
        if (cantidadJugadores == 4) {
            if (jugador == 1) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i][j] = matrizz[i][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 2) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 3) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i][j + 5] = matrizz[i][j + 5];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 4) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizValida[i + 5][j + 5] = matrizz[i + 5][j + 5];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }

        }
        if (cantidadJugadores == 5) {
            if (jugador == 1) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 2) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 2];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 3) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 4];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 4) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 6];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 5) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 8];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
        }

        if (cantidadJugadores == 10) {
            if (jugador == 1) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 2) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 2];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 3) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 4];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 4) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 6];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 5) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i][j] = matrizz[i][j + 8];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 6) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 2) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j + 2];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 3) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j + 4];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 4) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j + 6];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
            if (jugador == 5) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrizValida[i + 5][j] = matrizz[i + 5][j + 8];
                        matrizValida[ratonx][ratony] = 8;
                        matrizValida[salidax][saliday] = 5;
                    }
                }
                return matrizValida;
            }
        }

        return matrizValida;
    }

    public static void main(String[] args) throws IOException {

        Interface objetoRemoto; //Se crea un nuevo objeto llamado objetoRemoto
        objetoRemotoChat = null;
        InterfazTableroServer objetoRemotoTablero;

        //Se instancia el objeto que conecta con el servidor
        ConexionCliente conexion = new ConexionCliente();
        try {
            //Se conecta con el servidor
            if (conexion.iniciarRegistro(IPServer, Puerto, nombreReferenciaRemota) && conexion.iniciarRegistroChat(IPServer, Puerto, nombreReferenciaRemotaChat) && conexion.iniciarRegistroTablero(IPServer, Puerto, nombreReferenciaRemotaTablero)) {

                //Se obtiene la referencia al objeto remoto
                objetoRemoto = conexion.getServidor();
                //objetoRemotoChat=conexion.getServidorChat();
                objetoRemotoTablero = conexion.getServidorTablero();

                hiloChat chatActual = new hiloChat(conexion);
                VentanaPrincipal = new PaginaPrincipal(conexion);
                //escuchaTablero hiloCantidadPlayers = new escuchaTablero(conexion);

                chatActual.start();

                //hiloCantidadPlayers.start();
                cantidadUsuariosON = conexion.getServidorChat().cantidadUsuarios();

                //--------No iniciar hasta tener cantidad de jugadores
                while (cantidadUsuariosON != 2) {

                    cantidadUsuariosON = conexion.getServidorChat().cantidadUsuarios();

                }

                VentanaPrincipal.setMatriz(sacaMatriz(objetoRemotoTablero.getTablero(conexion.getServidorChat()), chatActual.turnoHilo(), cantidadUsuariosON, objetoRemotoTablero.getPosicion8X(), objetoRemotoTablero.getPosicion8Y(), objetoRemotoTablero.getPosicion5X(), objetoRemotoTablero.getPosicion5Y()));

                VentanaPrincipal.imprimirTablero();

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        VentanaPrincipal.setVisible(true);
                        VentanaPrincipal.cambiaSalida();
                        try {
                            VentanaPrincipal.numeroJugador = chatActual.turnoHilo();
                            VentanaPrincipal.ingresaTitulo(chatActual.nombreClienteActual());

                        } catch (RemoteException ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        escuchaTurno hiloTurno = new escuchaTurno(conexion, chatActual, VentanaPrincipal);
                        hiloTurno.start();

                    }
                });
                while (banderaChat == true) {
                    if (chatActual.isAlive() == false) {

                           //System.out.println("turnoCliente" + chatActual.turnoHilo() + "turnoservidor" + conexion.getServidorChat().getTurnoServ());
                        banderaChat = false;
                    }
                }
            }

        } catch (NumberFormatException | RemoteException e) {
            System.out.println("Ha ocurrido un error");
        }

        System.exit(0);
    }

}
