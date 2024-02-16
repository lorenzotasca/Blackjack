import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class provaDealer {
    private Card card;
    private Fiches fiches;
    private ServerSocket serverSocket;

    public provaDealer(){
        card = new Card();
        card.CreateStructureBunch();
        fiches = new Fiches();

        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start() {
        while (true) {
            try {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                provaServerThread serverThread = new provaServerThread(clientSocket, this);
                serverThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void handleBet(String betMessage, DataOutputStream os) throws IOException {
        // Gestione della scommessa e invio conferma al giocatore
        os.writeBytes("Received bet: " + betMessage + '\n');
    }

    public synchronized void handleGameStart(DataOutputStream os) throws IOException {
        // Inizio del gioco e invio notifica al giocatore
        os.writeBytes("Game started\n");
    }

    public synchronized void handleCardDistribution(Card card, DataOutputStream os) throws IOException {
        // Distribuzione delle carte e invio al giocatore
        String card1 = this.Distribute(card.bunchs);
        String card2 = this.Distribute(card.bunchs);
        os.writeBytes("Cards distributed: " + card1 + ", " + card2 + '\n');
    }

    public synchronized void handlePlayerAction(String playerAction, DataOutputStream os) throws IOException {
        // Gestione dell'azione del giocatore e invio al server
        // Questo potrebbe coinvolgere la logica del gioco, ad esempio verificare se l'azione è valida, ecc.
    }

    public synchronized void handleGameResponse(String playerResponse, DataOutputStream os) throws IOException {
        // Gestione della risposta del giocatore e invio al server
        // Anche qui potrebbe coinvolgere la logica del gioco, come calcolare il risultato della mano, ecc.
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

    public static void main(String[] args) throws Exception {
        provaDealer dealer = new provaDealer();
        dealer.start();
    }

}