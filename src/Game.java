// 

package src;

import javax.swing.*;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Game
{
    private final BoardLocation[] board;
    private Player currentPlayer;
    private Player[] players; 
    private static int dice;
    private Queue<Player> queuePlayer;
    
	public Game(int players)
	{
		board = makeBoard();
		this.players = makePlayers(players);
		BoardLocation.Link(board);		
        queuePlayer = setUpPlayersQueue();
        currentPlayer = queuePlayer.peek();
	}
    
/*	public void playGame()
	{
		
		System.out.println(queuePlayer.peek());
		
		
		dice = 3;
		players[0].move(dice);
		
		if(players[0].buyLocation((Property) players[0].getLocation()))
			System.out.println(players[0] + " bought " + players[0].getLocation());
		
		System.out.println(players[1] + " moves 3");
		players[1].move(dice);
		System.out.println(players[0] + " collected rent from " + players[1]);
		
		System.out.println(players[0] + " moves 4");
		
		dice = 4;
		players[0].move(dice);
		System.out.println(players[0] + " got rewarded");
		
		System.out.println(players[0] + " buys 2 houses");
		((Lot) players[0].getProperties().get(0)).improve();
		((Lot) players[0].getProperties().get(0)).improve();
		System.out.println(players[1] + " makes a cycle");
		dice = 15;
		players[1].move(dice);
		System.out.println(players[0] + " collected rent from " + players[1]);		
		((Lot) players[0].getProperties().get(0)).diminish();
		((Lot) players[0].getProperties().get(0)).diminish();
		System.out.println(players[0] + " sold 2 houses");
		
	}
	*/
	public Player getCurrentPlayer()
	{
	    return this.currentPlayer;	    
	}
	
	private Queue<Player> setUpPlayersQueue() 
	{
		Queue<Player> queuePlayers = new LinkedList<>();
		for(Player p : players)
			queuePlayers.add(p);
		int firstPlayer = (int)(Math.random()*players.length);
		for ( int i = 0; i < firstPlayer; i++ )
		{
			queuePlayers.add(queuePlayers.poll());
		}
		
		return queuePlayers;
	}

	public void printPlayers() 
	{
		for (Player p : players) 
		{
			System.out.println(p);
		}
		
	}

	public void printBoardInfo() 
	{
		for ( BoardLocation b : board ) 
		{
			System.out.println(b);
		}
	}
	
	private BoardLocation[] makeBoard()
    {
        return new BoardLocation[] {
                new CornerSquare("Go", 0),
                new Lot("MEDITERRANEAN AVE", 1, 60, "Dark Purple", 50, new int[] { 2, 10, 30, 90, 160, 230}),
                new CardSquare("Community Chest", 2),
                new Lot("BALTIC AVE.", 3, 60, "Dark Purple", 50, new int[] { 4, 20, 60, 180, 320, 450}),
                new TaxSquare("Income Tax", 4, 200),
                new RailRoad("READING RAILROAD", 5, 200),
                new Lot("ORIENTAL AVE.", 6, 100, "Light Blue", 50, new int[] { 6, 30, 90, 270, 400, 550}),
                new CardSquare("Chance", 7),
                new Lot("VERMONT AVE.", 8, 100, "Light Blue", 50, new int[] { 6, 30, 90, 270, 400, 550}),
                new Lot("CONNECTICUT AVE.", 9, 120, "Light Blue", 50, new int[] { 8, 40, 100, 300, 450, 600}),
                new CornerSquare("Jail/Just Visiting", 10),
                new Lot("ST. CHARLES PLACE", 11, 140, "Light Purple", 100, new int[] { 10, 50, 150, 450, 625, 750}),
                new Utility("ELECTRIC COMPANY", 12, 150),
                new Lot("STATES AVE.", 13, 140, "Light Purple", 100, new int[] { 10, 50, 150, 450, 625, 750}),
                new Lot("VIRGINIA AVE.", 14, 160, "Light Purple", 100, new int[] { 12, 60, 180, 500, 700, 900}),
                new RailRoad("PENNSYLVANIA RAILROAD", 15, 200),
                new Lot("ST. JAMES PLACE", 16, 180, "Orange", 100, new int[] { 14, 70, 200, 550, 750, 950}),
                new CardSquare("Community Chest", 17),
                new Lot("TENNESSEE AVE.", 18, 180, "Orange", 100, new int[] { 14, 70, 200, 550, 750, 950}),
                new Lot("NEW YORK AVE.", 19, 200, "Orange", 100, new int[] { 16, 80, 220, 600, 800, 1000}),
                new CornerSquare("Free Parking", 20),
                new Lot("KENTUCKY AVE.", 21, 220, "Red", 150, new int[] { 18, 90, 250, 700, 875, 1050}),
                new CardSquare("Chance", 22),
                new Lot("INDIANA AVE.", 23, 220, "Red", 150, new int[] { 18, 90, 250, 700, 875, 1050}),
                new Lot("ILLINOIS AVE.", 24, 240, "Red", 150, new int[] { 20, 100, 300, 750, 925, 1100}),
                new RailRoad("B & O RAILROAD", 25, 200),
                new Lot("ATLANTIC AVE.", 26, 260, "Yellow", 150, new int[] { 22, 110, 330, 800, 975, 1150}),
                new Lot("VENTNOR AVE.", 27, 260, "Yellow", 150, new int[] { 22, 110, 330, 800, 975, 1150}),
                new Utility("WATER WORKS", 28, 150),
                new Lot("MARVIN GARDENS", 29, 280, "Yellow", 150, new int[] { 24, 120, 360, 850, 1025, 1200}),
                new CornerSquare("Go To Jail", 30),
                new Lot("PACIFIC AVE.", 31, 300, "Green", 200, new int[] { 26, 130, 390, 900, 1100, 1275}),
                new Lot("NO. CAROLINA AVE.", 32, 300, "Green", 200, new int[] { 26, 130, 390, 900, 1100, 1275}),
                new CardSquare("Community Chest", 33),
                new Lot("PENNSYLVANIA AVE.", 34, 320, "Green", 200, new int[] { 28, 150, 450, 1000, 1200, 1400}),
                new RailRoad("SHORT LINE RAILROAD", 35, 200),
                new CardSquare("Chance", 36),
                new Lot("PARK PLACE", 37, 350, "Dark Blue", 200, new int[] { 35, 175, 500, 1100, 1300, 1500}),
                new TaxSquare("Luxury Tax", 38, 75),
                new Lot("BOARDWALK", 39, 400, "Dark Blue", 200, new int[] { 50, 200, 600, 1400, 1700, 2000}),
        };
    }

	private Player[] makePlayers(int pCount) 
	{
	    String[] token;
	    Player[] result;
	    
	    token = new String[] {"car", "boot", "top hat", "ship", 
	    		"wheelbarrow", "iron", "thimble", "dog"};	    
	    result = new Player[pCount]; 	    
	    for ( int i = 0; i < pCount; i++ ) 
	    {
	    	result[i] = new Player(token[i], board[0]);
	    }	    
	    
	    return result;
	}
	
	public String move()
	{	    	    
	    return currentPlayer.move(dice);
	}
    
    public int rollDice()
    {
        dice = (int) (((double)Math.random()*6)+1);
        Player.setDice(dice);
        return dice;
    }
}
