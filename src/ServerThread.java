//Importo i package
import java.net.*;
import java.io.*;

//Creazione di una classe per il Multrithreading
class ServerThread extends Thread {
    private Socket socket;
    Card card;
    Dealer dealer;
    Fiches fiches;

    public ServerThread (Socket socket) {
        this.socket = socket;
        //System.out.println("State    Request type  Server port  Client port  Client adress\n");
        card = new Card();
        dealer = new Dealer();
        card.CreateStructureBunch();
        fiches = new Fiches();
    }

    //esecuzione del Thread sul Socket
    public void run() {

        try {

            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Welcome player\n");
            //System.out.print("Welcome player, please insert yosur name: ");
            System.out.print("The dealer is waiting for the other players...\n");

            Thread.sleep(3000);
            
                    
            while(true){

                int verify = 0;
          
                System.out.print("The game is starting\n\n");
          
                Thread.sleep(1000);                
                
                System.out.println("The dealer is distributing the cards\n");
          
                Thread.sleep(1000);
          
                //get the start two cards
                String card1 = dealer.Distribute(card.bunchs);
                String card2 = dealer.Distribute(card.bunchs);
            
                int valueCard1 = card.calculateValueCard(card1);
                int valueCard2 = card.calculateValueCard(card2);
            
                int totalValue = valueCard1 + valueCard2;
            
                // Costruire il messaggio per il giocatore
                String playerMessage = card1 + "\n" + card2 + "\nTotal value of your cards: " + totalValue + "\n";

                os.writeBytes("INITIAL_CARDS\n");
                os.writeBytes(playerMessage);
          
                System.out.println("Your cards:");
                Thread.sleep(1000);
                System.out.println(card1);
                Thread.sleep(1000);
                System.out.println(card2);
                os.writeBytes("Cards of Player: \n");
                os.writeBytes(card1 + "\n");
                os.writeBytes(card2 + "\n");
                System.out.print("\nTotal value of your cards: " + totalValue + "\n");
                os.writeBytes("Total value: " + totalValue + '\n');
          
          
                while (true) 
                { 
          
                  //System.out.print("The dealer is waiting for your move\n");
                  
                  //System.out.print("Your cards:   " + dealer.Distribute(card.bunchs) + "   " + dealer.Distribute(card.bunchs) + "\n");
          
          
          
                  if ((valueCard1 == 11 && valueCard2 == 10) || (valueCard1 == 10 && valueCard2 == 11)) {
                    System.out.println("Blackjack! Your total value is 21.");
                    os.writeBytes("Blackjack!\n");
                    verify = -1;
                    break;
                  }
          
                  System.out.print("What do you want to do? (hit/stand): ");
                  String response = stdIn.readLine();
                  os.writeBytes(response + '\n');  
              
                  // fai anche tutti gli altri casi, ad esempio se le due carte sono uguali, si può sdoppiare; ecc...
                  if (response.equalsIgnoreCase("hit")) {
                    String newCard = dealer.Distribute(card.bunchs);
                    System.out.println("\nNew card:");
                    System.out.println(newCard + "\n");
                    os.writeBytes("New card of Player: \n");
          
                    // metti tutte le carte del player in un array, in modo che ogni volta che gli viene data una nuova carta, metti a video tutte le sue carte
          
                    os.writeBytes(newCard + "\n");
                    totalValue += card.calculateValueCard(newCard);
                    System.out.println("New total value of your cards: " + totalValue);
                    os.writeBytes("New total value of Player:" + totalValue + "\n");
          
                    if (totalValue > 21) {
                      System.out.println("Busted! Your total value is " + totalValue + ", over 21.");
                      os.writeBytes("Busted with " + totalValue + "\n");
                      verify = -1;
                      break;
                    }
          
                  }else if (response.equalsIgnoreCase("stand")) {
          
                    System.out.println("You chose to stand. Your final total value: " + totalValue);
                    os.writeBytes("Stand with " + totalValue + '\n');
                    verify = -1;
                    break;
                    
                  } else {
                    System.out.println("Invalid response. Please enter 'hit' or 'stand'.");
                  }
          
          
          
                  /*
                  System.out.print("Insert: "); 
                  String userInput = stdIn.readLine(); 
                  if (userInput.equals("QUIT")) 
                    break; 
                  os.writeBytes(userInput + '\n');  
                  System.out.println("Hai digitato: " + is.readLine()); 
                  */
                  
                } 
          
                Thread.sleep(1000); // to gave a delay for Dealer to receive the answer of the player
          
                if (verify == -1)
                {
                  System.out.println("Do you want to play another hand? (yes/QUIT)");
                  String response = stdIn.readLine();
                  os.writeBytes(response + '\n');
                  if (response.equalsIgnoreCase("QUIT")) {
                    System.out.println("You have disconnected from the game\n");
                    os.writeBytes("QUIT\n");
                    //Chiusura dello Stream e del Socket
                    os.close(); 
                    is.close(); 
                    socket.close();
                    break;
                  }
                }
              }

            /*
            while(true) {
                String userInput = is.readLine();
                if (userInput == null || userInput.equals("QUIT"))
                    break;
                os.writeBytes(userInput + '\n');
                System.out.println("Player "+ socket.getInetAddress() +" "
                + socket.getPort() +" "
                + socket.getLocalPort() +" wrote: " + userInput);
            }
            */

            os.close();
            is.close();
            System.out.println("Receiving a closing call from:\n" + socket + "\n");
            socket.close();
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //public String handlePlayerInput(String userInput){


    //}
}
