package src;

public class CardSquare extends BoardLocation
{
	public CardSquare(String name, int address)
	{
		super(name, address);
	}
	
	public int reward(Player player)
	{
		int amount;
		
		amount = (int)(Math.random()*401 - 200);
		player.addMoney(amount);
		
		return amount;
	}

	@Override
	public String[] getPossibleActions(Player player)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
