package model;

import org.json.JSONObject;
import persistence.Writable;

public class GameAppData implements Writable {
    private Inventory inventory;
    private Character user;

    public GameAppData(Character user, Inventory inventory) {
        this.user = user;
        this.inventory = inventory;
    }

    public Character getUser() {
        return user;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user.toJson());
        jsonObject.put("inventory", inventory.toJson());

        return jsonObject;
    }
}
