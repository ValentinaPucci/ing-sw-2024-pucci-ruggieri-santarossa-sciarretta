package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;


public  class CardsCollection {
    public List<Card> cards;

    public CardsCollection() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    /**
     * To do: populateDeck read a .json file and populate the data structure using
     * the constructors and the setters of Card subclasses.
     */
    // In this case we are creating only the FRONT, but we need also the back of the card.
    public void populateDeck(String jsonFilePath, String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            JsonNode cardsNode = rootNode.path("cards");

            for (JsonNode cardNode : cardsNode) {

                int id = cardNode.path("id").asInt();
                String colorStr = cardNode.path("color").asText();
                Color color = Color.valueOf(colorStr.toUpperCase());
                Orientation orientation = Orientation.FRONT;
                int score = cardNode.path("points").asInt();

                // This is the case where we have to create a ResourceCard.
                // NE corner.
                Corner[][] actual_corners = new Corner[2][2];
                String NE_corner_content = cardNode.path("NE").asText();
                if (NE_corner_content.equals("NonVisible")) {
                    actual_corners[0][1].is_visible = false;
                } else {
                        actual_corners[0][1].setCornerResource(Resource.valueOf(NE_corner_content.toUpperCase()));
                }
                // SE Corner.
                String SE_corner_content = cardNode.path("SE").asText();
                if (SE_corner_content.equals("NonVisible")) {
                    actual_corners[1][0].is_visible = false;
                } else {
                        actual_corners[1][0].setCornerResource(Resource.valueOf(SE_corner_content.toUpperCase()));
                }
                // NO corner.
                String NO_corner_content = cardNode.path("NO").asText();
                if (NO_corner_content.equals("NonVisible")) {
                    actual_corners[0][0].is_visible = false;
                } else {
                        actual_corners[0][0].setCornerResource(Resource.valueOf(NO_corner_content.toUpperCase()));
                }
                // SO corner.
                String SO_corner_content = cardNode.path("SO").asText();
                if (SO_corner_content.equals("NonVisible")) {
                    actual_corners[1][1].is_visible = false;
                } else {
                        actual_corners[1][1].setCornerResource(Resource.valueOf(SO_corner_content.toUpperCase()));
                }

                if (cardNode.path("type").asText().equals("Resource") && type.equals("Resource")){
                    ResourceCard card = new ResourceCard(id, orientation, color, score, actual_corners);
                    // Qui potresti settare ulteriori proprietà specifiche per ResourceCard
                    this.addCard(card);
                }

                if (cardNode.path("type").asText().equals("Gold") && type.equals("Gold")){
                    //TODO: cambia gli attributi di GoldCard, leggi int metti boolean nei nuovi attributi.
                    int MushroomRequired = cardNode.path("MushroomRequired").asInt();
                    int ButterflyRequired = cardNode.path("ButterflyRequired").asInt();
                    int WolfRequired = cardNode.path("WolfRequired").asInt();
                    int LeafRequired = cardNode.path("LeafRequired").asInt();

                    // Item_for_score
                    int PotionCount = cardNode.path("PotionCount").asInt();
                    int FeatherCount = cardNode.path("FeatherCount").asInt();
                    int ParchmentCount = cardNode.path("ParchmentCount").asInt();
                    int CoverCorners = cardNode.path("CoverCorners").asInt();

                    // Problem: Item optional!
                    Optional<Item> item_for_score = Optional.empty();
                    if (PotionCount == 1) {
                        item_for_score = Optional.of(Item.POTION);
                    }
                    if (FeatherCount == 1) {
                        item_for_score = Optional.of(Item.FEATHER);
                    }
                    if (ParchmentCount == 1) {
                        item_for_score = Optional.of(Item.PARCHMENT);
                    }

                    GoldCard gold_card = new GoldCard(id, orientation, color);
                    //gold_card.setGoldCard(item_for_score, CoverCorners, MushroomRequired, LeafRequired, ButterflyRequired, WolfRequired);
                    this.addCard(gold_card);
                }
            }
            System.out.println("Deck populated successfully.");
        } catch (Exception e) {
            System.err.println("Error populating deck: " + e.getMessage());
        }
    }

    // populate objective cards draft
    public void populateDeckObjective(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            JsonNode cardsNode = rootNode.path("cards");

            for (JsonNode cardNode : cardsNode) {

                int id = cardNode.path("id").asInt();
                Orientation orientation = Orientation.FRONT;
                int points = cardNode.path("points").asInt();
                String pattern = cardNode.path("pattern").asText();
               // System.out.println(pattern);
                if (pattern.equals("L")) {
                    System.out.println("L pattern");
                    LetterPatternObjectiveCard card = new LetterPatternObjectiveCard(id, orientation, points);
                    card.init_obj_L();
                    // come si implementa?? come si collegano??
                    this.addCard(card);
                }
                if (pattern.equals("J")) {
                    System.out.println("J pattern");
                    LetterPatternObjectiveCard card = new LetterPatternObjectiveCard(id, orientation, points);
                    card.init_obj_J();
                    this.addCard(card);
                }
                if (pattern.equals("P")) {
                    System.out.println("P pattern");
                    LetterPatternObjectiveCard card = new LetterPatternObjectiveCard(id, orientation, points);
                    this.addCard(card);
                }
                if (pattern.equals("Q")) {
                    System.out.println("Q pattern");
                    LetterPatternObjectiveCard card = new LetterPatternObjectiveCard(id, orientation, points);
                    card.init_obj_q();
                    this.addCard(card);
                }
                if (pattern.equals("increasing_diagonal")) {
                    DiagonalPatternObjectiveCard card = new DiagonalPatternObjectiveCard(id, orientation, points);
                    String color = cardNode.path("color1").asText();
                    System.out.println(card.toString());  // Print the color string
                    card.init_objIncreasingDiagonal(Color.valueOf(color.toUpperCase()));
                    this.addCard(card);
                }
                if (pattern.equals("decreasing_diagonal")) {
                    DiagonalPatternObjectiveCard card = new DiagonalPatternObjectiveCard(id, orientation, points);
                    String color = cardNode.path("color1").asText();
                    System.out.println(card.toString());
                    card.init_objDecreasingDiagonal(Color.valueOf(color.toUpperCase()));
                    this.addCard(card);
                }
                if (pattern.equals("None")) {
                   int num_feathers = cardNode.path("numFeathers").asInt();
                   int num_potions = cardNode.path("numPotions").asInt();
                   int num_parchments = cardNode.path("numParchments").asInt();
                   int num_mushrooms = cardNode.path("numMushrooms").asInt();
                   int num_leaves = cardNode.path("numLeaves").asInt();
                   int num_butterflies = cardNode.path("numButterflies").asInt();
                   int num_wolves = cardNode.path("numWolves").asInt();

                   if(num_feathers != 0 || num_potions != 0 || num_parchments != 0) {
                       ItemObjectiveCard card = new ItemObjectiveCard(id, orientation, points, num_feathers, num_potions, num_parchments);
                       System.out.println("item objective card");
                       this.addCard(card);
                   } else {
                       ResourceObjectiveCard card = new ResourceObjectiveCard(id, orientation, points, num_mushrooms, num_leaves, num_butterflies, num_wolves);
                       System.out.println("Resource objective card");
                       this.addCard(card);
                   }
                }
            }
            System.out.println("Objective cards Deck populated successfully.");
        } catch (Exception e) {
            System.err.println("Error populating objective cards deck: " + e.getMessage());
        }
    }

    // populate starter cards draft: it create the starter card collection, both front and back of the card. At the end
    // we have a collections of starter cards, that has size X2.
    public void populateDeckStarterFrontAndBack(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            JsonNode cardsNode = rootNode.path("cards");

            for (JsonNode cardNode : cardsNode) {
                int id = cardNode.path("id").asInt();
                String permanent_resource1 = cardNode.path("permanentResource1").asText();
                String permanent_resource2 = cardNode.path("permanentResource2").asText();
                String permanent_resource3 = cardNode.path("permanentResource3").asText();
                int front_visible_corner = cardNode.path("frontVisibleCorner").asInt();
                String front_NE= cardNode.path("frontNE").asText();
                String front_SE = cardNode.path("frontSE").asText();
                String front_NO = cardNode.path("frontNW").asText();
                String front_SO = cardNode.path("frontSW").asText();
                int back_visible_corner = cardNode.path("backVisibleCorner").asInt();
                String back_NE= cardNode.path("backNE").asText();
                String back_SE = cardNode.path("backSE").asText();
                String back_NO = cardNode.path("backNW").asText();
                String back_SO = cardNode.path("backSW").asText();

                StarterCard card_front = new StarterCard(id, Orientation.FRONT);
                Corner[][] actual_corners_front = new Corner[2][2];

                // Front of the Starter Card
                if (front_NE.equals("NonVisible")) {
                    actual_corners_front[0][1].is_visible = false;
                } else {
                    if(!front_NE.equals("Empty")){
                        actual_corners_front[0][1].setCornerResource(Resource.valueOf(front_NE.toUpperCase()));}
                }
                if (front_SE.equals("NonVisible")) {
                    actual_corners_front[1][0].is_visible = false;
                } else {
                    if (!front_SE.equals("Empty")) {
                        actual_corners_front[1][0].setCornerResource(Resource.valueOf(front_SE.toUpperCase()));
                    }
                }
                if (front_NO.equals("NonVisible")) {
                    actual_corners_front[0][0].is_visible = false;
                } else {
                    if(!front_NO.equals("Empty")){
                    actual_corners_front[0][0].setCornerResource(Resource.valueOf(front_NO.toUpperCase()));}
                }
                if (front_SO.equals("NonVisible")) {
                    actual_corners_front[1][1].is_visible = false;
                } else {
                    if(!front_SO.equals("Empty")){
                    actual_corners_front[1][1].setCornerResource(Resource.valueOf(front_SO.toUpperCase()));}}
                // Back of the Starter Card
                StarterCard card_back = new StarterCard(id, Orientation.BACK);
                Corner[][] actual_corners_back = new Corner[2][2];

                if (back_NE.equals("NonVisible")) {
                    actual_corners_back[0][1].is_visible = false;
                } else {
                    actual_corners_back[0][1].setCornerResource(Resource.valueOf(back_NE.toUpperCase()));}
                if (back_SE.equals("NonVisible")) {
                    actual_corners_back[1][0].is_visible = false;
                } else {
                    actual_corners_back[1][0].setCornerResource(Resource.valueOf(back_SE.toUpperCase()));}
                if (back_NO.equals("NonVisible")) {
                    actual_corners_back[0][0].is_visible = false;
                } else {
                    actual_corners_back[0][0].setCornerResource(Resource.valueOf(back_NO.toUpperCase()));}
                if (back_SO.equals("NonVisible")) {
                    actual_corners_back[1][1].is_visible = false;
                } else {
                    actual_corners_back[1][1].setCornerResource(Resource.valueOf(back_SO.toUpperCase()));}

                // Remark: read carefully the Starter Card class. Here we create at the same moment two cards:
                // the first that is the front, and the second one is the back of the same card.
                // But they are two different objects, they have in common the same id!

                card_front.setStarterCardFront(Resource.valueOf(permanent_resource1.toUpperCase()),
                        Resource.valueOf(permanent_resource2.toUpperCase()),
                        Resource.valueOf(permanent_resource3.toUpperCase()),
                        actual_corners_front);
                card_back.setStarterCardBack(actual_corners_back);
            }
            System.out.println("Starter cards Deck populated successfully.");
        } catch (Exception e) {
            System.err.println("Error populating starter cards deck: " + e.getMessage());
        }

    }


    }
