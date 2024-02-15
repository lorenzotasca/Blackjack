import java.io.*;
import java.net.Socket;

public class provaPlayer {
    private Card card;
    private Dealer dealer;
    private Fiches fiches;
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private BufferedReader stdIn;

    public provaPlayer() {
        card = new Card();
        dealer = new Dealer();
        card.CreateStructureBunch();
        fiches = new Fiches();
    }

    private void sendMessage(String message) throws IOException {
        os.writeBytes(message + '\n');
    }

    private String receiveMessage() throws IOException {
        return is.readLine();
    }

    private void handleBet() throws IOException {
        sendMessage("You have " + fiches.getTotalValue() + "$ devided in:\n");
        for (Integer value : fiches.fiches.keySet()) {
            sendMessage(fiches.getFiches(value) + " fiches of " + value);
        }
        int totBet = 0;
        while (totBet == 0) {
            for (Integer value : fiches.fiches.keySet()) {
                boolean betPlaced = false;
                while (!betPlaced) {
                    sendMessage("How many fiches of " + value + " do you want to bet: ");
                    int bet = Integer.parseInt(stdIn.readLine());
                    if (fiches.removeFiches(value, bet)) {
                        totBet += bet * value;
                        betPlaced = true;
                    }
                }
            }
            if (totBet == 0) {
                sendMessage("You have to bet at least 1 fiche");
            }
        }
        sendMessage("Bet: " + totBet);
        sendMessage("Fiches left: " + fiches.getTotalValue());
    }

    private void handleGameStart() throws IOException {
        sendMessage("The game is starting");
    }

    private void handleCardDistribution() throws IOException {
        sendMessage("The dealer is distributing the cards");
        // Distribuire le carte e inviarle al server
    }

    private void handlePlayerAction() throws IOException {
        // Gestire l'input del giocatore e inviarlo al server
        String userInput = stdIn.readLine();
        sendMessage(userInput);
    }

    private void handleGameResponse() throws IOException {
        // Ricevere e gestire la risposta del server
        String serverResponse = receiveMessage();
        sendMessage(serverResponse);
    }

    public void start() throws Exception {
        socket = new Socket("localhost", 7777);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        // Gestire le diverse azioni del giocatore
        handleBet();
        handleGameStart();
        handleCardDistribution();
        while (true) {
            handlePlayerAction();
            handleGameResponse();

            if (receiveMessage().equals("QUIT")) 
              break;

        }

        // Chiudere le risorse quando il gioco è finito
        os.close();
        is.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        provaPlayer player = new provaPlayer();
        player.start();
    }
}