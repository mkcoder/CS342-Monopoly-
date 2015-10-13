public abstract class Property extends BoardLocation
{
    protected int cost;						// the cost of property
    protected Player owner;					// who owns the  property
    protected int rent;						// how much rent the person who lends pays

    public abstract void collectRent(Player player);

    public Property(int cost, Player owner)
    {
        this.cost = cost;
        this.owner = owner;
    }

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
}