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
    return is.readLine();
  }

  public void start()throws Exception { 
    
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    
    receiveMessage(is);
    receiveMessage(is);
    

  } 

  public static void main (String[] args) throws Exception { 
    Player tcpClient = new Player(); 
    tcpClient.start(); 
  }

}
