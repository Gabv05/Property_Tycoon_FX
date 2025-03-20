package org.main.property_tycoon_fx.GameManager;

public class Card {
    private String type;
    private String description;
    private String action;
    public Card(String Ctype, String Cdescription, String Caction) {
        type = Ctype;
        description = Cdescription;
        action = Caction;
    }

    //method to convert description of an action into a code
    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getAction() {
        return action;
    }
}
