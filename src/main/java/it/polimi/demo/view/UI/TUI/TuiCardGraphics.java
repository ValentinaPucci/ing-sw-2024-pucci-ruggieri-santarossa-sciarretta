package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

import java.util.ArrayList;

//TODO: disegnare starter card front e back
//TODO: disegnare objective card

public class TuiCardGraphics {

    // Sequenze di escape ANSI per colorare il testo e lo sfondo
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    // Rappresentazione grafica della carta
    private static final String[] CARD_GRAPHICS = {
            "╭──────────────╮",
            "│ %s          %s │",
            "│              │",
            "│ %s          %s │",
            "╰──────────────╯"
    };

    private String corner1;
    private String corner2;
    private String corner3;
    private String corner4;
    private int points;


    //Resource Card creation
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.points = 0;
    }

    //Gold Card creation
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4, int points) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.points = points;
    }


    public void printCard(String Color) {
        System.out.println(ANSI_RESET + CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < CARD_GRAPHICS.length - 1; i++) {
            if (i == 1){
                String row = String.format(CARD_GRAPHICS[i], corner1, corner2);
                System.out.println(ANSI_RESET  + row.substring(0,4) + Color+ row.substring(4,12)+ Color+ ANSI_RESET + row.substring(12,16) + ANSI_RESET);
            }
            else if(i== 3){
                String row3 = String.format(CARD_GRAPHICS[i], corner3, corner4);
                //String colored_row3 = coloraRiga(String.format(CARD_GRAPHICS[i], corner3, corner4), Color, 2, 3);
                System.out.println(ANSI_RESET  + row3.substring(0,4) + Color+ row3.substring(4,12)+ Color+ ANSI_RESET + row3.substring(12,16) + ANSI_RESET);
            } else{
                String row4 = String.format(CARD_GRAPHICS[i]);
                System.out.println(ANSI_RESET  + row4.substring(0,2) + Color+ row4.substring(2,14)+ Color+ ANSI_RESET + row4.substring(14,16) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

        if(points != 0){
            System.out.print("Card points: " + points);
        }
    }

    public static void showResourceCard(ResourceCard resource_card){

        Color card_color = resource_card.getColor();
        String graphic_color = null;
        switch (card_color){
            case Color.RED ->
                    graphic_color = ANSI_RED_BACKGROUND;
            case Color.BLUE ->
                    graphic_color = ANSI_CYAN_BACKGROUND;
            case Color.GREEN ->
                    graphic_color = ANSI_GREEN_BACKGROUND;
            case Color.PURPLE ->
                    graphic_color = ANSI_PURPLE_BACKGROUND;
        }

        if(resource_card.orientation == Orientation.BACK){
            TuiCardGraphics graphic_card = new TuiCardGraphics(" ", " ", " ", " ");
            graphic_card.printCard(graphic_color);
        }
        else {
            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(resource_card.getCornerAtNW());
            corners.add(resource_card.getCornerAtNE());
            corners.add(resource_card.getCornerAtSW());
            corners.add(resource_card.getCornerAtSE());

            ArrayList<String> corners_string = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (corners.get(i).is_visible){
                    if (corners.get(i).item.isPresent()) {
                        Item item = corners.get(i).getItem();
                        System.out.println(item);
                        if (item == Item.FEATHER)
                            corners_string.add(i, "FE");
                        else if (item == Item.PARCHMENT)
                            corners_string.add(i, "PA");
                        else if (item == Item.POTION)
                            corners_string.add(i, "PO");
                        else
                            corners_string.add(i, "  ");
                    } else if (corners.get(i).resource.isPresent()){
                        Resource resource = corners.get(i).getResource();
                        if (resource == Resource.LEAF)
                            corners_string.add(i, "LE");
                        else if (resource == Resource.BUTTERFLY)
                            corners_string.add(i, "BU");
                        else if (resource == Resource.WOLF)
                            corners_string.add(i, "W=");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    }else
                        corners_string.add(i, " ");
                }else {
                    corners_string.add(i, "NV");
                }
            }
            TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3));
            graphic_card.printCard(graphic_color);
        }
    }


    public static void showGoldCard(GoldCard gold_card){
        Color card_color = gold_card.getColor();
        String graphic_color = null;
        switch (card_color){
            case Color.RED ->
                    graphic_color = ANSI_RED_BACKGROUND;
            case Color.BLUE ->
                    graphic_color = ANSI_CYAN_BACKGROUND;
            case Color.GREEN ->
                    graphic_color = ANSI_GREEN_BACKGROUND;
            case Color.PURPLE ->
                    graphic_color = ANSI_PURPLE_BACKGROUND;
        }

        if(gold_card.orientation == Orientation.BACK){
            TuiCardGraphics graphic_card = new TuiCardGraphics(" ", " ", " ", " ");
            graphic_card.printCard(graphic_color);
        }
        else {
            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(gold_card.getCornerAtNW());
            corners.add(gold_card.getCornerAtNE());
            corners.add(gold_card.getCornerAtSW());
            corners.add(gold_card.getCornerAtSE());

            ArrayList<String> corners_string = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (corners.get(i).is_visible){
                    if (corners.get(i).item.isPresent()) {
                        Item item = corners.get(i).getItem();
                        if (item == Item.FEATHER)
                            corners_string.add(i, "FE");
                        else if (item == Item.PARCHMENT)
                            corners_string.add(i, "PA");
                        else if (item == Item.POTION)
                            corners_string.add(i, "PO");
                        else
                            corners_string.add(i, "  ");
                    } else if (corners.get(i).resource.isPresent()){
                        Resource resource = corners.get(i).getResource();
                        if (resource == Resource.LEAF)
                            corners_string.add(i, "LE");
                        else if (resource == Resource.BUTTERFLY)
                            corners_string.add(i, "BU");
                        else if (resource == Resource.WOLF)
                            corners_string.add(i, "W=");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    }else
                        corners_string.add(i, " ");
                }else {
                    corners_string.add(i, "NV");
                }
            }
            if(gold_card.getMushroomRequired() != 0)
                System.out.println("Mushroom required:" + gold_card.getMushroomRequired());
            if(gold_card.getLeafRequired() != 0)
                System.out.println("Leaf required:" + gold_card.getLeafRequired());
            if(gold_card.getButterflyRequired() != 0)
                System.out.println("Butterfly required:" + gold_card.getButterflyRequired());
            if(gold_card.getWolfRequired() != 0)
                System.out.println("Mushroom required:" + gold_card.getWolfRequired());
            if(gold_card.getIsCornerCoverageRequired())
                System.out.println("You earn 2 points for every corner that this card covers.");
            if(gold_card.getIsFeatherRequired())
                System.out.println("You earn a points for every feather present on you personal board.");
            if(gold_card.getIsPotionRequired())
                System.out.println("You earn a points for every potion present on you personal board.");
            if(gold_card.getIsParchmentRequired())
                System.out.println("You earn a points for every parchment present on you personal board.");

            TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3), gold_card.getPoints());
            graphic_card.printCard(graphic_color);
        }
    }

    public void showStarterCardFront(StarterCard starter_card){
    }
    public void showStarterCardBack(StarterCard starter_card){
    }

    public void showStarterCard(StarterCard starter_card){
        showStarterCardBack(starter_card);
        showStarterCardFront(starter_card);
    }

    public static void main(String[] args) {

        ResourceCard resource_card = new ResourceCard( 11, Orientation.FRONT, Color.GREEN);
        resource_card.getCornerAtNW().setEmpty();
        resource_card.getCornerAtNW().is_visible = false;
        resource_card.getCornerAtSW().setCornerResource(Resource.LEAF);
        resource_card.getCornerAtSE().setCornerResource(Resource.LEAF);
        showResourceCard(resource_card);



        GoldCard gold_card = new GoldCard( 67, Orientation.FRONT, Color.BLUE);
        gold_card.getCornerAtNW().setEmpty();
        gold_card.getCornerAtNW().is_visible = false;
        gold_card.getCornerAtSW().setCornerItem(Item.PARCHMENT);
        gold_card.getCornerAtSE().is_visible = false;
        gold_card.setCornerCoverageRequired();
        showGoldCard(gold_card);
    }
}