package it.polimi.demo.view.UI.TUI;

public class CommonGraphics {


    public static void printGridWithPlayers(int[][] grid, char[][] players) {
        // Stampa la riga superiore della griglia
        printHorizontalLine(grid[0].length);

        // Stampa le righe interne della griglia
        for (int i = 0; i < grid.length; i++) {
            System.out.print("|"); // Linea di delimitazione sinistra
            for (int j = 0; j < grid[0].length; j++) {
                char playerSymbol = players[i][j];
                if (playerSymbol != '\u0000') { // Controllo se il simbolo del giocatore è presente
                    switch (playerSymbol) {
                        case 'R':
                            System.out.print("\u001B[31m " + playerSymbol + " \u001B[0m"); // Rosso
                            break;
                        case 'B':
                            System.out.print("\u001B[34m " + playerSymbol + " \u001B[0m"); // Blu
                            break;
                        case 'G':
                            System.out.print("\u001B[32m V \u001B[0m"); // Verde
                            break;
                        case 'Y':
                            System.out.print("\u001B[33m G \u001B[0m"); // Giallo
                            break;
                        default:
                            System.out.print(" " + playerSymbol + " ");
                            break;
                    }
                } else {
                    if (i == 0) {
                        System.out.printf("%3d", grid[i][j]); // Numero nella cella solo nella prima riga
                    } else {
                        System.out.print("   "); // Cella vuota senza numero
                    }
                }
                System.out.print("|"); // Linea di delimitazione destra
            }
            System.out.println(); // Nuova riga
            printHorizontalLine(grid[0].length); // Linea orizzontale
        }
    }


    public static void printHorizontalLine(int columns) {
            System.out.print("+"); // Angolo in alto a sinistra
            for (int i = 0; i < columns; i++) {
                System.out.print("---+"); // Linea orizzontale
            }
            System.out.println(); // Nuova riga
        }

        public static void main(String[] args) {

            int rows = 5;
            int columns = 30;

            int[][] grid = new int[rows][columns];
            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    grid[i][j] = count++;
                }
            }

            char[][] players = new char[rows][columns];

            players[1][1] = 'R';
            players[2][2] = 'B';
            players[3][3] = 'G';
            players[4][4] = 'Y';

            System.out.println("CommonBoard with players:");
            printGridWithPlayers(grid, players);
        }









}
