package worldofzuul;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Game
{
    private Parser parser;
    private Room currentRoom;

    Power power = new Power();
    Item[] allItems = {
            new Material("generator",EnergyType.VIND), new Material("vinger",EnergyType.VIND), new Material("tårn",EnergyType.VIND),
            new Material("turbine",EnergyType.VAND), new Material("vandrør",EnergyType.VAND), new Material("kabel",EnergyType.VAND),
            new Material("solpanel",EnergyType.SOL), new Material("inverter",EnergyType.SOL), new Material("stativ",EnergyType.SOL),
            new Product("vindmølle", EnergyType.VIND), new Product("vandmølle", EnergyType.VAND), new Product("solcelle", EnergyType.SOL)};

    Item vindmølle = allItems[9];
    Item vandmølle = allItems[10];
    Item solcelle = allItems[11];

    Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, sol1, sol2, sol3, sol4;
    Inventory inventory = new Inventory();

    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    private void createRooms()
    {
        Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, vand5, sol1, sol2, sol3, sol4;

        start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner strømkilden er mod øst");
        kul = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul");
        værksted = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor");
        vind1 = new Room("udenfor i et område, hvor du kan mærke det blæser");
        vind2 = new Room("udenfor i et område, hvor der er en mild vind, du ser nogle træer der giver læ for vinden",EnergyType.VIND,"middle");
        vind3 = new Room("udenfor i et område, hvor det blæser, du ser ikke noget der dække for vinden",EnergyType.VIND,"best");
        vind4 = new Room("udenfor i et område, hvor det er en meget stærk vind",EnergyType.VIND,"worst");
        vand1 = new Room("udenfor i et område, hvor du ser et vandfald",EnergyType.VAND,"best");
        vand2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af");
        vand3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området",EnergyType.VAND,"middle");
        vand4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet");
        vand5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod",EnergyType.VAND,"worst");
        sol1 = new Room("udenfor i et varmt område med meget sollys");
        sol2 = new Room("på en flad mark med meget sol",EnergyType.SOL,"middle");
        sol3 = new Room("i en skov, hvor træerne dækker for solen",EnergyType.SOL,"worst");
        sol4 = new Room("i et område med en bakke, der er meget sol",EnergyType.SOL,"best");

        //Udgange fra start
        start.setExit("øst", kul);
        start.setExit("vest", værksted);

        //Udgang fra kul
        kul.setExit("vest", start);

        //Udgange fra værksted
        værksted.setExit("nord", vind1);
        værksted.setExit("vest", vand1);
        værksted.setExit("syd", sol1);
        værksted.setExit("øst", start);

        //Udgange fra vind1
        vind1.setExit("øst",vind2);
        vind1.setExit("nord",vind3);
        vind1.setExit("syd",værksted);

        //Udgang fra vind2
        vind2.setExit("vest",vind1);

        //Udgange fra vind3
        vind3.setExit("syd",vind1);
        vind3.setExit("vest",vind4);

        //Udgang fra vind4
        vind4.setExit("øst",vind3);

        //Udgange fra vand1
        vand1.setExit("øst",værksted);
        vand1.setExit("nord",vand2);

        //Udgange fra vand2
        vand2.setExit("vest",vand3);
        vand2.setExit("syd",vand1);

        //udgange fra vand3
        vand3.setExit("øst",vand2);
        vand3.setExit("syd",vand4);
        vand3.setExit("nord",vand5);

        //Udgang fra vand4
        vand4.setExit("nord",vand3);

        //Udgang fra vand5
        vand5.setExit("syd",vand3);

        //Udgange fra sol1
        sol1.setExit("nord",værksted);
        sol1.setExit("vest",sol2);
        sol1.setExit("syd",sol3);

        //Udgang fra sol2
        sol2.setExit("øst",sol1);

        //Udgange fra sol3
        sol3.setExit("nord",sol1);
        sol3.setExit("vest",sol4);

        //Udgang fra sol4
        sol4.setExit("øst",sol3);

        currentRoom = start;

        //vind items placering i rum
        vind1.addItem(allItems[0]);
        vind2.addItem(allItems[1]);
        vind4.addItem(allItems[2]);

        //Vand items placering i rum
        vand2.addItem(allItems[3]);
        vand3.addItem(allItems[4]);
        vand4.addItem(allItems[5]);

        //Sol items placering i rum
        sol1.addItem(allItems[6]);
        sol2.addItem(allItems[7]);
        sol3.addItem(allItems[8]);
    }

    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Tak fordi du deltog. Hav en god dag.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Velkommen til spillet for bæredygtig energi!");
        System.out.println("Dette er et spil som vil lære dig om forskellige energi løsninger.");
        System.out.println("Skriv '" + CommandWord.HJÆLP + "' hvis du har brug for hjælp.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UKENDT) {
            System.out.println("Jeg er ikke helt med på hvad du mener...");
            return false;
        }

        if (commandWord == CommandWord.HJÆLP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GÅ) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.INVENTAR) {
            printInventory();
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }

        else if (commandWord == CommandWord.SÆT) {
            tryDropOff(command);
        }

        else if (commandWord == CommandWord.TAG)
        {
            getItem(command);
        }

        else if (commandWord == CommandWord.STRØM) {
            Power.getPower();
        }
          
        return wantToQuit;
          
    }
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg tage?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("Det er ikke her!");
        }
        else if (newItem.getWeight() + inventory.getCurrentWeight() > inventory.getWeightLimit()){
            System.out.println("Du har ikke plads til at samle denne ting op");
        }
        else {
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

    private void printHelp() 
    {
        System.out.println("Tak fordi du spørger om hjælp");
        System.out.println();
        System.out.println("Dine muligheder er følgende:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Hvorhen?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Det er ikke muligt!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Giver du op?");
            return false;
        }
        else {
            return true;
        }
    }

    private void tryDropOff(Command command) {
        if (currentRoom.getDropOffEffect() != null){
            if(!command.hasSecondWord()) {
                System.out.println("Hvad skal jeg sætte?");
                return;
            }
            String productName = command.getSecondWord();
            Item product = null;

            for (int i = 0; i < allItems.length; i++) {
                if (allItems[i].getName().equals(productName)) {
                    product = allItems[i];
                }
            }
            if (product == null){
                System.out.println("Genstanden blev ikke genkendt");
            }

            if (product instanceof Material){
                System.out.println("Du kan ikke sætte materialer, kun produkter");
            } else if (!inventory.getItems().contains(product)){
                System.out.println("Du har ikke det nævnte produkt i dit inventory");
            } else {
                
            }
        } else {
            System.out.println("Der er ikke noget sted at placerer produkter i dette rum");
        }

        /*if (currentRoom.getDropoff() == inventory.getName() && (currentRoom.getDropoff() != null)) {
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
        } else if (currentRoom.getDropoff() != inventory.getName() && (currentRoom.getDropoff() != null)
                && (inventory.getName() != null)) {
            System.out.println("Du kan ikke sætte det produkt som du har i den inventory her.");
        } else if (currentRoom.getDropoff() == null) {
            System.out.println("Du kan ikke sætte noget produkt her.");
        } else if (inventory.getName() == null) {
            System.out.println("Du har ikke nogen produkter på dig.");

        }*/
    }

}
