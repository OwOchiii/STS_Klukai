package Klukai.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Reload extends BaseCard{


    public Reload(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, String cardImage) {
        super(ID, cost, cardType, target, rarity, color, cardImage);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
