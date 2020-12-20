import java.util.*;

/**
 * This class models a hand of type FullHouse in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class FullHouse extends Hand{

	
	ArrayList<Integer> pair;
	ArrayList<Integer> triple;
	int tripleSuitIndex;
	/**
	 * The constructor used to build a fullhouse hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player,cards);
		pair = new ArrayList<Integer>(this.size());
		triple = new ArrayList<Integer>(this.size());
		tripleSuitIndex = 0;
	}
	/**
	 *  This functions checks if the hand played is a valid FullHouse hand or not
	 *  @return a boolean variable true if the hand is a valid FullHouse hand or false if the hand is not a valid FullHouse hand
	 */
	public boolean isValid() {
		
		if(this.size() == 5) {
			int pairLength = 0;
			int tripleLength = 0;
			int pairCheck = 0;
			int tripleCheck = 0;
			for(int i=0;i<5;i++) {
				
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
			}
			int tempCheck = this.getCard(0).rank;
			int numCheck = 0;
			for(int i=0;i<5;i++) {
				if(this.getCard(i).rank == tempCheck)
					numCheck++;
			}
			if(numCheck == 2) {
				pairCheck = this.getCard(0).rank;
				for(int i=0;i<5;i++) {
				if(this.getCard(i).rank!= pairCheck) {
					tripleCheck = this.getCard(i).rank;
					break;
				}
				}
			}
			else {
				tripleCheck = this.getCard(0).rank;
				for(int i=0;i<5;i++) {
					if(this.getCard(i).rank!= tripleCheck) {
						pairCheck = this.getCard(i).rank;
						break;
					}
				}
			}
			for(int i=0;i<5;i++) {
				if(this.getCard(i).rank == pairCheck) {
					pair.add(this.getCard(i).rank);
					pairLength++;
				}
				else
					tripleCheck = this.getCard(i).rank;
			}
			for(int i=0;i<5;i++) {
				int tripleSuitMax = 0;
				if(this.getCard(i).rank == tripleCheck) {
					if(this.getCard(i).suit > tripleSuitMax) {
						tripleSuitIndex = i;
						tripleSuitMax = this.getCard(i).suit;
					}
					triple.add(this.getCard(i).rank);
					tripleLength++;
				}
			}
			if(pairLength == 2 && tripleLength == 3) 
				return true;				
			
			else 
				return false;
		}
		else
			return false;
	}
	/**
	 * This functions returns the type of this hand which is a FullHouse
	 * @return a string containing the value "FullHouse"
	 * 
	 */
	public String getType() {
		return "FullHouse";
	}
	
	/**
	 * This function overrides the getTopCard function and returns a new topCard 
	 * @return a BigTwoCard which is the top card in this hand
	 */
	public Card getTopCard() {
		return getCard(tripleSuitIndex);
	}
}
	

