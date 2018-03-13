package main;

public class Pyromancer extends Hero {
	private Fireblast fireblast;
	private Ignite ignite;
	private static final int INITIAL_HP = 500;
	private static final int INCREASE_HP = 50;
	private static final char TERRAIN = 'V';
	private static final float TERRAIN_AMP = (float) 0.25;

	public Pyromancer(final int positionX, final int positionY) {
		super(positionX, positionY);
		this.totalHP = INITIAL_HP;
		this.increaseLevelUpHP = INCREASE_HP;
		this.terrainPreferred = TERRAIN;
		this.terrainAmplifier = TERRAIN_AMP;
		this.setInitialHP();
		fireblast = new Fireblast(this);
		ignite = new Ignite(this);
	}

	/**
	 * Ataca inamicul cu cele doua abilitati.
	 * @param enemyHero
	 * @param terrain
	 */
	@Override
	public void attack(final Hero enemyHero, final char terrain) {
		fireblast.action(enemyHero, terrain);
		ignite.action(enemyHero, terrain);
	}

	/**
	 * Eroul face level up, impreuna cu abilitatile sale.
	 */
    @Override
    void levelUp() {
        super.levelUp();
        fireblast.levelUp();
        ignite.levelUp();
    }

	/**
	 * Returneaza damage-ul fara modificatori de rasa -> pentru Deflect.
	 * @param terrain
	 * @return
	 */
	@Override
    public float returnDamageWithoutModifiers(final char terrain) {
        return fireblast.returnPureDamage(terrain) + ignite.returnPureDamage(terrain);
    }

	/**
	 * Nu face nimic, poate avea o implementare ulterioara.
	 */
	@Override
	public void roundPassed() {

	}
}
