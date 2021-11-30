package worldofzuul.model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Exit extends Rectangle {
    private Room room;
    private Label hoverLabel;

    public Exit(Room room, double width, double height, double posX, double posY) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(posX);
        this.setY(posY);
        this.setStroke(Color.BLACK);
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
