import java.util.ArrayList;
/**
 * This class models a hand of type Quad in a Big Two game
 * @author krishnakothandaraman
 *
 */
public class Quad extends Hand{
	
	ArrayList<Integer> single;
	ArrayList<Integer> quad;
	int quadSuitIndex;
	/**
	 * The constructor used to build a quad hand 
	 * @param player is an object of type CardGamePlayer that contains details of the player of this hand
	 * @param cards is the list of cards played by the player
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player,cards);
		single = new ArrayList<Integer>(this.size());
		quad = new ArrayList<Integer>(this.size());
		quadSuitIndex = 0;
	}
	/**
	 *  This functions checks if the hand played is a valid quad hand or not
	 *  @return a boolean variable true if the hand is a valid quad hand or false if the hand is not a valid quad hand
	 */
	public boolean isValid() {
		
		if(this.size() == 5) {
			int quadLength = 0;
			for(int i=0;i<5;i++) {
				
				int suitCheck = this.getCard(i).suit;
				int rankCheck = this.getCard(i).rank;
				if(!(0 <= rankCheck && rankCheck <13 && 0<=suitCheck && suitCheck < 4))
					return false;
			}
			for(int i=0;i<5;i++) {
				int quadCheck = this.getCard(i).rank;
				int quadCounter = 0;
				for(int j=0;j<5;j++) 
					if(this.getCard(j).rank == quadCheck) 
						quadCounter++;
				if(quadCounter == 4) {
					int maxSuit =0;
					for(int j=0;j<5;j++) {
						if(this.getCard(j).rank == quadCheck)
							if(this.getCard(j).suit > maxSuit) {
								maxSuit = this.getCard(j).suit;
								quadSuitIndex = j;
							}
						}
					return true;
					}
			}
			return false;
		}
		else
			return false;
	}
	/**
	 * This functions returns the type of this hand which is a quad
	 * @return a string containing the value "Quad"
	 * 
	 */
	public String getType() {
		return "Quad";
	}
	
	/**
	 * This function overrides the getTopCard function and returns a new topCard 
	 * @return a BigTwoCard which is the top card in this hand
	 */
	public Card getTopCard() {
		return getCard(quadSuitIndex);
	}

}
