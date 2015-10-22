// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Applet class that models applet which contains entire front end (UI)
//              for the project

package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Applet extends JApplet implements ActionListener, ItemListener
{
	// file paths
	public static final String BOARD_FILE = "./src/mon.jpg";       // board file name
	public static final String CAR_FILE = "./src/car.png";         // car token file name
	public static final String DOG_FILE = "./src/dog.png";         // dog token file name
	public static final String HAT_FILE = "./src/hat.png";         // hat token file name
	public static final String IRON_FILE = "./src/iron.png";       // iron token file name
	public static final String THIMBLE_FILE = "./src/thimble.png"; // thimble token file name
	public static final String SHIP_FILE = "./src/ship.png";       // ship token file name
	public static final String BOOT_FILE = "./src/boot.png";       // boot token file name
	public static final String WHEELBARROW_FILE = "./src/wheelbarrow.png"; // wheel token file name
	public static final String HOTEL_FILE = "./src/hotel.png";     // hotel file name
	public static final String HOUSE_FILE = "./src/house.png";     // house file name
	
	// other static finals
	public static final int OFFSET_X = 10; // x position of the board in applet in pixels from 0,0
	public static final int OFFSET_Y = 10; // y position of the board in applet in pixels from 0,0
	public static final int PANEL_WIDTH = 230; // width of the JPanel storing JComponents
	public static final int ITEM_SCALE = 20; // size ratio of the board to tokens/houses
	public static final int MIN_PANEL_WIDTH = 260; // min width of the jpanel
	public static final Color COLOR_SAND = new Color(255,255,245); // bg color for some components
	
	// images
	private BufferedImage boardImage; // image of the board
    private BufferedImage carImage;   // image of the car token
    private BufferedImage dogImage;   // image of the dog token
    private BufferedImage hatImage;   // image of the hat token
    private BufferedImage ironImage;  // image og the iron token
    private BufferedImage thimbleImage; // image of the thimble token
    private BufferedImage shipImage;  // image of the ship token
    private BufferedImage bootImage;  // image of the boot token
    private BufferedImage wheelImage; // image of the wheelbarrow token
    private BufferedImage houseImage; // image of the house
    private BufferedImage hotelImage; // image of the hotel   
    
	// JComponents
	private JButton    diceRollBtn;			  // Roll dice button
	private JButton    endBtn;			      // End game button
	private JButton    buyPropertyBtn;		  // Buy property button
	private JButton    improvePropertyBtn;    // Improve property button
	private JButton    diminishPropertyBtn;   // Diminish property button
	private JButton    giveTurnBtn;           // Give turn button
	private JButton    ownageButton;          // Who own what button
    private JLabel     diceRollLabel;         // dice result label
	private JLabel     playerLabel;	          // info about current player
	private JTextArea  propertyText;          // info about propery chosen from combo box
	private JTextArea  notificationText;      // feedback from curent action
	private JTextArea  historyText;           // text area that stores the gameplay history
	private JTextArea  playersText;           // text area that holds info about all players
    private JComboBox  propertiesCombo;       // properties owned by the current player
    private JComboBox  allPropsCombo;         // all properties on the board

    // other members
    private Point[] coordArray; // cooridnates of all game fields in pixels from 0,0
    private int boardScale;     // size of the board in pixels
    private int itemScale;      // size of the tokens, houses and hotels in pixels
    private Game game;          // game object
	
	public void init()
	// POST: initializes JApplet
	{
		JPanel basePanel;	// panel for all components
		JPanel historyPanel;// panel for history
		JScrollPane scroll; // scroll panel for history text area
		JScrollPane playerScroll; // scroll panel for players
		JPanel playerPanel; // panel for player info and roll dice
		JPanel actionPanel; // panel for buying and info about current action
		JPanel propPanel;   // panel for managinf properties
		int playerNum;      // number of players for the current game

		if(!Game.DEMO_MODE) // normal mode
		{
			do
			{
				try
				{
					playerNum = new Integer(JOptionPane.showInputDialog("How many players [2-8]"));
				}
				catch(NumberFormatException e)
				{
					playerNum = 0;
				}
			}while(playerNum < 2 || playerNum > 8); // keep asking for player num until valid
			                                        // value is given
		}
		else               // demo mode
			playerNum = 2;
			
		game = new Game(playerNum);
		
		// images
		boardImage = getImage(BOARD_FILE);
		carImage = getImage(CAR_FILE);
		dogImage = getImage(DOG_FILE);
		hatImage = getImage(HAT_FILE);
		ironImage = getImage(IRON_FILE);
		thimbleImage = getImage(THIMBLE_FILE);
		shipImage = getImage(SHIP_FILE);
		bootImage = getImage(BOOT_FILE);
		wheelImage = getImage(WHEELBARROW_FILE);
		houseImage = getImage(HOUSE_FILE);
		hotelImage = getImage(HOTEL_FILE);

		// history panel		
		historyText = new JTextArea();
		historyText.setEditable(false);		
		historyText.setBackground(new Color(255,255,245));
		addHistory("**** " + game.getCurrentPlayer().getToken() + " ****");
		scroll = new JScrollPane(historyText);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(PANEL_WIDTH-20, 130));
		
		historyPanel = new JPanel();
		historyPanel.add(new JLabel("History"));
		historyPanel.add(scroll);
		
		// player panel
		diceRollBtn = new JButton("Roll Dice");
		giveTurnBtn = new JButton("Give up turn");
		giveTurnBtn.setEnabled(false);
		ownageButton = new JButton("Who owns what?");
		endBtn = new JButton("End game");
		diceRollLabel = new JLabel();
		diceRollLabel.setPreferredSize(new Dimension(PANEL_WIDTH-20, 20));
		playerLabel = new JLabel(game.getCurrentPlayer().toString());
		
		playerPanel = new JPanel();
		playerPanel.add(playerLabel);
		playerPanel.add(diceRollLabel);
		playerPanel.add(diceRollBtn);
		playerPanel.add(giveTurnBtn);
		playerPanel.add(ownageButton);
		playerPanel.add(endBtn);
		
		// action panel		
		buyPropertyBtn = new JButton("Buy property");
		buyPropertyBtn.setEnabled(false);		
		notificationText = new JTextArea();
		notificationText.setEditable(false);
		notificationText.setBackground(getContentPane().getBackground());
		
		playersText = new JTextArea();
		playersText.setEditable(false);		
		playersText.setBackground(new Color(255,255,245));
		playerScroll = new JScrollPane(playersText);
		playerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		playerScroll.setPreferredSize(new Dimension(PANEL_WIDTH-20, 60));
		updatePlayersInfo();
		
		allPropsCombo = new JComboBox();
		allPropsCombo.setPreferredSize(new Dimension(PANEL_WIDTH-20, 20));
		initAllPropsCombo();
		
		actionPanel = new JPanel();		
		actionPanel.add(buyPropertyBtn);
		actionPanel.add(notificationText);
		actionPanel.add(new JLabel("Information about players"));
		actionPanel.add(playerScroll);
		actionPanel.add(new JLabel("Infomartion about all properties"));
		actionPanel.add(allPropsCombo);
		
		// property panel
		improvePropertyBtn = new JButton("Improve");
		improvePropertyBtn.setEnabled(false);
		diminishPropertyBtn = new JButton("Diminish");
		diminishPropertyBtn.setEnabled(false);
		propertyText = new JTextArea();
		propertyText.setEditable(false);
		propertyText.setBackground(getContentPane().getBackground());
		propertiesCombo = new JComboBox();		
		propertiesCombo.setPreferredSize(new Dimension(PANEL_WIDTH-20, 20));		
		
		propPanel = new JPanel();
		propPanel.add(new JLabel("Your properties"));
		propPanel.add(propertiesCombo);		
		propPanel.add(improvePropertyBtn);
		propPanel.add(diminishPropertyBtn);
		propPanel.add(propertyText);
		
		// layout
	    setLayout(new BorderLayout());
	    
	    basePanel = new JPanel();
	    basePanel.setLayout(new GridLayout(4,0));
	    basePanel.setPreferredSize(new Dimension(MIN_PANEL_WIDTH, 600));
	    
	    basePanel.add(historyPanel);
	    basePanel.add(playerPanel);
        basePanel.add(actionPanel);
        basePanel.add(propPanel);
        
        add(basePanel, BorderLayout.EAST);
		
        // listeners
		diceRollBtn.addActionListener(this);
		buyPropertyBtn.addActionListener(this);
		improvePropertyBtn.addActionListener(this);
		diminishPropertyBtn.addActionListener(this);
		giveTurnBtn.addActionListener(this);
		propertiesCombo.addItemListener(this);
		endBtn.addActionListener(this);
		allPropsCombo.addItemListener(this);
		ownageButton.addActionListener(this);
	}
	
	private void initAllPropsCombo()
	// PRE: allPropsCombo must be initialized
	// POST: populates allPropsCombo with all properties
	{
		for(BoardLocation b : game.getBoard()) // add all properties
		{
			if(b instanceof Property ) // is property
			{
				allPropsCombo.addItem(b.getName());
			}
		}
	}

	@Override
	public void paint(Graphics g)
	// POST: draws pictures and UI
	//       updates boardScale and itemScale members based on current applet size
	//       updates coordArray member based on boardScale
	{		
	    super.paint(g);
	    
	    setScale();
	    coordArray = getPoints();		
		g.drawImage(boardImage, OFFSET_X, OFFSET_Y, boardScale, boardScale, null);
		
		for(BoardLocation b: game.getBoard()) // draw houses and hotels
		{
		    if(b instanceof Lot) // check for houses only for lots
		    {
		    	drawHotelsAndHouses((Lot)b, g);
		    }
		}
		
		for(Player p: game.getPlayers()) // draw player tokens
        {
		    if(!p.isBankrupt()) // draw tokens for players that are not broke
		    {
		        drawToken(p,g,boardScale);
		    }
        }
	}
	
	private void setScale()
	// POST: updates boardScale and itemScale members based on current applet size
	{
		if((getWidth() - PANEL_WIDTH - OFFSET_X) < getHeight()) // allow board to be at most
			                                                    // applet minus room for JPanel
			boardScale = getWidth() - PANEL_WIDTH - OFFSET_X;
		else                                                    // allow board to be at most
			                                                    // applet height
			boardScale = getHeight() - OFFSET_Y;
		
		itemScale = boardScale/ITEM_SCALE;
	}
	
	private void drawHotelsAndHouses(Lot lot, Graphics g)
	// PRE: lot must be initialized
	//      g must be Graphics object from paint method
	//      coordArray class member must be initialized
	// POST: draws houses and hotels for lot lot
	{
		Point p;     // screen coordinate of lot in pixels
		
		p = coordArray[lot.getAddress()];
		
		if (lot.getRentIndex() < 5) // draw houses if lot level is less than 5
        {
            for(int i = 0; i < lot.getRentIndex(); i++ ) // draw as many houses as the level
            	                                         // of the lot
            {                
                g.drawImage(houseImage, p.x, p.y + (i - 1)*itemScale/3, 
                		itemScale, itemScale, null);
            }
        }
        else // draw hotel otherwise
        {
            g.drawImage(hotelImage,p.x , p.y+itemScale/2, itemScale, itemScale, null);
        }
	}
	
	private void drawToken(Player player, Graphics g, int imgScale)
	// PRE: player must be initialized
	//      imgScale must be greater than 0
	//      g must be Graphics object from paint method
	// POST: draws token for player player
	{
	    Point p;
	    BufferedImage token;
	    
	    p = coordArray[player.getLocation().getAddress()];	    
	    token = matchToken(player.getToken());
	    g.drawImage(token, p.x, p.y, itemScale, itemScale, null);
	    
	}

    public BufferedImage matchToken(String token)
    // PRE: token must be a valid token name
    //      valid token names: car, boot, top hat, ship, wheelbarrow, iron, thimble, dog
    // POST:FCVAL: image that matches valid token name or null otherwise
    {
        if(token.equals("car")) // match "car" to carImage
        {
            return carImage;
        }
        else if(token.equals("boot")) // match "boot" to bootImage
        {
            return bootImage;
        }
        else if(token.equals("top hat")) // match "top hat" to hatImage
        {
            return hatImage;
        }
        else if(token.equals("ship")) // match "ship" to shipImage
        {
            return shipImage;
        }
        else if(token.equals("wheelbarrow")) // match "wheelbarrow" to wheelImage
        {
            return wheelImage;
        }
        else if(token.equals("iron")) // match "iron" to ironImage
        {
            return ironImage;
        }
        else if(token.equals("thimble")) // match "thimble" to thimbleImage
        {
            return thimbleImage;
        }
        else if(token.equals("dog")) // match "dog" to dogImage
        {
            return dogImage;
        }
        else // return null if nothing matches
        {
            return null;
        }
    }
    
    private Point[] getPoints()
    // PRE: boardScale class member must be initialized
    // POST: FCTVAL: returns array of Points that represent cooridnates of board fields
    //               for current boardScale
	{
	    Point [] tempCoord; // result array of coordinates of board fields
	    double divFactor;   // distance between points on an edge
	 
	    tempCoord = new Point[40];
	    divFactor = (boardScale/10) * (0.825); // factor that scales edges nicely
	    
	    for(int i = 0; i < 11 ;i++) // bottom edge
	    {
	    	// for x coords walk edge right to left, offset all x and y properly
	        tempCoord[i] = new Point((int)(boardScale - divFactor*i - divFactor*1.5),
	        		                 (int) (boardScale - divFactor));
	    }
	    
	    for (int i = 11; i < 21; i++) // left edge
	    {
	    	// for y coords walk edge bottom to top, offset all x and y properly
	        tempCoord[i] = new Point((int)(divFactor/2),
	        		                 (int)(boardScale - divFactor*(i - 10) - divFactor*1.5));
        }
	    
	    for (int i = 21; i < 31; i++) // top edge
        {
	    	// for x coords walk edge left to right, offset all x and y properly
            tempCoord[i] = new Point((int)(divFactor*(i - 20) + divFactor),(int)(divFactor/2));
        }
	    
	    for (int i = 31; i < 40; i++) // right edge
        {
	    	// for y coords walk edge top to bottom, offset all x and y properly
            tempCoord[i] = new Point((int)(boardScale - divFactor),
            		                 (int)(divFactor*(i - 30) + divFactor*0.9));
        }
	    
	    for(Point p: tempCoord) // add board offsets to all points
	    {
	        p.x = p.x + OFFSET_X;
	        p.y = p.y + OFFSET_Y;
	    }
	    
        return tempCoord;
	}
	
	public BufferedImage getImage(String fileName)
	// PRE: fileName must be initialized
	// POST: FCTVAL: loaded image
	{
		BufferedImage img; // resulting loaded image
		
		try 
		{
			img = ImageIO.read(new File(fileName));
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Could not open " + fileName, "ERROR", 
					JOptionPane.ERROR_MESSAGE);
			img = null;
		}
		
		return img;
	}
	
	public void updatePlayersInfo()
	// PRE: playersText and players must be initialized
	// POST: populates playersText with correct info about players
	{
		String text; // string built for playersText
		
		text = "";

		for(Player p : game.getPlayers()) // enumerate all players
		{
			if(!p.isBankrupt()) // not bankrupt
			{
				text += p.toString() + "\n";
			}
			else  // bankrupt
			{
				text += p.getToken() + " is bankrupt";
			}
		}
		
		playersText.setText(text);
	}
	
	public void addHistory(String msg)
    // PRE: msg has to be initialized
    // POST: adds a new message to history of the gameplay text area
    {
    	historyText.append(msg + "\n");
    	historyText.setCaretPosition(historyText.getDocument().getLength()); // scroll to bottom
    }

    @Override
    public void actionPerformed(ActionEvent e)
    // POST: handles all buttons clicks
    {
    	String[] moveResult; // result of move method
    	String text;         // string used to build message for labels 
    	Property prop;       // auxilary Property to make code shorter
    	Player player;       // current player
    	boolean doubleThrow; // flag for double
    	
    	player = game.getCurrentPlayer();
    	
    	if(e.getSource() == diceRollBtn) // roll dice button clicked
		{
    		diceRollBtn.setEnabled(false);
    		giveTurnBtn.setEnabled(true);
    		doubleThrow = game.rollDice();
    		text = Integer.toString(Player.getDice());
    		
    		addHistory("Rolled " + Player.getDice());
    		if(!doubleThrow)
    		{
    			diceRollLabel.setText("Dice roll result: " + text);
    		}
    		else
    		{
    			diceRollLabel.setText("Dice roll result: " + text + " DOUBLE");
    		}
            moveResult = game.move();

            text = "";
            buyPropertyBtn.setEnabled(false);
            
            for(String str : moveResult) // append all strings returned by game.move()
            {
            	if(str != null) // there might be null strings
            	{            		
            		if(str.equals(BoardLocation.CAN_BE_PURCHASED)) // enable purchase button
            		{
            			text += "Can be purchased for $"+((Property)player.getLocation()).getCost();
            			buyPropertyBtn.setEnabled(true);
            		}
            		else if(!str.equals(BoardLocation.YOU_OWN) && 
            				!str.equals(BoardLocation.CORNER)) // add relevant actions to history
            		{            			
                		addHistory(str);
                		text += str + "\n";
            		}
            		else                                       // add everything to notification text
            		    text += str + "\n";
            	}
            }
            
            notificationText.setText(text);
            playerLabel.setText(player.toString());
            repaint();
        }
    	else if(e.getSource() == buyPropertyBtn) // Buy property button clicked
		{		    
		    text = "Sorry you can't purchase that";
		    
		    if (player.getLocation() instanceof Property &&
		    	player.buyLocation((Property)player.getLocation())) // property purchased
		    {
		            text = "You purchased " + player.getLocation().getName();
		            propertiesCombo.addItem(player.getLocation().getName());
		            addHistory(text);
		    }
		    
		    buyPropertyBtn.setEnabled(false);
		    notificationText.setText(text);
            playerLabel.setText(player.toString());
		}    	
    	else if(e.getSource() == improvePropertyBtn) // Improve property button clicked
		{
		    text = "You cannot improve the lot!";
		    prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
		    
		    if(((Lot) prop).improve()) // improved successfully
		    {
		    	text = "You have improved your lot!";
		    	addHistory(text);
		        repaint();
		    }
		    
		    notificationText.setText(text);
		    playerLabel.setText(player.toString());
		}
    	else if(e.getSource() == diminishPropertyBtn) // Diminish property button clicked
		{            
            text = "You cannot diminish the lot!";
            prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
            
            if(((Lot)prop).diminish()) // diminished successfully
            {            	
            	text ="You have diminished your lot!";
            	addHistory(text);
                repaint();
            }
            
            notificationText.setText(text);
            playerLabel.setText(player.toString());
        }
    	else if(e.getSource() == giveTurnBtn) // Give turn button clicked
		{
		    game.giveTurn();
		    player = game.getCurrentPlayer();
		    diceRollBtn.setEnabled(true);
		    giveTurnBtn.setEnabled(false);
		    
		    // reset the ui
		    buyPropertyBtn.setEnabled(false);
		    playerLabel.setText(player.toString());
		    diceRollLabel.setText("Dice roll result: ");
		    notificationText.setText("");
		    propertyText.setText("");

		    addHistory("**** " + player.getToken() + " ****");
            
            propertiesCombo.removeAllItems();
            for(String str : player.getProperties()) // populate combo with properties
            {
                propertiesCombo.addItem(str);
            }
		}
    	else if(e.getSource() == endBtn) // end game button
    	{
    		// disable UI
    		giveTurnBtn.setEnabled(false);
    		buyPropertyBtn.setEnabled(false);
    		diceRollBtn.setEnabled(false);
    		propertiesCombo.setEnabled(false);
    		improvePropertyBtn.setEnabled(false);
    		diminishPropertyBtn.setEnabled(false);
    		
    		text = "";
    		
    		for(Player p : game.getPlayers()) // enumerate players
    		{
    			text += p.toString() + "\nProperties:\n";
    			for(String str : p.getProperties()) // enumerate properties
    			{
    				text += str;
    				if(game.getProperty(str) instanceof Lot) // it's lot
    				{
    					text += ((Lot) game.getProperty(str)).getHousingLevel();
    				}
    				text += "\n";
    			}
    			text += "\n";
    		}
    		
    		JOptionPane.showMessageDialog(null,text, "End of the game",
    				JOptionPane.INFORMATION_MESSAGE);
    	}
    	
    	else if(e.getSource() == ownageButton) // Who owns what button
    	{
    		text = "";
    		
    		for(Player p : game.getPlayers()) // enumerate players
    		{
    			if(!p.isBankrupt()) // not bankrupt
    			{
	    			text += p.getToken() + "\n";
	    			for(String str : p.getProperties()) // enumerate properties
	    			{
	    				text += str;
	    				if(game.getProperty(str) instanceof Lot) // it's lot
	    				{
	    					text += ((Lot) game.getProperty(str)).getHousingLevel();
	    				}
	    				text += "\n";
	    			}
	    			text += "\n";
    			}
    		}
    		
    		JOptionPane.showMessageDialog(null,text, "Ownership",
    				JOptionPane.INFORMATION_MESSAGE);
    	}
    	
    	updatePlayersInfo();
    }   
    
    @Override
    public void itemStateChanged(ItemEvent e)
    {
    	String text;   // message built for propLabel
    	Property prop; // selected property
    	
    	improvePropertyBtn.setEnabled(false);
		diminishPropertyBtn.setEnabled(false);
    	
    	if(e.getSource() == propertiesCombo && 
    	   propertiesCombo.getSelectedItem() != null) // combo state changed and it's not empty
    	{
    		prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
    		
    		text = propertiesCombo.getSelectedItem().toString() + "\n";
    		if(prop instanceof Lot) // is Lot
    		{
	    		text += "Improvement cost: " + ((Lot) prop).getImproveCost() + "\n";
	    		text += "Gain from diminishing: " + ((Lot) prop).getImproveCost()/2 + "\n";
	    		
	    		improvePropertyBtn.setEnabled(true);
	    		diminishPropertyBtn.setEnabled(true);
    		}
    				
    		propertyText.setText(text);
    	}
    	
    	else if(e.getSource() == allPropsCombo && 
    			allPropsCombo.getSelectedItem() != null) // allPropsCombo
    	{
    		String msg; // string info to display
    		Property p;
    		
    		msg = "";
    		p = game.getProperty(allPropsCombo.getSelectedItem().toString());
    		
    		msg += p.toString() + "\n";
    		
    		if((p instanceof Lot || p instanceof RailRoad) // add rent if applicable
    				&& p.getOwner() != null)
    		{
    			msg += "Current rent: $" + p.getRent() + "\n";
    			if(p instanceof Lot)                // add housing level for Lots
    			{
    				msg += ((Lot) p).getHousingLevel() + "\n";
    			}
    		}
    		
    		msg += "\n";
    		
    		JOptionPane.showMessageDialog(null, msg,"Info",
    				JOptionPane.INFORMATION_MESSAGE);
    	}
    }

}
