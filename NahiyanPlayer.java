/**
 * The NahiyanPlayer class creates a player that will play the Surf's Up game
 *
 * @author (Nahiyan Ishtiaque)
 * @version (April 20th 2022)
 */
public class NahiyanPlayer
{
    //initiating instance variables for NahiyanPlayer
    public NahiyanHand hand; //hand of player
    public String status, name; //name and status of player
    
    //default constructor for NahiyanPlayer class
    public NahiyanPlayer(){
        //initializing hand, status and name variables for default constructor
        this.hand = new NahiyanHand();
        this.status = "----";
        this.name = "N/A";
    }
    
    //overloaded constructor that takes in a name parameter
    public NahiyanPlayer(String name){
        //initializing hand, status and name variables for constructor that takes in name
        this.hand = new NahiyanHand();
        this.status = "----";
        this.name = name;
    }
    
    //method to add a card to the hand
    public void addCard(NahiyanCard c){
        //the for loop goes through all the indexes of the hand
        for(byte  i = 0; i < this.hand.cards.length; i++){
            if(this.hand.cards[i] == null){//if there is an empty index...
                //the card is added to the specific index
                this.hand.cards[i] = c; 
                //breaking out of the loop                
                break;
            }
        }
    }
    
    //method for removing a card from the hand
    public void removeCard(NahiyanCard c){
        //the for loop goes through all the indexes of the hand
        for(byte  i = 0; i < this.hand.cards.length; i++){//looping through the indexes of the hand
            if(this.hand.cards[i] == c){//if the card to be removed is found in the hand
                //setting the hand at the card's position to null
                this.hand.cards[i] = null; 
                //breaking out of the loop
                break;
            }
        }
    }
    
    //method for clearing the hand of a player
    public void clearHand(){
        for(byte i = 0; i < this.hand.cards.length; i++){//looping through the number of cards in the hand
            //setting each card of the hand to null (essentially no hand)
            this.hand.cards[i] = null;
        }
    }
    
    //string that returns the player's name and status
    public String getStatus(){
        //return statement containing name and status
        return "\n"+ this.name + " has a status of " + this.status;
    }
    
    //the toString method for NahiyanPlayer
    public String toString(){ 
        //returns the player's name and the contents of their hand
        return "\n"+ this.name +"'s turn\nStatus: " + this.status + "\nHand: " + this.hand.toString(); 
    }
}
