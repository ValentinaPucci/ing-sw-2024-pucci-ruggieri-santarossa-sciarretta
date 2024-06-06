package it.polimi.demo.view.flow.utilities.gameFacts;
import it.polimi.demo.model.ModelView;

public class ModelFact {

    private ModelView model;
    private FactType type;

    public ModelFact(ModelView model, FactType type) {
        this.model = model;
        this.type = type;
    }

    public ModelView getModel() {
        return model;
    }

    public FactType getType() {
        return type;
    }
}
