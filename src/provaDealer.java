import java.util.*;

public class provaDealer {
    private Card card;
    private Fiches fiches;

    public provaDealer(){
        card = new Card();
        card.CreateStructureBunch();
        fiches = new Fiches();
    }

    public void handlePlayerAction(String playerAction) {
        // Qui gestisci l'azione del giocatore
        // Ad esempio, aggiorni lo stato del gioco, invii nuove carte, calcoli le vincite, ecc.
    }

    public String getGameResponse() {
        // Qui ottieni la risposta del server per il giocatore
        // Ad esempio, chiedi al giocatore di fare una mossa o invii informazioni sullo stato del gioco
        return "Server response"; // Sostituisci con la risposta effettiva del server
    }
}