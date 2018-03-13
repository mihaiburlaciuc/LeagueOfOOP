package main;

public class Execute implements Ability {
    private int damage;
    private int increaseDamage;
	private float percentage;
	private Hero currentHero;
	private Modifier modifier;
    private static final float MR = (float) 0.15;
    private static final float MK = (float) 0;
    private static final float MP = (float) 0.10;
    private static final float MW = (float) -0.20;
    private static final int INITIAL_DAMAGE = 200;
    private static final int INCREASE_DAMAGE = 30;
    private static final float MAX_PERCENTAGE = (float) 0.40;
    private static final float INITIAL_PERCENTAGE = (float) 0.20;
    private static final float INCREASE_PERCENTAGE = (float) 0.01;

	public Execute(final Hero currentHero) {
		this.currentHero = currentHero;
		damage = INITIAL_DAMAGE;
		increaseDamage = INCREASE_DAMAGE;
		percentage = INITIAL_PERCENTAGE;
		modifier = new Modifier();
		modifier.setModfierRogue(MR);
		modifier.setModfierKnight(MK);
		modifier.setModfierPyromancer(MP);
		modifier.setModfierWizard(MW);
	}

	/**
	 * Incrementeaza procentul abilitatii cand face level up.
	 * Opreste creseterea atunci cand ajunge la MAX_PERCENTAGE.
	 */
	public void levelUp() {
		damage += increaseDamage;
		if (percentage < MAX_PERCENTAGE) {
			percentage += INCREASE_PERCENTAGE;
		}
	}

	/**
	 * Returneaza 0 pentru ca nu are efect contra unui Wizard, fiind deja abilitate de Wizard.
	 * @param terrain terenul pe care se tine lupta.
	 * @return
	 */
	public float returnPureDamage(final char terrain) {
        int tmpDamage = damage;
		if (terrain == currentHero.terrainPreferred) {
        	tmpDamage += damage * currentHero.terrainAmplifier;
		}
		return tmpDamage;
	}

	/**
	 * Executa abilitatea asupra eroului inamic.
	 * @param enemyHero inamicul.
	 * @param terrain terenul pe care se desfasoara lupta.
	 */
	public void action(final Hero enemyHero, final char terrain) {
		float report = (float) enemyHero.getCurrentHP() / enemyHero.getTotalHP();
		if (report <= percentage) {
		    enemyHero.receivesDamage(enemyHero.getCurrentHP());
        } else {
            enemyHero.receivesDamage(
                    currentHero.returnDamageWithModifier(damage, modifier, terrain, enemyHero));
        }

	}

	public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
		// Nothing
	}
}
