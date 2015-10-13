public class Lot extends Property
{
	private final int houseCost;
	private final int hotelCost;
	private final String color;					// color of the board piece
		
	private int houses;
	private int hotel;
	
	public Lot(int cost, Player owner, String name, String color, int hotelCost, int  houseCost) 
	{
		super(cost, owner);
		this.houseCost = houseCost;
		this.hotelCost = hotelCost;	
		this.color = color;
	}

	@Override
	public void collectRent(Player player) 
	{
		// TODO Auto-generated method stub
		player.addMoney(-cost);		
	}
	
	public void buyHouse()
	{
		houses++;
	}
	
	public void sellHouse()
	{	
		houses--;
	}
	
	public void buyHotel()
	{
		hotel++;
	}
	
	public void sellHotel()
	{	
		hotel--;
	}

	@Override
	public String[] getPossibleActions(Player player) {
		// TODO Auto-generated method stub
		return null;
	}	
}
