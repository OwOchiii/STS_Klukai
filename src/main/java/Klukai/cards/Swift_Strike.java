package Klukai.cards;

import Klukai.actions.IgnoreThornsAttackAction;
import Klukai.character.MyCharacter;
import Klukai.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Swift_Strike extends BaseCard{

    public static final String ID = makeID("Swift_Strike");
    public static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public static final int DAMAGE = 6;
    public static final int UPG_DAMAGE = 3;

    public Swift_Strike() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Calculate damage manually with Vulnerable only (no Strength)
        int finalDamage = this.damage; // Base damage from card

        // Apply Vulnerable if enemy has it (50% more damage)
        if (abstractMonster.hasPower("Vulnerable")) {
            if (AbstractDungeon.player.hasRelic("Paper Phrog")) {
                finalDamage = (int) Math.floor(finalDamage * 1.75); // Paper Phrog increases Vulnerable damage by 25%
            } else {
                finalDamage = (int) Math.floor(finalDamage * 1.5); // Normal Vulnerable increases damage by 50%
            }
        }

        // Use THORNS type to bypass Strength, but we manually calculated Vulnerable
        DamageInfo damageInfo = new DamageInfo(abstractPlayer, finalDamage, DamageInfo.DamageType.THORNS);

        addToBot(new IgnoreThornsAttackAction(abstractMonster, damageInfo,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
