package worldofzuul;

public class Power {
    private String placedRoomVind;
    private String placedRoomVand;
    private String placedRoomSol;
    private static int vindPower;
    private static int vandPower;
    private static int solPower;
    private static int power;

    public void setRoomVind(Room currentRoom) {
        placedRoomVind = currentRoom.getName();
        System.out.println("Du har sat produktet i "+placedRoomVind);
        checkRoomVind();
    }

    public void setRoomVand(Room currentRoom) {
        placedRoomVand = currentRoom.getName();
        System.out.println("Du har sat produktet i "+placedRoomVand);
        checkRoomVand();
    }

    public void setRoomSol(Room currentRoom) {
        placedRoomSol = currentRoom.getName();
        System.out.println("Du har sat produktet i "+placedRoomSol);
        checkRoomSol();
    }

    public void checkRoomVind() {
        if (placedRoomVind == "vind2") {
            System.out.println("Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da den milde vind og træerne.");
            vindPower = 22;
        }
        else if (placedRoomVind == "vind3") {
            System.out.println("Din vindmølle genererer en rigtig god mængde energi, da det blæser og der ikke er noget som dækker.");
            vindPower = 33;
        }
        else if (placedRoomVind == "vind4") {
            System.out.println("Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da vinden er for stærk.");
            vindPower = 15;
        }
        else {
            System.out.println("Din vindmølle genererer ingen energi pga. dens placering.");
            vindPower = 0;
        }
    }

    public void checkRoomVand() {
        if (placedRoomVand == "vand1") {
            System.out.println("Din vandmølle genererer en rigtig god mængde energi, da der er en masse energi fra vandet der falder.");
            vandPower = 34;
        }
        else if (placedRoomVand == "vand3") {
            System.out.println("Din vandmølle genererer en god mængde energi, men det er ikke optimalt, da en flod ikke er hvor der er mest energi.");
            vandPower = 26;
        }
        else if (placedRoomVand == "vand5") {
            System.out.println("Din vandmølle genererer lidt energi, men det er ikke optimalt, da der ikke er meget energi i stilleliggende vand.");
            vandPower = 8;
        }
        else {
            System.out.println("Din vandmølle genererer ingen energi pga. dens placering.");
            vandPower = 0;
        }
    }

    public void checkRoomSol() {
        if (placedRoomSol == "sol2") {
            System.out.println("Din solcelle genererer en god mængde energi, men det er ikke optimalt, da en solcelle helst skal ligge på skrå.");
            solPower = 26;
        }
        else if (placedRoomSol == "sol3") {
            System.out.println("Din solcelle genererer lidt energi, men det er ikke optimalt, da træerne skygger for solen.");
            solPower = 11;
        }
        else if (placedRoomSol == "sol4") {
            System.out.println("Din solcelle genererer en rigtig god mængde energi, da der er en masse sol og den kan ligge med ca. 45 graders skråning på bakken. ");
            solPower = 33;
        }
        else {
            System.out.println("Din solcelle genererer næsten ingen energi pga. dens placering.");
            solPower = 5;
        }
    }

    public static void getPower() {
        power = vandPower+solPower+vindPower;
        System.out.println("Du har "+power+"% strøm.");
    }
}
