// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Game class that models main game object that stores all back end data
//              such as players and board fields, manages game state, and implements functions that
//              connect the back end with the front end

package src;

import java.util.LinkedList;
import java.util.Queue;

public class Game
{
    public static final int DICE_COUNT = 2;        // how many dice to roll
    public static final boolean DEMO_MODE = false;  // flag that enabled/disables demo mode
    private final BoardLocation[] board;           // array of all board locations
    private Player currentPlayer;                  // current player
    private Player[] players;                      // array of all players
    private int dice;                              // current dice value
    private Queue<Player> queuePlayer;             // queue of players, maintains the order
    
    public Game(int playerNum)
    // PRE: playerNum must be between 2 and 8
    // POST: initializes board, players, currentPlayer, queuePlayer class members
    //       in case of demo mode initializes the game to run in demo mode
    {
        if(DEMO_MODE) // demo mode active
        {
            playerNum = 2;
        }
        board = makeBoard();
        this.players = makePlayers(playerNum);
        BoardLocation.Link(board);
        queuePlayer = setUpPlayersQueue();
        currentPlayer = queuePlayer.peek();
        if(DEMO_MODE) // demo mode active
        {
            initDemoMode();
        }
    }

    private void initDemoMode()
    // PRE: players and board must be initialized
    // POST: 12 to 18 properties are randomly distributed among players
    //       some houses/hotels are placed on distributed lots
    {
        int previousPersonLastMove;                         // what was the person last location
        int propertyPurchased;                              // how many properties has player purchased
        previousPersonLastMove = 0;
        
        for (Player player : players )
        {
        	int propToPurchase;								// how properties to purchase for the player 
        	
            player.setMoney(9000);
            player.move(previousPersonLastMove);
            propertyPurchased = 0;            
            propToPurchase = (int)(Math.random()*(12-8))+8;
            while ( propertyPurchased < propToPurchase )
            {
            	player.move(1);
        		previousPersonLastMove++;
                if ( player.getLocation() instanceof Property ) // is my current location a property
                {
                    player.buyLocation((Property) player.getLocation());
                    propertyPurchased++;
                }
                
                if ( player.getProperties().size() > 3 )   // do i have 3 props and a factor of 2
                {
                    for ( Property p : player.getProperties())          // for all my properties
                    {
                        if ( p instanceof Lot )     	   // is p a lot than imrpove it
                        {
                        	((Lot) p).improve();
                        }
                    }
                }
            }            
        }
    }

    public Player getCurrentPlayer()
    // POST:FCTVAL: this.currentPlayer
    {
        return currentPlayer;
    }

    public BoardLocation[] getBoard()
    // POST:FCTVAL: this.board
    {
        return board;
    }

    public Player[] getPlayers()
    // POST:FCTVAL: this.players
    {
        return players;
    }

    private Queue<Player> setUpPlayersQueue()
    // PRE: players class member must be initialized
    // POST: FCTVAL: returns initialized queue of players
    //       first player is chosen randomly
    {
        int rand; // random number

        Queue<Player> queuePlayers = new LinkedList<>();
        for(Player p : players) // add all players to queue
            queuePlayers.add(p);

        rand = (int)(Math.random()*players.length); // choose random number between [0,#players-1]

        for ( int i = 0; i < rand; i++ ) // enqueue dequeue random number of times
        {
            queuePlayers.add(queuePlayers.poll());
        }

        return queuePlayers;
    }

    public void printPlayers()
    // PRE: players class member must be initialized
    // POST: prints all players to stdout
    {
        for (Player p : players)
        {
            System.out.println(p);
        }
    }

    public void printBoardInfo()
    // PRE: players class members must be initialized
    // POST: prints all board locations to stdout
    {
        for ( BoardLocation b : board )
        {
            System.out.println(b);
        }
    }

    private BoardLocation[] makeBoard()
    // POST: FCTVAL: array of board locations of monopoly game
    //               fully initialized
    {
        BoardLocation[] result;

        result = new BoardLocation[] {
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
        
        BoardLocation.Link(result);
        Utility.setOther((Utility)result[28], (Utility)result[12]);
        RailRoad.setOthers((RailRoad)result[5], (RailRoad)result[15], (RailRoad)result[25], (RailRoad)result[35]);
        
        return result;
    }

    private Player[] makePlayers(int pCount)
    // PRE: pCount has to be between 2 and 8
    //      board class member has to be initialized
    // POST:FCTVAL: returns initialized array of players
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

    public Property getProperty(String property)
    // PRE: property should be a name of a property on the board
    // POST:FCTVAL: return Property which names matches property arg
    //              returns null otherwise
    {
        for(BoardLocation p: board)
        {
            if(p.getName().equals(property))
            {
                return (Property)p;
            }
        }

        return null;	    
    }

    public String[] move()
    // PRE: currentPlayer class member must be initialized
    //      dice must be rolled before calling this func
    // POST: calls move() on current player
    //       FCTVAL: returns the message of what happened on the field where player moved to
    {
    	String[] result;
    	String[] possibleActions;
    	BoardLocation curLoc;
    	int i;

    	result = new String[10];
    	i = 0;
    	
        if(currentPlayer.move(dice))
        {
        	result[i] = "You just went passed GO and got $200.";
        	i++;
        	currentPlayer.addMoney(200);
        }
        
        curLoc = currentPlayer.getLocation();
        possibleActions = curLoc.getPossibleActions(currentPlayer);

        for(String str : possibleActions)
        {
        	if(str.equals(BoardLocation.PAY_RENT))
        	{
        		result[i] = ((Property) curLoc).collectRent(currentPlayer);
        		i++;
        	}
        	else if(str.equals(BoardLocation.PAY_TAX))
        	{
        		result[i] = ((TaxSquare) curLoc).payTax(currentPlayer);
        		i++;
        	}
        	else if(str.equals(BoardLocation.PICK_CARD))
        	{
        		result[i] = ((CardSquare) curLoc).reward(currentPlayer);
        		i++;
        	}
        	else
        	{
        		result[i] = str;
        		i++;
        	}
        }
        
        return result;
    }
    
    public int rollDice()    
    // POST: rolls DICE_COUNT dices
    //       FCTVAL: dice value rolled
    {
        dice = 0;
        for(int i=0;i<DICE_COUNT;i++) // dice
        {
            dice += (int) (((double)Math.random()*6)+1);
        }
        Player.setDice(dice);
        return dice;
    }

    public void giveTurn()
    // PRE: currentPlayer and queuePlayer must be initialized
    // POST: gives turn to the next player in the queue
    //       puts the currentPlayer at the end
    {
        if(currentPlayer.isBankrupt())
        {
            queuePlayer.poll();
        }

        queuePlayer.add(queuePlayer.poll());
        currentPlayer = queuePlayer.peek();
    }
    
    // ?????????????????????????????????????????????????????
    // ?????????????????????????????????????????????????????
    public boolean checkBankruptcy(Player player)
    // PRE:
    // POST:FCTVAL:
    {
        if(player.isBankrupt())
        {
            
        }
        
        return false;
    }
}
