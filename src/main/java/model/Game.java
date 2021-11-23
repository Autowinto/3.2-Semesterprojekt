package worldofzuul.model;

import java.util.Iterator;

public class Game {
    private final Parser parser;
    Power power = new Power();
    Item[] allItems = {
            new Material("generator", EnergyType.WIND), new Material("vinger", EnergyType.WIND), new Material("tårn", EnergyType.WIND),
            new Material("turbine", EnergyType.WATER), new Material("vandrør", EnergyType.WATER), new Material("kabel", EnergyType.WATER),
            new Material("solpanel", EnergyType.SOLAR), new Material("inverter", EnergyType.SOLAR), new Material("stativ", EnergyType.SOLAR),
            new Product("vindmølle", EnergyType.WIND), new Product("vandmølle", EnergyType.WATER), new Product("solcelle", EnergyType.SOLAR)};
    Product windmill = (Product) allItems[9];
    Product watermill = (Product) allItems[10];
    Product solarpanel = (Product) allItems[11];
    Inventory inventory = new Inventory();
    private Room currentRoom;

    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room start, coal, workshop, wind1, wind2, wind3, wind4, water1, water2, water3, water4, water5, solar1, solar2, solar3, solar4;

        start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner at strømkilden er mod øst");
        coal = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul");
        workshop = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor");
        wind1 = new Room("udenfor i et område, hvor du kan mærke det blæser");
        wind2 = new Room("udenfor i et område, hvor der er en mild vind. Du ser nogle træer der giver læ for vinden", EnergyType.WIND, "middle", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da den milde vind og træerne.");
        wind3 = new Room("udenfor i et område, hvor det blæser. Du ser ikke noget der dækker for vinden", EnergyType.WIND, "best", "Din vindmølle genererer en rigtig god mængde energi, da det blæser og der ikke er noget som dækker.");
        wind4 = new Room("udenfor i et område, hvor det er en meget stærk vind", EnergyType.WIND, "worst", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da vinden er for stærk.");
        water1 = new Room("udenfor i et område, hvor du ser et vandfald", EnergyType.WATER, "best", "Din vandmølle genererer en rigtig god mængde energi, da der er en masse energi fra vandet der falder.");
        water2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af");
        water3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området", EnergyType.WATER, "middle", "Din vandmølle genererer en god mængde energi, men det er ikke optimalt, da en flod ikke er hvor der er mest energi.");
        water4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet");
        water5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod", EnergyType.WATER, "worst", "Din vandmølle genererer lidt energi, men det er ikke optimalt, da der ikke er meget energi i stilleliggende vand.");
        solar1 = new Room("udenfor i et varmt område med meget sollys");
        solar2 = new Room("på en flad mark med meget sol", EnergyType.SOLAR, "middle", "Din solcelle genererer en god mængde energi, men det er ikke optimalt, da en solcelle helst skal ligge på skrå.");
        solar3 = new Room("i en skov, hvor træerne dækker for solen", EnergyType.SOLAR, "worst", "Din solcelle genererer lidt energi, men det er ikke optimalt, da træerne skygger for solen.");
        solar4 = new Room("i et område med en bakke, hvor der er meget sol", EnergyType.SOLAR, "best", "Din solcelle genererer en rigtig god mængde energi, da der er en masse sol og den kan ligge med ca. 45 graders skråning på bakken. ");
        CraftingRoom craftingWind = new CraftingRoom("foran et grønt bord. Over bordet er der et skilt hvorpå der står \"vindenergi\".", EnergyType.WIND, windmill);
        CraftingRoom craftingWater = new CraftingRoom("foran et blåt bord. Over bordet er der et skilt hvorpå der står \"vandenergi\".", EnergyType.WATER, watermill);
        CraftingRoom craftingSun = new CraftingRoom("foran et gult bord. Over bordet er der et skilt hvorpå der står \"solenergi\".", EnergyType.SOLAR, solarpanel);

        //Udgange fra start
        start.setExit("øst", coal);
        start.setExit("vest", workshop);

        //Udgang fra kul
        coal.setExit("vest", start);

        //Udgange fra værksted
        workshop.setExit("nord", wind1);
        workshop.setExit("vest", water1);
        workshop.setExit("syd", solar1);
        workshop.setExit("vindstation", craftingWind);
        workshop.setExit("solstation", craftingSun);
        workshop.setExit("vandstation", craftingWater);
        workshop.setExit("øst", start);

        craftingWind.setExit("ud", workshop);
        craftingSun.setExit("ud", workshop);
        craftingWater.setExit("ud", workshop);

        //Udgange fra vind1
        wind1.setExit("øst", wind2);
        wind1.setExit("nord", wind3);
        wind1.setExit("syd", workshop);

        //Udgang fra vind2
        wind2.setExit("vest", wind1);

        //Udgange fra vind3
        wind3.setExit("syd", wind1);
        wind3.setExit("vest", wind4);

        //Udgang fra vind4
        wind4.setExit("øst", wind3);

        //Udgange fra vand1
        water1.setExit("øst", workshop);
        water1.setExit("nord", water2);

        //Udgange fra vand2
        water2.setExit("vest", water3);
        water2.setExit("syd", water1);

        //udgange fra vand3
        water3.setExit("øst", water2);
        water3.setExit("syd", water4);
        water3.setExit("nord", water5);

        //Udgang fra vand4
        water4.setExit("nord", water3);

        //Udgang fra vand5
        water5.setExit("syd", water3);

        //Udgange fra sol1
        solar1.setExit("nord", workshop);
        solar1.setExit("vest", solar2);
        solar1.setExit("syd", solar3);

        //Udgang fra sol2
        solar2.setExit("øst", solar1);

        //Udgange fra sol3
        solar3.setExit("nord", solar1);
        solar3.setExit("vest", solar4);

        //Udgang fra sol4
        solar4.setExit("øst", solar3);

        currentRoom = start;

        //vind items placering i rum
        wind1.addItem(allItems[0]);
        wind2.addItem(allItems[1]);
        wind4.addItem(allItems[2]);

        //Vand items placering i rum
        water2.addItem(allItems[3]);
        water3.addItem(allItems[4]);
        water4.addItem(allItems[5]);

        //Sol items placering i rum
        solar1.addItem(allItems[6]);
        solar2.addItem(allItems[7]);
        solar3.addItem(allItems[8]);
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
        System.out.println("Skriv '" + CommandWord.HELP + "' hvis du har brug for hjælp.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("Jeg er ikke helt med på hvad du mener...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.INVENTORY) {
            printInventory();
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.PLACE) {
            placeOnDropOff(command);
        } else if (commandWord == CommandWord.TAKE) {
            getItem(command);
        } else if (commandWord == CommandWord.CRAFT) {
            craft(command);
        } else if (commandWord == CommandWord.POWER) {
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
        } else if (newItem.getWeight() + inventory.getCurrentWeight() > inventory.getWeightLimit()) {
            System.out.println("Du har ikke plads til at samle denne ting op");
        } else {
            inventory.addItem(newItem);
            currentRoom.removeItem(item);
            System.out.println("Du har samlet " + item + " op");
            if (newItem instanceof Product) {
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
                for (Iterator<Item> iterator = inventory.getItems().iterator(); iterator.hasNext(); ) {
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
        if (currentRoom.getDropOffEffect() != null) {
            if (!command.hasSecondWord()) {
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
            if (product == null) {
                System.out.println("Genstanden blev ikke genkendt");
            } else if (product instanceof Material) {
                System.out.println("Du kan ikke sætte materialer, kun produkter");
            } else if (!inventory.getItems().contains(product)) {
                System.out.println("Du har ikke det nævnte produkt i dit inventar");
            } else {
                inventory.removeItem(product);
                currentRoom.addItem(product);
                System.out.println("Du har sat " + product.getName() + " i det nuværende rum");
                power.addPower((Product) product, currentRoom);
            }
        } else {
            System.out.println("Der er ikke noget sted at placere produkter i dette rum");
        }
    }

    public void printPower() {
        System.out.println("Du har " + power.getPower() + "% strøm");
    }

}