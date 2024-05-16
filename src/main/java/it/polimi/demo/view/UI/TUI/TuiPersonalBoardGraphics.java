package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.board.Cell;
import it.polimi.demo.model.interfaces.PersonalBoardIC;

public class TuiPersonalBoardGraphics {

    public void showPersonalBoard(PersonalBoardIC personal_board){
        showObjectsCount(personal_board);
        Cell[][] matrix = personal_board.getBoard();


    }

    public void showObjectsCount(PersonalBoardIC personal_board){
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

    }
}
