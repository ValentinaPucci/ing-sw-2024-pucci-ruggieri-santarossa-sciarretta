package it.polimi.demo.view.text;

import it.polimi.demo.model.ConcreteDeck;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

/**
 * Class that handles the graphics of the common board in the text user interface.
 */
public class TuiCommonBoardGraphics {

    /**
     * Method that shows the common board in the text user interface.
     * @param common_board the common board to be shown
     */
    public static void showCommonBoard(CommonBoard common_board) {
        int[][] grid = createGrid(common_board);
        printGridWithPlayers(grid, common_board);
    }

//    public static void printCommonCards(CommonBoard common_board){
//        Card[][] table_cards = common_board.getTableCards();
//        TuiCardGraphics.showResourceCard((ResourceCard) table_cards[0][0]);
//        TuiCardGraphics.showResourceCard((ResourceCard) table_cards[0][1]);
//        TuiCardGraphics.showGoldCard((GoldCard) table_cards[1][0]);
//        TuiCardGraphics.showGoldCard((GoldCard) table_cards[1][1]);
//        TuiCardGraphics.showObjectiveCard((ObjectiveCard) table_cards[2][0]);
//        TuiCardGraphics.showObjectiveCard((ObjectiveCard) table_cards[2][1]);
//    }
//
//    public static void printCommonDecks(CommonBoard common_board){
//        //ConcreteDeck objective_deck = common_board.getObjectiveConcreteDeck();
//        ConcreteDeck resource_deck = common_board.getResourceConcreteDeck();
//        ConcreteDeck gold_deck = common_board.getGoldConcreteDeck();
//
//        TuiCardGraphics.showGoldCard(gold_deck.selectFirstGoldCard());
//        TuiCardGraphics.showResourceCard(resource_deck.selectFirstResourceCard());
//    }

    /**
     * Method that prints the grid with the players in the text user interface.
     * @param grid the grid to be printed
     * @param common_board the common board that contains the players
     */
    public static void printGridWithPlayers(int[][] grid, CommonBoard common_board) {

        char[][] players = new char[common_board.getPlayerCount() + 1][30];

        printHorizontalLine(grid[0].length);
        players[1][common_board.getPlayerPosition(0)] = 'B';
        players[2][common_board.getPlayerPosition(1)] = 'G';
        if (common_board.getPlayerCount() == 3)
            players[3][common_board.getPlayerPosition(2)] = 'R';
        else if (common_board.getPlayerCount() == 4)
            players[3][common_board.getPlayerPosition(2)] = 'R';
            players[4][common_board.getPlayerPosition(3)] = 'Y';

        for (int i = 0; i < grid.length; i++) {
            System.out.print("│");
            for (int j = 0; j < grid[0].length; j++) {
                char playerSymbol = players[i][j];
                if (playerSymbol != '\u0000') {
                    switch (playerSymbol) {
                        case 'R':
                            System.out.print("\u001B[31m " + playerSymbol + " \u001B[0m");
                            break;
                        case 'B':
                            System.out.print("\u001B[34m " + playerSymbol + " \u001B[0m");
                            break;
                        case 'G':
                            System.out.print("\u001B[32m " + playerSymbol + " \u001B[0m");
                            break;
                        case 'Y':
                            System.out.print("\u001B[33m " + playerSymbol + " \u001B[0m");
                            break;
                        default:
                            System.out.print(" " + playerSymbol + " ");
                            break;
                    }
                } else {
                    if (i == 0) {
                        System.out.printf("%3d", grid[i][j]);
                    } else {
                        System.out.print("   ");
                    }
                }

                System.out.print("│");
            }
            System.out.println();
            printHorizontalLine(grid[0].length);
        }
    }

    /**
     * Method that creates the grid for the common board.
     * @param common_board the common board that contains the players
     * @return the grid
     */
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

    /**
     * Method that prints a horizontal line in the text user interface.
     * @param columns the number of columns of the line
     */
    public static void printHorizontalLine(int columns) {
        for (int i = 0; i < columns; i++) {
            System.out.print("────" ); // "---+"
        }
        System.out.println();
    }
}
