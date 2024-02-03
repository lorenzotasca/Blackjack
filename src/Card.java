import java.util.*;

public class Card {
    
    String[] gameCards = {"asso", "2", "3" ,"4", "5", "6", "7", "8", "9", "10", "jack", "donna", "re"};
    String[] seeds = new String[4];

    String[] cards = new String[gameCards.length * seeds.length]; // until I don't put the images, I'll use this array

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
            System.out.println("Key: " + gameCards[i] + ", Value: " + bunchs.get(gameCards[i])); // print the key and its value
            
        }
        return bunchs;
    }

    /*
    public HashMap<String, ArrayList<String>> CreateDesk(){

        for (int i= 0; i < gameCards.length; i++){ 
            
            deskName.put(gameCards[i], new ArrayList<String>(Arrays.asList("cuori", "quadri", "fiori", "picche"))); // cuori, quadri, fiori, picche
            System.out.println("Key: " + gameCards[i] + ", Value: " + deskName.get(gameCards[i])); // print the key and its value
            
        }
        return deskName;
    }
    */

    //rifai con hashmap
    /*
    public String[] CreateDesk(){

        int count = 0;
        for (int i = 0; i < seeds.length; i++){
            for (int j = 0; j < gameCards.length; j++){
                
                cards[count] = gameCards[j] + " " + seeds[i];
                System.out.println(cards[count]);
                count++;
                
            }
        }
        return cards;
        
    }
    */

    public void ValueCard(){

        int[] value = new int[cards.length];

        for (int i = 0; i < cards.length; i++){
            if (cards[i].contains("asso")){
                value[i] = 1; // or 11
            } else if (cards[i].contains("2")){
                value[i] = 2;
            } else if (cards[i].contains("3")){
                value[i] = 3;
            } else if (cards[i].contains("4")){
                value[i] = 4;
            } else if (cards[i].contains("5")){
                value[i] = 5;
            } else if (cards[i].contains("6")){
                value[i] = 6;
            } else if (cards[i].contains("7")){
                value[i] = 7;
            } else if (cards[i].contains("8")){
                value[i] = 8;
            } else if (cards[i].contains("9")){
                value[i] = 9;
            } else if (cards[i].contains("10") || cards[i].contains("jack") || cards[i].contains("donna") || cards[i].contains("re")){
                value[i] = 10;
            }
        }

    }

    

}
