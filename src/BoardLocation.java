//habib
package src;

public abstract class BoardLocation
{
    protected final String name;   // piece name     
    protected final int address;   // the distance from go
    protected BoardLocation next;  // the next place to move

	public BoardLocation(String name, int address)
	// PRE: name and address are intialized,
	// POST: a BoardLocation object is initialized with the specifed name and address
	
	{
		this.name = name;
		this.address = address;
		this.next = null;
	}
	
	public abstract String[] getPossibleActions(Player player);

    public int getAddress() 
    // POST:FCTVAL = an integer value the denotes the dinstace from go
    {
        return address;
    }

    public BoardLocation getNext()
    // POST:FCTVAL = the next sequential location after this
    {
        return next;
    }
    
    public String getName()
    // POST:FCTVAL = A string that is this location's name         
    {
    	return name;
    }
    
    @Override
    public String toString()
    // POST:FCTVAL = A string the describes properties name, address, cost.         
    {
    	return "Location: " + name + ".";
    }
    
    public static void Link(BoardLocation[] board)
    // PRE: board is an array of BoardLocations with all objects initialized
    // POST: All the boardLocation objects will be linked in circular manner
    {
    	for(int i=0;i<board.length - 1;i++)
    		board[i].next = board[i+1];
    	board[board.length-1].next = board[0];
    }
}