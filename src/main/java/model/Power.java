package worldofzuul.model;

public class Power {
    private double power;

    public void addPower(Product product, Room currentRoom) {
        if (product.getEnergyType() == currentRoom.getEnergyType()) {
            if (currentRoom.getDropOffEffect() == "best") {
                this.power += 33.3;
            } else if (currentRoom.getDropOffEffect() == "middle") {
                this.power += 20.0;
            } else {
                this.power += 10.0;
            }
            System.out.println(currentRoom.getDropOffText());
        } else {
            System.out.println("Der ser ikke ud til der skete den store forskel, da du satte " + product.getName() + "n.");
        }
    }

    public void removePower(Product product, Room currentRoom) {
        if (product.getEnergyType() == currentRoom.getEnergyType()) {
            if (currentRoom.getDropOffEffect() == "best") {
                this.power -= 33.3;
            } else if (currentRoom.getDropOffEffect() == "middle") {
                this.power -= 20.0;
            } else {
                this.power -= 10.0;
            }
        }
    }

    public double getPower() {
        return 50;
        //Math.ceil(this.power);
    }
}
