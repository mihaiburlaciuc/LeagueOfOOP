package main;

import fileio.implementations.FileReader;
import fileio.implementations.FileWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public final class Main {

	private Main() {

	}

	public static void main(final String[] args) {
		String input = args[0];
		String output = args[1];

		try {
			int numRows, numCols;
			FileReader reader = new FileReader(input);

			numRows = reader.nextInt();
			numCols = reader.nextInt();

			Map map = new Map(numRows, numCols);

			for (int i = 0; i < numRows; i++) {
				String tmp = reader.nextWord();

				for (int j = 0; j < tmp.length(); j++) {
					map.insertElement(tmp.charAt(j), i, j);
				}
			}

			int numPlayers;

			numPlayers = reader.nextInt();

			ArrayList<Hero> players = new ArrayList<>();

			for (int i = 0; i < numPlayers; i++) {

				char playerType = reader.nextWord().charAt(0);
				int positionX = reader.nextInt(); // reprezinta randurile
				int positionY = reader.nextInt(); // reprezinta coloanele

				Hero tmpHero;

				if (playerType == 'W') {
					tmpHero = new Wizard(positionX, positionY);
				} else if (playerType == 'P') {
					tmpHero = new Pyromancer(positionX, positionY);
				} else if (playerType == 'R') {
					tmpHero = new Rogue(positionX, positionY);
				} else {
					tmpHero = new Knight(positionX, positionY);
				}

				players.add(tmpHero);
			}

			int numRounds = reader.nextInt();
			ArrayList<String> moves = new ArrayList<>();

			for (int i = 0; i < numRounds; i++) {
				String tmp = reader.nextWord();

				moves.add(tmp);
			}

			Game game = new Game(map, players, moves);

			game.play();

			FileWriter write = new FileWriter(output);

			for (int i = 0; i < numPlayers; i++) {
				write.writeWord(players.get(i).output());
				write.writeNewLine();
			}

			write.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
