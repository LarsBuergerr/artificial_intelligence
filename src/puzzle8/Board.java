package puzzle8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author Ihr Name
 */
public class Board {

	/**
	 * Problmegröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		Integer[] valid_numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		List<Integer> intList = Arrays.asList(valid_numbers);
		Collections.shuffle(intList);
		intList.toArray(valid_numbers);

		for(int i = 0; i < this.board.length; i++) {
			this.board[i] = valid_numbers[i];
		}
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		if(board.length - 1 != N) {
			throw new IllegalArgumentException();
		}

		this.board = board;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {
		int counter = 0;

		for (int i = 0; i <= N; i++) {
			if(board[i] == 0) {
				continue;
			}
			// for(int j = i - 1; j >= 0; j --) wäre auch gegangen
			for(int j = 0; j < i; j++) {
				if(board[j] > board[i]) {
					counter++;
				}
			}
		}
		return counter % 2 == 0;
	}
	
	/**
	 * Heurstik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int wrong_stones = 0;

		for(int i = 1; i <= N; i++) {
			if(i != this.board[i]) {
				wrong_stones++;
			}
		}

		return wrong_stones; 
	}
	
	/**
	 * Heurstik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int sum = 0;
		for(int i = 0; i <= N; i++){
			int val = this.board[i];
			if(val ==0){
				continue;
			}
			sum += Math.abs(val%3 - i%3) + Math.abs(val/3 - i/3);
		}
		
		return sum;
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();
		int x = 0;
		int y = 0;
		int zeroIndex = 0;
		for(int i = 0;i < N; i++) {
			if(this.board[i] == 0) {
				zeroIndex = i;
				break;
			}
		}
		x = zeroIndex % 3;
		y = zeroIndex / 3;

		if(x - 1 <= 2 && x -1 >= 0) {
			int[]tmp_arr = Arrays.copyOf(this.board, this.board.length);
			Board tmp = new Board(tmp_arr);
			swap(tmp, zeroIndex, zeroIndex - 1);
			boardList.add(tmp);
		}
 
		if(x + 1 <= 2 && x + 1 >= 0) {
			int[]tmp_arr = Arrays.copyOf(this.board, this.board.length);
			Board tmp = new Board(tmp_arr);
			swap(tmp, zeroIndex, zeroIndex + 1);
			boardList.add(tmp);
		}

		if(y - 1 <= 2 && y - 1 >= 0) {
			int[]tmp_arr = Arrays.copyOf(this.board, this.board.length);
			Board tmp = new Board(tmp_arr);
			swap(tmp, zeroIndex, zeroIndex - 3);
			boardList.add(tmp);
		}

		if(y + 1 <= 2 && y + 1 >= 0) {
			int[]tmp_arr = Arrays.copyOf(this.board, this.board.length);
			Board tmp = new Board(tmp_arr);
			swap(tmp, zeroIndex, zeroIndex + 3);
			boardList.add(tmp);
		}
		return boardList;
	}
	
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		for(int i = 0; i < N; i++) {
			if(this.board[i] != i) {
				return false;
			}
		}
		return true;
	}

	public void swap(Board tmp, int x, int y) {
		int temp = tmp.board[x];
		tmp.board[x] = tmp.board[y];
		tmp.board[y] = temp;
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7, 2, 4, 5, 0, 6, 8, 3, 1});		// abc aus Aufgabenblatt
		Board random = new Board();
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});
				
		 System.out.println(b);
		 // System.out.println(random);
		 System.out.printf("Parität: %b\n", b.parity());
		 System.out.printf("Heuristik 1: %d\n", b.h1());
		 System.out.printf("Heuristik 2: %d\n", b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		System.out.println(b);
		
		// System.out.println(goal.isSolved());
	}
}
	
