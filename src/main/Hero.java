package main;


public abstract class Hero {
	protected int positionX;
	protected int positionY;
	protected int totalHP;
	protected int currentHP;
	protected int increaseLevelUpHP;
	protected int level;
	protected int currentXP;
	protected char terrainPreferred;
	protected float terrainAmplifier;
	protected Ability overtimeAbility;
	protected int numberOfRoundsOfAbility; // overtime ability
	protected int countNumberOfRounds; // for overtime ability
    protected boolean isImmobilized;
    private static final int START_XP = 250;
    private static final int XP_PER_LEVEL = 50;
	public Hero() {
		this.level = 0;
		this.currentXP = 0;
	}
	public Hero(final int positionX, final int positionY) {
		this();
		this.positionX = positionX;
		this.positionY = positionY;
		isImmobilized = false;
	}

    /**
     * Formula de verificare pentru Level Up.
     * @return conform formulei.
     */
	private int xpNeededToLevelUp() {
		return (START_XP + XP_PER_LEVEL * this.level);
	}

    /**
     * reseteaza HP-ul cand se face LevelUP.
     */
	void resetHP() {
		totalHP += increaseLevelUpHP;
		currentHP = totalHP;
	}

	/**
	 * Eroul face level up, se reseteaza HP-ul.
	 */
	void levelUp() {
		resetHP();
		level++;
	}

	/**
	 * Primeste XP dupa lupta.
	 * @param xpGained
	 */
	public void gainXP(final int xpGained) {
		currentXP += xpGained;
		if (currentXP >= xpNeededToLevelUp()) {
			this.levelUp();
			this.gainXP(0);
		}
	}

	/**
	 * getter HP total.
	 * @return
	 */
	public int getTotalHP() {
		return totalHP;
	}

	/**
	 * getter HP curent.
	 * @return
	 */
	public int getCurrentHP() {
		return currentHP;
	}

	/**
	 * seteaza HP-ul curent cu HP-ul maxim posibil.
	 */
	public void setInitialHP() {
		this.currentHP = this.totalHP;
	}

	/**
	 * Ataca inamicul.
	 * @param enemyHero
	 * @param terrain
	 */
	abstract void attack(Hero enemyHero, char terrain);

	/**
	 * Verifica daca eroul mai e in viata.
	 * @return
	 */
	public boolean isDead() {
		if (currentHP <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * getter randul pe care se afla eroul.
	 * @return
	 */
	public int getPositionX() {
		return positionX;
	}

	/**
	 * getter coloana pe care se afla eroul.
	 * @return
	 */
	public int getPositionY() {
		return positionY;
	}

	/**
	 * Sus.
	 */
	public void moveUp() {
		positionX--;
	}

	/**
	 * Jos.
	 */
	public void moveDown() {
		positionX++;
	}

	/**
	 * Dreapta.
	 */
	public void moveRight() {
		positionY++;
	}

	/**
	 * Stanga.
	 */
	public void moveLeft() {
		positionY--;
	}

	/**
	 * Seteaza abilitatea overtime primita de la inamic.
	 * @param overtimeAbility
	 * @param numberOfRounds
	 */
	public void setOvertimeAbility(final Ability overtimeAbility, final int numberOfRounds) {
		this.overtimeAbility = overtimeAbility;
		numberOfRoundsOfAbility = numberOfRounds;
		countNumberOfRounds = 0;
	}

	/**
	 * Executa abilitatea overtime.
	 * @param terrain
	 */
	public void executeOvertimeAbility(final char terrain) {
	    if (countNumberOfRounds < numberOfRoundsOfAbility) {
            overtimeAbility.executeOvertimeAbility(this, terrain);
        } else {
	        isImmobilized = false;
        }
        countNumberOfRounds++;
    }

	/**
	 * Seteaza ca eroul nu se mai poate misca.
	 * @param immobilized
	 */
	public void setImmobilized(final boolean immobilized) {
        isImmobilized = immobilized;
    }

	/**
	 * Verifica daca eroul se mai poate misca.
	 * @return
	 */
	public boolean isImmobilized() {
        return isImmobilized;
    }

	/**
	 * Primeste damage de la alt erou.
	 * @param damageTaken
	 */
	public void receivesDamage(final int damageTaken) {
		currentHP -= damageTaken;
	}

	/**
	 * Returneaza damage in functie de modificatorii de rasa si de cei de teren.
	 * @param damage
	 * @param modifier
	 * @param terrain
	 * @param enemyHero
	 * @return
	 */
	public int returnDamageWithModifier(final int damage,
                                        final Modifier modifier,
                                        final char terrain,
                                        final Hero enemyHero) {
		float damageGiven = damage;
		float amplifier = 0;
		if (terrain == terrainPreferred) {
			amplifier = terrainAmplifier;
		}
		damageGiven += amplifier * damage;
		if (enemyHero instanceof Wizard) {
			damageGiven += modifier.getModfierWizard() * damageGiven;
		} else if (enemyHero instanceof Knight) {
			damageGiven += modifier.getModfierKnight() * damageGiven;
		} else if (enemyHero instanceof Rogue) {
			damageGiven += modifier.getModfierRogue() * damageGiven;
		} else {
			damageGiven += modifier.getModfierPyromancer() * damageGiven;
		}

		return Math.round(damageGiven);
	}

	/**
	 * Returneaza terenul unde eroul are bonus.
	 * @return
	 */
	public char getTerrainPreferred() {
		return terrainPreferred;
	}

	/**
	 * Afiseaza String-ul dorit pentru output.
	 * @return
	 */
	public String output() {
        if (this instanceof Wizard) {
            if (!this.isDead()) {
                return "W " + level
                        + " " + currentXP
                        + " " + currentHP
                        + " " + positionX
                        + " " + positionY;
            } else {
                return "W dead";
            }
        } else if (this instanceof Rogue) {
            if (!this.isDead()) {
                return "R " + level
                        + " " + currentXP
                        + " " + currentHP
                        + " " + positionX
                        + " " + positionY;
            } else {
                return "R dead";
            }
        } else if (this instanceof Pyromancer) {
            if (!this.isDead()) {
                return "P " + level
                        + " " + currentXP
                        + " " + currentHP
                        + " " + positionX
                        + " " + positionY;
            } else {
                return "P dead";
            }
        } else {
            if (!this.isDead()) {
                return "K " + level
                        + " " + currentXP
                        + " " + currentHP
                        + " " + positionX
                        + " " + positionY;
            } else {
                return "K dead";
            }
        }
    }

	/**
	 * returneaza nivelul curent al eroului.
	 * @return
	 */
	public int getLevel() {
        return level;
    }

	/**
	 * Returneaza damage-ul fara modificatori.
	 * @param terrain
	 * @return
	 */
	public float returnDamageWithoutModifiers(final char terrain) {
		return 0;
	}

	/**
	 * Se executa dupa ce trece runda.
	 */
	public abstract void roundPassed();
}
