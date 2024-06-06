package it.polimi.demo.view.flow.utilities.gameFacts;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

public class ModelFact {

    private GameModelImmutable model;
    private FactType type;

    public ModelFact(GameModelImmutable model, FactType type) {
        this.model = model;
        this.type = type;
    }

    public GameModelImmutable getModel() {
        return model;
    }

    public FactType getType() {
        return type;
    }
}
