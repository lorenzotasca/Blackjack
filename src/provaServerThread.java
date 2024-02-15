import java.io.*;
import java.net.Socket;

public class provaServerThread extends Thread {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private provaDealer dealer;

    public provaServerThread(Socket socket, provaDealer dealer) throws IOException {
        this.socket = socket;
        this.dealer = dealer;
        this.is = new DataInputStream(socket.getInputStream());
        this.os = new DataOutputStream(socket.getOutputStream());
    }

    private void sendMessage(String message) throws IOException {
        os.writeBytes(message + '\n');
    }

    private String receiveMessage() throws IOException {
        return is.readLine();
    }

    private void handlePlayerAction() throws IOException {
        // Ricevere l'azione del giocatore e inviarla al server
        String playerAction = receiveMessage();
        // Invia l'azione al dealer per gestirla
        dealer.handlePlayerAction(playerAction);
    }

    private void handleGameResponse() throws IOException {
        // Ricevere la risposta del server e inviarla al giocatore
        String serverResponse = dealer.getGameResponse();
        sendMessage(serverResponse);
    }

    @Override
    public void run() {
        try {
            // Gestire le azioni del giocatore
            handlePlayerAction();
            handleGameResponse();
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