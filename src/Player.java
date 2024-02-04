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
    System.out.print("Waiting for more players\n");
    System.out.print("The game is starting\n");
    

    //Ciclo infinito per inserimento testo del Client 
    while (true) 
    { 
      //System.out.print("Fai la puntata\n");
      System.out.print("The dealer is distributing the cards\n");
      //System.out.print("The dealer is waiting for your move\n");
      //System.out.print("The dealer is waiting for the other players\n");
      
      System.out.print("Your cards:" + dealer.Distribute(card.bunchs) + " " + dealer.Distribute(card.bunchs) + "\n");

      System.out.print("Insert: "); 
      String userInput = stdIn.readLine(); 
      if (userInput.equals("QUIT")) 
      break; 
      os.writeBytes(userInput + '\n');  
      System.out.println("Hai digitato: " + is.readLine()); 
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
