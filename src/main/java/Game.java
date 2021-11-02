package worldofzuul;

import java.util.ArrayList;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, sol1, sol2, sol3, sol4;
    ArrayList<Item> inventar = new ArrayList<>();

    public Game()
    {
        createRooms();
        parser = new Parser();
    }


    private void createRooms()
    {
        start = new Room("i et rum med en lyskilde");
        kul = new Room("i et rum med kul");
        værksted = new Room("i et værksted med tre arbejdsborde");
        vind1 = new Room("i vind1");
        vind2 = new Room("i vind2");
        vind3 = new Room("i vind3");
        vind4 = new Room("i vind4");
        vand1 = new Room("i vand1");
        vand2 = new Room("i vand2");
        vand3 = new Room("i vand3");
        vand4 = new Room("i vand4");
        sol1 = new Room("i sol1");
        sol2 = new Room("i sol2");
        sol3 = new Room("i sol3");
        sol4 = new Room("i sol4");

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

        //Udgang fra vand4
        vand4.setExit("nord",vand3);

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
        vind1.setItem(new Material("generator",EnergyType.VIND));
        vind2.setItem(new Material("vinger",EnergyType.VIND));
        vind4.setItem(new Material("tårn",EnergyType.VIND));

        //Vand items placering i rum
        vand2.setItem(new Material("Turbine",EnergyType.VAND));
        vand3.setItem(new Material("vandrør",EnergyType.VAND));
        vand4.setItem(new Material("kabel",EnergyType.VAND));

        //Sol items placering i rum
        sol1.setItem(new Material("solpanel",EnergyType.SOL));
        sol2.setItem(new Material("inverter",EnergyType.SOL));
        sol3.setItem(new Material("stativ",EnergyType.SOL));
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
        System.out.println("skriv '" + CommandWord.HJÆLP + "' hvis du har brug for hjælp.");
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
            printInventar();
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.TAG)
        {
            getItem(command);
        }
        return wantToQuit;
    }
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg tag ?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("Det er ikke her!");
        }
        else {
            inventar.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("du har samlet " + item + " op");
        }
    }

    private void printInventar() {
        String output = "";
        for (int i = 0; i < inventar.size(); i++) {
            output += inventar.get(i).getName() + " ";
        }
        System.out.println("Inventar indholder: " + output);
    }

    private void printHelp() 
    {
        System.out.println("Tak fordi du spøger om hjælp");
        System.out.println();
        System.out.println("dine muligheder er følgende:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Hvor hen ?");
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
            System.out.println("giver du op?");
            return false;
        }
        else {
            return true;
        }
    }
}
