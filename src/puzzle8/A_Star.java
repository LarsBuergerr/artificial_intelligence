package puzzle8;

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
	private static Deque<Board> closedList = new LinkedList<>();

	
	public static Deque<Board> aStar(Board startBoard) {
		if (startBoard.isSolved()) {
			System.out.println("Startknoten ist Zielknoten!");
			return new LinkedList<>();
		}
		
		closedList.add(startBoard);
		cost.put(startBoard, 0);
		pred.put(startBoard, null);

		for(Board child : startBoard.possibleActions()) {
			cost.put(child, 1);
			pred.put(child, startBoard);
			openList.add(child, 1 + child.h2());

		}
		while(!openList.isEmpty()) {
			Board curBoard = openList.removeMin();
			if (curBoard.isSolved()){
				Deque<Board> res = new LinkedList<>();
				res.add(curBoard);

				for(Board parent = pred.get(curBoard); parent != null; parent = pred.get(parent)) {
					res.addFirst(parent);
				}
				return res;
			}
			closedList.add(curBoard);
			
			for(Board child : curBoard.possibleActions()) {
				int costs = 1 + cost.get(curBoard);
				if (openList.get(child) == null && !closedList.contains(child)) {
					cost.put(child, costs);
					pred.put(child, curBoard);
					openList.add(child, costs + child.h2());
				} else if (openList.get(child) != null) {
					if (costs < cost.get(child)) {
						openList.change(child, costs + child.h2());
						cost.put(child, costs);
						pred.put(child, curBoard);
					}
				}
			}
		}
		return null;
	}

	public static int getSize() {
		return cost.size();
	}
}
