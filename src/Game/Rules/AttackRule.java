package Game.Rules;

import Game.GameManager;

import java.util.ArrayList;

import Game.Character;;

public class AttackRule {

	public ArrayList<Integer> Attack(boolean surprise) {
		ArrayList<Integer> degats = new ArrayList<>();
		Character attacker = null;
		Character target = null;
		int Attack = 0;
		int TargetInjury = 0;
		int AttackerInjury = 0;
		
		if(GameManager.getInstance().isPrinceTurn()) {
			attacker = GameManager.getInstance().getPrince();
			target = GameManager.getInstance().getMeet();
		}else {
			attacker = GameManager.getInstance().getMeet();
			target = GameManager.getInstance().getPrince();
		}
		
		int AttackerAttack = attacker.getAttack();
		int TargetAttack = target.getAttack();
		int AttackerEndurance = attacker.getLife();
		int TargetEndurance = target.getLife();

		Attack = AttackerAttack - TargetAttack + GameManager.getInstance().getDiceResult();
		if (Attack > 10) {
			TargetInjury = TargetInjury + 8;
		} else if (Attack > 6 && Attack < 8) {
			TargetInjury = TargetInjury + 4;
		} else if (Attack > 2 && Attack < 6) {
			TargetInjury = TargetInjury + 2;
		} else if (Attack < 6) {
			TargetInjury = TargetInjury + 1;
		}

		TargetEndurance = TargetEndurance - TargetInjury;
		
		degats.add(TargetInjury);
		
		if(GameManager.getInstance().isPrinceTurn()) {
			GameManager.getInstance().setOpponentLife(TargetEndurance);
		}else {
			GameManager.getInstance().setPrinceLife(TargetEndurance);
		}

		if (!surprise) {
			Attack = AttackerAttack - TargetAttack + GameManager.getInstance().getDiceResult() - TargetInjury;
			if (Attack > 10) {
				AttackerInjury = AttackerInjury + 8;
			} else if (Attack > 6 && Attack < 8) {
				AttackerInjury = AttackerInjury + 4;
			} else if (Attack > 2 && Attack < 6) {
				AttackerInjury = AttackerInjury + 2;
			} else if (Attack < 6) {
				AttackerInjury = AttackerInjury + 1;
			}

			AttackerEndurance = AttackerEndurance - AttackerInjury;
			
			if(GameManager.getInstance().isPrinceTurn()) {
				GameManager.getInstance().setOpponentLife(TargetEndurance);
			}else {
				GameManager.getInstance().setPrinceLife(TargetEndurance);
			}
			degats.add(AttackerInjury);
		}
		return degats;
	}

}
