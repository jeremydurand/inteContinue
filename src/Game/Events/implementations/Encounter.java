package Game.Events.implementations;

import Game.Events.Event;
import Game.Rules.AttackRule;
import Game.GameManager;
import Game.UI.UIManager;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Game.Character;

public class Encounter implements Event {
	@Override
	public void trigger(Character character, int choiceEncounter) {
		UIManager uiManager = UIManager.getInstance();
		// CONVERSER
		if (choiceEncounter == 1) {
			int diceResult = GameManager.getInstance().getDiceResult();
			if (diceResult == 1) {
				GameManager.getInstance().dayIncrement();
			}
			if (diceResult == 2) {
				GameManager.getInstance().dayIncrement();
			}
			if (diceResult == 3) {
				attack();
			}
			if (diceResult == 4) {
				attack();
			}
			if (diceResult == 5) {
				character.setGold(character.getGold() - 10);
			}
			if (diceResult == 6) {
				character.setGold(character.getGold() - 20);
			}
		}
		// ELUDER
		if (choiceEncounter == 2) {
			int diceResult = GameManager.getInstance().getDiceResult();
			if (diceResult == 1) {
				if (diceResult >= 4) {
					GameManager.getInstance().moveToNeighbour();
					GameManager.getInstance().dayIncrement();
				} else {
					attack();
				}
			}
			if (diceResult == 2) {
				if (diceResult >= 4) {
					GameManager.getInstance().moveToNeighbour();
					GameManager.getInstance().dayIncrement();
				} else {
					attack();
				}
			}
			if (diceResult == 3) {
				if (diceResult >= 3) {
					GameManager.getInstance().dayIncrement();
				} else {
					attack();
				}
			}
			if (diceResult == 4) {
				if (diceResult >= 4) {
					// rien ne se passe
				} else {
					attack();
				}
			}
			if (diceResult == 5) {
				character.setGold(character.getGold() - 10);
			}
			if (diceResult == 6) {
				character.setGold(character.getGold() - 20);
			}
		}
		// COMBAT
		if (choiceEncounter == 3) {
			int diceResult = GameManager.getInstance().getDiceResult();
			if (diceResult == 1) {
				// GameManager.getInstance().setSurpriseAttack(true);
				attack();
			}
			if (diceResult == 2) {
				// GameManager.getInstance().setSurpriseAttack(true);
				attack();
			}
			if (diceResult == 3) {
				// GameManager.getInstance().setSurpriseAttack(true);
				attack();
			}
			if (diceResult == 4) {
				attack();
			}
			if (diceResult == 5) {
				attack();
			}
			if (diceResult == 6) {
				attack();
			}
		}
	}

	public void attack() {
		ArrayList<Integer> degats = new AttackRule().Attack(GameManager.getInstance().isSurpriseAttack());
		String name = "";
		String combatInfo = "";
		if (GameManager.getInstance().isPrinceTurn()) {
			combatInfo = "Vous avez infligé " + degats.get(0) + " points de dégats à "
					+ GameManager.getInstance().getMeet().getNom();
			name = "\nLancez les dés pour le tour de " + GameManager.getInstance().getMeet().getNom();
		} else {
			combatInfo = GameManager.getInstance().getMeet().getNom() + " vous a infligé " + degats.get(0)
					+ " points de dégats";
			name = "\nLancez les dés pour votre tour";
		}
		if (GameManager.getInstance().getPrince().getLife() > 0 && GameManager.getInstance().getMeet().getLife() > 0) {
			GameManager.getInstance().setStep(3);
			GameManager.getInstance().setPrinceTurn(!GameManager.getInstance().isPrinceTurn());

			String typeAttaque = "";
			if (GameManager.getInstance().isSurpriseAttack()) {
				typeAttaque = "Attaque surprise";
			} else {
				typeAttaque = "Attaque standard";
			}

			JOptionPane.showMessageDialog(GameManager.getInstance().getBoard(), combatInfo + name,
					"Lancez les dés - " + typeAttaque, JOptionPane.INFORMATION_MESSAGE,
					GameManager.getInstance().getBoard().getIcon());
		} else {
			if (GameManager.getInstance().getPrince().getLife() <= 0) {
				JOptionPane.showMessageDialog(GameManager.getInstance().getBoard(),
						combatInfo + "\nC'est perdu ! " + GameManager.getInstance().getMeet().getNom() + " vous a tué",
						"Vous êtes mort", JOptionPane.INFORMATION_MESSAGE,
						GameManager.getInstance().getBoard().getIcon());
				GameManager.getInstance().end();
			} else {
				JOptionPane.showMessageDialog(GameManager.getInstance().getBoard(),
						combatInfo + "\nBravo vous avez gagné contre " + GameManager.getInstance().getMeet().getNom(),
						"Combat remborté", JOptionPane.INFORMATION_MESSAGE,
						GameManager.getInstance().getBoard().getIcon());
				GameManager.getInstance().setCanMove(true);
				GameManager.getInstance().resetOpponentStats();
				JOptionPane.showMessageDialog(GameManager.getInstance().getBoard(),
						"Vous avez survécu au jour " + GameManager.getInstance().getDay()
								+ " déplacez votre personnage pour la prochaine rencontre",
						"Jour " + GameManager.getInstance().getDay(), JOptionPane.INFORMATION_MESSAGE,
						GameManager.getInstance().getBoard().getIcon());
				GameManager.getInstance().dayIncrement();
				GameManager.getInstance().setSurpriseAttack(false);
				GameManager.getInstance().setStep(1);
			}
			GameManager.getInstance().getBoard().getBtnLancer().setEnabled(false);
		}
	}
}
