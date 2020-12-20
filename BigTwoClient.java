import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * 
 * The BigTwo class is used to model a Big Two card game. This class contains the
 * start function that has the game logic.
 * @author krishnakothandaraman
 *
 */
public class BigTwoClient implements CardGame,NetworkGame{

	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;  
	private ArrayList<Hand> handsOnTable; 
	private int currentIdx;
	private int playerID; 
	private BigTwoTable table;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	/**
	 *  Initializes playerList and adds 4 cardgame players to it. Initializes handsOnTable and creates a new bigTwotable.
	 */
	public BigTwoClient()
	{
		playerList = new ArrayList<CardGamePlayer> ();
		for(int i = 0; i < 4;i++)
		{
			CardGamePlayer player = new CardGamePlayer();
			player.setName("");
			playerList.add(player);
		}
		handsOnTable = new ArrayList<Hand>();
		table = new BigTwoTable(this);
		playerID = -1;
		int accepted=1;
		while(accepted==1) {
			String input = JOptionPane.showInputDialog("Enter your name");
			if(input!=null && !input.equals("")) {
				setPlayerName(input);
				accepted=0;
			}
		}
		setServerIP("127.0.0.1");
		setServerPort(2396);
		makeConnection();
		table.disable();
		table.repaint();
		currentIdx = -1;
	}
	
	/**
	 * A method for getting the current deck being used
	 *  
	 * @return an object of deck that's being used for the current BigTwo game
	 */
	public Deck getDeck()
	{
		return deck;
	}
	/**
	 * A method for getting the list of players playing the game.
	 * 
	 * @return a CardGamePlayer object of type ArrayList containing the list of players.
	 */
	public ArrayList<CardGamePlayer> getPlayerList()
	{
		return playerList;		
	}
	/**
	 * A method for getting the current cards that have been played on the table from the start of the game
	 * 
	 * @return a hand object of type ArrayList containing the hands played on the table.
	 */
	public ArrayList<Hand> getHandsOnTable()
	{
		return handsOnTable;
	}
	
	/**
	 * A method which gets the index of the active player.
	 * 
	 * @return an integer taking values 0,1,2,3 
	 */
	public int getCurrentIdx()
	{
		return currentIdx;	
	}
	/**
	 * A method to return a valid hand from all the list of cards played by the player. 
	 * 
	 * @param player A CardGamePlayer object which contains the list of players in the game.
	 * @param cards A CardList object which contains list of cards played by the active player.
	 * 
	 * @return Hand The type of hand.
	 */
	public Hand composeHand(CardGamePlayer player, CardList cards)
	{
		Single single = new Single(player,cards); 
		Pair pair = new Pair(player,cards); 
		Triple triple = new Triple(player,cards); 
		Straight straight = new Straight(player,cards); 
		Flush flush = new Flush(player,cards); 
		FullHouse fullhouse = new FullHouse(player,cards); 
		Quad quad = new Quad(player,cards); 
		StraightFlush straightflush = new StraightFlush(player,cards); 
	
		if(straightflush.isValid())
			return straightflush; 
		
		else if(quad.isValid())
			return quad; 
		
		else if(fullhouse.isValid()) 
			return fullhouse; 
		else if(flush.isValid())
			return flush; 
		
		else if(straight.isValid())
			return straight; 
		
		else if(triple.isValid())
			return triple; 
		
		else if(pair.isValid())
			return pair; 
		
		else if(single.isValid())
			return single; 
		else
			return null;
	}

	/**
	 * Main helps in creating BigTwo and BigTwoDeck objects and shuffle and start the game by calling the start function.
	 * 
	 * @param args unused
	 */
	public static void main(String [] args)
	{
		BigTwoClient game = new BigTwoClient();				
	}

	/**
	 * Overrides the getNumOfPlayers method from the cardgame interface. 
	 * 
	 * @return an integer with the number of players.
	 */
	public int getNumOfPlayers() {
		return this.playerList.size();
	}

	/**
	 * Overrides the start method of the cardgame interface. This method clears the hands of the players and distributes a fresh hand to the players.
	 * It then sorts the hands and finds the user who has the 3 of diamonds and sets the player index as the active player.
	 */
	public void start(Deck Deck) {
		
		handsOnTable.clear();
		
		for(int i=0;i<4;i++) 
			playerList.get(i).getCardsInHand().removeAllCards();
		
		
		for(int i=0;i<52; i++)   								//distributing 13 random cards to the players
			playerList.get(i%4).addCard(Deck.getCard(i));	
		
		
		for(int i=0;i<4;i++) 
			playerList.get(i).getCardsInHand().sort();

		BigTwoCard threeOfDiamonds = new BigTwoCard(0,2);			//finding which player has the 3 of diamonds
		for(int i = 0; i < 4;i++) 
		{
			if(playerList.get(i).getCardsInHand().contains(threeOfDiamonds))	
			{
				currentIdx = i;
				break;
			}
		}
		table.printMsg("All players are ready. Game starts.");
		table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();
	}
	/**
	 * Overrides the makeMove method from the cardGame interface. Calls checkMove 
	 * @param playerID of type int with the index of the player making the move
	 * 		  cardIDx of type int array with the index of the cards being played.
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		if(this.currentIdx == this.getPlayerID()) {
			CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE, -1 , cardIdx);
			sendMessage(move);
		}
		else
			table.printMsg("Not your turn!!!");
	}

	/**
	 * Overrides the checkMove method from the cardGame interface. This method first checks if a valid hand can be composed from the selected cards by calling the
	 * isValid method. It then checks if the hand played beats the hand already on the table. 
	 */
	public synchronized void checkMove(int playerID, int[] cardIdx) {
			BigTwoCard threeOfDiamonds = new BigTwoCard(0,2);
			boolean legalMove = true;
			CardList listOfCards = new CardList();
 			if(cardIdx!=null)				//Case when a hand is played
			{
				listOfCards = playerList.get(playerID).play(cardIdx);
				Hand handPlayedCompose = composeHand(playerList.get(playerID), listOfCards);
					if(handPlayedCompose != null)												
					{
						if(handsOnTable.isEmpty())											//handles case when the hand played is the first hand on the table
						{
								if(handPlayedCompose.contains(threeOfDiamonds))
									if(handPlayedCompose.isValid())
										legalMove = true; 
									else 
										legalMove = false;
								else
									legalMove = false;
							}
						else																//case when hands have already been played on the table
							{
								if(handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerID))
									legalMove = handPlayedCompose.beats(handsOnTable.get(handsOnTable.size() - 1));
								else {
									if(handPlayedCompose.isEmpty())
										legalMove = false;
									else
										legalMove = true;
								}
							}
						if(handPlayedCompose.isValid() && legalMove)						//removing the cards from the players card list if a valid hand is played
							{
								for(int i=0;i<listOfCards.size();i++)
									playerList.get(playerID).getCardsInHand().removeCard(listOfCards.getCard(i)); 
								table.printMsg("{" + handPlayedCompose.getType() + "}" + " " + handPlayedCompose);
								handsOnTable.add(handPlayedCompose);
								if(currentIdx == 3)
									currentIdx = 0;
								else
									currentIdx ++;
								table.printMsg(playerList.get(currentIdx).getName()+ "'s turn");
							}
							else															//handles invalid hand being played
								table.printMsg(listOfCards + " <== Not a legal move!!!"); 
							}
					else {																	//handles invalid hand 
						table.printMsg(listOfCards + " <== Not a legal move!!!");
					}
				}			
				else																		//case when player passes his turn 
				{	
					if(handsOnTable.isEmpty() == false && handsOnTable.get(handsOnTable.size() - 1).getPlayer()!=playerList.get(playerID)) 		//when player who passed was not the last player to play
					{
						table.printMsg("{Pass}");  
						if(currentIdx == 3)
							currentIdx = 0;
						else
							currentIdx++;
						table.printMsg(playerList.get(currentIdx).getName()+ "'s turn");
						legalMove = true;
					}
					else																	//when player who passed was the last player who played
					{
						table.printMsg("{Pass} <== Not a legal move!!!");
						legalMove = false;
					} 
				}
			table.repaint();
				
			if(endOfGame()) 																//handles the end of game case
			{
				int winner=0;
				String endMessage ="";
				table.printMsg("");
				table.printMsg("Game ends");
				for(int i=0;i<4;i++)
				{
					if(playerList.get(i).getNumOfCards() == 0) {
						endMessage += playerList.get(i).getName() + " wins the game.\n";
						winner=i;
					}
					else
						endMessage += playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards in hand.\n"; 
				}
				if(winner==this.getPlayerID()) 
					JOptionPane.showMessageDialog(null, endMessage, "You win", JOptionPane.INFORMATION_MESSAGE);
				else 
					JOptionPane.showMessageDialog(null, endMessage, "You lose", JOptionPane.INFORMATION_MESSAGE);
				CardGameMessage msg = new CardGameMessage(CardGameMessage.READY , -1 ,null);
				sendMessage(msg);
				table.disable();
			}
		}
	/**
	 * Overrides the endOfgame method from the cardGame interface. This method checks if any player has 0 cards left.
	 * @return a boolean true is a player has 0 cards left and false if the player has non zero cards left.
	 */
	public boolean endOfGame() {
		for(int i=0;i<playerList.size();i++) 
			if(this.playerList.get(i).getNumOfCards() == 0)
				return true;
		return false;
	}

	/**
	 * returns local playerID
	 * @return int containing local playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * sets local playerID
	 * @param int containing playerID
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		
	}

	/**
	 * returns local playerName
	 * @return string containing playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * sets local player name
	 * @param string containing playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		
	}

	/**
	 * returns serveIP 
	 * @return string with serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * sets serverIP
	 * @param string containing the serverIP
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * returns serverport 
	 * @return int with serverport value
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * sets serverport 
	 * @param integer containing the serverport
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
		
	}
	/**
	 * checks if a connection is made
	 * @return true of false
	 */
	public boolean checkConnected() {
		if(sock.isClosed())
			return false;
		else 
			return 
				true;
	}

	/**
	 * a method for making a socket connection with the game server. Upon successful connection, a thread is created for receiving messages.
	 */
	public void makeConnection() {
		try {
			sock = new Socket(getServerIP(), getServerPort());
			oos = new ObjectOutputStream(sock.getOutputStream());
			Runnable threadJob = new ServerHandler();
			Thread gameThread = new Thread(threadJob);
			gameThread.start();
		}catch (Exception e) { e.printStackTrace();} 
		 
	}

	/**
	 * a method for parsing the messages received from the game server. Based on the message received, it calls the required functions.
	 * 
	 * @param A GameMessage object that contains the message recieved by the server.
	 */
	public synchronized void parseMessage(GameMessage message) {
		if(message.getType() == CardGameMessage.PLAYER_LIST) {			//sets the name of players received by the server
			setPlayerID(message.getPlayerID());
			table.setActivePlayer(this.playerID);
			for(int i=0;i<getNumOfPlayers();i++) {
				if(((String[]) message.getData())[i] != null)
					this.playerList.get(i).setName(((String[]) message.getData())[i]);			
			}
		CardGameMessage msg = new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName());
		sendMessage(msg);
		}
		else if(message.getType() == CardGameMessage.JOIN) {			//sets the name of the player who joined the game.
			getPlayerList().get(message.getPlayerID()).setName((String)message.getData());
			table.repaint();	
			table.printMsg("Player " + (String)message.getData() + " has joined the game!");
			if(message.getPlayerID() == this.getPlayerID())
			{
				CardGameMessage msg = new CardGameMessage(CardGameMessage.READY, -1,null);
				sendMessage(msg);
			}
		}
		else if(message.getType() == CardGameMessage.FULL) {			//handles the case when player joins and server if full
			table.printMsg("The server is full and cannot be joined");
			try {
				sock.close();
			}catch(IOException e) { e.printStackTrace(); }
		}
		else if(message.getType() == CardGameMessage.QUIT) {				//handles when a player quits the game
			table.printMsg("Player " + getPlayerList().get(message.getPlayerID()).getName() + " disconnected.");
			getPlayerList().get(message.getPlayerID()).setName("");
			if(endOfGame() == false) {
				table.disable();
				CardGameMessage msg = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(msg);
				table.repaint();
			}
		}
		else if(message.getType() == CardGameMessage.READY) {				//handles when the game is ready after all players join
			table.printMsg("Player " + getPlayerList().get(message.getPlayerID()).getName() + " is ready to play!");
			table.repaint();
		}
		else if(message.getType() == CardGameMessage.START) {			//calls the start function and starts the game
			deck = (BigTwoDeck) message.getData();
			start(deck);
			table.enable();
			table.repaint();
		}
		else if(message.getType() == CardGameMessage.MSG) {				//handles chat messages
			table.printChatMsg((String) message.getData());
		}
		else if(message.getType() == CardGameMessage.MOVE) {			//handles moves recieved from the server.
			checkMove(message.getPlayerID(), (int[]) message.getData());
			table.repaint();
		}
	}
	@Override
	/**
	 * The method for sending the specified message to the game server.
	 * 
	 * @param A cardMessage object that contains the message to send to the server
	 */
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		}catch(Exception e) { e.printStackTrace(); }
	}
	/**
	 * 	an inner class that implements the Runnable interface. It implements the run() method from the Runnable interface and creates a thread
		with an instance of this class as its job in the makeConnection() method from the
		NetworkGame interface for receiving messages from the game server
	 * @author krishnakothandaraman
	 *
	 */
	class ServerHandler implements Runnable{

		@Override
		public void run() {
			CardGameMessage message = null;
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				while(!sock.isClosed()) {
					message = (CardGameMessage) ois.readObject();
					if(message !=null) 
						parseMessage(message);
				}
				ois.close();
			}catch(Exception e) { e.printStackTrace(); }
			table.repaint();
		}
		
	}
	
}