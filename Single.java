/**
 * This class models a hand of type Single in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Single extends Hand {
	
	/**
	 * The constructor used to build a single hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 *  This functions checks if the hand played is a valid single hand or not
	 *  @return a boolean variable true if the hand is a valid single hand or false if the hand is not a valid single hand
	 */
	public boolean isValid() {
		if(this.size() == 1) {
			int suitCheck = this.getCard(0).suit;
			int rankCheck = this.getCard(0).rank;
			if(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4)
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * This functions returns the type of this hand which is a single
	 * @return a string containing the value "Single"
	 * 
	 */
	public String getType() {
		return "Single";
	}
	
}
