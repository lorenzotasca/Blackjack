import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{

  private Socket socket;
  Card card;
  Dealer dealer;
  Fiches fiches;

  public ServerThread (Socket socket) {
    this.socket = socket;
    //System.out.println("State    Request type  Server port  Client port  Client adress\n");
    card = new Card();
    dealer = new Dealer();
    card.CreateStructureBunch();
    fiches = new Fiches();
  }

  private String receiveMessage(DataInputStream is) throws IOException {
    String message = is.readLine();
    System.out.println(message);
    return message;
  }

  //esecuzione del Thread sul Socket
  public void run(){

    try{

      DataInputStream is = new DataInputStream(socket.getInputStream());
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());
      //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

      os.writeBytes("Welcome player\n");
      //System.out.print("Welcome player, please insert yosur name: ");
      os.writeBytes("The dealer is waiting for the other players...\n");

      Thread.sleep(3000);

      while(true){

        int verify = 0;
  
        os.writeBytes("The game is starting\n");
  
        Thread.sleep(1000);                
        
        os.writeBytes("The dealer is distributing the cards\n");
  
        Thread.sleep(1000);
  
        //get the start two cards
        String card1 = dealer.Distribute(card.bunchs);
        String card2 = dealer.Distribute(card.bunchs);
    
        int valueCard1 = card.calculateValueCard(card1);
        int valueCard2 = card.calculateValueCard(card2);
    
        int totalValue = valueCard1 + valueCard2;

        os.writeBytes("\nYour cards: \n" + card1 + "\n" + card2 + "\n");

        receiveMessage(is); // bet

        System.out.println("Cards: \n" + card1 + "\n" + card2 + "\n");

        os.writeBytes("\nTotal value of your cards: " + totalValue + "\n");
        System.out.print("Total value: " + totalValue + '\n');


        while (true) 
      { 

        //System.out.print("The dealer is waiting for your move\n");
        
        //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");


        if ((valueCard1 == 11 && valueCard2 == 10) || (valueCard1 == 10 && valueCard2 == 11)) {
          os.writeBytes("Blackjack! Your total value is 21. \n");
          System.out.println("Blackjack!\n");
          verify = -1;
          break;
        }

        os.writeBytes("What do you want to do? (hit/stand): \n");
        //String response = stdIn.readLine();
        //os.writeBytes(response + '\n');  


        String clientResponse = receiveMessage(is);
        // fai anche tutti gli altri casi, ad esempio se le due carte sono uguali, si può sdoppiare; ecc...
        if (clientResponse.equals("hit")) {
          String newCard = dealer.Distribute(card.bunchs);
          os.writeBytes("Hit\n");
          os.writeBytes("\nNew card: \n");
          os.writeBytes(newCard + "\n");
          System.out.println("New card of Player: \n");

          // metti tutte le carte del player in un array, in modo che ogni volta che gli viene data una nuova carta, metti a video tutte le sue carte

          System.out.println(newCard + "\n");
          totalValue += card.calculateValueCard(newCard);
          os.writeBytes("New total value of your cards: " + totalValue + "\n");
          System.out.println("New total value of Player:" + totalValue + "\n");

          if (totalValue > 21) {
            os.writeBytes("Busted! Your total value is " + totalValue + ", over 21. \n");
            System.out.println("Busted with " + totalValue + "\n");
            verify = -1;
            break;
          }

        }else if (clientResponse.equals("stand")) {

          os.writeBytes("STAND\n");
          os.writeBytes("You chose to stand. Your final total value: " + totalValue + '\n');
          System.out.println("Stand with " + totalValue + '\n');
          verify = -1;
          break;
          
        } else {
          os.writeBytes("Invalid response. Please enter 'hit' or 'stand'. \n");
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



        String userInput = is.readLine();
        if (userInput.equals("QUIT"))
          break;
        os.writeBytes(userInput + '\n');

      }
      os.close(); 
      is.close(); 
      socket.close(); 

    }catch(Exception e){
      e.printStackTrace();
    }

  }
  
}