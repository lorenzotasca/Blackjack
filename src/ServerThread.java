import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

  //esecuzione del Thread sul Socket
  public void run(){

    try{

      DataInputStream is = new DataInputStream(socket.getInputStream());
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

      os.writeBytes("Welcome player\n");
      //System.out.print("Welcome player, please insert yosur name: ");
      os.writeBytes("The dealer is waiting for the other players...\n");

      Thread.sleep(3000);

    }catch(Exception e){
      e.printStackTrace();
    }

  }
  
}