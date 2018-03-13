package main;

public class Ignite implements Ability {
	private int damage;
	private int increaseDamage;
	private int damageOvertime;
	private int increaseDamageOvertime;
	private Hero currentHero;
	private Modifier modifier;
	private static final float MR = (float) -0.20;
	private static final float MK = (float) 0.20;
	private static final float MP = (float) -0.10;
	private static final float MW = (float) 0.05;
	private static final int INITIAL_DAMAGE = 150;
	private static final int INCREASE_DAMAGE = 20;
	private static final int INITIAL_DOT = 50;
	private static final int INCREASE_DOT = 30;
	public Ignite(final Hero currentHero) {
		this.currentHero = currentHero;
		this.damage = INITIAL_DAMAGE;
		this.increaseDamage = INCREASE_DAMAGE;
		this.damageOvertime = INITIAL_DOT;
		this.increaseDamageOvertime = INCREASE_DOT;
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
				currentHero.returnDamageWithModifier(damage,
						modifier,
						terrain,
						enemyHero));
		enemyHero.setOvertimeAbility(this, 2);
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
		return tmpDamage;
	}

	/**
	 * Executa abilitatea Overtime asupra inamicului, adica nimic.
	 * @param enemyHero
	 * @param terrain
	 */
	public void executeOvertimeAbility(final Hero enemyHero, final char terrain) {
		enemyHero.receivesDamage(
				currentHero.returnDamageWithModifier(damageOvertime,
						modifier,
						terrain,
						enemyHero));
	}
}
