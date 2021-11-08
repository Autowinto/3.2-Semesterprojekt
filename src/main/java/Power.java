package worldofzuul;

public class Power {
    private double power;

    public void addPower(Product product, Room currentRoom){
        if(product.getEnergyType() == currentRoom.getEnergyType()){
            if(currentRoom.getDropOffEffect() == "best"){
                this.power += 1/3;
            } else if (currentRoom.getDropOffEffect() == "middle"){
                this.power += 20;
            } else {
                this.power += 10;
            }
        } else {
            System.out.println("Der ser ikke ud til der skete den store forskel, da du satte "+product.getName()+"n.");
        }
    }

    public double getPower() {
        return this.power;
    }
}
