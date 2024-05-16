package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.ConcreteDeck;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

import java.util.ArrayList;

//TODO: place in the correct position the front_resource when there is only one, in starter card front
//TODO: disegnare objective card
// Remark: if you have to print the back of a card you have to specify it before calling this class

public class TuiCardGraphics {

    // Sequenze di escape ANSI per colorare il testo e lo sfondo
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_BEIGE_BACKGROUND = "\u001B[48;5;230m";

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

    private static final String[] STARTER_CARD_GRAPHICS ={
            "╭───────────────╮",
            "│ %s    %s    %s │",
            "│       %s       │",
            "│ %s    %s    %s │",
            "╰───────────────╯"
    };
    private String front_resource1;
    private String front_resource2;
    private String front_resource3;

    private static final String[] PATTERN_OBJECTIVE_CARD_GRAPHICS = {
            "╭───────────────╮",
            "│            %d │",
            "│                │",
            "│               │",
            "╰───────────────╯"
    };

    private static final String[] OBJECTIVE_CARD_GRAPHICS = {
            "╭──────────────╮",
            "│    Points: %d │",
            "│              │",
            "│ %s   %s   %s │",
            "╰───────────────╯"
    };
    private String el1;
    private String el2;
    private String el3;


    //Resource Card creation
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.points = 0;
    }

    // Starter Card constructor
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4, String front_resource1, String front_resource2, String front_resource3) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.front_resource1 = front_resource1;
        this.front_resource2 = front_resource2;
        this.front_resource3 = front_resource3;
    }
    public TuiCardGraphics(String el1, String el2, String el3, int points ){
        this.el1 = el1;
        this.el2 = el2;
        this.el3 = el3;
        this.points = points;
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
                System.out.println(ANSI_RESET  + row.substring(0,4) + Color+ row.substring(4,12)+ ANSI_RESET + row.substring(13,17) + ANSI_RESET);
            }
            else if(i== 3){
                String row3 = String.format(CARD_GRAPHICS[i], corner3, corner4);
                System.out.println(ANSI_RESET  + row3.substring(0,4) + Color+ row3.substring(4,12)+ ANSI_RESET + row3.substring(13,17) + ANSI_RESET);
            } else{
                String row4 = String.format(CARD_GRAPHICS[i]);
                System.out.println(ANSI_RESET  + row4.substring(0,2) + Color+ row4.substring(2,14)+ ANSI_RESET + row4.substring(14,16) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

        if(points != 0){
            System.out.print("Card points: " + points);
        }
    }

    public void printStarterCard(String Color) {
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < STARTER_CARD_GRAPHICS.length - 1; i++) {
            if (i == 1){
                String row = String.format(STARTER_CARD_GRAPHICS[i], corner1, front_resource1, corner2);
                System.out.println(ANSI_RESET  + row.substring(0,4) + Color + row.substring(4,7) + ANSI_RESET + row.substring(8,10) + Color + row.substring(10,14) + Color+ ANSI_RESET + row.substring(14,18) + ANSI_RESET);
            }
            else if(i== 3){
                String row3 = String.format(STARTER_CARD_GRAPHICS[i], corner3, front_resource3, corner4);

                System.out.println(ANSI_RESET  + row3.substring(0,4) + Color + row3.substring(4,7) + ANSI_RESET + row3.substring(8,10) + Color + row3.substring(10,14) + Color+ ANSI_RESET + row3.substring(14,18) + ANSI_RESET);
            } else if (i == 2){
                String row2 = String.format(STARTER_CARD_GRAPHICS[i], front_resource2);
                System.out.println(ANSI_RESET  + row2.substring(0,2) + Color+ row2.substring(2,7) + ANSI_RESET + row2.substring(8,10) + Color + row2.substring(10,16) + ANSI_RESET + row2.substring(16,18) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");
    }

    public void printObjectiveCard(){
        System.out.println(OBJECTIVE_CARD_GRAPHICS[0]);
        for (int i = 1; i < CARD_GRAPHICS.length - 1; i++) {
            if (i == 1){
                System.out.println(String.format(OBJECTIVE_CARD_GRAPHICS[i], points));
            }
            else if(i== 3){
                System.out.println(String.format(OBJECTIVE_CARD_GRAPHICS[i], el1, el2, el3));
            } else{
                System.out.println(OBJECTIVE_CARD_GRAPHICS[i]);
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

    }

    public void printIncrDiagPatternObjectiveCard(Color color){
        System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < STARTER_CARD_GRAPHICS.length - 1; i++) {
            if (i == 1){
                String row = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[1], points);
                System.out.println(ANSI_RESET + row.substring(0,6)+ color + row.substring(6,7));
            }
            else if(i== 3){
                String row3 = String.format(STARTER_CARD_GRAPHICS[i], corner3, front_resource3, corner4);

                System.out.println(ANSI_RESET  + row3.substring(0,4) + Color + row3.substring(4,7) + ANSI_RESET + row3.substring(8,10) + Color + row3.substring(10,14) + Color+ ANSI_RESET + row3.substring(14,18) + ANSI_RESET);
            } else if (i == 2){
                String row2 = String.format(STARTER_CARD_GRAPHICS[i], front_resource2);
                System.out.println(ANSI_RESET  + row2.substring(0,2) + Color+ row2.substring(2,7) + ANSI_RESET + row2.substring(8,10) + Color + row2.substring(10,16) + ANSI_RESET + row2.substring(16,18) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

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
                            corners_string.add(i, "WO");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    }else
                        corners_string.add(i, " ");
                }else {
                    corners_string.add(i, "XX");
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
                            corners_string.add(i, "WO");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    }else
                        corners_string.add(i, "  ");
                }else {
                    corners_string.add(i, "XX");
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

    public static void showStarterCardFront(StarterCard starter_card){
        ArrayList<Corner> corners = new ArrayList<>();
        corners.add(starter_card.getCornerAtNW());
        corners.add(starter_card.getCornerAtNE());
        corners.add(starter_card.getCornerAtSW());
        corners.add(starter_card.getCornerAtSE());

        ArrayList<String> corners_string = new ArrayList<>();
        String front_resource1 = null;
        String front_resource2 = null;
        String front_resource3 = null;

        for (int i = 0; i < 4; i++) {
            if (corners.get(i).is_visible){
                if (corners.get(i).item != null && corners.get(i).item.isPresent()) {
                    Item item = corners.get(i).getItem();
                    if (item == Item.FEATHER)
                        corners_string.add(i, "FE");
                    else if (item == Item.PARCHMENT)
                        corners_string.add(i, "PA");
                    else if (item == Item.POTION)
                        corners_string.add(i, "PO");
                    else
                        corners_string.add(i, "  ");
                } else if (corners.get(i).resource != null && corners.get(i).resource.isPresent()){
                    Resource resource = corners.get(i).getResource();
                    if (resource == Resource.LEAF)
                        corners_string.add(i, "LE");
                    else if (resource == Resource.BUTTERFLY)
                        corners_string.add(i, "BU");
                    else if (resource == Resource.WOLF)
                        corners_string.add(i, "WO");
                    else if (resource == Resource.MUSHROOM)
                        corners_string.add(i, "MU");
                }else
                    corners_string.add(i, "  ");
            }else {
                corners_string.add(i, "XX");
            }
        }
        if(starter_card.front_resource1.isPresent()){
            Resource res = starter_card.front_resource1.get();
            if(res == Resource.LEAF)
                front_resource1 = "LE";
            else if (res == Resource.BUTTERFLY)
                front_resource1 = "BU";
            else if (res == Resource.WOLF)
                front_resource1 = "WO";
            else if (res == Resource.MUSHROOM)
                front_resource1 = "MU";
        } else {
            front_resource1 = "  ";
        }
        if(starter_card.front_resource2.isPresent()){
            Resource res = starter_card.front_resource2.get();
            if(res == Resource.LEAF)
                front_resource2 = "LE";
            else if (res == Resource.BUTTERFLY)
                front_resource2 = "BU";
            else if (res == Resource.WOLF)
                front_resource2 = "WO=";
            else if (res == Resource.MUSHROOM)
                front_resource2 = "MU";
        } else {
            front_resource2 = "  ";
        }
        if(starter_card.front_resource3.isPresent()){
            Resource res = starter_card.front_resource3.get();
            if(res == Resource.LEAF)
                front_resource3 = "LE";
            else if (res == Resource.BUTTERFLY)
                front_resource3 = "BU";
            else if (res == Resource.WOLF)
                front_resource3 = "WO=";
            else if (res == Resource.MUSHROOM)
                front_resource3 = "MU";
        } else {
            front_resource3 = "  ";
        }
        TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3), front_resource1, front_resource2, front_resource3);
        graphic_card.printStarterCard(ANSI_BEIGE_BACKGROUND);
    }

    public static void showStarterCardBack(StarterCard starter_card) {
        ArrayList<Corner> corners = new ArrayList<>();
        corners.add(starter_card.getCornerAtNW());
        corners.add(starter_card.getCornerAtNE());
        corners.add(starter_card.getCornerAtSW());
        corners.add(starter_card.getCornerAtSE());

        ArrayList<String> corners_string = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (corners.get(i).is_visible) {
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
                } else if (corners.get(i).resource.isPresent()) {
                    Resource resource = corners.get(i).getResource();
                    if (resource == Resource.LEAF)
                        corners_string.add(i, "LE");
                    else if (resource == Resource.BUTTERFLY)
                        corners_string.add(i, "BU");
                    else if (resource == Resource.WOLF)
                        corners_string.add(i, "WO");
                    else if (resource == Resource.MUSHROOM)
                        corners_string.add(i, "MU");
                    else
                        corners_string.add(i, " ");
                } else {
                    corners_string.add(i, "XX");
                }
            }else{
                corners_string.add(i, "XX");
        }
        }
        TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3));
        graphic_card.printCard(ANSI_BEIGE_BACKGROUND);
    }


    // this is the method for starter card
    public static void showStarterCard(StarterCard starterCard){
        if(starterCard.getOrientation() == Orientation.FRONT){
            showStarterCardFront(starterCard);
        }else{
            showStarterCardBack(starterCard);
        }
    }


    //Print objective card NORMAL, not pattern


    public static void main(String[] args) {

        ResourceCard resource_card = new ResourceCard(11, Orientation.FRONT, Color.GREEN);
        resource_card.getCornerAtNW().setEmpty();
        resource_card.getCornerAtNW().is_visible = false;
        resource_card.getCornerAtSW().setCornerResource(Resource.LEAF);
        resource_card.getCornerAtSE().setCornerResource(Resource.LEAF);
        showResourceCard(resource_card);
//        TuiCardGraphics starter_card = new TuiCardGraphics("FE", "PA", "LE", "BU", "FE", "BU", "MU");
//        starter_card.printStarterCard(ANSI_BEIGE_BACKGROUND);

//        GoldCard gold_card = new GoldCard(67, Orientation.FRONT, Color.BLUE);
//        gold_card.getCornerAtNW().setEmpty();
//        gold_card.getCornerAtNW().is_visible = false;
//        gold_card.getCornerAtSW().setCornerItem(Item.PARCHMENT);
//        gold_card.getCornerAtSE().is_visible = false;
//        gold_card.setCornerCoverageRequired();
//        showGoldCard(gold_card);

//        ConcreteDeck deck_starter = new ConcreteDeck("Starter");
//        for (int i = 0; i < 12; i++) {
//            StarterCard starterCard = (StarterCard) deck_starter.pop();
//            System.out.println("Starter card: " + starterCard.getId());
//            System.out.println("Stareter card orientation: " + starterCard.getOrientation());
//            System.out.println(starterCard.toString());
//            showStarterCard(starterCard);
//
//        }

        TuiCardGraphics normal_objective_card = new TuiCardGraphics("MU", "MU", "MU", 2);
        normal_objective_card.printObjectiveCard();

        TuiCardGraphics ob_card = new TuiCardGraphics()
    }
}