package src;

public class Utility extends Property
{
	private Utility other;
	
	public Utility(String name, int address, int cost)
	{
		super(name, address, cost);
	}

	@Override
	public void collectRent(Player player)
	{
		int rent;		
		
		rent = other.owner == this.owner ? Player.getDice() * 10 : Player.getDice() * 4;
		player.transferMoneyTo(owner, rent);
	}
	
	public static void setOther(Utility water, Utility electric)
	{
		water.other = electric;
		electric.other = water;
	}

	@Override
	public String[] getPossibleActions(Player player)
	{
		return null;
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}
	
}
