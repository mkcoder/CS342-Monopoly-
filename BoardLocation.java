import java.util.List;

// habib
public abstract class BoardLocation
{
    protected static BoardLocation go;
    protected String name;
    protected int cost;
    protected List<Player> currentPlayers;


    // they pass in the head of the linked list
    // we
    public BoardLocation(BoardLocation go)
    {
        this.go = go;
    }

    public static BoardLocation getGo()
    {
        return go;
    }

    public void addPlayerToBoardPiece()
    {
        
    }
}
