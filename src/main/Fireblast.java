package main;

public class Fireblast implements Ability {
	private int damage;
	private int increaseDamage;
	private Modifier modifier;
    private Hero currentHero;
	private static final float MR = (float) -0.20;
	private static final float MK = (float) 0.20;
	private static final float MP = (float) -0.10;
	private static final float MW = (float) 0.05;
	private static final int INITIAL_DAMAGE = 350;
	private static final int INCREASE_DAMAGE = 50;
	public Fireblast(final Hero currentHero) {
	    this.currentHero = currentHero;
		this.damage = INITIAL_DAMAGE;
		this.increaseDamage = INCREASE_DAMAGE;
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
		enemyHero.receivesDamage(
				currentHero.returnDamageWithModifier(damage,
						modifier,
						terrain,
						enemyHero));
	}

	/**
	 * Returneaza damage-ul fara modificatori de rasa.
	 * @param terrain terenul pe care se tine lupta.
	 * @return
	 */
	public float returnPureDamage(final char terrain) {
		float tmpDamage = damage;
		if (terrain == currentHero.terrainPreferred) {
			tmpDamage += (float) damage * currentHero.terrainAmplifier;
		}
		return Math.round(tmpDamage);
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
