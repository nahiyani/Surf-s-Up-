/**
 * The NahiyanCard class creates a card which will be found in a deck
 *
 * @author (Nahiyan Ishtiaque)
 * @version (April 20th 2022)
 * 
 */
public class NahiyanCard
{   
    //arrays that will hold all possible ranks and suits of each card 
    public static final String[] RANKS = { null, "Ace", "2", "3", "4", "5", "6","7", "8", "9", "10", "Jack", "Queen", "King" };
    public static final String[] SUITS = { "Clubs", "Diamonds", "Hearts", "Spades" };
    
    //variables to hold the rank and suit numbers
    public byte rank, suit;
    
    //default constructor for the Nahiyan Card class
    public NahiyanCard(){
        //initializing the instance variables for the default constructor
        this.rank = -1;
        this.suit = -1;
    }
    
    //overloaded contructor with a given rank and suit
    public NahiyanCard(byte rank, byte suit){
        //initializing the rank and suits variable for this constructor
        this.rank = rank;
        this.suit = suit;
    }
    
    //toString for NahiyanCard class
    public String toString(){
        //returning the card's rank and suit
        return String.format("%s of %s", RANKS[this.rank], SUITS[this.suit]);
    }
       
}
