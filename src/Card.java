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

        switch (card.toLowerCase()) {
            case "asso":
                return 11; // o 1 a seconda della situazione
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
            case "jack":
            case "donna":
            case "re":
                return 10;
            default:
                return 0;
        }
    }
    
    

}
