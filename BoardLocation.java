import java.util.List;

// habib
public abstract class BoardLocation
{
    protected String name;
    protected List<Player> currentPlayers;
    private BoardLocation next;

    public BoardLocation()
    {
        next = null;
    }

    public void setNext(BoardLocation next)
    {
        this.next = next;
    }

    public void addPlayerToBoardPiece()
    {
    }

    public BoardLocation getNext()
    {
        return next;
    }


}
