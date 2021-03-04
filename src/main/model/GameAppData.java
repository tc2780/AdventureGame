package model;

import org.json.JSONObject;
import persistence.Writable;

//represents data to be saved/loaded in a game --- class mainly used to save/load data from JSONObject
public class GameAppData implements Writable {

    private Inventory inventory;    //represents current inventory
    private Character user;         //represents current user

    //EFFECTS: creates a data object that stores given user and inventory
    public GameAppData(Character user, Inventory inventory) {
        this.user = user;
        this.inventory = inventory;
    }

    //EFFECTS: returns user
    public Character getUser() {
        return user;
    }

    //EFFECTS: returns inventory
    public Inventory getInventory() {
        return inventory;
    }

    //EFFECTS: returns this as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user.toJson());
        jsonObject.put("inventory", inventory.toJson());

        return jsonObject;
    }
}
