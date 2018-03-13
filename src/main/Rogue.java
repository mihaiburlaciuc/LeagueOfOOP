package main;

public class Rogue extends Hero {
    private Backstab backstab;
    private Paralysis paralysis;
    private static final int INITIAL_HP = 600;
    private static final int INCREASE_HP = 40;
    private static final char TERRAIN = 'W';
    private static final float TERRAIN_AMP = (float) 0.15;

    public Rogue(final int positionX, final int positionY) {
        super(positionX, positionY);
        this.totalHP = INITIAL_HP;
        this.increaseLevelUpHP = INCREASE_HP;
        this.terrainPreferred = TERRAIN;
        this.terrainAmplifier = TERRAIN_AMP;
        this.setInitialHP();
        backstab = new Backstab(this);
        paralysis = new Paralysis(this);
    }

    /**
     * Ataca inamicul cu cele doua abilitati.
     * @param enemyHero
     * @param terrain
     */
    public void attack(final Hero enemyHero, final char terrain) {
        backstab.action(enemyHero, terrain);
        paralysis.action(enemyHero, terrain);
    }

    /**
     * Eroul face level up, impreuna cu abilitatile sale.
     */
    @Override
    void levelUp() {
        super.levelUp();
        backstab.levelUp();
        paralysis.levelUp();
    }

    /**
     * Returneaza damage-ul fara modificatori de rasa -> pentru Deflect.
     * @param terrain
     * @return
     */
    @Override
    public float returnDamageWithoutModifiers(final char terrain) {
        return backstab.returnPureDamage(terrain) + paralysis.returnPureDamage(terrain);
    }

    /**
     * Creste variabila de sansa a rogue-ului pentru Backstab.
     */
    @Override
    public void roundPassed() {
        backstab.increaseChance();
    }
}
