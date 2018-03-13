package main;

public class Drain implements Ability {
    private float percentage;
    private float increasePercentage;
    private Modifier modifier;
    private Hero currentHero;
    private static final float MR = (float) -0.20;
    private static final float MK = (float) 0.2;
    private static final float MP = (float) -0.1;
    private static final float MW = (float) 0.05;
    private static final float INITIAL_PERCENTAGE = (float) 0.2;
    private static final float INCREASE_PERCENTAGE = (float) 0.05;
    private static final float FORMULA_PERCENTAGE = (float) 0.3;

    public Drain(final Hero currentHero) {
        this.currentHero = currentHero;
        percentage = INITIAL_PERCENTAGE;
        increasePercentage = INCREASE_PERCENTAGE;
        modifier = new Modifier();
        modifier.setModfierRogue(MR);
        modifier.setModfierKnight(MK);
        modifier.setModfierPyromancer(MP);
        modifier.setModfierWizard(MW);
    }

    /**
     * Incrementeaza procentul-ul abilitatii cand face level up.
     */
    public void levelUp() {
            percentage += increasePercentage;
    }


    /**
     * Executa abilitatea asupra eroului inamic.
     * @param enemyHero inamicul.
     * @param terrain terenul pe care se desfasoara lupta.
     */
    public void action(final Hero enemyHero, final char terrain) {
        float tmpPercentage;
        if (enemyHero instanceof Wizard) {
            tmpPercentage = percentage + modifier.getModfierWizard() * percentage;
        } else if (enemyHero instanceof Knight) {
            tmpPercentage = percentage +  modifier.getModfierKnight() * percentage;
        } else if (enemyHero instanceof Rogue) {
            tmpPercentage = percentage +  modifier.getModfierRogue() * percentage;
        } else {
            tmpPercentage = percentage + modifier.getModfierPyromancer() * percentage;
        }
        float terPercentage = 0;
        if (terrain == currentHero.terrainPreferred) {
            terPercentage = currentHero.terrainAmplifier;
        }

        tmpPercentage += terPercentage * tmpPercentage;

        enemyHero.receivesDamage((int) Math.round((tmpPercentage)
                * Math.min(FORMULA_PERCENTAGE * enemyHero.getTotalHP(), enemyHero.getCurrentHP())));

    }

    /**
     * Lupta contra unui Wizard deci e 0.
     * @param terrain terenul pe care se tine lupta.
     * @return returneaza 0.
     */
    @Override
    public float returnPureDamage(final char terrain) {
        return 0;
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
