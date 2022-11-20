/**
 * NahiyanHand class deals out a hand and contains all functions associated with a hand of cards
 *
 * @author (Nahiyan Ishtiaque)
 * @version (April 20th 2022)
 */
public class NahiyanHand
{   
    //declaring instance variables
    public final byte NUM_CARDS = 5; //five cards in a hand
    public NahiyanCard[] cards; //array to hold the cards
    
    //default constructor for NahiyanHand
    public NahiyanHand(){
        //initializing the rank and suits variable for this constructor
        this.cards = new NahiyanCard[NUM_CARDS];
    }
    
    //toString method to print out the card
    public String toString(){
        //string that will be returned 
        String out = "";
        for(byte i = 0; i < this.cards.length; i++){//looping through the hand of the player 
            //adding on the card at i's identity
            out += "\nCard " + (i+1) + ": " + this.cards[i].toString();
        }
        //returning the string
        return out;
    }
}

