import java.util.*;

public class Card {
    
    String[] gameCards = {"asso", "2", "3" ,"4", "5", "6", "7", "8", "9", "10", "jack", "donna", "re"};
    String[] seeds = new String[4];

    //String[] cards = new String[gameCards.length * seeds.length]; // until I don't put the images, I'll use this array

    HashMap<String, ArrayList<Integer>> bunchs;
    //HashMap<String, ArrayList<String>> deskName;

    public Card(){
        bunchs = new HashMap<String, ArrayList<Integer>>();
        //deskName = new HashMap<String, ArrayList<String>>();
        seeds = new String[]{"cuori", "quadri", "fiori", "picche"};
    }

    public HashMap<String, ArrayList<Integer>> CreateStructureBunch(){
        //create the 6 bunches

        for (int i= 0; i < gameCards.length; i++){ 
            // in 6 bunches, there are 6 cards for each gameCard with the same seed
            bunchs.put(gameCards[i], new ArrayList<Integer>(Arrays.asList(6, 6, 6, 6))); // cuori, quadri, fiori, picche
            //System.out.println("Key: " + gameCards[i] + ", Value: " + bunchs.get(gameCards[i])); // print the key and its value
            
        }
        return bunchs;
    }

    public int calculateValueCard(String card) {
        return getValue(card);
    }

    private int getValue(String card) {

        if(card.contains("asso"))
            return 11; // o 1 a seconda della situazione
        else if(card.contains("2"))
            return 2;
        else if(card.contains("3"))
            return 3;
        else if(card.contains("4"))
            return 4;
        else if(card.contains("5"))
            return 5;
        else if(card.contains("6"))
            return 6;
        else if(card.contains("7"))
            return 7;
        else if(card.contains("8"))
            return 8;
        else if(card.contains("9"))
            return 9;
        else if(card.contains("10") || card.contains("jack") || card.contains("donna") || card.contains("re"))
            return 10;
        else
            return 0;
    }
    
    

}
