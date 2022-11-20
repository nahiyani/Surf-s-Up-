//importing the ArrayList functions
import java.util.ArrayList;
//importing the Scanner function
import java.util.Scanner;

/**
 * The NahiyanGame class contains all methods required to play Surf's Up
 *
 * @author (Nahiyan Ishtiaque)
 * @version (April 20th 2022)
 * 
 */
public class NahiyanGame
{   
    //scanner to take in user input
    Scanner input = new Scanner(System.in);
    
    public NahiyanDeck deck; //deck to play the game
    public byte eliminated; //byte to hold the number of players currently out
    public byte total; //byte to hold the sum value of the cards placed
    public byte playingOrder; //byte to hold the order of who places the cards, where 1 is counting up in the array of players, and -1 is counting down
    public ArrayList<NahiyanPlayer> players; //array to hold all players in game
    public byte currentPlayerIndex; //byte to hold the index in the players array of the current player
    public NahiyanPlayer currentPlayer; //the current player playing in the game
    public byte numPlayers; //byte to hold the total number of players at the start
    public String winner;//string to hold winner's name
    
    //byte values to hold the ranks of special cards (including face cards)
    public final byte aceRank = 1;
    public final byte twoRank = 2;
    public final byte skipRank = 3;
    public final byte sevenRank = 7;
    public final byte jackRank = 11;
    public final byte queenRank = 12;
    public final byte kingRank = 13 ;

    public final byte endValue = 77; //game ends at this value
    public final byte threeRank = 0; //the three does not contribute to total
    public final byte aceLowValue = 1; //lower option for Ace
    public final byte aceHighValue = 11; //higher option for Ace
    
    //defaut constructor for the NahiyanGame class
    public NahiyanGame(){
        //initializing all variables of the default NahiyanGame constructor
        this.deck = new NahiyanDeck();
        this.players = new ArrayList<NahiyanPlayer>();
        this.numPlayers = (byte) this.players.size();
        this.currentPlayer = new NahiyanPlayer();
        this.eliminated = 0;
        this.total = 0;
        this.playingOrder = 1;
        this.winner ="NO WINNER";
        this.currentPlayerIndex = 0;
    }
    
    //overloaded constructor with all the players added
    public NahiyanGame(ArrayList<NahiyanPlayer> allPlayers){
        //initializing all variables of the overloaded NahiyanGame constructor
        this.players = allPlayers;
        this.numPlayers = (byte) allPlayers.size();
        this.deck = new NahiyanDeck();
        this.currentPlayer = new NahiyanPlayer();
        this.eliminated = 0;
        this.total = 0;
        this.playingOrder = 1;
        this.winner = "NO WINNER";
        this.currentPlayerIndex = 0;
    }
    
    // method that simulates playing a card from the deck
    public void playCard(NahiyanCard aCard){
        //checking the beginning move of every new game
        setTotal(aCard);
        //outputting the sum of the cards already played
        System.out.println("\nPile value: " + this.total);
        do {//do while loop as long as there isn't a winner yet
            
            do {//do while loop as long as the round isn't over 
                //removing the played card from the current player
                this.currentPlayer.removeCard(aCard);
                //adding the card played back to the end of to deck
                this.deck.cards.add(aCard);
                //making the current user pick up a card
                pickUp();
                //making the next player the current player
                nextPlayer();
                //making the now current player pick a card
                aCard = pickCard();
                //checking through all the cards for next steps
                setTotal(aCard);
                //outputting the current pile sum
                System.out.println("\nPile value: " + this.total);
            } while (!roundOver());
            //the loop will end once a winner is found
        } while((!isWinner()));
        
        //congratulations message
        System.out.println("Congratulations! " + this.currentPlayer.name + " on winning the game! You won with status: " + this.currentPlayer.status);
    }
    
    // method that sets the total of the pile
    public void setTotal(NahiyanCard aCard){
        //if statement that affects the game depending on which card is placed
        if(aCard.rank == aceRank){ //if an ace is placed
            //asking if they would like to add 1 or 11
            System.out.print("Would you like your ace to be a " + aceLowValue + " or "+ aceHighValue + "?: ");
            // variable to be their answer to the ACE card question
            int answer = input.nextInt(); 
            while (answer != aceLowValue  && answer != aceHighValue){//while loop as long as they don't enter 1 or 11
                //message asking them to input the right answer
                System.out.print("Please choose only " + aceLowValue + " or " + aceHighValue + ": ");
                //getting the answer
                answer = input.nextInt();
            } //going throught the while loop again if necessary
            //adding their choice once it's appropriate
            this.total += (byte)answer;
        } else if (aCard.rank == skipRank){ //if a 3 is placed
            // the play order is reversed
            this.playingOrder *=-1;
        } else if (aCard.rank == jackRank){ //if a Jack is placed
            // the total becomes the remainder after dividing the total by 10
            this.total = (byte) (this.total % 10);
        } else if (aCard.rank == sevenRank){ //if a seven is placed
            // the sum becomes 77 (the value required to win)
            this.total = endValue; 
        } else if (aCard.rank == twoRank){ //if a two is placed
            // total is multiplied by two
            this.total *= 2;
        } else { //if any other card is placed
            //its rank value is added to the sum
            this.total += aCard.rank; 
        }
    }
    
    //method to check for the winner method
    public boolean isWinner(){
        if(this.players.size() == 1){// if there is only one player left
            //only one player remains in the ArrayList  
            return true;
        }
        //more than one player left
        return false;
    }
    
    //method that allows the user to pick which card they want to play
    public NahiyanCard pickCard(){
        //outputting the player
        System.out.println(this.currentPlayer);
        //displaying a message and asking for the card they want to play
        System.out.print("Enter the number of the card that you want to play (if you want to play Card 1, for example, please enter 1, etc.): ");
        
        //creating an answer variable
        byte answer = input.nextByte();
        while (answer > this.currentPlayer.hand.cards.length || answer < 0) {//while the number answer they give is not a Card option
            //asking for a valid number answer
            System.out.print("Invalid input, enter a number between from 1 to " + currentPlayer.hand.cards.length + ": ");
            //getting the player's response
            answer = input.nextByte();
        }
        //sending the card at the index of the answer minus one in the player's hand to the playCard method 
        return this.currentPlayer.hand.cards[answer - 1];
        
    }
    
    //method that deals with the end of a round
    public boolean roundOver(){
        //if the sum of the cards is over the end value (77)
        if (this.total > endValue) {
            if (this.currentPlayer.status.equals("----")) { // if the status string of the current player is ----
                //S is the new status string
                this.currentPlayer.status = "S---";
                //outputting who lost the round
                System.out.println("\n"+ this.currentPlayer.name + " has their first letter!\n"+this); 
            } else if (this.currentPlayer.status.equals("S---")) {//if the player's status is "S"
                //change the status string to add a U
                this.currentPlayer.status = "SU--";
                //outputting who lost the round
                System.out.println("\n"+ this.currentPlayer.name + " has two more letters to go!\n" + this);
            } else if (this.currentPlayer.status.equals("SU--")) {//if the player's status is "SU"
                //change the status string to add a R
                this.currentPlayer.status = "SUR-";
                //outputting who lost the round
                System.out.println("\n"+ this.currentPlayer.name + " is down to their last letter!\n" + this); 
            } else {//if their status is not "----", "S---", "SU--", or "SUR-"
                //adding to the number of players out
                this.eliminated += 1;
                //message stating elimination
                System.out.println("\nThe round comes to an end! "+ this.currentPlayer.name + " has been eliminated!\n");
                //the current player is then removed from the arraylist
                this.players.remove(this.currentPlayer); 
                //printing out the remaining palyers' scores
                System.out.println(this);
                //next player;
                nextPlayer();
                //creating a new round
                newRound();
                //returning true
                return true;
            } 
            //call the newRound method to start a new round
            newRound();
            //returning true
            return true;
        }
        //since the value of the pile is not over 77, game keeps going
        return false;
    }
    
    //method to pick up a card
    public void pickUp(){
        //adding the first card of the deck to the player's hand
        this.currentPlayer.addCard(this.deck.cards.get(0));
        //removing the card at the first card index from the deck
        this.deck.cards.remove(0); 
    }
    
    //method to switch to the next player
    public void nextPlayer(){
        //play order value is added to  
        this.currentPlayerIndex += this.playingOrder;
        //seeing if the currentPlayerIndex is out of the array bounds 
        if (currentPlayerIndex > this.players.size() - 1) {//if the currentPlayerIndex is over the last index
            //current player becomes the player at the first index
            this.currentPlayerIndex = 0;
        } else if (currentPlayerIndex < 0) {//if the currentPlayerIndex is below the first index
            //current player becomes the player at the last index
            this.currentPlayerIndex = (byte)(this.players.size() - 1);
        }
        //returns the new current player 
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
    }
    
    //method that deals to all the players at the beginning of a new round
    public void dealPlayers(){
        for(int i = 0; i < this.players.size(); i++){//looping through the number of players
            //player hand is cleared
            this.players.get(i).clearHand(); 
            //the player is dealt a new hand
            this.deck.deal(this.players.get(i));
        }
    }
    
    //method to start the next round
    public void newRound(){
        //resetting the deck by creating a new one
        this.deck = new NahiyanDeck();
        //shuffling new deck
        this.deck.shuffle();
        //deals a new hand to all players
        dealPlayers(); 
        //the pile total resets to 0
        this.total = 0;
    }
    
    //method to print out all the players
    private String printPlayers(){
        //string to be returned
        String out = "";
        //for loop to go through all the players 
        for(int i = 0; i < numPlayers; i++){
            //adding the player's name and status to the String
            out += "\n" + this.players.get(i).toString();
        }
        //returning the string
        return out;
    }
    
    //toString method for NahiyanGame class
    public String toString(){
        //indicates the number of players left in the game
        String out =  "Number of players left in game: " + (this.numPlayers - this.eliminated);
        for(int i = 0; i < this.players.size(); i++){//for loop to add every player and their status
            //prints out the player's status
            out += this.players.get(i).getStatus();
        }
        //returning the string
        return out;
    }
    
    //scanner to accept input from user for the game
    public static Scanner userInput = new Scanner(System.in);
    
    //method to restart game depending on user input
    public static void restart(){
        //asking for input from user
        System.out.print("Please enter \"y\" with no spaces if you would like to play again. If not, enter anything else! : ");
        //avoiding scanner bug
        userInput.nextLine();
        System.out.println("");
        //setting up a variable to take in user's answer
        String answer = userInput.nextLine();
        //if the answer is anything but 'y', then it calls the main, restarting the game
        if (answer.toLowerCase().charAt(0) == 'Y') { 
            main(null);
        } else {
            //output message if they press anything else
            System.out.println("Thank you for playing the game. Enjoy the rest of your day! SURF'S UP!");
        }
    }
    
    //method that creates the game and takes in the number of players playing the game
    public static NahiyanGame createGame(){
        //creating a deck of cards
        NahiyanDeck newDeck = new NahiyanDeck();
        //shuffling the deck
        newDeck.shuffle();
        
        //asking for the number of players playing in the game
        System.out.print("How many players are playing the game (minumum of 3, maximum of 5)?: ");
        //takes in asnwer
        byte answer = userInput.nextByte();
        //while user input is greater than 5 and less than 3 (giving user the option to play with 3-5 players)
        while(answer > 5 || answer < 3){
            //output message, and asking for proper answer
            System.out.print("There can only be 3, 4, or 5 players playing the game. How many players are playing the game?: ");
            //takes in asnwer
            answer = userInput.nextByte();
            //the while loop keeping looping until they give an acceptable amount of players
        }
        
        //avoiding scanner bug
        userInput.nextLine();
        
        //creating the Game class with an arraylist of players from getPlayers
        NahiyanGame game = new NahiyanGame(getPlayers(answer));
        //making the deck from before the new deck for the Game class
        game.deck = newDeck;
        //dealing a hand to all of the players
        game.dealPlayers();
        
        //letting players choose who plays first (who the first current player will be)
        System.out.print("Which player is going first (please indicate by number)?: ");
        //getting the answer
        answer = userInput.nextByte();
        
        //while the answer is not equal to tne number of player playing the game
        while(answer > game.players.size() || answer < 1){
            //asking for an acceptable answer
            System.out.print("Please enter a player number from " + 1 + " to "+ game.players.size()+" for the first player: ");
            //getting next answer
            answer = userInput.nextByte();
            
        } //the while loop keeps looping until they give an acceptable answer to who will go first
        //making the current player & current player index the adjusted number of the first player
        game.currentPlayer = game.players.get(answer - 1);
        game.currentPlayerIndex = (byte)(answer - 1);
        //returning the instance of the NahiyanGame class
        return game;
    }
    
    //method that enters the players into the game
    public static ArrayList<NahiyanPlayer> getPlayers(byte numPlayers){
        //ArrayList of players to be returned
        ArrayList<NahiyanPlayer> players = new ArrayList<NahiyanPlayer>();
        //for loop to add players to the ArrayList
        for(int i = 0; i < numPlayers; i++){
             //asking for the player's name 
            System.out.print("What is Player #" + (i + 1)+"'s name?: ");
            //getting name input from user
            String name = userInput.nextLine(); 
            
            //trying to see if the name entered has already been entered by a previous player
            for(int j = 0; j < i; j++){ //looping through the amount of existing players, which is i
                //if the name input is the same as a previous player's name
                if(name.equals(players.get(j).name)){
                    do {//asking them to pick a different name if name is taken
                        //message asking for a new name
                        System.out.print("This name is already taken, please enter another name: ");
                        //getting new name
                        name = userInput.nextLine();
                        //restarting loop so that new name can be compared to all previous names
                        j = 0;
                    } while(name.equals(players.get(j).name));//reapting while loop as long as the name still matches a previous one
                }
            }
            
            //a new player is added to the game with the new player's name 
            players.add(new NahiyanPlayer(name)); 
        }
        //retuning ArrayList of players
        return players;
    }
    
    public static void main (String [] args) {
        //message for the rules of input
        System.out.println("INSTRUCTIONS: " +
        "\n1) Enter only numerical characters except for names." + "\n2) The goal of Surf's Up is to put down cards in a way that when it is your turn, the pile total doesn't exceed 77." +
        "\n3) If you put down an ace, you can choose its value to be either 1 or 11." + "\n4) If you lose the round, you get a letter, starting with 'S'." +
        "\n5) If you spell out the word SURF, you are eliminated." + "\n6) The last person remaining is the winner. Good luck to all!");
        
        //creating a new game and initiating it 
        NahiyanGame game = createGame();
        //starting the game by picking the card using pickCard(), and giving it to playCard()
        game.playCard(game.pickCard());
        
        //calling the restart method
        restart();
    }
}

