package Klukai.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class IgnoreThornsAttackAction extends AbstractGameAction {
    private DamageInfo info;
    private AttackEffect effect;
    private AbstractCreature target;

    public IgnoreThornsAttackAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.target = target;
        this.info = info;
        this.effect = effect;
    }

    @Override
    public void update() {
        // Store current Thorns amount
        AbstractPower thorns = target.getPower("Thorns");
        int thornsAmount = 0;

        if (thorns != null) {
            thornsAmount = thorns.amount;
            target.powers.remove(thorns);  // Temporarily remove
        }

        // Execute the attack
        addToTop(new DamageAction(target, info, effect));

        // Restore Thorns
        if (thornsAmount > 0) {
            target.addPower(new ThornsPower(target, thornsAmount));
        }

        this.isDone = true;
    }
}
