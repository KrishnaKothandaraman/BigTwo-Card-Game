/**
 * 
 * @author krishnakothandaraman
 *
 *	The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a
	Big Two card game
 */
public class BigTwoCard extends Card{
	/**
	 * 
	 * @param suit  an int value between 0 and 3 representing the suit of a card:
	 *            <p>
	 *            0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank
	 * 			  an int value between 0 and 12 representing the rank of a card:
	 *            <p>
	 *            0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11
	 *            = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
			super(suit,rank);
		}
	/**
	 * 
	 *	a method for comparing the order of this card with the
		specified card based on the BigTwo game rules
		
	 * @return  an int value of 0,1,-1
	 * 			0 if the two cards have same rank and same suit
	 * 			1 if this card has a higher rank than the card it's being compared to
	 * 			-1 if this card has a lower rank than the card it's being compared to
	 * 
	 * @param card
	 * 			card to be compared to 
	 * 		
	 */
	public int compareTo(Card card) {
		int thisRank = this.rank;
		int cardRank = card.rank;

		if(thisRank == 0 || thisRank == 1) 
			thisRank +=13;
		if(cardRank == 0 || cardRank == 1) 
			cardRank+=13;
		if(thisRank < cardRank)
			return -1;
		else if(thisRank > cardRank)
			return 1;
		else {
			if(this.getSuit() > card.getSuit())
				return 1;
			else if(this.getSuit() < card.getSuit())
				return -1;
			else 
				return 0;
		}
	}
}
