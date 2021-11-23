package worldofzuul.model;

public enum CommandWord {
    GO("gå"),
    QUIT("afslut"),
    HELP("hjælp"),
    PLACE("sæt"),
    UNKNOWN("?"),
    TAKE("tag"),
    INVENTORY("inventar"),
    POWER("strøm"),
    CRAFT("byg");

    private String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}
