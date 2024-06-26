package it.polimi.demo.view.text;

import it.polimi.demo.model.board.Cell;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.enumerations.Color;

/**
 * This class is used to print the Personal Board of a player in the console.
 */
public class TuiPersonalBoardGraphics {

    private static final String ANSI_GREEN = "\u001B[32m ";
    private static final String ANSI_RED = "\u001B[31m ";
    private static final String ANSI_PURPLE = "\u001B[35m ";
    private static final String ANSI_CYAN = "\u001B[34m ";
    private static final String ANSI_RST = "\u001B[0m";

    /**
     * This method prints the Personal Board of a player in the console.
     * @param personal_board the Personal Board to be printed
     */
    public static void showPersonalBoard(PersonalBoard personal_board) {

        showObjectsCount(personal_board);
        Cell[][] matrix = personal_board.getBoard();

        int first_row = 15;
        int last_row = 35;
        int first_column = 15;
        int last_column = 35;

        printHorizontalLine(10);
        for (int i = first_row; i <= last_row; i++) {
            System.out.print("|");
            for (int j = first_column; j <= last_column; j++) {
                // auxiliary print ************************************************************************************************
                if (matrix[i][j].getCornerFromCell() != null && matrix[i][j].getCornerFromCell().reference_card.getId() == -1){
                    System.out.print(" S ");
                }
                // ****************************************************************************************************************
                else if (matrix[i][j].getCornerFromCell() != null && matrix[i][j].getCornerFromCell().reference_card != null) {
                    if (matrix[i][j].getCornerFromCell().reference_card.getColor().equals(Color.RED)){
                        System.out.print(ANSI_RED + matrix[i][j].getCornerFromCell().reference_card.getId()+ANSI_RST);

                    } else if (matrix[i][j].getCornerFromCell().reference_card.getColor().equals(Color.GREEN)){
                        System.out.print(ANSI_GREEN + matrix[i][j].getCornerFromCell().reference_card.getId()+ANSI_RST);

                    } else if (matrix[i][j].getCornerFromCell().reference_card.getColor().equals(Color.BLUE)){
                        System.out.print(ANSI_CYAN + matrix[i][j].getCornerFromCell().reference_card.getId()+ANSI_RST);

                    } else if (matrix[i][j].getCornerFromCell().reference_card.getColor().equals(Color.PURPLE)){
                        System.out.print(ANSI_PURPLE + matrix[i][j].getCornerFromCell().reference_card.getId()+ANSI_RST);
                    }
                } else {
                    System.out.print("   ");
                } System.out.print("│");//System.out.print("|");
            }
            System.out.println();
            printHorizontalLine(10);
        }
    }

    /**
     * This method prints a horizontal line in the console.
     * @param columns the number of columns of the line
     */
    public static void printHorizontalLine(int columns) {
        //System.out.print("+");
        for (int i = 15 ; i < 32; i++) {
            System.out.print("─────");
        }
        System.out.println();
    }

    /**
     * This method prints the number of objects on the Personal Board of a player in the console.
     * @param personal_board the Personal Board to be printed
     */
    public static void showObjectsCount(PersonalBoard personal_board) {
        System.out.println("Your currents point counter is: *** [" + personal_board.getPoints() + "] *** points.");
        System.out.println("These are the objects on your Personal Board:");
        System.out.println("Mushrooms: " + personal_board.getNum_mushrooms());
        System.out.println("Butterflies: " + personal_board.getNum_butterflies());
        System.out.println("Leaves: " + personal_board.getNum_leaves());
        System.out.println("Wolves: " + personal_board.getNum_wolves());
        System.out.println("Parchments: " + personal_board.getNum_parchments());
        System.out.println("Feathers: " + personal_board.getNum_feathers());
        System.out.println("Potions: " + personal_board.getNum_potions());
    }
}
