/**
 * 
 * This class models a hand of type Triple in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Triple extends Hand{
	/**
	 * The constructor used to build a triple hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	
	public Triple(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 *  This functions checks if the hand played is a valid triple hand or not
	 *  @return a boolean variable true if the hand is a valid triple hand or false if the hand is not a valid triple hand
	 */
	public boolean isValid() {
		if(this.size() == 3) {
			for(int i=0;i<3;i++) {
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
			}	
			if((this.getCard(0).rank != this.getCard(1).rank) || (this.getCard(0).rank != this.getCard(2).rank)|| (this.getCard(1).rank != this.getCard(2).rank)) {
				return false;
			}	
			else
				return true;		
		}
		else
			return false;
	}
	
	/**
	 * This functions returns the type of this hand which is a Triple
	 * @return a string containing the value "Triple"
	 * 
	 */
	public String getType() {
		return "Triple";
	}
}

