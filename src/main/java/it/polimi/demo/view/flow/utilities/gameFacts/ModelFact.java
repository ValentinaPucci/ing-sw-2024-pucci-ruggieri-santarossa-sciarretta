package it.polimi.demo.view.flow.utilities.gameFacts;
import it.polimi.demo.model.ModelView;

public class ModelFact {

    private ModelView model;
    private FactType type;

    /**
     * Init
     *
     * @param model
     * @param type
     */
    public ModelFact(ModelView model, FactType type) {
        this.model = model;
        this.type = type;
    }

    /**
     * @return model
     */
    public ModelView getModel() {
        return model;
    }

    /**
     * @return event type
     */
    public FactType getType() {
        return type;
    }
}
