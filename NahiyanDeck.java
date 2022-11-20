//importing the ArrayList functions
import java.util.ArrayList;
/**
 * The NahiyanDeck class is responsible for creating a deck of cards
 *
 * @author (Nahiyan Ishtiaque)
 * @version (April 20th 2022)
 */
public class NahiyanDeck
{
    //arraylist of all the cards
    public ArrayList<NahiyanCard> cards;
    
    //variables to hold the number of ranks and suits
    public final byte numOfRanks = 14; //includes null to work with index
    public final byte numOfSuits = 4; //four suits
    
    //default constructor, filling it with all 52 cards
    public NahiyanDeck(){
        //new ArrayList
        this.cards = new ArrayList<NahiyanCard>();
        //looping through the 4 card suits by using final variable
        for(byte suit = 0; suit < numOfSuits; suit++){
            //looping through the 13 card ranks by using final variable
            for(byte rank = 1; rank < numOfRanks; rank++){
                //the cards are printed with the ranks and suits
                this.cards.add(new NahiyanCard(rank, suit));
            }
        }
    }
    
    //method that simulates the dealing of cards
    public void deal(NahiyanPlayer player){
        //looping through the player's hand's length 
        for(int i = 0; i < player.hand.cards.length; i++){
            //the card is added to the player's hand
            player.addCard(this.cards.get(i));
            //removing the card from the deck at the indicated index
            this.cards.remove(i);
        }
    }
    
    //shuffling the cards
    public void shuffle(){
        for(int i = 0; i < this.cards.size()/2; i++){
            //creating a random number from 1 to the index of the deck
            byte randomNum = (byte)(Math.random() * this.cards.size()); 
        
            //creating a temporary card that will replace another card
            NahiyanCard temp = new NahiyanCard(this.cards.get(i).rank, this.cards.get(i).suit);
            
            //replacing the card at index i with a card at a random index
            this.cards.set(i, new NahiyanCard(this.cards.get(randomNum).rank, this.cards.get(randomNum).suit));
            
            //the card at the random index is then replaced with the temporary variable, which holds the value of the card at the current index pre-swap
            this.cards.set(randomNum, new NahiyanCard(temp.rank, temp.suit));
        }
    }
}
