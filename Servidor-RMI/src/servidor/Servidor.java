package servidor;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.*;

/**
 *
 * @author Daniel Wladdimiro Cottet
 * @title Taller de sistemas distribuidos - Clase 1
 */
public class Servidor {

    public static ServidorRMI servidor;
    public static int puerto = 2014;
    public static Implementacion objetoLocal;
    public static GroupChat objetoLocalChat;
    public static ServicioTablero objetoLocalTablero;
    public static String nombreReferenciaRemota = "Ejemplo-RMI";

    /**
     *
     */
    public static String nombreReferenciaRemotaChat = "rmi://localhost/ABCD";// Nombre del objeto subido
    public static String nombreReferenciaRemotaTablero="Tablero";

    static Logger logger;

    public static void main(String[] args) {
        logger = Logger.getLogger("Servidor");

        //Se inicializa el objeto, el cual podrá ser llamado remotamente
        try {
            objetoLocal = new Implementacion();
            objetoLocalChat = new GroupChat();
            objetoLocalTablero = new ServicioTablero();
        } catch (RemoteException re) {
            //En caso de haber un error, es mostrado por un mensaje
            logger.log(Level.SEVERE, re.getMessage());
        }

        //El objeto se dejerá disponible para una conexión remota
        logger.log(Level.INFO, "Se va a conectar...");

        servidor = new ServidorRMI();

        boolean resultadoConexion = servidor.iniciarConexion(objetoLocal, nombreReferenciaRemota, puerto);
        boolean resultadoConexion2 = servidor.iniciarConexionChat(objetoLocalChat, nombreReferenciaRemotaChat, puerto);
        boolean resultadoConexion3 = servidor.iniciarConexionTablero(objetoLocalTablero, nombreReferenciaRemotaTablero, puerto);

        if (resultadoConexion && resultadoConexion2 && resultadoConexion3) {
            logger.log(Level.INFO, "Se ha establecido la conexión correctamente");
        } else {
            logger.log(Level.INFO, "Ha ocurrido un error al conectarse");
        }

        System.out.println("Presione cualquier tecla y luego Enter para desconectar el servidor...");
        Scanner lector = new Scanner(System.in);
        lector.next();

        //En caso que presione una tecla el administrador, se detiene el servicio
        try {
            servidor.detenerConexion(nombreReferenciaRemota);
            servidor.detenerConexion(nombreReferenciaRemotaChat);
            servidor.detenerConexion(nombreReferenciaRemotaTablero);

        } catch (RemoteException re) {
            //En caso de haber un error, es mostrado por un mensaje
            logger.log(Level.SEVERE, re.getMessage());
        }

        System.exit(0);
    }
}
