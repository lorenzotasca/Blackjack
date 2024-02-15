import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Dealer {

    Card card;

    public Dealer(){
        
        card = new Card();
        card.CreateStructureBunch();
        //card.CreateDesk();
        //card.ValueCard();
        //for (int i = 0; i < 20; i++)
        Distribute(card.bunchs);

    }


    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(7777);
    
        //Ciclo infinito di ascolto dei Client
        //fai uscire le carte anche al dealer
        while(true)
        {
          System.out.println(" Waiting... ");
          Socket socket = serverSocket.accept();
          System.out.println("Receiving an opening call from:\n" + socket);
          //avvia il processo per ogni client 
          provaServerThread serverThread = new provaServerThread(socket, this);
          //ServerThread serverThread = new ServerThread(socket);
          serverThread.start();
        }
    
    }


    public String Distribute(HashMap<String, ArrayList<Integer>> bunch) {

        Random random = new Random();
    
        // Get a random key from the hashmap
        Object[] keys = bunch.keySet().toArray();
        String randomKey = (String) keys[random.nextInt(keys.length)];
    
        // Get the ArrayList associated with the random key
        ArrayList<Integer> positions = bunch.get(randomKey);
    
        String theCard = "";

        // Check if the ArrayList is not empty
        if (!positions.isEmpty()) {
                
            // Get a random position from the ArrayList
            int randomPositionIndex = random.nextInt(positions.size());
            int randomPosition = positions.get(randomPositionIndex);
    
            // Print the results
            /*
            System.out.println("Distributed card from bunch: " + randomKey +
            ", at position index: " + randomPositionIndex + ", with value: " + randomPosition);
            */
            
            // Subtract 1 from the corresponding value in the ArrayList
            positions.set(randomPositionIndex, positions.get(randomPositionIndex) - 1);
    
            // print the value and seed of the card distributed
            theCard = (randomKey + " " + card.seeds[randomPositionIndex]);
            //System.out.println(theCard + "\n");
            
            // Update the hashmap
            bunch.put(randomKey, positions);
    
            // Print the updated hashmap
            //System.out.println("Updated hashmap: " + bunch + "\n");

        } else {
            System.out.println("Bunch is empty for key: " + randomKey);
        }
        
        return theCard;
    }

    public static void main (String[] args) throws Exception {
        Dealer tcpServer = new Dealer();
        tcpServer.start();
    }


}
