//khalil
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
	// POST: FCTVAL: 
    {
        return owner;
    }

    public void setOwner(Player owner)
    // PRE:
	// POST:FCTVAL:
    {
        this.owner = owner;
    }

    public boolean isOwned()
    // PRE:
	// POST:FCTVAL:
    {
        return owner != null;
    }
    
    @Override
    public String toString()
    // PRE:
	// POST:FCTVAL:
    {
    	String player;
    	
    	player = owner == null ? "no one" : owner.getToken();
    	return super.toString() + " Cost $" + cost + ". Owned by " + player + ".";
    }
}