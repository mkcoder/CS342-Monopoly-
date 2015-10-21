//maciek
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
	public static final String BOARD_FILE = "./src/mon.jpg";
	public static final String CAR_FILE = "./src/car.png";
	public static final String DOG_FILE = "./src/dog.png";
	public static final String HAT_FILE = "./src/hat.png";
	public static final String IRON_FILE = "./src/iron.png";
	public static final String THIMBLE_FILE = "./src/thimble.png";
	public static final String SHIP_FILE = "./src/ship.png";
	public static final String BOOT_FILE = "./src/boot.png";
	public static final String WHEELBARROW_FILE = "./src/wheelbarrow.png";
	public static final String HOTEL_FILE = "./src/hotel.png";
	public static final String HOUSE_FILE = "./src/house.png";	
	
	public static final int OFFSET_X = 10;
	public static final int OFFSET_Y = 10;
	public static final String DICE_DEFAULT_LABEL = "Dice roll result: ";
	
	private BufferedImage boardImage;
    private BufferedImage carImage;
    private BufferedImage dogImage;
    private BufferedImage hatImage;
    private BufferedImage ironImage;
    private BufferedImage thimbleImage;
    private BufferedImage shipImage;
    private BufferedImage bootImage;
    private BufferedImage wheelImage;
    private BufferedImage houseImage;
    private BufferedImage hotelImage;    
   
    private Point[] coordArray;
    private int boardScale;
    private int itemScale;
    private Game game;
    
	//JComponents
	private JButton    diceRollBtn;			  // Roll dice button
	private JButton    buyPropertyBtn;		  // Buy property button
	private JButton    improvePropertyBtn;    // Improve property button
	private JButton    diminishPropertyBtn;   // Diminish property button
	private JButton    giveTurnBtn;           // Give turn button
    private JLabel     diceRollLabel;         // dice result label
	private JLabel     playerLabel;	          // info about current player
	private JLabel     playerPropertiesLabel; // info about propery chosen from combo box
	private JTextArea  notificationText;       // feedback from curent action
    private JComboBox  playerPropertiesCombo; // properties owned by the current player
	
	
	public void init()
	{
		JPanel panel;	// panel for JComponents
		int playerNum;  // number of players for the current game
		
		// game
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
		playerPropertiesLabel = new JLabel("Properties list");
		notificationText = new JTextArea();
		notificationText.setEditable(false);
		notificationText.setBackground(getContentPane().getBackground());
		
		playerPropertiesCombo = new JComboBox();		
		playerPropertiesCombo.addItemListener(this);
		
		// layout
	    setLayout(new BorderLayout());
	    
	    panel = new JPanel();
	    panel.setLayout(new GridLayout(10, 0));
	    panel.setPreferredSize(new Dimension(220, 600));
	    
	    panel.add(playerLabel);
        panel.add(diceRollLabel);
        panel.add(notificationText);
        panel.add(diceRollBtn);
        panel.add(buyPropertyBtn);
        panel.add(improvePropertyBtn);
        panel.add(diminishPropertyBtn);
        panel.add(giveTurnBtn);
        panel.add(playerPropertiesLabel);
        panel.add(playerPropertiesCombo);
        
        add(panel, BorderLayout.EAST);
		
		// dice roll listener
		diceRollBtn.addActionListener(this);
		
		// buy property listener
		buyPropertyBtn.addActionListener(this);
		
		// improve property listener
		improvePropertyBtn.addActionListener(this);
		
		// diminish listener
		diminishPropertyBtn.addActionListener(this);
		
		// give turn listener
		giveTurnBtn.addActionListener(this);
	}
	
	@Override
	public void paint(Graphics g)
	{		
	    super.paint(g);
	    
	    setScale();
	    coordArray = getPoints(boardScale);		
		g.drawImage(boardImage, OFFSET_X, OFFSET_Y, boardScale, boardScale, null);
		
		for(BoardLocation b: game.getBoard())
		{
		    if(b instanceof Lot)
		    {
		    	drawHotelsAndHouses((Lot)b, g);
		    }
		}
		
		for(Player player: game.getPlayers())
        {
		    if(!player.isBankrupt())
		    {
		        drawToken(player,g,boardScale);
		    }
        }
	}
	
	private void setScale()
	//
	// POST: modifies boardScale and itemScale
	{
		if((getWidth() - 230) < getHeight())
			boardScale = getWidth() - 250;
		else
			boardScale = getHeight() - 20;
		
		itemScale = boardScale/20;
	}
	
	private void drawHotelsAndHouses(Lot lot, Graphics g)
	{
		Point p;
		
		if (lot.getRentIndex() < 5)
        {
            for(int i = 0; i < lot.getRentIndex(); i++ )
            {
                p = coordArray[lot.getAddress()];
                g.drawImage(houseImage, p.x, p.y + (i - 1)*boardScale/60, itemScale, itemScale, null);
            }
        }
        else
        {
            p = coordArray[lot.getAddress()];
            g.drawImage(hotelImage,p.x , p.y+boardScale/40, itemScale, itemScale, null);
        }
	}
	
	private void drawToken(Player player, Graphics g, int imgScale) 
	{
	    Point p;
	    BufferedImage token;
	    
	    p = coordArray[player.getLocation().getAddress()];	    
	    token = matchToken(player.getToken());
	    g.drawImage(token, p.x, p.y, itemScale, itemScale, null);
	    
	}

    public BufferedImage matchToken(String token)
    {
        if(token.equals("car"))
        {
            return carImage;
        }
        else if(token.equals("boot"))
        {
            return bootImage;
        }
        else if(token.equals("top hat"))
        {
            return hatImage;
        }
        else if(token.equals("ship"))
        {
            return shipImage;
        }
        else if(token.equals("wheelbarrow"))
        {
            return wheelImage;
        }
        else if(token.equals("iron"))
        {
            return ironImage;
        }
        else if(token.equals("thimble"))
        {
            return thimbleImage;
        }
        else if(token.equals("dog"))
        {
            return dogImage;
        }
        else
        {
            return null;
        }
    }
    
    private Point[] getPoints(int imageScale)
	{
	    Point [] tempCoord;
	    double divFactor;
	 
	    tempCoord = new Point[40];
	    divFactor = (imageScale/10) * (0.825);
	    
	    for(int i = 0; i < 11 ;i++) // bottom edge
	    {
	        tempCoord[i] = new Point((int)(imageScale - divFactor*i - divFactor*1.5),
	        		                 (int) (imageScale - divFactor));
	    }
	    
	    for (int i = 11; i < 21; i++) // left edge
	    {
	        tempCoord[i] = new Point((int)(divFactor/2),
	        		                 (int)(imageScale - divFactor*(i  -10) - divFactor*1.5));
        }
	    
	    for (int i = 21; i < 31; i++) // top edge
        {
            tempCoord[i] = new Point((int)(divFactor*(i - 20) + divFactor),(int)(divFactor/2));
        }
	    
	    for (int i = 31; i < 40; i++) // right edge
        {
            tempCoord[i] = new Point((int)(imageScale - divFactor),
            		                 (int)(divFactor*(i - 30) + divFactor*0.9));
        }
	    
	    for(Point p: tempCoord) // add offsets to all points
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
    {
    	String text;
    	Property prop;
    	
    	if(e.getSource() == diceRollBtn)
		{ 
            diceRollLabel.setText(DICE_DEFAULT_LABEL + game.rollDice()); 
            notificationText.setText(game.getCurrentPlayer().move(Player.getDice()).toString());
            playerLabel.setText(game.getCurrentPlayer().toString());
            repaint();
        }
    	else if(e.getSource() == buyPropertyBtn)
		{		    
		    text = "Sorry you can't purchase the property";
		    if (game.getCurrentPlayer().getLocation() instanceof Property &&
		        game.getCurrentPlayer().buyLocation((Property)game.getCurrentPlayer().getLocation()))
		    {
		            text = "You purchased " + game.getCurrentPlayer().getLocation().getName();
		            playerPropertiesCombo.addItem(game.getCurrentPlayer().getLocation().getName());
		    }
		    
		    notificationText.setText(text);
            playerLabel.setText(game.getCurrentPlayer().toString());
		}    	
    	else if(e.getSource() == improvePropertyBtn)
		{
		    text = "You cannot improve the lot!";
		    prop = game.getProperty(playerPropertiesCombo.getSelectedItem().toString());
		    
		    if(prop instanceof Lot && ((Lot) prop).improve())
		    {
		    	text ="You have improved your lot!\n";
		        repaint();
		    }
		    
		    notificationText.setText(text);
		    playerLabel.setText(game.getCurrentPlayer().toString());
		}
    	else if(e.getSource() == diminishPropertyBtn)
		{            
            text = "You cannot diminish the lot!";
            prop = game.getProperty(playerPropertiesCombo.getSelectedItem().toString());
            
            if(prop instanceof Lot && ((Lot)prop).diminish())
            {
            	text ="You have diminished your lot!\n";
                repaint();
            }
            
            notificationText.setText(text);
            playerLabel.setText(game.getCurrentPlayer().toString());
        }
    	else if(e.getSource() == giveTurnBtn)
		{
		    game.giveTurn();
		    
		    // reset the ui
		    playerLabel.setText(game.getCurrentPlayer().toString());		    
		    diceRollLabel.setText("");		    
		    notificationText.setText("");
            
            playerPropertiesCombo.removeAllItems();
            for(Property p : game.getCurrentPlayer().getProperties()) // populate combo with
            														  // properties
            {
                playerPropertiesCombo.addItem(p.getName());
            }
		}
    }
    
    @Override
    public void itemStateChanged(ItemEvent e)
    {        
    }

}
