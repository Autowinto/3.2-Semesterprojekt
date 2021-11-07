package worldofzuul;
public enum CommandWord
{
    GÅ("gå"), QUIT("quit"), HJÆLP("hjælp"), SÆT("sæt"), UKENDT("?"),TAG("tag"),INVENTAR("inventar"),STRØM("strøm");
    
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
