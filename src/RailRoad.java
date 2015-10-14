package src;

public class RailRoad extends Property
{
	private RailRoad[] others;
	
	public RailRoad(int cost, String name, int address)
	{
		super(cost, name, address);
		others = new RailRoad[3];
	}	
	
	public void setOthers(RailRoad other1, RailRoad other2, RailRoad other3)
	{
		others[0] = other1;
		others[1] = other2;
		others[2] = other3;
	}

	@Override
	public void collectRent(Player player)
	{
		int payment;
		
		payment = 25;
		for(RailRoad r : others)
			if(r.getOwner() == this.owner)
				payment *= 2;
		
		player.transferMoneyTo(owner, payment);
	}

	@Override
	public String[] getPossibleActions(Player player)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
