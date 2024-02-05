//Importo i package
import java.net.*;
import java.io.*;

//Creazione di una classe per il Multrithreading
class ServerThread extends Thread {
    private Socket socket;
    public ServerThread (Socket socket) {
    this.socket = socket;
    System.out.println("State    Request type  Server port  Client port  Client adress\n");
    }

    //esecuzione del Thread sul Socket
    public void run() {
    try {
        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        while(true) {
        String userInput = is.readLine();
        if (userInput == null || userInput.equals("QUIT"))
            break;
        os.writeBytes(userInput + '\n');
        System.out.println("Player "+ socket.getInetAddress() +" "
        + socket.getPort() +" "
        + socket.getLocalPort() +" wrote: " + userInput);
        }
        os.close();
        is.close();
        System.out.println("Receiving a closing call from:\n" + socket + "\n");
        socket.close();
    }
    catch (IOException e) {
        System.out.println("IOException: " + e);
    }
    }
}
