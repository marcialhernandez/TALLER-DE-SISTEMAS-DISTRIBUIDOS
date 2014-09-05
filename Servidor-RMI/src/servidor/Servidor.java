package servidor;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Implementacion;
import rmi.ServidorRMI;

/**
 *
 * @author Daniel Wladdimiro Cottet
 * @title Taller de sistemas distribuidos - Clase 1
 */
public class Servidor {

    public static ServidorRMI servidor;
    public static int puerto = 2014;
    public static Implementacion objetoLocal;
    public static String nombreReferenciaRemota = "Ejemplo-RMI";

    static Logger logger;

    public static void main(String[] args) {
        logger = Logger.getLogger("Servidor");

        //Se inicializa el objeto, el cual podrá ser llamado remotamente
        try {
            objetoLocal = new Implementacion();
        } catch (RemoteException re) {
            //En caso de haber un error, es mostrado por un mensaje
            logger.log(Level.SEVERE, re.getMessage());
        }

        //El objeto se dejerá disponible para una conexión remota
        logger.log(Level.INFO, "Se va a conectar...");

        servidor = new ServidorRMI();

        boolean resultadoConexion = servidor.iniciarConexion(objetoLocal, nombreReferenciaRemota, puerto);
        if (resultadoConexion) {
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
        } catch (RemoteException re) {
            //En caso de haber un error, es mostrado por un mensaje
            logger.log(Level.SEVERE, re.getMessage());
        }

        System.exit(0);
    }
}
