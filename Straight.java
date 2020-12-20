import java.util.*;

/**
 * This class models a hand of type Straight in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Straight extends Hand{
	
	/**
	 * The constructor used to build a straight hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 *  This functions checks if the hand played is a valid straight hand or not
	 *  @return a boolean variable true if the hand is a valid straight hand or false if the hand is not a valid straight hand
	 */
	public boolean isValid() {
		
		if(this.size() == 5) {
			ArrayList<Integer> rankOfHand = new ArrayList<Integer>(this.size());
			int lengthOfHand = 0;
			for(int i=0;i<5;i++) {
				
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
				if(rankCheck == 0 || rankCheck == 1)
					rankCheck +=13;
				if(!(rankOfHand.contains(rankCheck))) {
				rankOfHand.add(rankCheck);
				lengthOfHand++;
				}
			}	
			
			Collections.sort(rankOfHand);
			
			if(lengthOfHand == 5) {
				if(rankOfHand.get(4) - rankOfHand.get(0) == 4)
					return true;
				else
					return false;
			}
			else
				return false;	
		}
		else
			return false;	
	}
	/**
	 * This functions returns the type of this hand which is a Straight
	 * @return a string containing the value "Straight"
	 * 
	 */
	public String getType() {
		return "Straight";
	}
	
}
