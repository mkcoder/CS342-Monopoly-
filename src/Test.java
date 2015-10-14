package src;

public class Test
{
	public static void main(String[] args)
	{
		BoardLocation[] board;
		Player player1;
		Player player2;
		
		//init
		board = new BoardLocation[]
				{
					new CornerSquare("Go", 0),
					new Lot("MEDITERRANEAN AVE", 1, 60, "Dark Purple", 50, 
							new int[] { 2, 10, 30, 90, 160, 230}),
					new CardSquare("Community Chest", 2),
					new Lot("BALTIC AVE.", 3, 60, "Dark Purple", 50, 
							new int[] { 4, 20, 60, 180, 320, 450}),
					new TaxSquare("Income Tax", 4, 200),
					new RailRoad("READING RAILROAD", 5, 200),
					new Lot("ORIENTAL AVE.", 6, 100, "Light Blue", 50, 
							new int[] { 6, 30, 90, 270, 400, 550}),
					new CardSquare("Chance", 7),
					new Lot("VERMONT AVE.", 8, 100, "Light Blue", 50, 
							new int[] { 6, 30, 90, 270, 400, 550}),
					new Lot("CONNECTICUT AVE.", 9, 120, "Light Blue", 50, 
							new int[] { 8, 40, 100, 300, 450, 600}),
					new CornerSquare("Jail/Just Visiting", 10),
					new Lot("ST. CHARLES PLACE", 11, 140, "Light Purple", 100, 
							new int[] { 10, 50, 150, 450, 625, 750}),
					new Utility("ELECTRIC COMPANY", 12, 150),
					new Lot("STATES AVE.", 13, 140, "Light Purple", 100, 
							new int[] { 10, 50, 150, 450, 625, 750}),
					new Lot("VIRGINIA AVE.", 14, 160, "Light Purple", 100, 
							new int[] { 12, 60, 180, 500, 700, 900}),				
				};
		BoardLocation.Link(board);
		
		player1 = new Player("Car", board[0]);
		player2 = new Player("Boot", board[0]);
		
		//toString()
		for(BoardLocation b : board)
			System.out.println(b.toString());
		
		//move()
		player1.move(3);
		if(player1.buyLocation((Property) player1.getLocation()))
			System.out.println(player1.toString() + " bought " + player1.getLocation().toString());
		
		System.out.println("Before player 2 moved");
		System.out.println("Player1 has $" + player1.getMoney());
		System.out.println("Player2 has $" + player2.getMoney());
		player2.move(3);		
		((Lot) player2.getLocation()).collectRent(player2);
		System.out.println("After player 2 moved");
		System.out.println("Player1 has $" + player1.getMoney());
		System.out.println("Player2 has $" + player2.getMoney());
	}
}
