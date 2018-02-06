package Game.Rules;

import Game.GameManager;

public class AttackRule {

    int AttackerAttack = GameManager.getAttackerAttack;
    int TargetAttack = GameManager.getTargetAttack;
    int AttackerEndurance = GameManager.getAttackerEndurance;
    int TargetEndurance = GameManager.getTargetEndurance;
    int TargetInjury = 0;
    int AttackerInjury = 0;
    int Attack = 0;

    Attack = AttackerAttack - TargetAttack + GameManager.getDiceResult();
    if (Attack > 10){TargetInjury = TargetInjury+8;}
    else if (Attack > 6 &&  Attack < 8){
        TargetInjury = TargetInjury+4;
    }
    else if (Attack > 2 &&  Attack < 6){
        TargetInjury = TargetInjury+2;
    }
    else if (Attack < 6){
        TargetInjury = TargetInjury+1;
    }

    TargetEndurance = TargetEndurance - TargetInjury;

    Attack = AttackerAttack - TargetAttack + GameManager.getDiceResult() - TargetInjury;
    if (Attack > 10){AttackerInjury = AttackerInjury+8;}
    else if (Attack > 6 &&  Attack < 8){
        AttackerInjury = AttackerInjury+4;
    }
    else if (Attack > 2 &&  Attack < 6){
        AttackerInjury = AttackerInjury+2;
    }
    else if (Attack < 6){
        AttackerInjury = AttackerInjury+1;
    }

    AttackerEndurance = AttackerEndurance - AttackerInjury;


}
