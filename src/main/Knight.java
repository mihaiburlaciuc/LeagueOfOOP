package main;

public class Knight extends Hero {
	private Execute execute;
	private Slam slam;
	private static final int INITIAL_HP = 900;
	private static final int INCREASE_HP = 80;
	private static final char TERRAIN = 'L';
	private static final float TERRAIN_AMP = (float) 0.15;
	public Knight(final int positionX, final int positionY) {
		super(positionX, positionY);
		this.totalHP = INITIAL_HP;
		this.increaseLevelUpHP = INCREASE_HP;
		this.setInitialHP();
		this.terrainPreferred = TERRAIN;
		this.terrainAmplifier = TERRAIN_AMP;
		execute = new Execute(this);
		slam = new Slam(this);
	}
	/**
	 * Ataca inamicul cu cele doua abilitati.
	 * @param enemyHero
	 * @param terrain
	 */
	@Override
	public void attack(final Hero enemyHero, final char terrain) {
		execute.action(enemyHero, terrain);
		slam.action(enemyHero, terrain);
	}
	/**
	 * Eroul face level up, impreuna cu abilitatile sale.
	 */
	@Override
	void levelUp() {
		super.levelUp();
		execute.levelUp();
		slam.levelUp();
	}

	/**
	 * Returneaza damage-ul fara modificatori de rasa -> pentru Deflect.
	 * @param terrain
	 * @return
	 */
	@Override
	public float returnDamageWithoutModifiers(final char terrain) {
		return execute.returnPureDamage(terrain) + slam.returnPureDamage(terrain);
	}

	/**
	 * Nu face nimic, poate avea o implementare ulterioara.
	 */
	@Override
	public void roundPassed() {

	}
}
