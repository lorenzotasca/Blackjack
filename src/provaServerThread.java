import java.io.*;
import java.net.Socket;

public class provaServerThread extends Thread {
    private Socket socket;
    Dealer dealer;
    Card card;
    Fiches fiches;
    DataInputStream is = new DataInputStream(socket.getInputStream());
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());

    public provaServerThread(Socket socket) throws IOException {
        this.socket = socket;
        dealer = new Dealer();
        
        card = new Card();
        fiches = new Fiches();
        card.CreateStructureBunch();
    }

    private void sendMessage(String message) throws IOException {
        os.writeBytes(message + '\n');
    }

    private String receiveMessage() throws IOException {
        return is.readLine();
    }

    private void handleBet() throws IOException {
        // Ricevere la scommessa dal giocatore
        String betMessage = receiveMessage();
        // Gestire la scommessa e inviare conferma al giocatore
        sendMessage("Received bet: " + betMessage);
    }

    private void handleGameStart() throws IOException {
        // Iniziare il gioco e inviare notifica al giocatore
        sendMessage("Game started");
    }

    private void handleCardDistribution() throws IOException {
        // Distribuire le carte e inviarle al giocatore
        String card1 = dealer.Distribute(card.bunchs);
        String card2 = dealer.Distribute(card.bunchs);
        sendMessage("Cards distributed: " + card1 + ", " + card2);
    }

    private void handlePlayerAction() throws IOException {
        // Ricevere l'azione del giocatore e inviarla al server
        String playerAction = receiveMessage();
        // Gestire l'azione del giocatore
        // Invia eventuali risposte al giocatore
    }

    private void handleGameResponse() throws IOException {
        // Ricevere la risposta del giocatore e inviarla al server
        String playerResponse = receiveMessage();
        // Gestire la risposta del giocatore
        // Invia eventuali risposte al giocatore
    }

    @Override
    public void run() {
        try {
            
            // Gestire le azioni del giocatore
            handleBet();
            handleGameStart();
            handleCardDistribution();
            while (true) {
                handlePlayerAction();
                handleGameResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}