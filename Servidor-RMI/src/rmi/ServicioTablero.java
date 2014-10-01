/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import rmi_interface.*;

/**
 *
 * @author alvaro
 */
public final class ServicioTablero extends UnicastRemoteObject implements InterfazTableroServer {

    static int tablero2[][] = new int[10][10];
    static int tablero4[][] = new int[10][10];
    static int tablero5[][] = new int[10][10];
    static int tablero10[][] = new int[10][10];
    static int tableroNulo[][] = new int[10][10];
    String turnoActual;
    int posicion8X, posicion8Y;
    int posicion5X, posicion5Y;

    //public int cantidadUsuarios() throws RemoteException{
    public ServicioTablero() throws RemoteException {
        llenatablero2();
        llenatablero4();
        llenatablero5();
        llenatablero10();
        posicion8X=0;
        posicion8Y=0;
    }
    @Override
    public int getPosicion5X ()  throws RemoteException{
        return this.posicion5X;
    }
    
    @Override
    public int getPosicion5Y ()  throws RemoteException{
        return this.posicion5Y;
    }
    
    
    @Override
    public void setPosicion8 (int x, int y)  throws RemoteException{
        this.posicion8X=x;
        this.posicion8Y=y;
    }
    
    @Override
    public int getPosicion8X ()  throws RemoteException{
        return this.posicion8X;
    }
    
    @Override
    public int getPosicion8Y ()  throws RemoteException{
        return this.posicion8Y;
    }
    
    @Override
    public void setTurnoActual(String turno) throws RemoteException {
        this.turnoActual = turno;
    }

    @Override
    public String getTurnoActual() throws RemoteException {
        return this.turnoActual;
    }

    @Override
    public void setTablero(GroupChatInterface infoServer, int [][] tableroRecibido) throws RemoteException {
        
        if (infoServer.cantidadUsuarios() == 2) {
            tablero2=tableroRecibido;
        }
        if (infoServer.cantidadUsuarios() == 4) {
            tablero4=tableroRecibido;
        }
        if (infoServer.cantidadUsuarios() == 5) {
            tablero5=tableroRecibido; 
        }
        if (infoServer.cantidadUsuarios() == 10) {
            tablero10=tableroRecibido;
        }
        
    }
    public void saca5(int[][] tableroSacado){
        for(int k=0;k<10;k++){
            for(int q=0;q<10;q++){
                if(tableroSacado[k][q]==5){
                    posicion5X=k;
                    posicion5Y=q;
                }
            }
        }
    }
    @Override
    public int[][] getTablero(GroupChatInterface infoServer) throws RemoteException {
        
        if (infoServer.cantidadUsuarios() == 2) {
            saca5(tablero2);
            return tablero2;
        }
        if (infoServer.cantidadUsuarios() == 4) {
            saca5(tablero4);
            return tablero4;
        }
        if (infoServer.cantidadUsuarios() == 5) {
            saca5(tablero5);
            return tablero5;
        }
        if (infoServer.cantidadUsuarios() == 10) {
            saca5(tablero10);
            return tablero10;
        }
        return tableroNulo;
    }

    @Override
    public void llenatablero10() throws RemoteException {

        int matriz[][] = generaRandom(5, 2, 3);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i][j] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i][j + 2] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i][j + 4] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i][j + 6] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i][j + 8] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i + 5][j] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i + 5][j + 2] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i + 5][j + 4] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i + 5][j + 6] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                tablero10[i + 5][j + 8] = matriz[i][j];
            }
        }
        tablero10 = colocaRaton(tablero10);
        tablero10 = colocaSalida(tablero10, 2);
        /*for(int i = 0;i<10;i++){
         for(int j = 0;j<10;j++){
         System.out.print(tablero10[i][j]);
                
         }
         System.out.println();
         }*/
    }

    @Override
    public void llenatablero5() throws RemoteException {

        int matriz[][] = generaRandom(10, 2, 3);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                tablero5[i][j] = matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                tablero5[i][j + 2] = matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                tablero5[i][j + 4] = matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                tablero5[i][j + 6] = matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                tablero5[i][j + 8] = matriz[i][j];
            }
        }

        tablero5 = colocaRaton(tablero5);
        tablero5 = colocaSalida(tablero5, 5);
        /* for(int i = 0;i<10;i++){
         for(int j = 0;j<10;j++){
         System.out.print(tablero5[i][j]);
                
         }
         System.out.println();
         }*/
    }

    @Override
    public void llenatablero4() throws RemoteException {

        int matriz[][] = generaRandom(5, 5, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tablero4[i][j] = matriz[i][j];
            }
        }

        matriz = generaRandom(5, 5, 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tablero4[i][j + 5] = matriz[i][j];
            }
        }
        matriz = generaRandom(5, 5, 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tablero4[i + 5][j] = matriz[i][j];
            }
        }

        matriz = generaRandom(5, 5, 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tablero4[i + 5][j + 5] = matriz[i][j];
            }
        }

        tablero4 = colocaRaton(tablero4);
        tablero4 = colocaSalida(tablero4, 4);
        /*for(int i = 0;i<10;i++){
         for(int j = 0;j<10;j++){
         System.out.print(tablero4[i][j]);
                
         }
         System.out.println();
         }*/
    }

    @Override
    public final void llenatablero2() throws RemoteException {

        int matriz[][] = generaRandom(10, 5, 10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                tablero2[i][j] = matriz[i][j];
            }
        }
        matriz = generaRandom(10, 5, 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                tablero2[i][j + 5] = matriz[i][j];
            }
        }

        tablero2 = colocaRaton(tablero2);
        tablero2 = colocaSalida(tablero2, 2);
        /*for(int i = 0;i<10;i++){
         for(int j = 0;j<10;j++){
         System.out.print(tablero2[i][j]);
                
         }
         System.out.println();
         }*/
    }

    //genera una sub matriz con trampas aleatorias
    @Override
    public int[][] generaRandom(int i, int j, int nTrampas) throws RemoteException {

        int matriz[][] = new int[i][j];
        Random rnd1 = new Random();
        Random rnd2 = new Random();
        for (int k = 0; k < nTrampas; k++) {
            int filas = rnd1.nextInt(i);
            //System.out.print(filas);
            int columnas = rnd2.nextInt(j);
            //System.out.print(columnas);
            matriz[filas][columnas] = 1;
            //System.out.println();
        }
        if (!verificacionStndar(matriz)) {
            matriz = generaRandom(i, j, nTrampas);
        }

        return matriz;
    }

    @Override
    public int[][] colocaRaton(int matriz[][]) throws RemoteException {
        matriz[0][0] = 8;
        return matriz;
    }

    @Override
    public int[][] colocaSalida(int matriz[][], int inicio) throws RemoteException {

        int filas = numeroAzar();
        int columnas = numeroAzar();
        if (!validaSalida(inicio, filas, columnas)) {
            matriz = colocaSalida(matriz, inicio);
            return matriz;
        }
        matriz[filas][columnas] = 5;
        return matriz;
    }

    @Override
    public int numeroAzar() throws RemoteException {
        Random rnd = new Random();
        int numero = rnd.nextInt(10);
        return numero;
    }

    @Override
    public boolean validaSalida(int inicio, int fila, int columna) throws RemoteException {
        if (inicio == 2) {
            if ((fila == 0 && columna == 0) || (fila == 0 && columna == 5)) {
                return false;
            }
        }
        if (inicio == 4) {
            if ((fila == 0 && columna == 0) || (fila == 0 && columna == 5) || (fila == 5 && columna == 0) || (fila == 5 && columna == 5)) {
                return false;
            }
        }
        if (inicio == 5) {
            if ((fila == 0 && columna == 0) || (fila == 0 && columna == 2) || (fila == 0 && columna == 4) || (fila == 0 && columna == 6) || (fila == 0 && columna == 8)) {
                return false;
            }
        }
        if (inicio == 10) {
            if ((fila == 0 && columna == 0) || (fila == 0 && columna == 2) || (fila == 0 && columna == 4) || (fila == 0 && columna == 6) || (fila == 0 && columna == 8) || (fila == 5 && columna == 0) || (fila == 5 && columna == 2) || (fila == 5 && columna == 4) || (fila == 5 && columna == 6) || (fila == 5 && columna == 8)) {
                return false;
            }
        }
        return true;
    }

    // varifica si el raton puede salir del origen
    @Override
    public boolean verificacionStndar(int matriz[][]) throws RemoteException {

        if (matriz[0][0] == 1) {
            return false;
        }
        if (matriz[1][0] == 1 && matriz[0][1] == 1) {
            return false;
        }
        return true;
    }

    @Override
    public int[][] getTablero2() throws RemoteException {
        return tablero2;
    }

    @Override
    public int[][] getTablero4() throws RemoteException {
        return tablero4;
    }

    @Override
    public int[][] getTablero5() throws RemoteException {
        return tablero5;
    }

    @Override
    public int[][] getTablero10() throws RemoteException {
        return tablero10;
    }

    @Override
    public void setTablero2(int matriz[][]) throws RemoteException {
        tablero4 = matriz;
    }

    @Override
    public void setTablero4(int matriz[][]) {
        tablero4 = matriz;
    }

    @Override
    public void setTablero5(int matriz[][]) {
        tablero5 = matriz;
    }

    @Override
    public void setTablero10(int matriz[][]) {
        tablero10 = matriz;
    }
}
