package puzzle8;

import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Ihr Name
 */
public class Puzzle8 {
	
	public static void main(String[] args) {
		Board b = new Board(); // Loesbares Puzzle b zufällig genrieren.
		System.out.println(b);
		while (!b.parity()) b = new Board();

		Deque<Board> res = A_Star.aStar(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);

		// Deque<Board> res = IDFS.idfs(b);
		// int n = res == null ? -1 : res.size();
		// System.out.println("Anz.Zuege: " + n + ": " + res);
	}
}
