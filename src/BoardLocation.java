package src;

public abstract class BoardLocation
{
    protected final String name;								// piece name     
    protected final int address;								// the distance from go
    protected BoardLocation next;						// the next place to move

    public BoardLocation(String name, int address)
    {
        this.name = name;
        this.address = address;
        this.next = null;
    }

    public abstract String[] getPossibleActions(Player player);

    public int getAddress() 
    {
        return address;
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
        return "Location: " + name + ".";
    }
    
    public static void Link(BoardLocation[] board)
    {
        for(int i=0;i<board.length - 1;i++)
            board[i].next = board[i+1];
        board[board.length-1].next = board[0];
    }
}