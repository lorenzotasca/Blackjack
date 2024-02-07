import java.util.*;

public class Fiches {
            
    HashMap<Integer, Integer> fiches; //value of fiches and the quantity of fiches

    public Fiches() {
        this.fiches = new HashMap<>();
        // Inizializza il numero fisso di fiches per ogni valore
        this.fiches.put(100, 10); // Fiches da 100 con 10 pezzi iniziali
        this.fiches.put(50, 20); // Fiches da 50 con 20 pezzi iniziali
        this.fiches.put(20, 50); // Fiches da 20 con 50 pezzi iniziali
        this.fiches.put(10, 100); // Fiches da 10 con 100 pezzi iniziali
        this.fiches.put(5, 100); // Fiches da 5 con 100 pezzi iniziali
    }


    /*
        quando si chiama il metodo getFiches(int value), si passa il valore nominale delle fiches di cui 
        si desidera conoscere la quantità attuale. In pratica, se si vuole sapere quanti pezzi da 5 hai, 
        si chiama getFiches(5); se si desidera aggiungere 10 pezzi da 10, si chiama addFiches(10, 10); 
        e se si desidera rimuovere 5 pezzi da 20, si chiama removeFiches(20, 5).
    */

    // Metod to get the number of fisches of a value
    public int getFiches(int value) {
        //return fiches.getOrDefault(value, 0);
        return fiches.get(value);
    }

    // Metod to add fiches of a value
    public void addFiches(int value, int quantity) {
        int currentQuantity = fiches.getOrDefault(value, 0);
        fiches.put(value, currentQuantity + quantity);
    }

    // Metod to remove fiches of a valore
    public void removeFiches(int value, int quantity) {
        int currentQuantity = fiches.getOrDefault(value, 0);
        if (currentQuantity >= quantity) {
            fiches.put(value, currentQuantity - quantity);
        } else {
            System.out.println("You don't have enough fisches of " + value + " to remove frome it " + quantity);
        }
    }

    /*
    public void removeFiches(int value){
        Iterator<Integer> iterator = fiches.keySet().iterator();
        while(iterator.hasNext()){
            int key = iterator.next();
            if(value - key >= 0){
                value -= key;
                fiches.put(key, fiches.get(value) - 1);

                // se il numero di fiches di un valore diventa 0, non si può togliere da li
                
                //if(fiches.get(value) == 0){
                //   
                //}
                

                //iterator.remove();
            }
        }

    }
    */

    public int size() {
        return fiches.size();
    }

    // Metod to get the total value of the fiches of Player
    public int getTotalValue() {
        int totalValue = 0;
        for (int value : fiches.keySet()) {
            totalValue += value * fiches.get(value);
        }
        return totalValue;
    }
}