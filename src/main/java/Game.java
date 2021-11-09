package worldofzuul;

import java.util.ArrayList;

public class Game {
    private Parser parser;
    private Room currentRoom;

    Power power = new Power();
    Item solcelle = new Product("solcelle", EnergyType.SOL);
    Item vindmølle = new Product("vindmølle", EnergyType.VIND);
    Item vandmølle = new Product("vandmølle", EnergyType.VAND);

    Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, sol1, sol2, sol3, sol4;
    Inventory inventory = new Inventory();

    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, vand5, sol1, sol2, sol3, sol4;

        start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner strømkilden er mod øst");
        kul = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul");
        værksted = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor");
        vind1 = new Room("udenfor i et område, hvor du kan mærke det blæser");
        vind2 = new Room("udenfor i et område, hvor der er en mild vind, du ser nogle træer der giver læ for vinden", "vindmølle", "vind2");
        vind3 = new Room("udenfor i et område, hvor det blæser, du ser ikke noget der dække for vinden", "vindmølle", "vind3");
        vind4 = new Room("udenfor i et område, hvor det er en meget stærk vind", "vindmølle", "vind4");
        vand1 = new Room("udenfor i et område, hvor du ser et vandfald");
        vand2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af", "vandmølle", "vand2");
        vand3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området", "vandmølle", "vand3");
        vand4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet");
        vand5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod", "vandmølle", "vand5");
        sol1 = new Room("udenfor i et varm område, med meget sollys");
        sol2 = new Room("på en flad mark med meget sol", "solcelle", "sol2");
        sol3 = new Room("i en skov, hvor træerne dækker for solen", "solcelle", "sol3");
        sol4 = new Room("i et område med en bakke, der er meget sol", "solcelle", "sol4");
        CraftingRoom craftingWind = new CraftingRoom("foran et grønt bord", new Product("Vindmølle", EnergyType.VIND));

        //Udgange fra start
        start.setExit("øst", kul);
        start.setExit("vest", værksted);

        //Udgang fra kul
        kul.setExit("vest", start);

        //Udgange fra værksted
        værksted.setExit("nord", vind1);
        værksted.setExit("vest", vand1);
        værksted.setExit("syd", sol1);
        værksted.setExit("grøn", craftingWind);
        værksted.setExit("øst", start);

        //Udgange fra vind1
        vind1.setExit("øst", vind2);
        vind1.setExit("nord", vind3);
        vind1.setExit("syd", værksted);

        //Udgang fra vind2
        vind2.setExit("vest", vind1);

        //Udgange fra vind3
        vind3.setExit("syd", vind1);
        vind3.setExit("vest", vind4);

        //Udgang fra vind4
        vind4.setExit("øst", vind3);

        //Udgange fra vand1
        vand1.setExit("øst", værksted);
        vand1.setExit("nord", vand2);

        //Udgange fra vand2
        vand2.setExit("vest", vand3);
        vand2.setExit("syd", vand1);

        //udgange fra vand3
        vand3.setExit("øst", vand2);
        vand3.setExit("syd", vand4);
        vand3.setExit("nord", vand5);

        //Udgang fra vand4
        vand4.setExit("nord", vand3);

        //Udgang fra vand5
        vand5.setExit("syd", vand3);

        //Udgange fra sol1
        sol1.setExit("nord", værksted);
        sol1.setExit("vest", sol2);
        sol1.setExit("syd", sol3);

        //Udgang fra sol2
        sol2.setExit("øst", sol1);

        //Udgange fra sol3
        sol3.setExit("nord", sol1);
        sol3.setExit("vest", sol4);

        //Udgang fra sol4
        sol4.setExit("øst", sol3);

        currentRoom = start;

        //vind items placering i rum
        vind1.addItem(new Material("generator", EnergyType.VIND));
        vind2.addItem(new Material("vinger", EnergyType.VIND));
        vind4.addItem(new Material("tårn", EnergyType.VIND));

        //Vand items placering i rum

        vand2.addItem(new Material("turbine", EnergyType.VAND));
        vand3.addItem(new Material("vandrør", EnergyType.VAND));
        vand4.addItem(new Material("kabel", EnergyType.VAND));

        //Sol items placering i rum
        sol1.addItem(new Material("solpanel", EnergyType.SOL));
        sol2.addItem(new Material("inverter", EnergyType.SOL));
        sol3.addItem(new Material("stativ", EnergyType.SOL));
    }

    public void play() {
        printWelcome();

        inventory.addItem(vindmølle);

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Tak fordi du deltog. Hav en god dag.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Velkommen til spillet for bæredygtig energi!");
        System.out.println("Dette er et spil som vil lære dig om forskellige energi løsninger.");
        System.out.println("Skriv '" + CommandWord.HJÆLP + "' hvis du har brug for hjælp.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UKENDT) {
            System.out.println("Jeg er ikke helt med på hvad du mener...");
            return false;
        }

        if (commandWord == CommandWord.HJÆLP) {
            printHelp();
        } else if (commandWord == CommandWord.GÅ) {
            goRoom(command);
        } else if (commandWord == CommandWord.INVENTAR) {
            printInventory();
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.PLACE) {
            if (currentRoom.getDropoff() == inventory.getName()) {
                if (currentRoom.getDropoff() == "solcelle") {
                    power.setRoomSol(currentRoom);
                    inventory.removeItem(solcelle);
                } else if (currentRoom.getDropoff() == "vindmølle") {
                    power.setRoomVind(currentRoom);
                    inventory.removeItem(vindmølle);
                } else if (currentRoom.getDropoff() == "vandmølle") {
                    power.setRoomVand(currentRoom);
                    inventory.removeItem(vandmølle);
                }
            } else {
                System.out.println("Unable to place down item. \n ~~~");
                System.out.println("The current room's drop off is: ");
                currentRoom.getDropoff();
                System.out.println("Your inventory is: ");
                inventory.show();
            }
        } else if (commandWord == CommandWord.TAG) {
            getItem(command);
        } else if (commandWord == CommandWord.BYG) {
            craft(command);
        }

        return wantToQuit;

    }

    private void getItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg tage?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("Det er ikke her!");
        } else {
            inventory.addItem(newItem);
            currentRoom.removeItem(item);
            System.out.println("Du har samlet " + item + " op");
        }
    }

    private void printInventory() {
        String output = "";
        for (int i = 0; i < inventory.getItems().size(); i++) {
            output += inventory.getItems().get(i).getName() + " ";
        }
        System.out.println("Dit inventar indholder:");
        System.out.println("-----------------------");
        System.out.println(output);
    }

    private void printHelp() {
        System.out.println("Tak fordi du spørger om hjælp");
        System.out.println();
        System.out.println("Dine muligheder er følgende:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Hvorhen?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Det er ikke muligt!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void craft(Command command) {
        if (!(currentRoom instanceof CraftingRoom craftingRoom)) {
            System.out.println("Du kan ikke bygge noget her!");
            return;
        }
        if (craftingRoom.canCraft()) {
            System.out.println("Du bygger en " + craftingRoom.getCraftingResult().getName());
            inventory.addItem(craftingRoom.getCraftingResult());
        }
        System.out.println("Du mangler noget før du kan bygge en " + craftingRoom.getCraftingResult().getName());
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Giver du op?");
            return false;
        } else {
            return true;
        }
    }

}
