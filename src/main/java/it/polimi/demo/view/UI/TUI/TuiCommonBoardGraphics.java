package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.CommonBoardNode;

public class TuiCommonBoardGraphics {

    public static void showCommonBoard(CommonBoard common_board) {
        int[][] grid = createGrid(common_board);
        printGridWithPlayers(grid, common_board);
    }


    public static void printGridWithPlayers(int[][] grid, CommonBoard common_board) {

        char[][] players = new char[common_board.getPlayerCount() + 1][30];

        // Stampa la riga superiore della griglia
        printHorizontalLine(grid[0].length);
        //stampa le altre righe
        players[1][common_board.getPlayerPosition(0)] = 'R';
        players[2][common_board.getPlayerPosition(1)] = 'B';
        if (common_board.getPlayerCount() == 3)
            players[3][common_board.getPlayerPosition(2)] = 'G';
        else if (common_board.getPlayerCount() == 4)
            players[4][common_board.getPlayerPosition(3)] = 'Y';


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

    public static int[][] createGrid(CommonBoard common_board) {
        int rows = common_board.getPlayerCount() + 1;
        int columns = 30;

        int[][] grid = new int[rows][columns];
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = count++;
            }
        }

        return grid;
    }


    public static void printHorizontalLine(int columns) {
        System.out.print("+"); // Angolo in alto a sinistra
        for (int i = 0; i < columns; i++) {
            System.out.print("---+"); // Linea orizzontale
        }
        System.out.println(); // Nuova riga
    }

    public static void main(String[] args) {

        CommonBoard common_board = new CommonBoard();

        common_board.setPlayerCount(3);
        common_board.setInitialPosition();
        showCommonBoard(common_board);
    }
}
