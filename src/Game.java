public class Game {
    
    
    public static void main(String[] args) {

        // Crea un oggetto Dealer (server)
        Dealer dealer = new Dealer();

        // Avvia la partita nel dealer
        Thread dealerThread = new Thread(() -> {
            dealer.startGame();
        });
        dealerThread.start();

    }

    public void Start(){
        // inizializza il gioco
        // crea i mazzi
        // crea i giocatori
        // crea il dealer
        // crea il tavolo
        // crea le carte
        // crea i bottoni
        // crea le puntate
        // crea le scommesse
        // crea le vincite
        // crea le perdite
        // crea le statistiche
        // crea le regole
        // crea le istruzioni
        // crea le opzioni
        // crea le impostazioni
    }
    

}
