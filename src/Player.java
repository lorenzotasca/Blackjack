import java.io.*;
import java.net.Socket;

public class Player {
  // risoste del player al dealer (hit, stand, double, split, surrender)

  Card card;
  Dealer dealer;
  

  public Player() {

    card = new Card();
    dealer = new Dealer();
    card.CreateStructureBunch();
    
  }
  
  public String newCard() {
    String newCard = dealer.Distribute(card.bunchs);
    System.out.println("New card:");
    System.out.println(newCard + "\n");
    return newCard;
  }
  

  public void start()throws IOException { 
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    System.out.print("To disconnect from the game, type: QUIT\n"); 

    System.out.print("Welcome player\n");
    //System.out.print("Welcome player, please insert yosur name: ");
    System.out.print("The dealer is waiting for the other players\n");
    System.out.print("The game is starting\n\n");
    
    System.out.print("The dealer is distributing the cards\n");


    //get the start two cards
    String card1 = dealer.Distribute(card.bunchs);
    String card2 = dealer.Distribute(card.bunchs);

    int totalValue = card.calculateValueCard(card1) + card.calculateValueCard(card2);

    System.out.print("Your cards:");
    System.out.println(card1);
    System.out.println(card2);
    System.out.print("Total value of your cards: " + totalValue + "\n");

    while (true) 
    { 
      //System.out.print("Fai la puntata\n");
      
      //System.out.print("The dealer is waiting for your move\n");
      
      //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");




      System.out.print("What do you want to do? (card/stand): ");
      String response = stdIn.readLine();
  
      // fai anche tutti gli altri casi, ad esempio se le due carte sono uguali, si può sdoppiare; ecc...
      if (response.equalsIgnoreCase("card")) {
        String newCard = newCard();
        totalValue += card.calculateValueCard(newCard);
        System.out.println("New total value of your cards: " + totalValue);

        if (totalValue > 21) {
          System.out.println("Busted! Your total value is over 21.");
          break;
        }
      } else if (response.equalsIgnoreCase("stand")) {
        System.out.println("You chose to stand. Your final total value: " + totalValue);
        break;
      } else {
        System.out.println("Invalid response. Please enter 'card' or 'stand'.");
      }



      /*
      System.out.print("Insert: "); 
      String userInput = stdIn.readLine(); 
      if (userInput.equals("QUIT")) 
        break; 
      os.writeBytes(userInput + '\n');  
      System.out.println("Hai digitato: " + is.readLine()); 
      */
    } 

    //Chiusura dello Stream e del Socket 
    os.close(); 
    is.close(); 
    socket.close(); 
  } 
  public static void main (String[] args) throws Exception { 
    Player tcpClient = new Player(); 
    tcpClient.start(); 
  }

}
