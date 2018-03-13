package main;

public class Slam implements Ability {
	private int damage;
	private int increaseDamage;
	private Hero currentHero;
	private Modifier modifier;
	private static final float MR = (float) -0.20;
	private static final float MK = (float) 0.20;
	private static final float MP = (float) -0.10;
	private static final float MW = (float) 0.05;
	private static final int INITIAL_DAMAGE = 100;
	private static final int INCREASE_DAMAGE = 40;
	private static final int ROUND_NUMBER = 1;

	public Slam(final Hero currentHero) {
		this.currentHero = currentHero;
		damage = INITIAL_DAMAGE;
		increaseDamage = INCREASE_DAMAGE;
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
		enemyHero.setOvertimeAbility(this, ROUND_NUMBER);
	}

	/**
	 * Returneaza damage-ul fara modificatori de rasa.
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
	 * Executa abilitatea Overtime asupra inamicului.
	 * @param enemyHero
	 * @param terrain
	 */
	public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
		enemyHero.setImmobilized(true);
	}
}
