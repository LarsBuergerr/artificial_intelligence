package exercise_1;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Ihr Name
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();
	
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();
	
	public static Deque<Board> aStar(Board startBoard) {
		if (startBoard.isSolved())
			return new LinkedList<>();
		
		// ...
		
		return null; // Keine Lösung
	}
}
