package worldofzuul.model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Exit extends Rectangle {
    private Room room;

    public Exit(Room room, double width, double height, double posX, double posY) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(posX);
        this.setY(posY);
        this.setStroke(Color.BLACK);
        this.room = room;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            }
        });

    }

    public Room getRoom() {
        return room;
    }
}
