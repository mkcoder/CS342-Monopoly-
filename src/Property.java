// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Holds the property information such as how much each property cost, and who owns the property.
//              Lets you collect rent from other players in the game.

package src;

public abstract class Property extends BoardLocation
{
    protected final int cost;						// the cost of property
    protected Player owner;					// who owns the  property
    //protected int rent;						// how much rent the person who lends pays

    public Property(String name, int address, int cost)
    // PRE: name, address, and cost is initialized 0 <= address <= 39, cost >= 0  
	// POST: creates a Property object with name set to name, address set to address
    //	cost set to cost and owner set to owner
    {
    	super(name,address);
        this.cost = cost;
        this.owner = null;
    }
    
    public abstract void collectRent(Player player);

    public int getCost()
    // PRE: cost and player is initialized  
	// POST: FCTVAL: returns the cost to collect from player
    {
        return cost;
    }

    public Player getOwner()
    // PRE: player is initalized
	// POST: FCTVAL: returns the owner of the class
    {
        return owner;
    }

    public void setOwner(Player owner)
	// POST:FCTVAL: sets the owner to owner
    {
        this.owner = owner;
    }

    public boolean isOwned()
	// POST:FCTVAL: is this owned by someone
    {
        return owner != null;
    }
    
    @Override
    public String toString()
    // PRE:
	// POST:FCTVAL: a string that describes the property and the cost of it
    //   and if it is owned by anyone
    {
    	String player;
    	
    	player = owner == null ? "no one" : owner.getToken();
    	return super.toString() + " Cost $" + cost + ". Owned by " + player + ".";
    }
}