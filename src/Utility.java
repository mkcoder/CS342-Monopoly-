public class Utility extends Property
{
	private Utility other;
	
	public Utility(int cost, String name, int address)
	{
		super(cost, name, address);
	}

	@Override
	public void collectRent(Player player)
	{
		int rent;		
		
		rent = other.owner == this.owner ? player.getDice() * 10 : player.getDice() * 4;
		player.transferMoneyTo(owner, rent);
	}
	
	public void setOther(Utility other)
	{
		this.other = other;
	}

	@Override
	public String[] getPossibleActions(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}
	
}
