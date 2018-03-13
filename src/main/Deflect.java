package main;

public class Deflect implements Ability {
    private float percentage;
    private float increasePercentage;
    private Modifier modifier;
    private final Hero currentHero;
    private static final float MR = (float) 0.20;
    private static final float MK = (float) 0.4;
    private static final float MP = (float) 0.3;
    private static final float MW = (float) 0;
    private static final float MAX_PERCENTAGE = (float) 0.7;
    private static final float INITIAL_PERCENTAGE = (float) 0.35;
    private static final float INCREASE_PERCENTAGE = (float) 0.02;

    public Deflect(final Hero currentHero) {
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
     * Incrementeaza procentul abilitatii Deflect cand face level up.
     * Opreste creseterea atunci cand ajunge la MAX_PERCENTAGE.
     */
    public void levelUp() {
        if (percentage < MAX_PERCENTAGE) {
            percentage += increasePercentage;
        }
        if (percentage > MAX_PERCENTAGE) {
            percentage = MAX_PERCENTAGE;
        }
    }

    /**
     * Returneaza 0 pentru ca nu are efect contra unui Wizard, fiind deja abilitate de Wizard.
     * @param terrain terenul pe care se tine lupta.
     * @return returneaza 0.
     */

    public float returnPureDamage(final char terrain) {
        return 0;
    }

    /**
     * Executa abilitatea Defect asupra eroului inamic.
     * @param enemyHero inamicul.
     * @param terrain terenul pe care se desfasoara lupta.
     */
    public void action(final Hero enemyHero, final char terrain) {

        if (!(enemyHero instanceof Wizard)) {
            int damageTaken = Math.round(enemyHero.returnDamageWithoutModifiers(terrain));
            float terPercentage = 0;
            if (terrain == currentHero.terrainPreferred) {
                terPercentage = currentHero.terrainAmplifier;
            }
            float tmpPercentage = percentage;
            tmpPercentage += terPercentage * tmpPercentage;

            if (enemyHero instanceof Knight) {
                tmpPercentage += modifier.getModfierKnight() * tmpPercentage;
            } else if (enemyHero instanceof Rogue) {
                tmpPercentage += modifier.getModfierRogue() * tmpPercentage;
            } else {
                tmpPercentage += modifier.getModfierPyromancer() * tmpPercentage;
            }

            enemyHero.receivesDamage(Math.round(tmpPercentage * damageTaken));
        }

    }


    public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
        // Nothing
    }

}
