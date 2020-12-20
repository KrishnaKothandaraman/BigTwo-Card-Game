/**
 * 
 * This class models a hand of type Pair in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Pair extends Hand{
	
	/**
	 * The constructor used to build a pair hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	
	public Pair(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 *  This functions checks if the hand played is a valid pair hand or not
	 *  @return a boolean variable true if the hand is a valid pair hand or false if the hand is not a valid pair hand
	 */
	public boolean isValid() {
		if(this.size() == 2) {
			for(int i=0;i<2;i++) {
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
			}	
			if(this.getCard(0).rank != this.getCard(1).rank) {
				return false;
			}
			else
				return true;		
		}
		else
			return false;
	}
	
	/**
	 * This functions returns the type of this hand which is a pair
	 * @return a string containing the value "Pair"
	 * 
	 */
	public String getType() {
		return "Pair";
	}
}
