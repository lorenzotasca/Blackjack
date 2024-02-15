import java.io.*;
import java.net.Socket;

public class provaPlayer {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private BufferedReader stdIn;

    public provaPlayer() throws IOException {
        socket = new Socket("localhost", 7777);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    private void sendMessage(String message) throws IOException {
        os.writeBytes(message + '\n');
    }

    private String receiveMessage() throws IOException {
        return is.readLine();
    }

    private void handlePlayerAction() throws IOException {
        // Ricevere l'azione del giocatore dall'input utente e inviarla al server
        System.out.print("Enter your action (hit/stand/double/split/surrender): ");
        String playerAction = stdIn.readLine();
        sendMessage(playerAction);
    }

    private void handleGameResponse() throws IOException {
        // Ricevere e gestire la risposta del server
        String serverResponse = receiveMessage();
        System.out.println(serverResponse);
    }

    public void start() throws IOException {
        // Gestire le azioni del giocatore
        handlePlayerAction();
        handleGameResponse();
    }

    public static void main(String[] args) throws IOException {
        provaPlayer player = new provaPlayer();
        player.start();
    }
}