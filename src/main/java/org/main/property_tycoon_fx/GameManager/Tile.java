package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Tile {
    private double position; //position on the board
    private String space; //name of the tile
    private String group;
    private String action; //action that the tile may trigger
    private boolean canBeBought; //shows if tile can be bought by the player
    private String Type;
    private double cost; //shows cost of tile if it can be bought by player
    private double[] rent; //shows cost of rent if player has property on the tile and someone steps on it
    private int isOwnedBy; //ID number of player that owns tile (default 0)
    private Image TileImage;
    private ArrayList<Integer> playerIDs = new ArrayList<>(); //stores the ids of the players on the tile

    public Tile(double Tposition, String Tspace, String Tgroup, String Taction, boolean TcanBeBought, double Tcost, double[] Trent, int TisOwnedBy) {
        position = Tposition;
        space = Tspace;
        group = Tgroup;
        action = Taction;
        canBeBought = TcanBeBought;
        cost = Tcost;
        rent = Trent;
        isOwnedBy = TisOwnedBy;

        assignImages(group); // Automatically set the image when creating the tile
    }

    public double getPosition() {
        return position;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSpace() {
        return space;
    }

    public String getGroup() {
        return group;
    }

    public String getAction() {
        return action;
    }

    public boolean isCanBeBought() {
        return canBeBought;
    }

    public double getCost() {
        return cost;
    }

    public double[] getRent() {
        return rent;
    }

    public int getIsOwnedBy() {
        return isOwnedBy;
    }

    public Image getTileImage() {
        return TileImage;
    }

    public void setTileImage(Image tileImage) {
        TileImage = tileImage;
    }

    public void assignImages(String group) {
        // Check if group is null or empty, and assign a default value if necessary
        if (group == null || group.isEmpty()) {
            //System.out.println("Group is null or empty, assigning default image.");
            group = "default";

        }

        // Convert group name to match file naming convention
        String formattedName = group.toLowerCase().replace(" ", "_") + ".png";

        //System.out.println(formattedName);

        try {
            // Attempt to load the image
            TileImage = new Image(getClass().getResource("/Images/" + formattedName).toExternalForm());
        } catch (Exception e) {
            // Handle error if image is not found
            //System.err.println("Image not found for group: " + group);
            TileImage = new Image(getClass().getResource("/Images/Yellow.png").toExternalForm());
        }
    }

}
