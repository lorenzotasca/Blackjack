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
  /*
  public String newCard() {
    String newCard = dealer.Distribute(card.bunchs);
    System.out.println("\nNew card:");
    System.out.println(newCard + "\n");
    return newCard;
  }
  */

  public void start()throws Exception { 

    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    
    

    //introduction
    System.out.print("Welcome player\n");
    //System.out.print("Welcome player, please insert yosur name: ");
    System.out.print("The dealer is waiting for the other players...\n");

    Thread.sleep(3000);

    while(true){

      int verify = 0;

      System.out.print("The game is starting\n\n");

      Thread.sleep(1000);

      //bet
      System.out.print("You have " + fiches.getTotalValue() + "$ devide in:\n");
      /*
      for (Integer key : fiches.getFiches().keySet()) {
        System.out.print(fiches.getFiches(key) + " fiches of " + key + "\n");
      }
      */

      for (Integer value : fiches.fiches.keySet()){
        System.out.println(fiches.getFiches(value) + " fiches of " + value);
      }
      /*
      System.out.print(fiches.getFiches(100) + " fiches of " + 100 + "\n");
      System.out.print(fiches.getFiches(50) + " fiches of " + 50 + "\n");
      System.out.print(fiches.getFiches(20) + " fiches of " + 20 + "\n");
      System.out.print(fiches.getFiches(10) + " fiches of " + 10 + "\n");
      System.out.print(fiches.getFiches(5) + " fiches of " + 5 + "\n");
      */
      //System.out.print("Make a bet\n");
      int totBet = 0;
      while(totBet == 0){
        for (Integer value : fiches.fiches.keySet()){
          boolean betPlaced = false;
          while (!betPlaced) {
            System.out.println("How many fiches of " + value + " do you want to bet: ");
            int bet = Integer.parseInt(stdIn.readLine());
            if (fiches.removeFiches(value, bet)) {
              totBet += bet * value;
              betPlaced = true; // Esci dal ciclo while se la scommessa è stata piazzata con successo
            }
          }  
        }
        if (totBet == 0) {
          System.out.println("You have to bet at least 1 fiche");
        }
      }
      
      
      os.writeBytes("Bet: " + totBet + '\n');

      System.out.println("Fiches left: " + fiches.getTotalValue());
      os.writeBytes("Fiches left: " + fiches.getTotalValue() + '\n');
      
      
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
      System.out.print("\nTotal value of your cards: " + totalValue + "\n");
      os.writeBytes("Total value: " + totalValue + '\n');


      while (true) 
      { 

        //System.out.print("The dealer is waiting for your move\n");
        
        //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");



        if ((valueCard1 == 11 && valueCard2 == 10) || (valueCard1 == 10 && valueCard2 == 11)) {
          System.out.println("Blackjack! Your total value is 21.");
          os.writeBytes("Blackjack!\n");
          verify = -1;
          break;
        }

        System.out.print("What do you want to do? (hit/stand): ");
        String response = stdIn.readLine();
        os.writeBytes(response + '\n');  
    
        // fai anche tutti gli altri casi, ad esempio se le due carte sono uguali, si può sdoppiare; ecc...
        if (response.equalsIgnoreCase("hit")) {
          String newCard = dealer.Distribute(card.bunchs);
          System.out.println("\nNew card:");
          System.out.println(newCard + "\n");
          os.writeBytes("New card of Player: \n");

          // metti tutte le carte del player in un array, in modo che ogni volta che gli viene data una nuova carta, metti a video tutte le sue carte

          os.writeBytes(newCard + "\n");
          totalValue += card.calculateValueCard(newCard);
          System.out.println("New total value of your cards: " + totalValue);
          os.writeBytes("New total value of Player:" + totalValue + "\n");

          if (totalValue > 21) {
            System.out.println("Busted! Your total value is " + totalValue + ", over 21.");
            os.writeBytes("Busted with " + totalValue + "\n");
            verify = -1;
            break;
          }

        }else if (response.equalsIgnoreCase("stand")) {

          System.out.println("You chose to stand. Your final total value: " + totalValue);
          os.writeBytes("Stand with " + totalValue + '\n');
          verify = -1;
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

      Thread.sleep(1000); // to gave a delay for Dealer to receive the answer of the player

      if (verify == -1)
      {
        System.out.println("Do you want to play another hand? (yes/QUIT)");
        String response = stdIn.readLine();
        os.writeBytes(response + '\n');
        if (response.equalsIgnoreCase("QUIT")) {
          System.out.println("You have disconnected from the game\n");
          os.writeBytes("QUIT\n");
          //Chiusura dello Stream e del Socket
          os.close(); 
          is.close(); 
          socket.close();
          break;
        }
      }
    }
    

    
  } 
  public static void main (String[] args) throws Exception { 
    Player tcpClient = new Player(); 
    tcpClient.start(); 
  }

}
