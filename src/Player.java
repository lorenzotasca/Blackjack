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

    while(true){

      int verify = 0;

      //bet
      System.out.print("You have " + fiches.getTotalValue() + "$ devided in:\n");
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
      
      String serverMessage = is.readLine();
      
      os.writeBytes("Bet: " + totBet + '\n');

      System.out.println("Fiches left: " + fiches.getTotalValue());
      os.writeBytes("Fiches left: " + fiches.getTotalValue() + '\n');
      

      // Leggi il comando inviato dal ServerThread
      String serverCommand = is.readLine();      
      if (serverCommand.equals("INITIAL_CARDS")) {
        // Leggi le carte iniziali inviate dal ServerThread
        String initialCards = is.readLine();
        System.out.println("Your cards:");
        System.out.println(initialCards);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("HIT")) {
        // Leggi la carta inviata dal ServerThread
        String newCard = is.readLine();
        System.out.println("New card:");
        System.out.println(newCard);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("STAND")) {
        // Leggi il messaggio inviato dal ServerThread
        String message = is.readLine();
        System.out.println(message);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("BUSTED")) {
        // Leggi il messaggio inviato dal ServerThread
        String message = is.readLine();
        System.out.println(message);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("BLACKJACK")) {
        // Leggi il messaggio inviato dal ServerThread
        String message = is.readLine();
        System.out.println(message);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("PLAY_AGAIN")) {
        // Leggi il messaggio inviato dal ServerThread
        String message = is.readLine();
        System.out.println(message);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else if (serverCommand.equals("QUIT")) {
        // Leggi il messaggio inviato dal ServerThread
        String message = is.readLine();
        System.out.println(message);
        
        // Invia la risposta al ServerThread
        String response = getUserResponse(); // Metodo per ottenere la risposta del giocatore
        os.writeBytes(response + "\n");
      }
      else {
        System.out.println("Invalid command received from the server");
      }
      /*
      System.out.println("Your cards:");
      Thread.sleep(1000);
      System.out.println(card1);
      Thread.sleep(1000);
      System.out.println(card2);
      os.writeBytes("Cards of Player: \n");
      os.writeBytes(card1 + "\n");
      os.writeBytes(card2 + "\n");
      System.out.print("\nTotal value of your cards: " + totalValue + "\n");
      os.writeBytes("Total value: " + totalValue + '\n');
      */

      //while (true) 
      //{ 

        //System.out.print("The dealer is waiting for your move\n");
        
        //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");


        /*
        if ((valueCard1 == 11 && valueCard2 == 10) || (valueCard1 == 10 && valueCard2 == 11)) {
          System.out.println("Blackjack! Your total value is 21.");
          os.writeBytes("Blackjack!\n");
          verify = -1;
          break;
        }
        */

        /*
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
      }*/
    }
    
    
  } 

  private String getUserResponse() throws IOException {
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Enter your response: ");
    return stdIn.readLine();
  }

  public static void main (String[] args) throws Exception { 
    Player tcpClient = new Player(); 
    tcpClient.start(); 
  }

}
