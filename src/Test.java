package src;

public class Test
{
	public static void main(String[] args)
	{
		BoardLocation[] board;
		Player player1;
		Player player2;
		
		//init
		board = GameModel.GetModel();
		BoardLocation.Link(board);
		
		player1 = new Player("Car", board[0]);
		player2 = new Player("Boot", board[0]);
		
		for(BoardLocation b : board)
			System.out.println(b);
		
		System.out.println();
		
		player1.move(3);
		if(player1.buyLocation((Property) player1.getLocation()))
			System.out.println(player1 + " bought " + player1.getLocation());
		
		System.out.println(player2 + " moves 3");
		player2.move(3);
		System.out.println(player1 + " collected rent from " + player2);
		
		System.out.println(player1 + " moves 4");
		player1.move(4);
		System.out.println(player1 + " got rewarded");
		
		System.out.println(player1 + " buys 2 houses");
		((Lot) player1.getProperties().get(0)).improve();
		((Lot) player1.getProperties().get(0)).improve();
		System.out.println(player2 + " makes a cycle");
		player2.move(15);
		System.out.println(player1 + " collected rent from " + player2);
		
		((Lot) player1.getProperties().get(0)).diminish();
		((Lot) player1.getProperties().get(0)).diminish();
		System.out.println(player1 + " sold 2 houses");
		
		for (int i = 0; i < 100; i++ ) {
			System.out.println("ROLL DICE: " + Player.RollDice());
		}
	}
}
