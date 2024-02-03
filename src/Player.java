import java.io.*;
import java.net.Socket;

public class Player {
    // risoste del player al dealer (hit, stand, double, split, surrender)

    public void start()throws IOException { 
      //Connessione della Socket con il Server 
      Socket socket = new Socket("localhost", 7777); 
  
      //Stream di byte da passare al Socket 
      DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
      DataInputStream is = new DataInputStream(socket.getInputStream()); 
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
      System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 
  
      //Ciclo infinito per inserimento testo del Client 
      while (true) 
      { 
        System.out.print("Inserisci: "); 
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


    // controlla bene come implementarla al meglio
    /*
    public void start()throws IOException { 
    //Connessione della Socket con il Server 
    Socket socket = new Socket("localhost", 7777); 

    //Stream di byte da passare al Socket 
    DataOutputStream os = new DataOutputStream(socket.getOutputStream()); 
    DataInputStream is = new DataInputStream(socket.getInputStream()); 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
    System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

    //Ciclo infinito per inserimento testo del Client 
    while (true) 
    { 
      System.out.print("Inserisci: "); 
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
  */
}
