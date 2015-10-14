import java.util.ArrayList;
import java.util.List;

public abstract class BoardLocation
{
    protected final String name;								// piece name     
    protected final int address;								// the distance from go
    protected List<Player> currentPlayers;				// current player on this board piece
    protected BoardLocation next;						// the next place to move

	public BoardLocation(String name, int address)
	{
		this.name = name;
		this.address = address;
		this.currentPlayers = new ArrayList<>();
		this.next = null;
	}
	
	public abstract String[] getPossibleActions(Player player);

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
    
    public String getName()
    {
    	return name;
    }
    
    @Override
    public String toString()
    {
    	return "Location: " + name;
    }
}