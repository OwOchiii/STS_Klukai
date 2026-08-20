package Klukai.cards;

import Klukai.BasicMod;
import Klukai.actions.IgnoreThornsAttackAction;
import Klukai.character.MyCharacter;
import Klukai.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Swift_Strike extends BaseCard{

    public static final String ID = makeID("Swift_Strike");
    private static CardStats info() {
        return new CardStats(
                MyCharacter.Meta.CARD_COLOR,
                CardType.ATTACK,
                CardRarity.BASIC,
                CardTarget.ENEMY,
                1
        );
    }

    public static final int DAMAGE = 6;
    public static final int UPG_DAMAGE = 3;

    public Swift_Strike() {
        super(ID, info());

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Start from baseDamage to avoid Strength already applied by calculateCardDamage()
        int finalDamage = this.baseDamage;

        if (abstractMonster.hasPower("Vulnerable")) {
            float multiplier = AbstractDungeon.player.hasRelic("Paper Phrog") ? 1.75f : 1.5f;
            finalDamage = (int) Math.floor(finalDamage * multiplier);
        }

        DamageInfo damageInfo = new DamageInfo(abstractPlayer, finalDamage, DamageInfo.DamageType.THORNS);
        addToBot(new IgnoreThornsAttackAction(abstractMonster, damageInfo,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
