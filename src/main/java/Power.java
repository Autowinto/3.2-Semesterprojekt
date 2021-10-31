package worldofzuul;

public class Power {
    private String placedRoomVind;
    private String placedRoomVand;
    private String placedRoomSol;

    public void setRoomVind(Room currentRoom) {
        placedRoomVind = currentRoom.getName();
        System.out.println("You've placed the product in room "+placedRoomVind);
    }

    public void setRoomVand(Room currentRoom) {
        placedRoomVand = currentRoom.getName();
        System.out.println("You've placed the product in room "+placedRoomVand);
    }

    public void setRoomSol(Room currentRoom) {
        placedRoomSol = currentRoom.getName();
        System.out.println("You've placed the product in room "+placedRoomSol);
    }
}
