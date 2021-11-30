package worldofzuul.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Exit extends Rectangle {
    private Room room;
    private String direction;

    public Exit(Room room, double width, double height, double posX, double posY, String direction) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(posX);
        this.setY(posY);
        this.direction = direction;
//        this.setStroke(Color.BLACK);
        this.setFill(Color.BLACK);
        this.room = room;
    }

    public Exit(Room water4, int width, int height, int posX, int posY) {
    }

    public Room getRoom() {
        return room;
    }

    public String getDirection() {
        return direction;
    }
}
