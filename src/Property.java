package src;

public abstract class Property extends BoardLocation
{
    protected final int cost;						// the cost of property
    protected Player owner;					// who owns the  property
    //protected int rent;						// how much rent the person who lends pays

    public Property(String name, int address, int cost)
    {
    	super(name,address);
        this.cost = cost;
        this.owner = null;
    }
    
    public abstract void collectRent(Player player);

    public int getCost()
    {
        return cost;
    }

    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public boolean isOwned()
    {
        return owner != null;
    }
    
    @Override
    public String toString()
    {
    	String player;
    	
    	player = owner == null ? "no one" : owner.getToken();
    	return super.toString() + " costs $" + cost + " owned by " + player;
    }
    
 
}