package model;

/**
 * 
 * @author Jian Fang
 *
 */

public class DiceTray {
	/**
	 * Construct a tray of dice using a hard coded 2D array of chars. Use this for
	 * testing
	 * 
	 * @param newBoard The 2D array of characters used in testing
	 */
	public DiceTray(char[][] newBoard) {
		// TODO Implement this constructor

		used = new int[newBoard.length][newBoard[0].length];
		Board = newBoard;
		setZeros();
	}

	/**
	 * Return true if search is word that can found on the board following the rules
	 * of Boggle
	 * 
	 * @param str A word that may be in the board by connecting consecutive letters
	 * @return True if search is found
	 */
	public static boolean found(String attempt) {
		// TODO: Implement this method
		attempt = attempt.toUpperCase();
		int i = 0;
		int j = 0;
		if (attempt.length() == 17) {
			int idx = attempt.indexOf("QU");
			if (idx == -1)
				return false;
			String s = new String();
			for (i = 0; i < attempt.length(); i++)
				if (attempt.charAt(i) != 'U')
					s += attempt.charAt(i);
			attempt = s;
		}

		if (attempt.length() < 3 || attempt.length() > 16)
			return false;

		for (i = 0; i < Board.length; i++) {
			setZeros();
			for (j = 0; j < Board[0].length; j++) {
				if (FindNext(attempt, 0, i, j))
					return true;
			}
		}
		return false;
	}

	private static void setZeros() {
		int i = 0;
		int j = 0;
		for (i = 0; i < Board.length; i++) {
			for (j = 0; j < Board[0].length; j++) {
				used[i][j] = 0;
			}
		}
	}

	private static boolean FindNext(String attempt, int start, int r, int c) {
		if (r < 0 || r >= Board.length || c < 0 || c >= Board[0].length)
			return false;
		if (start == attempt.length())
			return true;
		if (attempt.charAt(start) != Board[r][c] || used[r][c] == 1)
			return false;

		used[r][c] = 1;
		if (FindNext(attempt, start + 1, r + 1, c) || FindNext(attempt, start + 1, r - 1, c)
				|| FindNext(attempt, start + 1, r, c + 1) || FindNext(attempt, start + 1, r, c - 1)
				|| FindNext(attempt, start + 1, r + 1, c + 1) || FindNext(attempt, start + 1, r + 1, c - 1)
				|| FindNext(attempt, start + 1, r - 1, c + 1) || FindNext(attempt, start + 1, r - 1, c - 1)) {
			return true;
		}

		used[r][c] = 0;
		return false;
	}

	private static char[][] Board;
	private static int[][] used;
}