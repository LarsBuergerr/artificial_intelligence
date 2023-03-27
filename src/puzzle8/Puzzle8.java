package puzzle8;

import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Ihr Name
 */
public class Puzzle8 {
	
	public static void main(String[] args) {
		Board b = new Board(); // Loesbares Puzzle b zufällig genrieren.
		while (!b.parity()) b = new Board();
		System.out.println(b);

		Deque<Board> res = A_Star.aStar(b);
		int n = res == null ? -1 : res.size();
		System.out.println("A*:");
		System.out.println("Anz.Zuege: " + n + ": " + res);
		System.out.println("Anz.Zuege: " + A_Star.getSize());

		res = IDFS.idfs(b);
		n = res == null ? -1 : res.size();
		System.out.println("IDFS:");
		System.out.println("Anz.Zuege: " + n + ": " + res);
		System.out.println("Anz.Zuege: " + IDFS.getSize());
	}
}
