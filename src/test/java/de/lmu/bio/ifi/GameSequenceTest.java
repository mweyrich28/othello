package de.lmu.bio.ifi;

import org.junit.jupiter.api.*;
import szte.mi.Move;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameSequenceTest {

	@Test
	@Order(11)
	@DisplayName("possible moves before move 1 of 7")
	public void moodleGameSequenceTest1PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(2, 3));
		moveList.add(new Move(3, 2));
		moveList.add(new Move(4, 5));
		moveList.add(new Move(5, 4));

		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is black on (5, 4).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . O X . . .
	 * . . . X X X . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(12)
	@DisplayName("game sequence (from moodle) move 1 of 7: black on (5, 4)")
	public void moodleGameSequenceTest1() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);

		int[][] referenceState = new int[8][8];
		referenceState[3][4] = 1;
		referenceState[4][3] = 1;
		referenceState[4][4] = 1;
		referenceState[4][5] = 1;
		referenceState[3][3] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(21)
	@DisplayName("possible moves before move 2 of 7")
	public void moodleGameSequenceTest2PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(5, 3));
		moveList.add(new Move(3, 5));
		moveList.add(new Move(5, 5));

		((Game) gameObject).makeMove(true, 5, 4);

		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is white on (5, 3).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . O O O . .
	 * . . . X X X . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(22)
	@DisplayName("game sequence (from moodle) move 2 of 7: white on (5, 3)")
	public void moodleGameSequenceTest2() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);

		int[][] referenceState = new int[8][8];
		referenceState[4][3] = 1;
		referenceState[4][4] = 1;
		referenceState[4][5] = 1;
		referenceState[3][3] = 2;
		referenceState[3][4] = 2;
		referenceState[3][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(31)
	@DisplayName("possible moves before move 3 of 7")
	public void moodleGameSequenceTest3PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(2, 2));
		moveList.add(new Move(3, 2));
		moveList.add(new Move(4, 2));
		moveList.add(new Move(5, 2));
		moveList.add(new Move(6, 2));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is black on (4, 2).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . X . . .
	 * . . . O X O . .
	 * . . . X X X . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(32)
	@DisplayName("game sequence (from moodle) move 3 of 7: black on (4, 2)")
	public void moodleGameSequenceTest3() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);

		int[][] referenceState = new int[8][8];
		referenceState[2][4] = 1;
		referenceState[3][4] = 1;
		referenceState[4][3] = 1;
		referenceState[4][4] = 1;
		referenceState[4][5] = 1;
		referenceState[3][3] = 2;
		referenceState[3][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(41)
	@DisplayName("possible moves before move 4 of 7")
	public void moodleGameSequenceTest4PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(3, 1));
		moveList.add(new Move(5, 1));
		moveList.add(new Move(3, 5));
		moveList.add(new Move(5, 5));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is white on (5, 5).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . X . . .
	 * . . . O X O . .
	 * . . . X O O . .
	 * . . . . . O . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(42)
	@DisplayName("game sequence (from moodle) move 4 of 7: white on (5, 5)")
	public void moodleGameSequenceTest4() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);

		int[][] referenceState = new int[8][8];
		referenceState[2][4] = 1;
		referenceState[3][4] = 1;
		referenceState[4][3] = 1;
		referenceState[3][3] = 2;
		referenceState[3][5] = 2;
		referenceState[4][4] = 2;
		referenceState[4][5] = 2;
		referenceState[5][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(51)
	@DisplayName("possible moves before move 5 of 7")
	public void moodleGameSequenceTest5PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(3, 2));
		moveList.add(new Move(2, 3));
		moveList.add(new Move(6, 3));
		moveList.add(new Move(2, 4));
		moveList.add(new Move(6, 4));
		moveList.add(new Move(4, 5));
		moveList.add(new Move(6, 5));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is black on (2, 4).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . X . . .
	 * . . . X X O . .
	 * . . X X O O . .
	 * . . . . . O . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(52)
	@DisplayName("game sequence (from moodle) move 5 of 7: black on (2, 4)")
	public void moodleGameSequenceTest5() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);

		int[][] referenceState = new int[8][8];
		referenceState[2][4] = 1;
		referenceState[3][3] = 1;
		referenceState[3][4] = 1;
		referenceState[4][2] = 1;
		referenceState[4][3] = 1;
		referenceState[3][5] = 2;
		referenceState[4][4] = 2;
		referenceState[4][5] = 2;
		referenceState[5][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(61)
	@DisplayName("possible moves before move 6 of 7")
	public void moodleGameSequenceTest6PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(3, 1));
		moveList.add(new Move(4, 1));
		moveList.add(new Move(2, 2));
		moveList.add(new Move(3, 2));
		moveList.add(new Move(2, 3));
		moveList.add(new Move(1, 4));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is white on (2, 3).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . X . . .
	 * . . O O O O . .
	 * . . X X O O . .
	 * . . . . . O . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(62)
	@DisplayName("game sequence (from moodle) move 6 of 7: white on (2, 3)")
	public void moodleGameSequenceTest6() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);
		((Game) gameObject).makeMove(false, 2, 3);

		int[][] referenceState = new int[8][8];
		referenceState[2][4] = 1;
		referenceState[4][2] = 1;
		referenceState[4][3] = 1;
		referenceState[3][2] = 2;
		referenceState[3][3] = 2;
		referenceState[3][4] = 2;
		referenceState[3][5] = 2;
		referenceState[4][4] = 2;
		referenceState[4][5] = 2;
		referenceState[5][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(71)
	@DisplayName("possible moves before move 7 of 7")
	public void moodleGameSequenceTest7PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(1, 2));
		moveList.add(new Move(2, 2));
		moveList.add(new Move(3, 2));
		moveList.add(new Move(5, 2));
		moveList.add(new Move(6, 4));
		moveList.add(new Move(4, 5));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);
		((Game) gameObject).makeMove(false, 2, 3);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	/**
	 * Test if the game rules are implemented correctly by checking a certain sequence of moves (from moodle).
	 * The move to be checked is black on (1, 2).
	 * The board state must be as follows:
	 * . . . . . . . .
	 * . . . . . . . .
	 * . X . . X . . .
	 * . . X O O O . .
	 * . . X X O O . .
	 * . . . . . O . .
	 * . . . . . . . .
	 * . . . . . . . .
	 */
	@Test
	@Order(72)
	@DisplayName("game sequence (from moodle) move 7 of 7: black on (1, 2)")
	public void moodleGameSequenceTest7() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);
		((Game) gameObject).makeMove(false, 2, 3);
		((Game) gameObject).makeMove(true, 1, 2);

		int[][] referenceState = new int[8][8];
		referenceState[2][1] = 1;
		referenceState[2][4] = 1;
		referenceState[3][2] = 1;
		referenceState[4][2] = 1;
		referenceState[4][3] = 1;
		referenceState[3][3] = 2;
		referenceState[3][4] = 2;
		referenceState[3][5] = 2;
		referenceState[4][4] = 2;
		referenceState[4][5] = 2;
		referenceState[5][5] = 2;

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Assertions.assertEquals(referenceState[y][x], board[y][x]);
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@Order(73)
	@DisplayName("possible moves after move 7 of 7")
	public void moodleGameSequenceTest8PossibleMoves() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(3, 1));
		moveList.add(new Move(4, 1));
		moveList.add(new Move(5, 1));
		moveList.add(new Move(1, 3));
		moveList.add(new Move(1, 4));
		moveList.add(new Move(1, 5));
		moveList.add(new Move(2, 5));
		moveList.add(new Move(3, 5));

		((Game) gameObject).makeMove(true, 5, 4);
		((Game) gameObject).makeMove(false, 5, 3);
		((Game) gameObject).makeMove(true, 4, 2);
		((Game) gameObject).makeMove(false, 5, 5);
		((Game) gameObject).makeMove(true, 2, 4);
		((Game) gameObject).makeMove(false, 2, 3);
		((Game) gameObject).makeMove(true, 1, 2);


		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		this.checkMoveLists(moveList, possibleMoves);
	}

	private void checkMoveLists(List<Move> l1, List<Move> l2) {
		for (Move move : l2) {
			int index = -1;
			for (int i = 0; i < l1.size(); i++) {
				Move m = l1.get(i);
				if (move.x == m.x && move.y == m.y) {
					index = i;
					break;
				}
			}
			Assertions.assertNotEquals(-1, index);
			l1.remove(index);
		}
		Assertions.assertEquals(0, l1.size());
	}
}
