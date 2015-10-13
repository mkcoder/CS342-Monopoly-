import java.util.List;

public abstract class BoardLocation
{
    protected final String name;								// piece name     
    protected final int address;								// the distance from go
    protected List<Player> currentPlayers;				// current player on this board piece
    protected BoardLocation next;						// the next place to move
    
    /**
	 * @param name
	 * @param address
	 */
	public BoardLocation(String name, int address) {
		super();
		this.name = name;
		this.address = address;
	}

	public BoardLocation()
    {
		name = "";
		address = 0;
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
    
    public abstract String[] getPossibleActions(Player player);

}