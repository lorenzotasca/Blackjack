import java.io.*;
import java.net.Socket;

public class Player {
  // risoste del player al dealer (hit, stand, double, split, surrender)

  Card card;
  Dealer dealer;
  Fiches fiches;
  

  public Player() {

    card = new Card();
    dealer = new Dealer();
    card.CreateStructureBunch();
    fiches = new Fiches();
    
  }
  
  public String newCard() {
    String newCard = dealer.Distribute(card.bunchs);
    System.out.println("\nNew card:");
    System.out.println(newCard + "\n");
    return newCard;
  }
  

  public void start()throws Exception { 
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    System.out.print("To disconnect from the game, type: QUIT\n"); 

    //introduction
    System.out.print("Welcome player\n");
    //System.out.print("Welcome player, please insert yosur name: ");
    System.out.print("The dealer is waiting for the other players\n");
    System.out.print("The game is starting\n\n");


    //bet
    System.out.print("You have " + fiches.getTotalValue() + " fiches\n");
    System.out.print("Make a bet\n");
    int bet = Integer.parseInt(stdIn.readLine());
    os.writeBytes("Bet: " + bet + '\n');
    fiches.removeFiches(bet, 1);

    
    System.out.println("The dealer is distributing the cards\n");


    //get the start two cards
    String card1 = dealer.Distribute(card.bunchs);
    String card2 = dealer.Distribute(card.bunchs);

    int valueCard1 = card.calculateValueCard(card1);
    int valueCard2 = card.calculateValueCard(card2);

    int totalValue = valueCard1 + valueCard2;

    System.out.println("Your cards:");
    System.out.println(card1);
    System.out.println(card2);
    os.writeBytes("Cards of Player: \n");
    os.writeBytes(card1 + "\n");
    os.writeBytes(card2 + "\n");
    System.out.print("Total value of your cards: " + totalValue + "\n");
    os.writeBytes("Total value: " + totalValue + '\n');


    while (true) 
    { 
      
      System.out.println("Fiches rimaste: " + fiches.getTotalValue());
      os.writeBytes("Fiches rimaste: " + fiches.getTotalValue() + '\n');

      
      //System.out.print("The dealer is waiting for your move\n");
      
      //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");



      if ((valueCard1 == 11 && valueCard2 == 10) || (valueCard1 == 10 && valueCard2 == 11)) {
        System.out.println("Blackjack! Your total value is 21.");
        os.writeBytes("Blackjack!\n");
        break;
      }

      System.out.print("What do you want to do? (hit/stand): ");
      String response = stdIn.readLine();
      os.writeBytes(response + '\n');  
  
      // fai anche tutti gli altri casi, ad esempio se le due carte sono uguali, si può sdoppiare; ecc...
      if (response.equalsIgnoreCase("hit")) {
        String newCard = newCard();
        os.writeBytes("New card of Player: \n");

        // metti tutte le carte del player in un array, in modo che ogni volta che gli viene data una nuova carta, metti a video tutte le sue carte

        os.writeBytes(newCard + "\n");
        totalValue += card.calculateValueCard(newCard);
        System.out.println("New total value of your cards: " + totalValue);
        os.writeBytes("New total value of Player:" + totalValue + "\n");

        if (totalValue > 21) {
          System.out.println("Busted! Your total value is " + totalValue + ", over 21.");
          os.writeBytes("Busted with " + totalValue + "\n");
          break;
        }

      }else if (response.equalsIgnoreCase("stand")) {

        System.out.println("You chose to stand. Your final total value: " + totalValue);
        os.writeBytes("Stand with " + totalValue + '\n');
        break;
        
      } else {
        System.out.println("Invalid response. Please enter 'hit' or 'stand'.");
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

    Thread.sleep(1000); // to gave a delay for Dealer to receive the answer

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
