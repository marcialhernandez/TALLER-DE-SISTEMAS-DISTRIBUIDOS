/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablero;
import java.util.Random;
/**
 *
 * @author alvaro
 */
public final class tablero_jugador {
    
    static int tablero2[][] = new int [10][10];
    static int tablero4[][] = new int [10][10];
    static int tablero5[][] = new int [10][10];
    static int tablero10[][] = new int [10][10];
    public tablero_jugador(){
        llenatablero2();
        llenatablero4();
        llenatablero5();
        llenatablero10();
        
    }

    public void llenatablero10(){
        
        int matriz[][] = generaRandom(5,2,3);
        
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i][j]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i][j+2]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i][j+4]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i][j+6]=matriz[i][j];
            }
        }
                matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i][j+8]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i+5][j]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i+5][j+2]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i+5][j+4]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i+5][j+6]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 2, 3);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<2;j++){
                tablero10[i+5][j+8]=matriz[i][j];
            }
        }                        
        tablero10=colocaRaton(tablero10);
        tablero10=colocaSalida(tablero10, 2);
        /*for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                System.out.print(tablero10[i][j]);
                
            }
            System.out.println();
        }*/
    }    
    public void llenatablero5(){
        
        int matriz[][] = generaRandom(10,2,3);
        
        for(int i = 0;i<10;i++){
            for(int j = 0;j<2;j++){
                tablero5[i][j]=matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for(int i = 0;i<10;i++){
            for(int j = 0;j<2;j++){
                tablero5[i][j+2]=matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for(int i = 0;i<10;i++){
            for(int j = 0;j<2;j++){
                tablero5[i][j+4]=matriz[i][j];
            }
        }
        matriz = generaRandom(10, 2, 3);
        for(int i = 0;i<10;i++){
            for(int j = 0;j<2;j++){
                tablero5[i][j+6]=matriz[i][j];
            }
        }
                matriz = generaRandom(10, 2, 3);
        for(int i = 0;i<10;i++){
            for(int j = 0;j<2;j++){
                tablero5[i][j+8]=matriz[i][j];
            }
        }
                        
        tablero5=colocaRaton(tablero5);
        tablero5=colocaSalida(tablero5, 5);
       /* for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                System.out.print(tablero5[i][j]);
                
            }
            System.out.println();
        }*/
    }
    
    public void llenatablero4(){
        
        int matriz[][] = generaRandom(5,5,5);
        
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                tablero4[i][j]=matriz[i][j];
            }
        }
        
        matriz = generaRandom(5, 5, 5);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                tablero4[i][j+5]=matriz[i][j];
            }
        }
        matriz = generaRandom(5, 5, 5);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                tablero4[i+5][j]=matriz[i][j];
            }
        }
        
        matriz = generaRandom(5, 5, 5);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                tablero4[i+5][j+5]=matriz[i][j];
            }
        }
        
        tablero4=colocaRaton(tablero4);
        tablero4=colocaSalida(tablero4, 4);
        /*for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                System.out.print(tablero4[i][j]);
                
            }
            System.out.println();
        }*/
    }
    
    public final void llenatablero2(){
        
        int matriz[][] = generaRandom(10,5,10);
        
        for(int i = 0;i<10;i++){
            for(int j = 0;j<5;j++){
                tablero2[i][j]=matriz[i][j];
            }
        }
        matriz = generaRandom(10, 5, 10);
        for(int i = 0;i<10;i++){
            for(int j = 0;j<5;j++){
                tablero2[i][j+5]=matriz[i][j];
            }
        }
        
        tablero2=colocaRaton(tablero2);
        tablero2=colocaSalida(tablero2, 2);
        /*for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                System.out.print(tablero2[i][j]);
                
            }
            System.out.println();
        }*/
    }
    //genera una sub matriz con trampas aleatorias
    public int[][] generaRandom(int i, int j, int nTrampas){
        
        int matriz[][] = new int [i][j];
        Random rnd1 = new Random();
        Random rnd2 = new Random();
        for(int k=0;k< nTrampas;k++){
            int filas = rnd1.nextInt(i);
            //System.out.print(filas);
            int columnas = rnd2.nextInt(j);
            //System.out.print(columnas);
            matriz[filas][columnas]=1;
            //System.out.println();
        }
        if(!verificacionStndar(matriz)){
            matriz = generaRandom(i, j, nTrampas);
        }

        return matriz;
    }
    
    public int[][] colocaRaton(int matriz[][]){
        matriz[0][0]=8;
        return matriz;
    }
    
    public int[][] colocaSalida(int matriz[][],int inicio){
        
        int filas = numeroAzar();
        int columnas = numeroAzar();
        if(!validaSalida(inicio, filas, columnas)){
            matriz=colocaSalida(matriz, inicio);
            return matriz;
        }
        matriz[filas][columnas]=5;
        return matriz;
    }
    
    private int numeroAzar(){
        Random rnd = new Random();
        int numero = rnd.nextInt(10);
        return numero;
    }
            
    boolean validaSalida(int inicio, int fila, int columna){
        if(inicio==2){
            if((fila==0 && columna==0)||(fila==0 && columna==5)){
                return false;
            }
        }
        if(inicio==4){
            if((fila==0 && columna==0)||(fila==0 && columna==5)||(fila==5 && columna==0)||(fila==5 && columna==5)){
                return false;
            }
        }
        if(inicio==5){
            if((fila==0 && columna==0)||(fila==0 && columna==2)||(fila==0 && columna==4)||(fila==0 && columna==6)||(fila==0 && columna==8)){
                return false;
            }
        }
        if(inicio==10){
             if((fila==0 && columna==0)||(fila==0 && columna==2)||(fila==0 && columna==4)||(fila==0 && columna==6)||(fila==0 && columna==8)||(fila==5 && columna==0)||(fila==5 && columna==2)||(fila==5 && columna==4)||(fila==5 && columna==6)||(fila==5 && columna==8)){
                return false;
            }
        }
    return true;
    }
    // varifica si el raton puede salir del origen
    boolean verificacionStndar(int matriz[][]){
    
        if(matriz[0][0]==1){
            return false;
        }
        if(matriz[1][0]==1 && matriz[0][1]==1){
            return false;
        }
        return true;
    }
    
    public int [][] getTablero2(){   
        return tablero2;
    }
    public int [][] getTablero4(){    
        return tablero4;
    }
    public int [][] getTablero5(){ 
        return tablero5;
    }
    public int [][] getTablero10(){
        return tablero10;
    }

    public void setTablero2(int matriz[][]){
        tablero4=matriz;
    }
    public void setTablero4(int matriz[][]){
        tablero4=matriz;
    }
    public void setTablero5(int matriz[][]){
        tablero5=matriz;
    }
    public void setTablero10(int matriz[][]){
        tablero10=matriz;
    }
}
