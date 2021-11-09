package worldofzuul;
import java.util.Iterator;

public class Game {
    private Parser parser;
    private Room currentRoom;

    Power power = new Power();
    Item[] allItems = {
            new Material("generator",EnergyType.VIND), new Material("vinger",EnergyType.VIND), new Material("tårn",EnergyType.VIND),
            new Material("turbine",EnergyType.VAND), new Material("vandrør",EnergyType.VAND), new Material("kabel",EnergyType.VAND),
            new Material("solpanel",EnergyType.SOL), new Material("inverter",EnergyType.SOL), new Material("stativ",EnergyType.SOL),
            new Product("vindmølle", EnergyType.VIND), new Product("vandmølle", EnergyType.VAND), new Product("solcelle", EnergyType.SOL)};

    Product vindmølle = (Product) allItems[9];
    Product vandmølle = (Product) allItems[10];
    Product solcelle = (Product) allItems[11];

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
        vind2 = new Room("udenfor i et område, hvor der er en mild vind, du ser nogle træer der giver læ for vinden",EnergyType.VIND,"middle","Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da den milde vind og træerne.");
        vind3 = new Room("udenfor i et område, hvor det blæser, du ser ikke noget der dække for vinden",EnergyType.VIND,"best","Din vindmølle genererer en rigtig god mængde energi, da det blæser og der ikke er noget som dækker.");
        vind4 = new Room("udenfor i et område, hvor det er en meget stærk vind",EnergyType.VIND,"worst", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da vinden er for stærk.");
        vand1 = new Room("udenfor i et område, hvor du ser et vandfald",EnergyType.VAND,"best", "Din vandmølle genererer en rigtig god mængde energi, da der er en masse energi fra vandet der falder.");
        vand2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af");
        vand3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området",EnergyType.VAND,"middle", "Din vandmølle genererer en god mængde energi, men det er ikke optimalt, da en flod ikke er hvor der er mest energi.");
        vand4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet");
        vand5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod",EnergyType.VAND,"worst","Din vandmølle genererer lidt energi, men det er ikke optimalt, da der ikke er meget energi i stilleliggende vand.");
        sol1 = new Room("udenfor i et varmt område med meget sollys");
        sol2 = new Room("på en flad mark med meget sol",EnergyType.SOL,"middle", "Din solcelle genererer en god mængde energi, men det er ikke optimalt, da en solcelle helst skal ligge på skrå.");
        sol3 = new Room("i en skov, hvor træerne dækker for solen",EnergyType.SOL,"worst","Din solcelle genererer lidt energi, men det er ikke optimalt, da træerne skygger for solen.");
        sol4 = new Room("i et område med en bakke, hvor der er meget sol",EnergyType.SOL,"best", "Din solcelle genererer en rigtig god mængde energi, da der er en masse sol og den kan ligge med ca. 45 graders skråning på bakken. ");
        CraftingRoom craftingWind = new CraftingRoom("foran et grønt bord.", EnergyType.VIND, vindmølle);
        CraftingRoom craftingWater = new CraftingRoom("foran et blåt bord.", EnergyType.VAND, vandmølle);
        CraftingRoom craftingSun = new CraftingRoom("foran et gult bord.", EnergyType.SOL, solcelle);

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
        værksted.setExit("gult", craftingSun);
        værksted.setExit("blå", craftingWater);
        værksted.setExit("øst", start);

        craftingWind.setExit("ud", værksted);
        craftingSun.setExit("ud", værksted);
        craftingWater.setExit("ud", værksted);

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

    public void play() {
        printWelcome();

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
        System.out.println("Dette er et spil som vil lære dig om forskellige energiløsninger.");
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
        }

        else if (commandWord == CommandWord.SÆT) {
            placeOnDropOff(command);
        } else if (commandWord == CommandWord.TAG) {
            getItem(command);
        } else if (commandWord == CommandWord.BYG) {
            craft(command);
        }
        else if (commandWord == CommandWord.STRØM) {
            printPower();
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
        }
        else if (newItem.getWeight() + inventory.getCurrentWeight() > inventory.getWeightLimit()){
            System.out.println("Du har ikke plads til at samle denne ting op");
        }
        else {
            inventory.addItem(newItem);
            currentRoom.removeItem(item);
            System.out.println("Du har samlet " + item + " op");
            if(newItem instanceof Product){
                power.removePower((Product) newItem, currentRoom);
            }
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

            // If the room you're entering is a CraftingRoom, check the energyType and take any materials of that type.
            if (currentRoom instanceof CraftingRoom craftingRoom) {

                // Vi bruger en iterator for at undgå en ConcurrentModificationException
                for (Iterator<Item> iterator = inventory.getItems().iterator(); iterator.hasNext();) {
                    Item item = iterator.next();
                    if (item.getEnergyType() == craftingRoom.getEnergyType() && item instanceof Material materialItem) {
                        System.out.println("Du får en lys idé og lægger din/dit " + item.getName() + " på arbejdsbordet!");
                        craftingRoom.placeItem(materialItem);
                        iterator.remove();
                    }
                }
            }
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
            return;
        }
        System.out.println("Du mangler noget før du kan bygge en " + craftingRoom.getCraftingResult().getName());
        System.out.println("Arbejdsbordet indeholder følgende: " + craftingRoom.getPlacedItemsString());
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Giver du op?");
            return false;
        } else {
            return true;
        }
    }

    private void placeOnDropOff(Command command) {
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
            } else if (product instanceof Material){
                System.out.println("Du kan ikke sætte materialer, kun produkter");
            } else if (!inventory.getItems().contains(product)){
                System.out.println("Du har ikke det nævnte produkt i dit inventory");
            } else {
                inventory.removeItem(product);
                currentRoom.addItem(product);
                System.out.println("Du har sat "+product.getName()+" i det nuværende rum");
                power.addPower((Product) product, currentRoom);
            }
        } else {
            System.out.println("Der er ikke noget sted at placere produkter i dette rum");
        }
    }

    public void printPower(){
        System.out.println("Du har "+power.getPower()+"% strøm");
    }

}
