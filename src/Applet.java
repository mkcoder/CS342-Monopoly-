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
	public static final int MIN_PANEL_WIDTH = 220; // min width of the jpanel
	
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
	private JButton    buyPropertyBtn;		  // Buy property button
	private JButton    improvePropertyBtn;    // Improve property button
	private JButton    diminishPropertyBtn;   // Diminish property button
	private JButton    giveTurnBtn;           // Give turn button
    private JLabel     diceRollLabel;         // dice result label
	private JLabel     playerLabel;	          // info about current player
	private JLabel     propertyLabel;         // info about propery chosen from combo box
	private JTextArea  notificationText;      // feedback from curent action
    private JComboBox  propertiesCombo;       // properties owned by the current player

    // other members
    private Point[] coordArray; // cooridnates of all game fields in pixels from 0,0
    private int boardScale;     // size of the board in pixels
    private int itemScale;      // size of the tokens, houses and hotels in pixels
    private Game game;          // game object
	
	public void init()
	// POST: initializes JApplet
	{
		JPanel panel;	// panel for JComponents
		int playerNum;  // number of players for the current game
				
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
		                                        // answer is given
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
		
	    // jcomponents		
		diceRollBtn = new JButton("Roll Dice");
		buyPropertyBtn = new JButton("Buy property");
		improvePropertyBtn = new JButton("Improve property");
		diminishPropertyBtn = new JButton("Diminish property");
		giveTurnBtn = new JButton("Give up turn");
		
		diceRollLabel = new JLabel();
		playerLabel = new JLabel(game.getCurrentPlayer().toString());
		propertyLabel = new JLabel("Properties list");
		notificationText = new JTextArea();
		notificationText.setEditable(false);
		notificationText.setBackground(getContentPane().getBackground());
		
		propertiesCombo = new JComboBox();		
		propertiesCombo.addItemListener(this);
		
		// layout
	    setLayout(new BorderLayout());
	    
	    panel = new JPanel();
	    panel.setLayout(new GridLayout(10, 0));
	    panel.setPreferredSize(new Dimension(MIN_PANEL_WIDTH, 600));
	    
	    panel.add(playerLabel);
        panel.add(diceRollLabel);
        panel.add(notificationText);
        panel.add(diceRollBtn);
        panel.add(buyPropertyBtn);
        panel.add(improvePropertyBtn);
        panel.add(diminishPropertyBtn);
        panel.add(giveTurnBtn);
        panel.add(propertyLabel);
        panel.add(propertiesCombo);
        
        add(panel, BorderLayout.EAST);
		
        // listeners
		diceRollBtn.addActionListener(this);
		buyPropertyBtn.addActionListener(this);
		improvePropertyBtn.addActionListener(this);
		diminishPropertyBtn.addActionListener(this);
		giveTurnBtn.addActionListener(this);
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
	    divFactor = (boardScale/10) * (0.825);
	    
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

    @Override
    public void actionPerformed(ActionEvent e)
    // POST: handles all buttons clicks
    {
    	String text;   // string used to build message for labels 
    	Property prop; // auxilary Property to make code shorter
    	Player player; // current player
    	
    	player = game.getCurrentPlayer();
    	
    	if(e.getSource() == diceRollBtn) // roll dice button clicked
		{
    		text = Integer.toString(game.rollDice());
            diceRollLabel.setText("Dice roll result" + text);
            text = player.move(Player.getDice());
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
		    }
		    
		    notificationText.setText(text);
            playerLabel.setText(player.toString());
		}    	
    	else if(e.getSource() == improvePropertyBtn) // Improve property button clicked
		{
		    text = "You cannot improve the lot!";
		    prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
		    
		    if(prop instanceof Lot && ((Lot) prop).improve()) // improved successfully
		    {
		    	text = "You have improved your lot!\n";
		        repaint();
		    }
		    
		    notificationText.setText(text);
		    playerLabel.setText(player.toString());
		}
    	else if(e.getSource() == diminishPropertyBtn) // Diminish property button clicked
		{            
            text = "You cannot diminish the lot!";
            prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
            
            if(prop instanceof Lot && ((Lot)prop).diminish()) // diminished successfully
            {
            	text ="You have diminished your lot!\n";
                repaint();
            }
            
            notificationText.setText(text);
            playerLabel.setText(player.toString());
        }
    	else if(e.getSource() == giveTurnBtn) // Give turn button clicked
		{
		    game.giveTurn();
		    player = game.getCurrentPlayer();
		    
		    // reset the ui
		    playerLabel.setText(player.toString());		    
		    diceRollLabel.setText("");		    
		    notificationText.setText("");
		    propertyLabel.setText("");
            
            propertiesCombo.removeAllItems();
            for(Property p : player.getProperties()) // populate combo with properties
            {
                propertiesCombo.addItem(p.getName());
            }
		}
    }
    
    @Override
    public void itemStateChanged(ItemEvent e)
    {
    	String text;   // message built for propLabel
    	Property prop; // selected property
    	
    	if(e.getSource() == propertiesCombo && 
    	   propertiesCombo.getSelectedItem() != null) // combo state changed and it's not empty
    	{
    		prop = game.getProperty(propertiesCombo.getSelectedItem().toString());
    		
    		text = propertiesCombo.getSelectedItem().toString() + "\n";
    		if(prop instanceof Lot)
    		{
	    		text += "Improvement cost: " + ((Lot) prop).getImproveCost() + "\n";
	    		text += "Gain from diminishing: " + ((Lot) prop).getImproveCost()/2 + "\n";
    		}
    				
    		propertyLabel.setText(text);
    	}
    }

}
