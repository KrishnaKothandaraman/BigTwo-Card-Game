 /**
  * 
  * @author krishnakothandaraman
  * 
  * subclass of the CardList class, and is used to model a hand of cards in the Big Two card game.
  */
public abstract class Hand extends CardList {
	
	private CardGamePlayer player;
	private int handsize;
	/**
	 * The constructor is used to build a hand for player 
	 * @param player is the player who played the hand
	 * @param cards is a list of cards played by the player in this hand
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		
		this.player = player;
		this.handsize = cards.size();
		for(int i=0;i<this.handsize;i++) 
			addCard(cards.getCard(i));
	}
	 /**
	  * Gets the player who played the hand
	  * @return a CardGamePlayer object of the player who played the hand
	  */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	/**
	 * Gets the top card in the hand played
	 * @return a Card object that contains the details of the top card in the hand
	 */
	public Card getTopCard() {
		int maxRank = getCard(0).rank;
		int maxSuit = getCard(0).suit;
		int maxIndex = 0;
		if(this.handsize == 1) 
			return getCard(maxIndex);
		for(int i=0;i<this.handsize;i++) {
			if(getCard(i).rank == '1') {
				if(maxRank == '1') {
					if(getCard(i).suit > maxSuit) {
						maxRank = getCard(i).rank;
						maxSuit = getCard(i).suit;
						maxIndex = i;
					}
				}
				else {
					maxRank = getCard(i).rank;
					maxSuit = getCard(i).suit;
					maxIndex = i;
				}
			}
			else if(getCard(i).rank == '0') {
				if(maxRank == '0') {
					if(getCard(i).suit > maxSuit) {
						maxRank = getCard(i).rank;
						maxSuit = getCard(i).suit;
						maxIndex = i;
						}
					}
				else if(maxRank != '1') {
					maxRank = getCard(i).rank;
					maxSuit = getCard(i).suit;
					maxIndex = i;
				}
			}
			else {
				if(getCard(i).rank > maxRank) {
					maxRank = getCard(i).rank;
					maxSuit = getCard(i).suit;
					maxIndex = i;
				}
				else if(getCard(i).rank == maxRank) {
					if(getCard(i).suit > maxSuit) {
						maxRank = getCard(i).rank;
						maxSuit = getCard(i).suit;
						maxIndex = i;
						}
				}
			}
		}
		return getCard(maxIndex);
	}
	
	/**
	 * Checks if the hand played beats the hand passed as the argument 
	 * @param hand is the hand this hand is compared to
	 * @return true if this hand beats the hand in the parameter and false if it does not
	 */
	
	boolean beats(Hand hand) {
		if(this.handsize != hand.handsize)
			return false;
		else {
			if(this.getType() == hand.getType()) {
				if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
						return true;
					else
						return false;
				}
			else {
				String[] order = {"Straight", "Flush", "FullHouse", "Quad", "StraightFlush"};
				int this_order = 0;
				int hand_order = 0;
				for(int i=0;i<4;i++) {
					if(this.getType().equals(order[i])) 
						this_order = i;
					if(hand.getType().equals(order[i]))
						hand_order = i;
				}
				if(this_order > hand_order) 
					return true;
				else
					return false;	
			}
		}
	}
	
	/**
	 * an abstract method used to check if a given hand is valid
	 * @return a boolean variable that is true if the hand is valid and false if the hand is invalid
	 */
	public abstract boolean isValid();
	/**
	 * an abstract method used to get the type of hand played
	 * @return a string that contains the type of hand played
	 */
	public abstract String getType();
}
