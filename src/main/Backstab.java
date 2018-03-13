package main;

public class Backstab implements Ability {
    private int damage;
    private int increaseDamage;
    private int chance;
    private Modifier modifier;
    private Hero currentHero;
    private static final float MR = (float) 0.20;
    private static final float MK = (float) -0.10;
    private static final float MP = (float) 0.25;
    private static final float MW = (float) 0.25;
    private static final int INITIAL_DAMAGE = 200;
    private static final int INCREASE_DAMAGE = 20;
    private static final int INITIAL_CHANCE = 0;
    private static final int NUMBER_OF_CHANCES = 3;
    private static final float BONUS_CRIT = (float) 0.5;

    public Backstab(final Hero currentHero) {
        this.currentHero = currentHero;
        damage = INITIAL_DAMAGE;
        increaseDamage = INCREASE_DAMAGE;
        chance = INITIAL_CHANCE;
        modifier = new Modifier();
        modifier.setModfierRogue(MR);
        modifier.setModfierKnight(MK);
        modifier.setModfierPyromancer(MP);
        modifier.setModfierWizard(MW);
    }

    /**
     * Incrementeaza damagu-ul abilitatii cand face level up.
     */
    public void levelUp() {
        damage += increaseDamage;
    }

    /**
     * Executa abilitatea asupra eroului inamic.
     * @param enemyHero inamicul.
     * @param terrain terenul pe care se desfasoara lupta.
     */
    public void action(final Hero enemyHero, final char terrain) {
        if (terrain == currentHero.terrainPreferred) {
            int tmpDamage = damage;
            if (chance % NUMBER_OF_CHANCES == 0) {
                tmpDamage += damage * BONUS_CRIT;
            }

            enemyHero.receivesDamage(
                    currentHero.returnDamageWithModifier(tmpDamage, modifier, terrain, enemyHero));
        } else {
            chance = 0;
            enemyHero.receivesDamage(
                    currentHero.returnDamageWithModifier(damage, modifier, terrain, enemyHero));
        }
    }

    /**
     * creste sansa de a da critical damage.
     */
    public void increaseChance() {
        chance++;
    }

    /**
     * Returneaza damage-ul fara modificatori de rasa.
     * @param terrain terenul pe care se tine lupta.
     * @return
     */
    public float returnPureDamage(final char terrain) {
        int tmpDamage = damage;
        if (chance % NUMBER_OF_CHANCES == 0 && terrain == currentHero.terrainPreferred) {
            tmpDamage += damage * BONUS_CRIT;
        }
        if (terrain == currentHero.terrainPreferred) {
            tmpDamage += tmpDamage * currentHero.terrainAmplifier;
        }
        return tmpDamage;
    }

    /**
     * Executa abilitatea Overtime asupra inamicului, adica nimic.
     * @param enemyHero
     * @param terrain
     */
    public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
        // Nothing
    }
}
