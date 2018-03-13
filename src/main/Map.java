package main;


public class Map {
    private char[][] map;

    public Map(final int rows, final int colomns) {
        map = new char[rows][colomns];
    }

    /**
     * Insereaza un anumit tip de teren la o anumita pozitie.
     * @param value
     * @param rowPos
     * @param colPos
     */
    public void insertElement(final char value, final int rowPos, final int colPos) {
        map[rowPos][colPos] = value;
    }

    /**
     * Returneaza tipul de teren de la pozitia data.
     * @param rowPos
     * @param colPos
     * @return
     */
    public char getElement(final int rowPos, final int colPos) {
        return map[rowPos][colPos];
    }
}
