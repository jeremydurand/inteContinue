package Game.Events.implementations;

import Game.Events.Event;
import Game.GameManager;
import Game.UI.UIManager;

public class Encounter implements Event{
    @Override
    public void trigger() {
        GameManager gameManager = GameManager.getInstance();
        UIManager uiManager = UIManager.getInstance();
        Integer choiceEncounter = UIManager.getChoiceEncounter();
        if (choiceEncounter == 1){ //CONVERSER
            GameManager diceResult = GameManager.getDiceResult();
            if (diceResult == 1){
                //day = day+1;
            }
            if (diceResult == 2){
                //day = day+1;
            }
            if (diceResult == 3){
                //attack
            }
            if (diceResult == 4){
                //attack
            }
            if (diceResult == 5){
                //richesse-10
            }
            if (diceResult == 6){
                //richesse-10
            }
        }
        if (choiceEncounter == 2){ //ELUDER
            GameManager diceResult = GameManager.getDiceResult();
            if (diceResult == 1){
                //Evasion (dé 4 ou +) + dépacement ailleur
            }
            if (diceResult == 2){
                //Evasion (dé 4 ou +) + déplacement ailleur
            }
            if (diceResult == 3){
                //Cache (dé 3 ou +)
            }
            if (diceResult == 4){
                //passer
            }
            if (diceResult == 5){
                //richesse-10
            }
            if (diceResult == 6){
                //richesse-20
            }
        }
        if (choiceEncounter == 3){ //COMBAT
            GameManager diceResult = GameManager.getDiceResult();
            if (diceResult == 1){
                //attaque surprise
            }
            if (diceResult == 2){
                //attaque surprise
            }
            if (diceResult == 3){
                //attaque surprise
            }
            if (diceResult == 4){
                //attaque
            }
            if (diceResult == 5){
                //attaque
            }
            if (diceResult == 6){
                //attaque
            }
        }
        //day = day+1;
    }
}
