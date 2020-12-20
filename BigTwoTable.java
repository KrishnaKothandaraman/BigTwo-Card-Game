import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * This class creates a GUI for the bigTwo Card game. It implements the CardGameTable interface and handles user click events.
 * @author krishnakothandaraman
 *
 */
public class BigTwoTable implements CardGameTable{
	
	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatBox;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	
	/**
	 * Parametrized constructor to initialize bigtwotable. It calls the methods to set images and build the GUI.
	 * 
	 * @param a variable game of type CardGame.
	 */
	BigTwoTable(BigTwoClient game){
		this.game = game;
		selected = new boolean[13];
		setImages();
		setGUI();
	}
	
	private void setImages() {
		avatars = new Image[4];
		for(int i=1;i<5;i++)
			avatars[i-1] = new ImageIcon("src/avatars/player_" + i +".png").getImage();
		cardBackImage = new ImageIcon("src/images/b.gif").getImage();
		char [] suits = {'d','c','h','s'};
		char [] ranks = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
		cardImages = new Image[4][13];
		for(int i =0;i<13;i++)
			for(int j=0;j<4;j++)
				cardImages[j][i] = new ImageIcon("src/images/" + ranks[i] + suits[j] + ".gif").getImage();

	}

	private void setGUI() {
		frame = new JFrame("BigTwo Card Game");
		JPanel textArea = new JPanel();
		JPanel buttons = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JPanel messagePanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BoxLayout(bigTwoPanel, BoxLayout.Y_AXIS));
	    textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
		setButtonsPanel(buttons);
		setMessagePanel(messagePanel);
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(buttons);
		bottomPanel.add(messagePanel);
		setMenuBar(menuBar);
		setMsgArea();
		setChatBox();
	    JScrollPane scrollPane = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    JScrollPane scrollPaneChatBox = new JScrollPane(chatBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.add(scrollPane);
		textArea.add(scrollPaneChatBox);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.setJMenuBar(menuBar);
		frame.add(textArea, BorderLayout.EAST);
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		frame.setSize(1500,900);  
	    frame.setVisible(true); 
	}
	private void setButtonsPanel(JPanel buttons) {
		buttons.setBackground(Color.DARK_GRAY);
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		buttons.add(playButton);
		buttons.add(passButton);
	}
	
	private void setMessagePanel(JPanel messagePanel) {
		messagePanel.setBackground(Color.DARK_GRAY);
		messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel messageLabel = new JLabel("Message: ");
	    JTextField chatBox = new typeArea(40);
	    chatBox.setPreferredSize(new Dimension(200, 40));
	    messagePanel.add(messageLabel);
	    messagePanel.add(chatBox);
	}
	private void setMenuBar(JMenuBar menuBar) {
		JMenu menu = new JMenu("Game Options");
		JMenu chatMenu = new JMenu("Message");
		JMenuItem ConnectGame = new JMenuItem("Connect");
		JMenuItem quitGame = new JMenuItem("Quit");
		menuBar.add(menu);
		menuBar.add(chatMenu);
		menu.add(ConnectGame);
		menu.addSeparator();
		menu.add(quitGame);
		ConnectGame.addActionListener(new ConnectMenuItemListener());
		quitGame.addActionListener(new QuitMenuItemListener());
	}
	
	private void setMsgArea() {
		msgArea = new JTextArea(35,40);
		msgArea.setEditable(false);
		Font font = new Font("SansSerif", Font.ITALIC, 15);
		msgArea.setFont(font);
/*		JScrollPane scrollPane = new JScrollPane(msgArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(450,0)); */
	}
	
	private void setChatBox() {
		chatBox = new JTextArea(35,40);
	    chatBox.setEnabled(false);
		Font font = new Font("SansSerif", Font.ITALIC, 15);
		chatBox.setFont(font);
	}
	
	/**
	 * Inner class that builds the panel with green background, avatars and cards.
	 * Implements the MouseListener interface to handle click events from the user.
	 * 
	 * @author krishnakothandaraman
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		private int lineIncrease = 150;
		private int playerNameXValue = 40;
		private int avatarIncrease = 150;
		private int avatarYValue = 30;
		private int lineXValue = 150;
		private int lineYValue = 1600;
		private int widthOfCard = 40;
		
		/**
		 * BigTwoPanel default constructor which adds the Mouse Listener.
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		
		/**
		 * overrides the paintComponent method of inherited from JPanel. This method handles the creation of the table and placement of avatars and cardImages.
		 * 
		 * @param variable g of type Graphics given by system to allow drawing.
		 */
		public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.WHITE);
		this.setBackground(new Color(0,102,0));
		int playerID = 0;
		for(playerID=0;playerID<4;playerID++) {
			if(playerID == game.getCurrentIdx()) 																
				g2D.setColor(new Color(51,153,255));															//set blue if active player
			else
				g2D.setColor(Color.WHITE);
			if(playerID == game.getPlayerID()) {
				g2D.drawString(" You" , playerNameXValue, 20 + lineIncrease*playerID);	//placing name of player
				g2D.drawImage(avatars[playerID], 10, avatarYValue + avatarIncrease*playerID, 100,100, this);				//placing avatar of player
			}
			else
				if(game.getPlayerList().get(playerID).getName() != ""){
				g2D.drawString(game.getPlayerList().get(playerID).getName(), playerNameXValue, 20 + lineIncrease*playerID);
				g2D.drawImage(avatars[playerID], 10, avatarYValue + avatarIncrease*playerID, 100,100, this);				//placing avatar of player
				}
				g2D.setColor(Color.WHITE);
			    g2D.drawLine(0, lineXValue*(playerID+1), lineYValue, lineXValue*(playerID+1));					//drawing line to separate each player
			if(playerID == activePlayer) {																		//handles case when player is active player so prints faceUp cards
				for(int i=0; i < game.getPlayerList().get(playerID).getCardsInHand().size(); i++) {
					int cardSuit = game.getPlayerList().get(playerID).getCardsInHand().getCard(i).getSuit();
					int cardRank = game.getPlayerList().get(playerID).getCardsInHand().getCard(i).getRank();
					if(selected[i])																				//if card is selected print it raised up
						g2D.drawImage(cardImages[cardSuit][cardRank], 150+widthOfCard*i, 5+lineIncrease*playerID , this);
					else
						g2D.drawImage(cardImages[cardSuit][cardRank], 150+widthOfCard*i, 20+lineIncrease*playerID , this);
				}					
			}
			else {																								//if player is not active print the back side of the cards
				for(int i=0; i < game.getPlayerList().get(playerID).getCardsInHand().size(); i++) {
					g2D.drawImage(cardBackImage, 150+widthOfCard*i, 20+lineIncrease*playerID , this);
			}	
			}
		}
		g2D.drawString("Last hand played", 10, 620);															

		if(!game.getHandsOnTable().isEmpty()) {																	//handles printing of the last hand played on the table
			g2D.drawString(" was a " + game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getType() +": ", 117,620);
			g2D.drawString("by " + game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getPlayer().getName(), 10,640);
			for(int i=0; i< game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size();i++) {
				int CardSuit = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(i).getSuit();
				int CardRank = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(i).getRank();
				g2D.drawImage(cardImages[CardSuit][CardRank], 30+widthOfCard*i, 650 , this);
			} 
		}
		}
		/**
		 * 
		 * Overrides the mouseClicked event of MouseListener. This method handles the user click event to select or deselect a card.
		 * The logic used is that we start at the last card and check the X and Y coordinates of the click to see if it falls within the boundary of the card. If it does not,
		 * we move backwards one card at a time and check the same boundaries again.
		 * 
		 * @param e of type MouseEvent that has the user click information.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int lastCard = game.getPlayerList().get(activePlayer).getNumOfCards() - 1;							//get the index value of the last card
			int handLength = game.getPlayerList().get(activePlayer).getNumOfCards();	
			if(e.getX()>=(150+(handLength-1)*40) && e.getX() <= (223 +handLength*40))							//check if click X value is between the X coordinates of the two corners of the last card
				if(!selected[lastCard]){
					if(e.getY() >= (20+lineIncrease*activePlayer) && e.getY() <= (117+lineIncrease*activePlayer)) {		//if card is selected check within the Y boundaries of the raised card
						selected[lastCard] = true;
						this.repaint();
						return;
					}
				}
				else {																									//if card is not selected, check within the Y boundaries of the card is normal position
					if(e.getY() >= (5+lineIncrease*activePlayer) && e.getY() <= (102+lineIncrease*activePlayer)) {
						selected[lastCard] = false;
						this.repaint();
						return;
					}
				}
			for(int cardNumber = handLength - 2 ; cardNumber>=0;cardNumber--) {											//looping from second last card till the first card
				if(e.getX() >= (150+cardNumber*40) && e.getX() <= (190+cardNumber*40)) {								//check if click X values fall between the boundaries of the card
					if(!selected[cardNumber]) {
						if(e.getY() >= (20+lineIncrease*activePlayer) && e.getY() <= (117+lineIncrease*activePlayer)) {	//if selected, check with raised Y values
							selected[cardNumber] = true;
							this.repaint();
							return;
						}
					}
					else {
						if(e.getY() >=(5+lineIncrease*activePlayer) && e.getY() <= (102+lineIncrease*activePlayer)) {	//if not selected, check with normal Y values.
							selected[cardNumber] = false;
							this.repaint();
							return;
						}
					}
				}
			}	
		}

		@Override
		/**
		 * Overrides mousePressed event from MouseListener
		 * 
		 * @param e of type mouseEvent 
		 */
		public void mousePressed(MouseEvent e) {
		}

		@Override
		/**
		 * Overrides mouseRelased event from MouseListener
		 * 
		 * @param e of type mouseEvent 
		 */
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		/**
		 * Overrides mouseEntered event from MouseListener
		 * 
		 * @param e of type mouseEvent 
		 */
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		/**
		 * Overrides mouseExited event from MouseListener
		 * 
		 * @param e of type mouseEvent 
		 */
		public void mouseExited(MouseEvent e) {
		}
	}
	
	/**
	 * Inner class to handle the event of clicking on the play button. Implements actionListener interface
	 * On clicking on the play button, the makeMove method is called with the player Index and cards the player selected.
	 * @author krishnakothandaraman
	 *
	 */
	public class PlayButtonListener implements ActionListener{

		@Override
		/**
		 * Overrides the actionPerformed method inherited from ActionListener. Handles user action event related to the play button
		 * @param e of type actionListener
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			game.makeMove(activePlayer, getSelected());
			repaint();
		}
		
	}
	/**
	 * Inner class to handle the event of clicking on the pass button. Implements actionListener interface
	 * On clicking on the pass button, the makeMove method is called with the player Index and cards the player selected.
	 * @author krishnakothandaraman
	 *
	 */
	public class PassButtonListener implements ActionListener{

		@Override
		/**
		 * Overrides the actionPerformed method inherited from ActionListener. Handles user action event related to the pass button
		 * @param e of type actionListener
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			game.makeMove(activePlayer, null);
			repaint();
		}
		
	}
	/**
	 * Inner class to handle the event of clicking on the restart button. Implements actionListener interface
	 * On clicking on the restart button, a new game is created and started.
	 * @author krishnakothandaraman
	 *
	 */
	public class ConnectMenuItemListener implements ActionListener{

		@Override
		/**
		 * Method to handle events on the connectButton. connects to the server if the local client is not already connected.
		 * @param e of type actionListener
		 */
		public void actionPerformed(ActionEvent e) {
			if(!game.checkConnected()) {
				reset();
				game.makeConnection();
			}
		}
	}
	/*
	 * Inner class to handle the event of clicking on the quit button. Implements actionListener interface
	 * On clicking on the quit button, the game is excited.
	 * @author krishnakothandaraman
	 *
	 */
	public class QuitMenuItemListener implements ActionListener{

		@Override
		/**
		 * method to exit game.
		 * @param e of type actionListener
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}

	@Override
	/**
	 * Method to initialize the activePlayer of the class.
	 * @param activePlayer of type int containing the index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	@Override
	/**
	 * Overrides the getSelected method of the cardGametable interface .Method to return an array of the indices of the cards selected by the player
	 * @return an integer array with the indices of the selected cards
	 */
	public int[] getSelected() {
		int ct = 0;
		int numSelected = 0;
		for(int j=0;j<selected.length;j++)
			if(selected[j] == true)
				numSelected++;
		if(numSelected ==0)
			return null;
		else {
			int[] selectedCards = new int[numSelected];
			for(int i=0;i<selected.length;i++) {
				if(selected[i]==true) {
					selectedCards[ct] = i;
					ct++;
				}
			}
				return selectedCards;
		}
	}
	/**
	 * Overrides the resetSelected method of cardGametable interface. resets all cards to unselected mode.
	 */
	public void resetSelected() {
		for(int i=0;i<selected.length;i++)
			this.selected[i] = false;
	}

	/**
	 * Overrides the repaint method of cardgametable interface. Calls the reset selected method and the repaint() method of frame.
	 */
	public void repaint() {
		resetSelected();
		frame.repaint();
	}

	/**
	 * Overrides the printMsg method of the cardGame interface. Appends the message onto the textArea
	 * @param string msg containing the text to be added to the textArea.
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");
		
	}
	/**
	 * prints chatMessage into the chatBox
	 * @param string containing the text to be added
	 */
	public void printChatMsg(String msg) {
		chatBox.append(msg+"\n");
		
	}
	/**
	 * Overrides the clearMsgarea method of the cardGametable interface. Clears the text area.
	 */
	public void clearMsgArea() {
		msgArea.setText(null);
	}

	/**
	 * Overrides the reset method of the cardGametable interface. Calls the resetSelected,clearMsgArea and enable methods.
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}

	/**
	 * Overrides the enable method of the cardgametable interface. Enables user actions on the buttons and enables the panel
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);		
	}

	/**
	 *  Overrides the disable method of the cardgametable interface. Disables user actions on the buttons and disables the panel
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);		
	}
	
	private class typeArea extends JTextField implements ActionListener{


		private static final long serialVersionUID = -1125260696213657185L;
		public typeArea(int len) {
			super(len);
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String chatMessage = getText();
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1,chatMessage);
			game.sendMessage(message);
			this.setText("");
		}
	}
}
