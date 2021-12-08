package worldofzuul.model;

public class Power {
    private static double power;

    public static void addPower(Product product, Room currentRoom) {
        if (product.getEnergyType() == currentRoom.getEnergyType()) {
            if (currentRoom.getDropOffEffect() == "best") {
                power += 33.3;
            } else if (currentRoom.getDropOffEffect() == "middle") {
                power += 20.0;
            } else {
                power += 10.0;
            }
            System.out.println(currentRoom.getDropOffText());
        } else {
            System.out.println("Der ser ikke ud til der skete den store forskel, da du satte " + product.getName() + "n.");
        }
    }

    public static void removePower(Product product, Room currentRoom) {
        if (product.getEnergyType() == currentRoom.getEnergyType()) {
            if (currentRoom.getDropOffEffect() == "best") {
                power -= 33.3;
            } else if (currentRoom.getDropOffEffect() == "middle") {
                power -= 20.0;
            } else {
                power -= 10.0;
            }
        }
    }

    public static void resetPower(){
        power = 0;
    }

    public static double getPower() {
        return Math.ceil(power);
    }
}
