import java.io.*;
import java.net.Socket;

public class provaPlayer {
    private Card card;
    private provaDealer dealer;
    private Fiches fiches;
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private BufferedReader stdIn;

    public provaPlayer() {
        card = new Card();
        dealer = new provaDealer();
        card.CreateStructureBunch();
        fiches = new Fiches();
    }

    private void sendMessage(String message) throws IOException {
        os.writeBytes(message + '\n');
    }

    private String receiveMessage() throws IOException {
        return is.readLine();
    }

    public void start() throws Exception {
        // Connessione della Socket con il Server
        Socket socket = new Socket("localhost", 7777);
    
        // Stream di byte da passare al Socket
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    
        // Introduction
        receiveMessage(); // Stampa il messaggio di benvenuto
        receiveMessage(); // Stampa il messaggio "The dealer is waiting for the other players..."
    
        while (true) {
            // Inizio del gioco
            receiveMessage(); // Stampa il messaggio "The game is starting"
    
            // Scommessa
            System.out.print("You have " + fiches.getTotalValue() + "$ divided in:\n");
            for (Integer value : fiches.fiches.keySet()) {
                System.out.println(fiches.getFiches(value) + " fiches of " + value);
            }
            // Esegui il codice per consentire al giocatore di fare una scommessa e invialo al server
    
            // Distribuzione delle carte iniziali
            receiveMessage(); // Stampa il messaggio "The dealer is distributing the cards"
            receiveMessage(); // Stampa la prima carta del giocatore
            receiveMessage(); // Stampa la seconda carta del giocatore
            // Esegui il codice per gestire la ricezione delle carte e invia eventuali azioni al server
    
            // Loop per gestire le azioni del giocatore (hit/stand/double/split/surrender)
            while (true) {
                // Ricevi un messaggio dal server che richiede una mossa da parte del giocatore
                String serverMessage = receiveMessage();
                if (serverMessage.startsWith("What do you want to do?")) {
                    // Chiedi al giocatore di fare una mossa (hit/stand/double/split/surrender)
                    System.out.print("Enter your action (hit/stand/double/split/surrender): ");
                    String playerAction = stdIn.readLine();
                    // Invia l'azione al server
                    sendMessage(playerAction);
                } else {
                    // Stampa altri messaggi ricevuti dal server (come nuove carte, totali, etc.)
                    System.out.println(serverMessage);
                }
    
                // Se il server invia un messaggio di fine partita, esci dal loop
                if (serverMessage.startsWith("Do you want to play another hand?") || serverMessage.equals("QUIT")) {
                    break;
                }
            }
    
            // Se il server invia un messaggio di fine partita, esci dal loop principale
            String finalMessage = receiveMessage();
            if (finalMessage.equals("QUIT")) {
                break;
            }
        }
    
        // Chiusura delle risorse
        os.close();
        is.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        provaPlayer player = new provaPlayer();
        player.start();
    }
}