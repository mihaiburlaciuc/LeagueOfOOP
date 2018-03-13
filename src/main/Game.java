package main;

import java.util.ArrayList;

public class Game {
	private Map map;
	private ArrayList<Hero> heroList;
	private ArrayList<String> moves;
	private static final int EO = 200;
	private static final int ET = 40;
	public Game(final Map map, final ArrayList<Hero> heroList, final ArrayList<String> moves) {
		this.map = map;
		this.heroList = heroList;
		this.moves = moves;
	}

	private boolean playersOnSamePosition(final Hero first, final Hero second) {
		if (first.getPositionX() == second.getPositionX()
				&& first.getPositionY() == second.getPositionY()) {
			return true;
		}

		return false;
	}

	/**
	 * Cei doi eroi isi folosesc abilitatile.
	 * Daca un erou a murit si celalalt e in viata atunci acela primeste XP.
	 * @param first
	 * @param second
	 * @param terrain
	 */
	private void fight(final Hero first, final Hero second, final char terrain) {
	    first.attack(second, terrain);
		second.attack(first, terrain);

		if (first.isDead() && !second.isDead()) {
		    second.gainXP(Math.max(0,
                    EO - (second.getLevel() - first.getLevel()) * ET));
        } else if (!first.isDead() && second.isDead()) {
            first.gainXP(Math.max(0,
                    EO - (first.getLevel() - second.getLevel()) * ET));
        }
	}

    /**
     * Cauta doi eroi care sunt pe aceeasi pozitie.
     * Cand ii gaseste incepe batalia.
     */
	void startTheBattles() {
		// Verificam si efectuam ce lupte au loc
		int numberOfPlayers = heroList.size();
		for (int i = 0; i < numberOfPlayers; i++) {
			if (heroList.get(i).isDead()) {
				continue;
			}
			for (int j = i + 1; j < numberOfPlayers; j++) {
				if (heroList.get(j).isDead()) {
					continue;
				}
				if (playersOnSamePosition(heroList.get(i), heroList.get(j))) {
					// Avem nevoie de cei doi eroi si de tipul de teren
					fight(heroList.get(i), heroList.get(j),
                            map.getElement(
                                    heroList.get(i).getPositionX(),
                                    heroList.get(i).getPositionY()));
				}
			}
		}
	}

    /**
     * Se efectueaza jocul propriu-zis.
     * Pentru fiecare runda se executa abilitatile overtime.
     * Se misca eroii pe harta.
     * Au loc bataliile.
     * Se efectueaza prorietatile de la sfarsitul rundei (adica se creste chance-ul la Backstab)
     */
	public void play() {
		int numberOfRounds = moves.size();
		int numberOfPlayers = heroList.size();

		for (int i = 0; i < numberOfRounds; i++) { // i reprezinta numarul rundei


            for (int j = 0; j < numberOfPlayers; j++) {

                int posX = heroList.get(j).getPositionX();
                int posY = heroList.get(j).getPositionY();

                char terrain = map.getElement(posX, posY);
                heroList.get(j).executeOvertimeAbility(terrain);

            }
			// Fiecare players isi efectueaza deplasarea
			for (int j = 0; j < numberOfPlayers; j++) {
				Hero tmpHero = heroList.get(j);
				if (!tmpHero.isImmobilized()) {
                    String tmpRound = moves.get(i);
                    if (tmpRound.charAt(j) == 'U') {
                        tmpHero.moveUp();
                    } else if (tmpRound.charAt(j) == 'D') {
                        tmpHero.moveDown();
                    } else if (tmpRound.charAt(j) == 'L') {
                        tmpHero.moveLeft();
                    } else if (tmpRound.charAt(j) == 'R') {
                        tmpHero.moveRight();
                    }
                }

			}

			startTheBattles();

            // a trecut runda
			for (int j = 0; j < numberOfPlayers; j++) {
				heroList.get(j).roundPassed();
			}
		}

	}

}
