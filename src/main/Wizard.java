package main;

public class Wizard extends Hero {
	private Drain drain;
	private Deflect deflect;
	private static final int INITIAL_HP = 400;
	private static final int INCREASE_HP = 30;
	private static final char TERRAIN = 'D';
	private static final float TERRAIN_AMP = (float) 0.10;
	public Wizard(final int positionX, final int positionY) {
		super(positionX, positionY);
		this.totalHP = INITIAL_HP;
		this.increaseLevelUpHP = INCREASE_HP;
		this.setInitialHP();
		this.terrainPreferred = TERRAIN;
		this.terrainAmplifier = TERRAIN_AMP;
		drain = new Drain(this);
		deflect = new Deflect(this);
	}

	/**
	 * Ataca inamicul cu cele doua abilitati.
	 * @param enemyHero
	 * @param terrain
	 */
	public void attack(final Hero enemyHero, final char terrain) {
		drain.action(enemyHero, terrain);
		deflect.action(enemyHero, terrain);
	}

	/**
	 * Eroul face level up, impreuna cu abilitatile sale.
	 */
	@Override
	void levelUp() {
		super.levelUp();
		drain.levelUp();
		deflect.levelUp();
	}

	/**
	 * Returneaza 0 pentru ca e Wizard si nu se aplica Deflect.
	 * @param terrain
	 * @return
	 */
	@Override
	public float returnDamageWithoutModifiers(final char terrain) {
		return 0;
	}

	/**
	 * Nu face nimic, poate avea o implementare ulterioara.
	 */
	@Override
	public void roundPassed() {

	}
}
