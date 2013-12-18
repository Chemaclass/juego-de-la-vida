/*
 * Conversor -  Copyright (C) 2013 
 * José María Valera Reales <chemaclass@outlook.es> Twitter: @Chemaclass
 * http://www.chemaclass.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package juegovida;

import java.util.Scanner;

/**
 *
 * @author Chemaclass
 * @version 1.0
 */
public class JuegoVida {

    private char tablero[][] = new char[20][20];
    private static final char VIVO = '1';
    private static final char MUERTO = ' ';

    public static void main(String[] args) {
       // new JuegoVida().doMain(args); // <- consola
        Vida.main(args); // <- gráfico
    }

    private void doMain(String[] args) {
        
        Juego juego = new Juego(tablero);
        juego.start();

        while (true) {
            if (new Scanner(System.in).next().equals("e")) {
                System.err.println("Ok! Ya nos vamos!");
                juego.stop();//rompo el juego
                break;//salgo del while
            }
        }


    }

    private class Juego extends Thread {

        private char tablero[][];

        public Juego(char tablero[][]) {
            this.tablero = tablero;
        }

        @Override
        public void run() {

            fillTablero();
            test();
            while (true) {
                checkTablero();

                showTablero();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.getMessage();
                }
            }

        }
        private int cont = 0;

        /**
         * Recorre el tablero comprobando en todas las casillas sus
         * alrrededores. Comprobamos su estado. Si está 0: si tiene 3 activos
         * entonces 1. Si está 1: si tiene 2 o 3 activos entonces 1. Si está 1:
         * si tiene menos de 2 activos entonces 0
         */
        private void checkTablero() {
            boolean flag;
            for (int i = 0; i < tablero.length - 1; i++) {
                for (int j = 0; j < tablero[i].length - 1; j++) {
                    char casilla = tablero[i][j];
                    //Si la célula está muerta
                    if (casilla == MUERTO) {
                        //puede nacer o seguir muerta
                        flag = checkCasilla(false, i, j);
                    } 
                    //Si la célula está viva
                    else {
                        //puede sobrevivir o morir
                        flag = checkCasilla(true, i, j);
                    }
                    if (flag) {
                        tablero[i][j] = VIVO;
                    } else {
                        tablero[i][j] = MUERTO;
                    }
                }
            }
        }

        /**
         * Comprobar la célula
         * @param flag muestra el estado actual de la célula
         * @param i coordenada en el eje x de la matriz
         * @param j coordenada en el eje y de la matriz
         * @return si debe vivir, sobrevivir o morir
         */
        private boolean checkCasilla(boolean flag, int i, int j) {
            int count = 0;
            try{
                if(tablero[i-1][j-1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i-1][j] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i-1][j+1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i][j-1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            //if(tablero[i][j] == '1')count++;//la casilla en cuestión
            try{
                if(tablero[i][j+1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i+1][j-1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i+1][j] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            try{
                if(tablero[i+1][j+1] == VIVO)count++;
            }catch(java.lang.ArrayIndexOutOfBoundsException e){}
            //Si está muerta y tiene 3 o más vivas 
            if(!flag && count >= 3) return true;
            //si está viva y tiene 2 o 3 vivas sigue viva
            if(flag && count==2 || count==3) return true;
            //de lo contrario muere
            return false;
        }


        private void fillTablero() {
            for (int i = 0; i < tablero.length - 1; i++) {
                for (int j = 0; j < tablero[i].length - 1; j++) {
                    tablero[i][j] = MUERTO;
                }
            }
        }

        private void showTablero() {
            System.err.println("-----------------");
            for (int i = 0; i < tablero.length - 1; i++) {
                for (int j = 0; j < tablero[i].length; j++) {
                    System.out.print(tablero[i][j] + " ");
                }
                System.out.println("");
            }
        }

        /**
         * Colocar unos valores de prueba
         */
        private void test() {
            tablero[0][0] = VIVO;
            tablero[0][1] = VIVO;
            tablero[1][0] = VIVO;
            tablero[2][1] = VIVO;
            tablero[3][1] = VIVO;
            tablero[3][2] = VIVO;
            
        }
    }
}
