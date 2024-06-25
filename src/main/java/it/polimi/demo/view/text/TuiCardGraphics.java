package it.polimi.demo.view.text;

import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.*;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;
import java.util.ArrayList;

/**
 * This class is used to print the graphics of the cards in the terminal.
 */
public class TuiCardGraphics {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_BEIGE_BACKGROUND = "\u001B[48;5;230m";

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

    private static final String[] STARTER_CARD_GRAPHICS = {
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
    private int objective_pattern_id;

    private static final String[] OBJECTIVE_CARD_GRAPHICS = {
            "╭──────────────╮",
            "│    Points: %d │",
            "│      %s      │",
            "│ %s        %s │",
            "╰───────────────╯"
    };
    private String el1;
    private String el2;
    private String el3;


    /**
     * Constructor for the TuiCardGraphics class.
     * @param corner1 the first corner of the card
     * @param corner2 the second corner of the card
     * @param corner3 the third corner of the card
     * @param corner4 the fourth corner of the card
     */
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.points = 0;
    }

    /**
     * Constructor for the TuiCardGraphics class.
     * @param corner1 the first corner of the card
     * @param corner2 the second corner of the card
     * @param corner3 the third corner of the card
     * @param corner4 the fourth corner of the card
     * @param points the points of the card
     */
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4, int points) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.points = points;
    }

    /**
     * Constructor for the TuiCardGraphics class.
     * @param corner1 the first corner of the card
     * @param corner2 the second corner of the card
     * @param corner3 the third corner of the card
     * @param corner4 the fourth corner of the card
     * @param front_resource1 the first front resource of the card
     * @param front_resource2 the second front resource of the card
     * @param front_resource3 the third front resource of the card
     */
    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4, String front_resource1, String front_resource2, String front_resource3) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.front_resource1 = front_resource1;
        this.front_resource2 = front_resource2;
        this.front_resource3 = front_resource3;
    }

    /**
     * Constructor for the TuiCardGraphics class.
     * @param el1 the first element of the card
     * @param el2 the second element of the card
     * @param el3 the third element of the card
     * @param points the points of the card
     */
    public TuiCardGraphics(String el1, String el2, String el3, int points) {
        this.el1 = el1;
        this.el2 = el2;
        this.el3 = el3;
        this.points = points;
    }

    /**
     * Constructor for the TuiCardGraphics class.
     * @param id the id of the card
     * @param points the points of the card
     */
    public TuiCardGraphics(int id, int points) {
        this.points = points;
        this.objective_pattern_id = id;
    }

    /**
     * Method to print the card.
     * @param Color the color of the card
     */
    public void printCard(String Color) {
        System.out.println(ANSI_RESET + CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < CARD_GRAPHICS.length - 1; i++) {
            if (i == 1) {
                String row = String.format(CARD_GRAPHICS[i], corner1, corner2);
                System.out.println(ANSI_RESET + row.substring(0, 4) + Color + row.substring(4, 12) + ANSI_RESET + row.substring(14, 18) + ANSI_RESET);
            } else if (i == 3) {
                String row3 = String.format(CARD_GRAPHICS[i], corner3, corner4);
                System.out.println(ANSI_RESET + row3.substring(0, 4) + Color + row3.substring(4, 12) + ANSI_RESET + row3.substring(14, 18) + ANSI_RESET);
            } else {
                String row4 = String.format(CARD_GRAPHICS[i]);
                System.out.println(ANSI_RESET + row4.substring(0, 2) + Color + row4.substring(2, 14) + ANSI_RESET + row4.substring(14, 16) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

        if (points != 0) {
            System.out.print("Card points: " + points + "\n");
        }
    }

    /**
     * Method to print the starter card.
     * @param Color the color of the card
     */
    public void printStarterCard(String Color) {
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < STARTER_CARD_GRAPHICS.length - 1; i++) {
            if (i == 1) {
                String row = String.format(STARTER_CARD_GRAPHICS[i], corner1, front_resource1, corner2);
                System.out.println(ANSI_RESET + row.substring(0, 4) + Color + row.substring(4, 7) + ANSI_RESET + row.substring(8, 10) + Color + row.substring(10, 14) + Color + ANSI_RESET + row.substring(14, 18) + ANSI_RESET);
            } else if (i == 3) {
                String row3 = String.format(STARTER_CARD_GRAPHICS[i], corner3, front_resource3, corner4);

                System.out.println(ANSI_RESET + row3.substring(0, 4) + Color + row3.substring(4, 7) + ANSI_RESET + row3.substring(8, 10) + Color + row3.substring(10, 14) + Color + ANSI_RESET + row3.substring(14, 18) + ANSI_RESET);
            } else if (i == 2) {
                String row2 = String.format(STARTER_CARD_GRAPHICS[i], front_resource2);
                System.out.println(ANSI_RESET + row2.substring(0, 2) + Color + row2.substring(2, 7) + ANSI_RESET + row2.substring(8, 10) + Color + row2.substring(10, 16) + ANSI_RESET + row2.substring(16, 18) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");
    }

    /**
     * Method to print the objective card.
     */
    public void printObjectiveCard() {
        System.out.println(OBJECTIVE_CARD_GRAPHICS[0]);
        for (int i = 1; i < CARD_GRAPHICS.length - 1; i++) {
            if (i == 1) {
                System.out.println(String.format(OBJECTIVE_CARD_GRAPHICS[i], points));
            } else if (i == 3) {
                System.out.println(String.format(OBJECTIVE_CARD_GRAPHICS[i], el2, el3));
            } else {
                System.out.println(String.format(OBJECTIVE_CARD_GRAPHICS[i], el1));
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

    }

    /**
     * Method to print the pattern objective card.
     * @param color the color of the card
     */
    public void printIncrDiagPatternObjectiveCard(String color) {
        System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < STARTER_CARD_GRAPHICS.length - 1; i++) {
            if (i == 1) {
                String row = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row.substring(0, 9) + color + row.substring(10, 13) + ANSI_RESET + row.substring(11, 16));
            } else if (i == 3) {
                String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row2.substring(0, 3) + color + row2.substring(3, 6) + ANSI_RESET + row2.substring(6, 17));
            } else if (i == 2) {
                String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row3.substring(0, 6) + color + row3.substring(6, 9) + ANSI_RESET + row3.substring(10, 18));
            }
        }
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

    }

    /**
     * Method to print the pattern objective card.
     * @param color the color of the card
     */
    public void printDecrDiagPatternObjectiveCard(String color) {
        System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < STARTER_CARD_GRAPHICS.length - 1; i++) {
            if (i == 1) {
                String row = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row.substring(0, 3) + color + row.substring(3, 6) + ANSI_RESET + row.substring(5, 16));
            } else if (i == 3) {
                String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row2.substring(0, 9) + color + row2.substring(10, 13) + ANSI_RESET + row2.substring(12, 17));
            } else if (i == 2) {
                String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[i], points);
                System.out.println(ANSI_RESET + row3.substring(0, 6) + color + row3.substring(6, 9) + ANSI_RESET + row3.substring(10, 18));
            }
        }
        System.out.println(ANSI_RESET + STARTER_CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");

    }


    /**
     * Method to print the pattern objective card.
     */
    public void printPatternObjectivecard() {
        if (objective_pattern_id == 87) {
            printIncrDiagPatternObjectiveCard(ANSI_RED_BACKGROUND);
        } else if (objective_pattern_id == 88) {
            printDecrDiagPatternObjectiveCard(ANSI_GREEN_BACKGROUND);
        } else if (objective_pattern_id == 89) {
            printIncrDiagPatternObjectiveCard(ANSI_CYAN_BACKGROUND);
        } else if (objective_pattern_id == 90) {
            printDecrDiagPatternObjectiveCard(ANSI_PURPLE_BACKGROUND);
        } else if (objective_pattern_id == 91) {
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
            String row1 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[1], points);
            System.out.println(ANSI_RESET + row1.substring(0, 3) + ANSI_RED_BACKGROUND + row1.substring(3, 6) + ANSI_RESET + row1.substring(5, 16));
            String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[2]);
            System.out.println(ANSI_RESET + row2.substring(0, 3) + ANSI_RED_BACKGROUND + row2.substring(3, 6) + ANSI_RESET + row2.substring(7, 18));
            String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[3]);
            System.out.println(ANSI_RESET + row3.substring(0, 6) + ANSI_GREEN_BACKGROUND + row3.substring(6, 9) + ANSI_RESET + row3.substring(9, 17));
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[4] + ANSI_RESET);
        } else if (objective_pattern_id == 92) {
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
            String row1 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[1], points);
            System.out.println(ANSI_RESET + row1.substring(0, 6) + ANSI_GREEN_BACKGROUND + row1.substring(6, 9) + ANSI_RESET + row1.substring(8, 16));
            String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[2]);
            System.out.println(ANSI_RESET + row2.substring(0, 6) + ANSI_GREEN_BACKGROUND + row2.substring(6, 9) + ANSI_RESET + row2.substring(10, 18));
            String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[3]);
            System.out.println(ANSI_RESET + row3.substring(0, 3) + ANSI_PURPLE_BACKGROUND + row3.substring(3, 6) + ANSI_RESET + row3.substring(6, 17));
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[4] + ANSI_RESET);
        } else if (objective_pattern_id == 93) {
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
            String row1 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[1], points);
            System.out.println(ANSI_RESET + row1.substring(0, 6) + ANSI_RED_BACKGROUND + row1.substring(6, 9) + ANSI_RESET + row1.substring(8, 16));
            String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[2]);
            System.out.println(ANSI_RESET + row2.substring(0, 3) + ANSI_CYAN_BACKGROUND + row2.substring(4, 7) + ANSI_RESET + row2.substring(7, 18));
            String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[3]);
            System.out.println(ANSI_RESET + row3.substring(0, 3) + ANSI_CYAN_BACKGROUND + row3.substring(3, 6) + ANSI_RESET + row3.substring(6, 17));
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[4] + ANSI_RESET);
        } else if (objective_pattern_id == 94) {
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[0] + ANSI_RESET);
            String row1 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[1], points);
            System.out.println(ANSI_RESET + row1.substring(0, 3) + ANSI_CYAN_BACKGROUND + row1.substring(3, 6) + ANSI_RESET + row1.substring(5, 16));
            String row2 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[2]);
            System.out.println(ANSI_RESET + row2.substring(0, 6) + ANSI_PURPLE_BACKGROUND + row2.substring(6, 9) + ANSI_RESET + row2.substring(10, 18));
            String row3 = String.format(PATTERN_OBJECTIVE_CARD_GRAPHICS[3]);
            System.out.println(ANSI_RESET + row3.substring(0, 6) + ANSI_PURPLE_BACKGROUND + row3.substring(6, 9) + ANSI_RESET + row3.substring(9, 17));
            System.out.println(ANSI_RESET + PATTERN_OBJECTIVE_CARD_GRAPHICS[4] + ANSI_RESET);
        }
    }

    /**
     * Method to print the resource card.
     * @param resource_card the resource card to print
     */
    public static void displayResourceCard(ResourceCard resource_card) {

        Color card_color = resource_card.getColor();
        String graphic_color = null;
        switch (card_color) {
            case Color.RED -> graphic_color = ANSI_RED_BACKGROUND;
            case Color.BLUE -> graphic_color = ANSI_CYAN_BACKGROUND;
            case Color.GREEN -> graphic_color = ANSI_GREEN_BACKGROUND;
            case Color.PURPLE -> graphic_color = ANSI_PURPLE_BACKGROUND;
        }

        if (resource_card.orientation == Orientation.BACK) {
            TuiCardGraphics graphic_card = new TuiCardGraphics("  ", "  ", "  ", "  ");
            graphic_card.printCard(graphic_color);
        } else {
            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(resource_card.getCornerAtNW());
            corners.add(resource_card.getCornerAtNE());
            corners.add(resource_card.getCornerAtSW());
            corners.add(resource_card.getCornerAtSE());

            ArrayList<String> corners_string = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (corners.get(i).is_visible) {
                    if (corners.get(i).item !=null) {
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
                    } else if (corners.get(i).resource != null) {
                        Resource resource = corners.get(i).getResource();
                        if (resource == Resource.LEAF)
                            corners_string.add(i, "LE");
                        else if (resource == Resource.BUTTERFLY)
                            corners_string.add(i, "BU");
                        else if (resource == Resource.WOLF)
                            corners_string.add(i, "WO");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    } else
                        corners_string.add(i, "  ");
                } else {
                    corners_string.add(i, "XX");
                }
            }
            TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3));
            graphic_card.printCard(graphic_color);
        }
    }

    /**
     * Method to print the gold card.
     * @param gold_card the gold card to print
     */
    public static void displayGoldCard(GoldCard gold_card) {
        Color card_color = gold_card.getColor();
        String graphic_color = null;
        switch (card_color) {
            case Color.RED -> graphic_color = ANSI_RED_BACKGROUND;
            case Color.BLUE -> graphic_color = ANSI_CYAN_BACKGROUND;
            case Color.GREEN -> graphic_color = ANSI_GREEN_BACKGROUND;
            case Color.PURPLE -> graphic_color = ANSI_PURPLE_BACKGROUND;
        }

        if (gold_card.orientation == Orientation.BACK) {
            TuiCardGraphics graphic_card = new TuiCardGraphics(" ", " ", " ", " ");
            graphic_card.printCard(graphic_color);
        } else {
            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(gold_card.getCornerAtNW());
            corners.add(gold_card.getCornerAtNE());
            corners.add(gold_card.getCornerAtSW());
            corners.add(gold_card.getCornerAtSE());

            ArrayList<String> corners_string = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (corners.get(i).is_visible) {
                    if (corners.get(i).item != null) {
                        Item item = corners.get(i).getItem();
                        if (item == Item.FEATHER)
                            corners_string.add(i, "FE");
                        else if (item == Item.PARCHMENT)
                            corners_string.add(i, "PA");
                        else if (item == Item.POTION)
                            corners_string.add(i, "PO");
                        else
                            corners_string.add(i, "  ");
                    } else if (corners.get(i).resource != null) {
                        Resource resource = corners.get(i).getResource();
                        if (resource == Resource.LEAF)
                            corners_string.add(i, "LE");
                        else if (resource == Resource.BUTTERFLY)
                            corners_string.add(i, "BU");
                        else if (resource == Resource.WOLF)
                            corners_string.add(i, "WO");
                        else if (resource == Resource.MUSHROOM)
                            corners_string.add(i, "MU");
                    } else
                        corners_string.add(i, "  ");
                } else {
                    corners_string.add(i, "XX");
                }
            }
            if (gold_card.getMushroomRequired() != 0)
                System.out.println("\nMushroom required:" + gold_card.getMushroomRequired());
            if (gold_card.getLeafRequired() != 0)
                System.out.println("\nLeaf required:" + gold_card.getLeafRequired());
            if (gold_card.getButterflyRequired() != 0)
                System.out.println("\nButterfly required:" + gold_card.getButterflyRequired());
            if (gold_card.getWolfRequired() != 0)
                System.out.println("\nMushroom required:" + gold_card.getWolfRequired());
            if (gold_card.getIsCornerCoverageRequired())
                System.out.println("\nYou earn 2 points for every corner that this card covers.");
            if (gold_card.getIsFeatherRequired())
                System.out.println("\nYou earn a points for every feather present on you personal board.");
            if (gold_card.getIsPotionRequired())
                System.out.println("\nYou earn a points for every potion present on you personal board.");
            if (gold_card.getIsParchmentRequired())
                System.out.println("\nYou earn a points for every parchment present on you personal board.");

            TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3), gold_card.getPoints());
            graphic_card.printCard(graphic_color);
        }
    }

    /**
     * Method to print the starter card.
     * @param starter_card the starter card to print
     */
    public static void displayStarterCardFront(StarterCard starter_card) {
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
            if (corners.get(i).is_visible) {
                if (corners.get(i).item != null) {
                    Item item = corners.get(i).getItem();
                    if (item == Item.FEATHER)
                        corners_string.add(i, "FE");
                    else if (item == Item.PARCHMENT)
                        corners_string.add(i, "PA");
                    else if (item == Item.POTION)
                        corners_string.add(i, "PO");
                    else
                        corners_string.add(i, "ERROR");
                } else if (corners.get(i).resource != null) {
                    Resource resource = corners.get(i).getResource();
                    if (resource == Resource.LEAF)
                        corners_string.add(i, "LE");
                    else if (resource == Resource.BUTTERFLY)
                        corners_string.add(i, "BU");
                    else if (resource == Resource.WOLF)
                        corners_string.add(i, "WO");
                    else if (resource == Resource.MUSHROOM)
                        corners_string.add(i, "MU");
                } else
                    corners_string.add(i, "  ");
            } else {
                corners_string.add(i, "XX");
            }
        }
        if (starter_card.front_resource1 != null) {
            Resource res = starter_card.front_resource1;
            if (res == Resource.LEAF)
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
        if (starter_card.front_resource2 != null) {
            Resource res = starter_card.front_resource2;
            if (res == Resource.LEAF)
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
        if (starter_card.front_resource3 != null) {
            Resource res = starter_card.front_resource3;
            if (res == Resource.LEAF)
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

    /**
     * Method to print the starter card.
     * @param starter_card the starter card to print
     */
    public static void displayStarterCardBack(StarterCard starter_card) {
        ArrayList<Corner> corners = new ArrayList<>();
        corners.add(starter_card.getCornerAtNW());
        corners.add(starter_card.getCornerAtNE());
        corners.add(starter_card.getCornerAtSW());
        corners.add(starter_card.getCornerAtSE());

        ArrayList<String> corners_string = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (corners.get(i).is_visible) {
                if (corners.get(i).item != null) {
                    Item item = corners.get(i).getItem();
                    System.out.println(item);
                    if (item == Item.FEATHER)
                        corners_string.add(i, "FE");
                    else if (item == Item.PARCHMENT)
                        corners_string.add(i, "PA");
                    else if (item == Item.POTION)
                        corners_string.add(i, "PO");
                    else
                        corners_string.add(i, "ERROR");
                } else if (corners.get(i).resource != null) {
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
                        corners_string.add(i, "ERROR");
                } else {
                    corners_string.add(i, "  ");
                }
            } else {
                corners_string.add(i, "XX");
            }
        }
        TuiCardGraphics graphic_card = new TuiCardGraphics(corners_string.get(0), corners_string.get(1), corners_string.get(2), corners_string.get(3));
        graphic_card.printCard(ANSI_BEIGE_BACKGROUND);
    }

    /**
     * Method to print the objective card.
     * @param objective_card the objective card to print
     */
    public static void displayObjectiveCard(ObjectiveCard objective_card) {
        if (objective_card instanceof LetterPatternObjectiveCard || objective_card instanceof DiagonalPatternObjectiveCard) {
            TuiCardGraphics ob_card = new TuiCardGraphics(objective_card.getId(), objective_card.getPoints());
            ob_card.printPatternObjectivecard();
        } else if (objective_card instanceof ItemObjectiveCard) {
            int num_feathers = ((ItemObjectiveCard) objective_card).getNumItem(Item.FEATHER);
            int num_parchments = ((ItemObjectiveCard) objective_card).getNumItem(Item.PARCHMENT);
            int num_potions = ((ItemObjectiveCard) objective_card).getNumItem(Item.POTION);

            if (num_feathers == 1 && num_parchments == 1 && num_potions == 1) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("FE", "PO", "PA", 3);
                normal_objective_card.printObjectiveCard();
            } else if (num_feathers == 2) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("XX", "FE", "FE", 2);
                normal_objective_card.printObjectiveCard();
            } else if (num_parchments == 2) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("XX", "PA", "PA", 2);
                normal_objective_card.printObjectiveCard();
            } else if (num_potions == 2) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("XX", "PO", "PO", 2);
                normal_objective_card.printObjectiveCard();
            } else {
                System.out.println("Invalid ItemObjectiveCard");
            }
        } else if (objective_card instanceof ResourceObjectiveCard) {
            int num_leaves = ((ResourceObjectiveCard) objective_card).getNumResource(Resource.LEAF);
            int num_butterflies = ((ResourceObjectiveCard) objective_card).getNumResource(Resource.BUTTERFLY);
            int num_wolves = ((ResourceObjectiveCard) objective_card).getNumResource(Resource.WOLF);
            int num_mushrooms = ((ResourceObjectiveCard) objective_card).getNumResource(Resource.MUSHROOM);

            if (num_leaves == 3) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("LE", "LE", "LE", 2);
                normal_objective_card.printObjectiveCard();
            } else if (num_butterflies == 3) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("BU", "BU", "BU", 2);
                normal_objective_card.printObjectiveCard();
            } else if (num_wolves == 3) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("WO", "WO", "WO", 2);
                normal_objective_card.printObjectiveCard();
            } else if (num_mushrooms == 3) {
                TuiCardGraphics normal_objective_card = new TuiCardGraphics("MU", "MU", "MU", 2);
                normal_objective_card.printObjectiveCard();
            } else {
                System.out.println("Invalid ResourceObjectiveCard");
            }
        }

    }
}
