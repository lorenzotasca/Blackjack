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
        Distribute(card.bunchs);

    }


    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(7777);
    
        //Ciclo infinito di ascolto dei Client
        while(true)
        {
          System.out.println(" Attesa ");
          Socket socket = serverSocket.accept();
          System.out.println("Ricezione una chiamata di apertura da:\n" + socket);
          //avvia il processo per ogni client 
          ServerThread serverThread = new ServerThread(socket);
          serverThread.start();
        }
    }


    public void Distribute(HashMap<String, ArrayList<Integer>> bunch) {

        boolean continueLoop = true;
        int cardDistributed = 0;

        // i primi 2 giri di carte li distribuisce subito a tutti i giocatori e al dealer(la seconda del dealer la tiene coperta)
        // poi, in base alla risposta del player, continua il gioco
        do{

            Random random = new Random();
        
            // Get a random key from the hashmap
            Object[] keys = bunch.keySet().toArray();
            String randomKey = (String) keys[random.nextInt(keys.length)];


            // Get the ArrayList associated with the random key
            ArrayList<Integer> positions = bunch.get(randomKey);

            
            // Check if the ArrayList is not empty
            if (!positions.isEmpty()) {
                    
                System.out.println((cardDistributed + 1) + " ciclo");

                // Get a random position from the ArrayList
                int randomPositionIndex = random.nextInt(positions.size());
                int randomPosition = positions.get(randomPositionIndex);


                // Print the results
                System.out.println("Distributed card from bunch: " + randomKey +
                ", at position index: " + randomPositionIndex + ", with value: " + randomPosition);
                
                // Subtract 1 from the corresponding value in the ArrayList
                positions.set(randomPositionIndex, positions.get(randomPositionIndex) - 1);

                // print the value and seed of the card distributed
                System.out.println(randomKey + " " + card.seeds[randomPositionIndex] + "\n");
                
                // Update the hashmap
                bunch.put(randomKey, positions);

                // Print the updated hashmap
                System.out.println("Updated hashmap: " + bunch + "\n");

                cardDistributed++;

                if (cardDistributed < 16) {
                    continue;
                }
                else {
                    continueLoop = false; // per prova
                    // in base alla risposta del player
                }
                System.out.println("\n");
                

            } else {
                System.out.println("Bunch is empty for key: " + randomKey);
            }

        }while(continueLoop);

        
    }

    public static void main (String[] args) throws Exception {
        Dealer tcpServer = new Dealer();
        tcpServer.start();
    }


}
