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
	public static final String CAR = "./src/car.png";
	public static final int OFFSET_X = 10;
	public static final int OFFSET_Y = 10;
	public static final String diceDefaultLabel = "Dice roll result:  ";
	private BufferedImage boardImage;
    private BufferedImage carImage;
    private Point  [] coordArray;
    private Game game;
    
	//JComponents
	private JButton    diceRollBtn; 	
	private JButton    buyPropertyBtn;
	private JButton    improvePropertyBtn;    
	private JButton    giveTurnBtn;    
    private JLabel     diceRollLabel;
	private JLabel     playerLabel;	
	private JLabel     playerPropertiesLabel;
	private JLabel     boardLocationNotificationLabel;
    private JComboBox  playerPropertiesCombo;
	
	
	public void init()
	{
	    BorderLayout layout;
	    GridLayout gLayout;
	    JPanel panel;
	    
	    gLayout = new GridLayout(10, 0);
	    layout = new BorderLayout();
	    panel = new JPanel(); 	
	    
	    panel.setLayout(gLayout);
	    setLayout(layout);
	   	
		boardImage = getImage(BOARD_FILE);
		carImage = getImage(CAR);
		game = new Game(new Integer(JOptionPane.showInputDialog("how many players")));
		
		diceRollBtn = new JButton("Roll Dice!");
		buyPropertyBtn = new JButton("Buy property");
		improvePropertyBtn = new JButton("Improve property");
		giveTurnBtn = new JButton("Give up turn");
		
		diceRollLabel = new JLabel("dice");
		playerLabel = new JLabel(game.getCurrentPlayer().toString()); // PLAYER [$Money]		
		playerPropertiesLabel = new JLabel("prop"); // info about current player combo property
		boardLocationNotificationLabel = new JLabel("noti");
		playerPropertiesCombo = new JComboBox();
		
		// ACTION LISTENERS
		diceRollBtn.addActionListener(e -> { 
            diceRollLabel.setText(diceDefaultLabel + game.rollDice()); 
            boardLocationNotificationLabel.setText(game.getCurrentPlayer().move(Player.getDice()));
            playerLabel.setText(game.getCurrentPlayer().toString());
        });
		
		buyPropertyBtn.addActionListener(e -> {
		    
		});
		
		improvePropertyBtn.addActionListener(this);
		giveTurnBtn.addActionListener(this);
        playerPropertiesCombo.addItemListener(this);

        panel.add(playerLabel);
        panel.add(diceRollLabel);
        panel.add(boardLocationNotificationLabel);
        panel.add(diceRollBtn);
        panel.add(buyPropertyBtn);
        panel.add(improvePropertyBtn);
        panel.add(giveTurnBtn);
        panel.add(playerPropertiesLabel);
        panel.add(playerPropertiesCombo);
        
        add(panel, layout.EAST);
        
	}
	
	@Override
	public void paint(Graphics g)
	{
	    super.paint(g);
		int imgScale;			
		
		if((getWidth() - 130) < getHeight())
			imgScale = getWidth() - 150;
		else
			imgScale = getHeight() - 20;
		
		coordArray = getPoints(imgScale);		
		g.drawImage(boardImage, OFFSET_X, OFFSET_Y, imgScale, imgScale, null);
		
		for(Point p: coordArray)
		{
		    g.drawImage(carImage, p.x, p.y,imgScale/20,imgScale/20 , null);
		}		
	}
	
	private Point[] getPoints(int imageScale)
	{
	    Point [] tempCoord;
	    double divFactor;
	 
	    tempCoord = new Point[40];
	    divFactor = (imageScale/10) * (0.825);
	    
	    for(int i = 0; i < 11 ;i++)
	    {
	        tempCoord[i] = new Point( (int) (imageScale-divFactor*i -divFactor*1.5),(int) (imageScale-divFactor));
	    }
	    
	    for (int i = 11; i < 21; i++)
	    {
	        tempCoord[i] = new Point((int) divFactor/2,(int) (imageScale-divFactor*(i-10)-divFactor*1.5));
        }
	    
	    for (int i = 21; i < 31; i++)
        {
            tempCoord[i] = new Point((int) (divFactor*(i-20) +divFactor*1),(int) (divFactor/2));
        }
	    
	    for (int i = 31; i < 40; i++)
        {
            tempCoord[i] = new Point((int) (imageScale-divFactor),(int) (divFactor*(i-30)+divFactor*.9));
        }
	    
	    for(Point p: tempCoord)
	    {
	        p.x = p.x + OFFSET_X;
	        p.y = p.y + OFFSET_Y;
	    }
	    
        return tempCoord;
	}
	
	public BufferedImage getImage(String fileName)
	{
		BufferedImage img;
		
		img = null;
		try 
		{
			img = ImageIO.read(new File(fileName));
		} 
		catch (IOException e)
		{
		}
		
		return img;
	}

    @Override
    public void actionPerformed(ActionEvent e)
    {
//        if (e.getSource() == diceRollBtn) 
//        {
//            
//        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        
    }

}
