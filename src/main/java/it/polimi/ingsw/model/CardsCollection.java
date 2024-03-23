package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


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
                    if (NE_corner_content.equals("Mushroom")) {
                        actual_corners[0][1].setCornerResource(Resource.MUSHROOM);
                    }
                    if (NE_corner_content.equals("Butterfly")) {
                        actual_corners[0][1].setCornerResource(Resource.BUTTERFLY);
                    }
                    if (NE_corner_content.equals("Wolf")) {
                        actual_corners[0][1].setCornerResource(Resource.WOLF);
                    }
                    if (NE_corner_content.equals("Leaf")) {
                        actual_corners[0][1].setCornerResource(Resource.LEAF);
                    }
                    // This if the card has ITEM objects in the corner.
                    if (NE_corner_content.equals("Potion")) {
                        actual_corners[0][1].setCornerItem(Item.POTION);
                    }
                    if (NE_corner_content.equals("Feather")) {
                        actual_corners[0][1].setCornerItem(Item.FEATHER);
                    }
                    if (NE_corner_content.equals("Parchment")) {
                        actual_corners[0][1].setCornerItem(Item.PARCHMENT);
                    }
                }
                // SE Corner.
                String SE_corner_content = cardNode.path("SE").asText();
                if (SE_corner_content.equals("NonVisible")) {
                    actual_corners[1][0].is_visible = false;
                } else {
                    if (SE_corner_content.equals("Mushroom")) {
                        actual_corners[1][0].setCornerResource(Resource.MUSHROOM);
                    }
                    if (SE_corner_content.equals("Butterfly")) {
                        actual_corners[1][0].setCornerResource(Resource.BUTTERFLY);
                    }
                    if (SE_corner_content.equals("Wolf")) {
                        actual_corners[1][0].setCornerResource(Resource.WOLF);
                    }
                    if (SE_corner_content.equals("Leaf")) {
                        actual_corners[1][0].setCornerResource(Resource.LEAF);
                    }
                    // This if the card has ITEM objects in the corner.
                    if (SE_corner_content.equals("Potion")) {
                        actual_corners[1][0].setCornerItem(Item.POTION);
                    }
                    if (SE_corner_content.equals("Feather")) {
                        actual_corners[1][0].setCornerItem(Item.FEATHER);
                    }
                    if (SE_corner_content.equals("Parchment")) {
                        actual_corners[1][0].setCornerItem(Item.PARCHMENT);
                    }
                }
                // NO corner.
                String NO_corner_content = cardNode.path("NO").asText();
                if (NO_corner_content.equals("NonVisible")) {
                    actual_corners[0][1].is_visible = false;
                } else {
                    if (NO_corner_content.equals("Mushroom")) {
                        actual_corners[0][1].setCornerResource(Resource.MUSHROOM);
                    }
                    if (NO_corner_content.equals("Butterfly")) {
                        actual_corners[0][1].setCornerResource(Resource.BUTTERFLY);
                    }
                    if (NO_corner_content.equals("Wolf")) {
                        actual_corners[0][1].setCornerResource(Resource.WOLF);
                    }
                    if (NO_corner_content.equals("Leaf")) {
                        actual_corners[0][1].setCornerResource(Resource.LEAF);
                    }
                    // This if the card has ITEM objects in the corner.
                    if (NO_corner_content.equals("Potion")) {
                        actual_corners[0][1].setCornerItem(Item.POTION);
                    }
                    if (NO_corner_content.equals("Feather")) {
                        actual_corners[0][1].setCornerItem(Item.FEATHER);
                    }
                    if (NO_corner_content.equals("Parchment")) {
                        actual_corners[0][1].setCornerItem(Item.PARCHMENT);
                    }
                }
                // SO corner.
                String SO_corner_content = cardNode.path("SO").asText();
                if (SO_corner_content.equals("NonVisible")) {
                    actual_corners[1][1].is_visible = false;
                } else {
                    if (SO_corner_content.equals("Mushroom")) {
                        actual_corners[1][1].setCornerResource(Resource.MUSHROOM);
                    }
                    if (SO_corner_content.equals("Butterfly")) {
                        actual_corners[1][1].setCornerResource(Resource.BUTTERFLY);
                    }
                    if (SO_corner_content.equals("Wolf")) {
                        actual_corners[1][1].setCornerResource(Resource.WOLF);
                    }
                    if (SO_corner_content.equals("Leaf")) {
                        actual_corners[1][1].setCornerResource(Resource.LEAF);
                    }
                    // This if the card has ITEM objects in the corner.
                    if (SO_corner_content.equals("Potion")) {
                        actual_corners[1][1].setCornerItem(Item.POTION);
                    }
                    if (SO_corner_content.equals("Feather")) {
                        actual_corners[1][1].setCornerItem(Item.FEATHER);
                    }
                    if (SO_corner_content.equals("Parchment")) {
                        actual_corners[1][1].setCornerItem(Item.PARCHMENT);
                    }
                }
                if (cardNode.path("type").asText().equals("Resource") && type.equals("Resource")){
                    ResourceCard card = new ResourceCard(id, orientation, color, score, actual_corners);
                    // Qui potresti settare ulteriori proprietà specifiche per ResourceCard
                    this.addCard(card);
                }

                if (cardNode.path("type").asText().equals("Gold") && type.equals("Gold")){
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
                    gold_card.setGoldCard(item_for_score, CoverCorners, MushroomRequired, LeafRequired, ButterflyRequired, WolfRequired);
                    this.addCard(gold_card);
                }
            }
            System.out.println("Deck populated successfully.");
        } catch (Exception e) {
            System.err.println("Error populating deck: " + e.getMessage());
        }
    }

    // to do: then starter cards and objective cards;

    public void populateDeckObjective(String jsonFilePath){
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

            }
            System.out.println("Deck populated successfully.");
        } catch (Exception e) {
            System.err.println("Error populating deck: " + e.getMessage());
        }


    }

}