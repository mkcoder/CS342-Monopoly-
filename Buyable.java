public abstract class Buyable extends BoardLocation
{
    protected int cost;
    protected Player owner;
    protected int rent;

    public abstract void collectRent(Player player);

    public Buyable(int cost, Player owner)
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
