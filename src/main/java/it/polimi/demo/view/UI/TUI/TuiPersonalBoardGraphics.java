package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.board.Cell;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;
import it.polimi.demo.model.interfaces.PersonalBoardIC;

public class TuiPersonalBoardGraphics {

    private static final String ANSI_GREEN = "\u001B[32m ";
    private static final String ANSI_RED = "\u001B[31m ";
    private static final String ANSI_PURPLE = "\u001B[35m ";
    private static final String ANSI_CYAN = "\u001B[34m ";
    private static final String ANSI_RST = "\u001B[0m";



    public static void showPersonalBoard(PersonalBoard personal_board) {

//        ResourceCard resource_card = new ResourceCard( 76, Orientation.FRONT, Color.GREEN);
//        resource_card.getCornerAtNW().setEmpty();
//        resource_card.getCornerAtNW().is_visible = false;
//        resource_card.getCornerAtSW().setCornerResource(Resource.LEAF);
//        resource_card.getCornerAtSE().setCornerResource(Resource.LEAF);
//
//        ResourceCard resource_card_1 = new ResourceCard( 33, Orientation.FRONT, Color.RED);
//        resource_card.getCornerAtNW().setEmpty();
//        resource_card.getCornerAtNW().is_visible = false;
//        resource_card.getCornerAtSW().setCornerResource(Resource.WOLF);
//        resource_card.getCornerAtSE().setCornerResource(Resource.LEAF);
//
//        GoldCard gold_card = new GoldCard( 11, Orientation.FRONT, Color.BLUE);
//        gold_card.getCornerAtNW().setEmpty();
//        gold_card.getCornerAtNW().is_visible = false;
//        gold_card.getCornerAtSW().setCornerItem(Item.PARCHMENT);
//        gold_card.getCornerAtSE().is_visible = false;
//        gold_card.setCornerCoverageRequired();
//
//        GoldCard gold_card_1 = new GoldCard( 54, Orientation.FRONT, Color.PURPLE);
//        gold_card.getCornerAtNW().setEmpty();
//        gold_card.getCornerAtNW().is_visible = false;
//        gold_card.getCornerAtSW().setCornerItem(Item.POTION);
//        gold_card.getCornerAtSE().is_visible = false;
//        gold_card.setCornerCoverageRequired();
//
//        StarterCard starterCard = new StarterCard(86, Orientation.BACK);
//        starterCard.getCornerAtNW().setEmpty();
//        gold_card.getCornerAtNW().is_visible = false;
//        gold_card.getCornerAtSW().setCornerResource(Resource.LEAF);
//        gold_card.getCornerAtSE().is_visible = false;
//
//        personal_board.bruteForcePlaceCardSE(starterCard, 500, 500);
//
//        personal_board.bruteForcePlaceCardSE(resource_card, 491, 491);
//        personal_board.bruteForcePlaceCardSE(gold_card, 499, 499);
//        personal_board.bruteForcePlaceCardSE(resource_card_1, 502, 502);
//        personal_board.bruteForcePlaceCardSE(gold_card_1, 498, 498);


        showObjectsCount(personal_board);
        Cell[][] matrix = personal_board.getBoard();

        int first_row = 490;
        int last_row = 510;
        int first_column = 490;
        int last_column = 510;

        printHorizontalLine(20);
        for (int i = first_row; i <= last_row; i++) {
            System.out.print("│");
            for (int j = first_column; j <= last_column; j++) {
                if (matrix[i][j].getCornerFromCell() != null && matrix[i][j].getCornerFromCell().reference_card == null){
                    System.out.print(" S ");
                }
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
            printHorizontalLine(20);
        }
    }

    public static void printHorizontalLine(int columns) {
        //System.out.print("+");
        for (int i = 490 ; i < 507; i++) {
            System.out.print("─────");
        }
        System.out.println();
    }


    public static void showObjectsCount(PersonalBoard personal_board){
        System.out.println("These are the objects on your Personal Board:");
        System.out.println("Mushrooms: " + personal_board.getNum_mushrooms());
        System.out.println("Butterflies: " + personal_board.getNum_butterflies());
        System.out.println("Leaves: " + personal_board.getNum_leaves());
        System.out.println("Wolves: " + personal_board.getNum_wolves());
        System.out.println("Parchments: " + personal_board.getNum_parchments());
        System.out.println("Feathers: " + personal_board.getNum_feathers());
        System.out.println("Potions: " + personal_board.getNum_potions());
    }


    public static void main(String[] args) {
        PersonalBoard personal_board = new PersonalBoard();
        showPersonalBoard(personal_board);
    }
}
