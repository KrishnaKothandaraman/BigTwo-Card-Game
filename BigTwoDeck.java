/**
 * 
 *  The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game
 * 
 *
 *	@author krishnakothandaraman
 */
public class BigTwoDeck extends Deck{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BigTwoDeck() {
		super();
		this.initialise();
	}

	/**
	 *  Initialises the deck of 52 Big two cards
	 */
	public void initialise() {
		removeAllCards();
		for(int i=0;i<4;i++) {
			for(int j=0;j<13;j++) {
				BigTwoCard card =  new BigTwoCard(i,j);
				addCard(card);
			}
		}
		
		}

}
