package worldofzuul;

public class Game
{
    private Parser parser;
    private Room currentRoom;
        

    public Game() 
    {
        createRooms();
        parser = new Parser();
    }


    private void createRooms()
    {
        Room start, kul, værksted, vind1, vind2, vind3, vind4, vand1, vand2, vand3, vand4, vand5, sol1, sol2, sol3, sol4;
      
        start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner strømkilden er mod øst.");
        kul = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul.");
        værksted = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor.");
        vind1 = new Room("udenfor i et område, hvor du kan mærke det blæser.");
        vind2 = new Room("udenfor i et område, hvor der er en mild vind, du ser nogle træer der giver læ for vinden.");
        vind3 = new Room("udenfor i et område, hvor det blæser, du ser ikke noget der dække for vinden.");
        vind4 = new Room("udenfor i et område, hvor det er en meget stærk vind.");
        vand1 = new Room("udenfor i et område, hvor du ser et vandfald.");
        vand2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af.");
        vand3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området.");
        vand4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet.");
        vand5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod.");
        sol1 = new Room("udenfor i et varm område, med meget sollys.");
        sol2 = new Room("på en flad mark med meget sol.");
        sol3 = new Room("i en skov, hvor træerne dækker for solen.");
        sol4 = new Room("i et område med en bakke, der er meget sol.");

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
    }

    public void play() 
    {            
        printWelcome();

                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
