import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Dealer {


    private ServerSocket serverSocket;
    private Socket playerSocket;
    private BufferedReader reader;
    private PrintWriter writer;



    Card card;

    public Dealer(){
        
        card = new Card();
        card.CreateStructureBunch();
        //card.CreateDesk();
        //card.ValueCard();
        Distribute(card.bunchs);

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("Il dealer è in attesa di giocatori...");

            playerSocket = serverSocket.accept();
            System.out.println("Giocatore connesso.");

            reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            writer = new PrintWriter(playerSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void startGame() {
        try {
            // Esempio di lettura del messaggio dal giocatore
            String message = reader.readLine();
            System.out.println("Messaggio dal giocatore: " + message);
            
            // Esempio di invio di una risposta al giocatore
            writer.println("Benvenuto, giocatore!");

            // Implementa il resto della logica di gioco del dealer qui
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                playerSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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


}
