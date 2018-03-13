package main;

public class Paralysis implements Ability {
    private int damage;
    private int increaseDamage;
    private int damageOvertime;
    private int increaseDamageOvertime;
    private Modifier modifier;
    private Hero currentHero;
    private static final float MR = (float) -0.10;
    private static final float MK = (float) -0.20;
    private static final float MP = (float) 0.20;
    private static final float MW = (float) 0.25;
    private static final int INITIAL_DOT = 40;
    private static final int INCREASE_DOT = 10;
    private static final int NUM_ROUNDS = 3;
    private static final int NUM_ROUNDS_EXTRA = 6;
    public Paralysis(final Hero currentHero) {
        this.currentHero = currentHero;
        damage = INITIAL_DOT;
        increaseDamage = INCREASE_DOT;
        damageOvertime = INITIAL_DOT;
        increaseDamageOvertime = INCREASE_DOT;
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
        damageOvertime += increaseDamageOvertime;
    }

    /**
     * Executa abilitatea asupra eroului inamic.
     * @param enemyHero inamicul.
     * @param terrain terenul pe care se desfasoara lupta.
     */
    public void action(final Hero enemyHero, final char terrain) {
        enemyHero.receivesDamage(
                currentHero.returnDamageWithModifier(damage, modifier, terrain, enemyHero));
        if (terrain == currentHero.getTerrainPreferred()) { // rogue e pe teren de tip woods
            enemyHero.setOvertimeAbility(this, NUM_ROUNDS_EXTRA);
        } else { // rogue nu e pe teren de tip woods
            enemyHero.setOvertimeAbility(this, NUM_ROUNDS);
        }
    }

    /**
     * Returneaza damage-ul fara modificatori de rasa.
     * @param terrain terenul pe care se tine lupta.
     * @return
     */
    public float returnPureDamage(final char terrain) {
        float tmpDamage = damage;
        if (terrain == currentHero.terrainPreferred) {
            tmpDamage += damage * currentHero.terrainAmplifier;
        }
        return tmpDamage;
    }

    /**
     * Executa abilitatea Overtime asupra inamicului.
     * @param enemyHero
     * @param terrain
     */
    public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
        enemyHero.receivesDamage(
                currentHero.returnDamageWithModifier(damageOvertime, modifier, terrain, enemyHero));
        enemyHero.setImmobilized(true);
    }

}
