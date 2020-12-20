import java.util.*;

/**
 * This class models a hand of type Flush in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Flush extends Hand {

	/**
	 * The constructor used to build a flush hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
		
	/**
	 *  This functions checks if the hand played is a valid flush hand or not
	 *  @return a boolean variable true if the hand is a valid flush hand or false if the hand is not a valid flush hand
	 */
	public boolean isValid() {
		
		if(this.size() == 5) {
			
			ArrayList<Integer> rankOfHand = new ArrayList<Integer>(this.size());
			ArrayList<Integer> suitOfHand = new ArrayList<Integer>(this.size());
			int lengthOfHandRank = 0;
			int lengthOfHandSuit = 0;
			for(int i=0;i<5;i++) {
				
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
				if(rankCheck == 0 || rankCheck == 1)
					rankCheck +=13;
				if(!(rankOfHand.contains(rankCheck))) {
					rankOfHand.add(rankCheck);
					lengthOfHandRank++;
				}
				if(!(suitOfHand.contains(suitCheck))) {
					suitOfHand.add(suitCheck);
					lengthOfHandSuit++;
				}
			}	
			Collections.sort(rankOfHand);
			if(lengthOfHandSuit == 1) {
				if(lengthOfHandRank == 5) {
					if(rankOfHand.get(4) - rankOfHand.get(0) == 1)
						return false;
					else
						return true;
				}
				else
					return true;	
			}
			else
				return false;
		}
		else
			return false;	
	}
	
	/**
	 * This functions returns the type of this hand which is a Flush
	 * @return a string containing the value "Flush"
	 * 
	 */
	public String getType() {
		return "Flush";
	}
}
