// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: BoardLocation is an abstract class that is the heirachly parent of all locations on the board.
//              It is meant to represent a generic location on the Monoply Board. All of the methods are inherited from this class 
//              and implemented by any of its subclasses.
//             
package src;

public abstract class BoardLocation
{
	public static final String CAN_BE_PURCHASED = "Can be purchased."; // can be purchased string
	public static final String PAY_RENT = "You have to pay the rent."; // pay rent string
	public static final String PICK_CARD = "Pick a card."; // pick card string
	public static final String PAY_TAX = "Pay a tax."; // pay tax string
	public static final String YOU_OWN = "You own this."; // pay tax string
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
    	for(int i=0;i<board.length - 1;i++)             // iterate through the length of the board
    		board[i].next = board[i+1];                 // and connect the next pointer
    	board[board.length-1].next = board[0];
    }
}