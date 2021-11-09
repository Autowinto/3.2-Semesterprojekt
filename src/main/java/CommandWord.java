package worldofzuul;
public enum CommandWord
{
    GO("gå"),
    QUIT("quit"),
    HELP("hjælp"),
    PLACE("sæt"),
    UNKNOWN("?"),
    TAKE("tag"),
    INVENTORY("inventar"),
    POWER("strøm"),
    CRAFT("byg");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
