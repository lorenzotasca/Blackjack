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

  public String receiveMessage(DataInputStream is) throws IOException {
    String message = is.readLine();
    System.out.println(message);
    return message;
  }

  public void start()throws Exception { 
    
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    
    //introduction
    receiveMessage(is);
    receiveMessage(is);

       

    while (true) {

      //game start
      receiveMessage(is);
      receiveMessage(is); 

      //bet
      System.out.print("You have " + fiches.getTotalValue() + "$ devided in:\n");

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

      System.out.println("You bet: " + totBet);
      os.writeBytes("Bet: " + totBet + '\n');

      System.out.println("Fiches left: " + fiches.getTotalValue());
      os.writeBytes("Fiches left: " + fiches.getTotalValue() + '\n');

      //distribute cards
      receiveMessage(is);
      


      String userInput = stdIn.readLine(); 
      if (userInput.equals("QUIT")) 
        break;

    }

    os.close(); 
    is.close(); 
    socket.close(); 

  } 

  public static void main (String[] args) throws Exception { 
    Player tcpClient = new Player(); 
    tcpClient.start(); 
  }

}
