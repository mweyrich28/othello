package de.lmu.bio.ifi;

import org.junit.jupiter.api.*;
import szte.mi.Move;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelTest {

	/**
	 * Test if the initial board state is set up correctly.
	 * The game rules state that the board is set up with 2 black and 2 white stones in the middle.
	 * The board is 8x8, so the middle is at 3,4 and 4,3 for black and 3,3 and 4,4 for white (0-based).
	 */
	@Test
	@DisplayName("initial board state set up correctly")
	@Order(1)
	public void initialState() {

		Object gameObject = TestUtils.getNewOthelloObject();

		Assertions.assertNotNull(gameObject);

		String boardState = ((Game) gameObject).toString();

		try {
			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {

					// black must be at 3,4 and 4,3 (0-based)
					if ((x == 3 && y == 4) || (x == 4 && y == 3)) {
						Assertions.assertEquals(1, board[y][x]);
					}
					// white must be at 3,3 and 4,4 (0-based)
					else if ((x == 3 && y == 3) || (x == 4 && y == 4)) {
						Assertions.assertEquals(2, board[y][x]);
					}
					// all other fields must be empty
					else {
						Assertions.assertEquals(0, board[y][x]);
					}
				}
			}

		} catch (ParseException e) {
			Assertions.fail("initial board state could not be parsed");
		}
	}

	@Test
	@DisplayName("white player can not move first")
	@Order(2)
	public void testWhitePlayerMovingFirstValid() {

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		boolean isValid = ((Game) gameObject).makeMove(false, 5, 3);
		Assertions.assertFalse(isValid);

		isValid = ((Game) gameObject).makeMove(false, 5, 4);
		Assertions.assertFalse(isValid);
	}

	@Test
	@DisplayName("black player can move first")
	@Order(3)
	public void testBlackPlayerMovingFirstValid() {

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		boolean isValid = ((Game) gameObject).makeMove(true, 5, 4);
		Assertions.assertTrue(isValid);
	}

	@Test
	@DisplayName("valid first moves of black are possible")
	@Order(4)
	public void blackPlayerMovingFirstValid() {
		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		boolean isValid = ((Game) gameObject).makeMove(true, 5, 4);
		Assertions.assertTrue(isValid);

		gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		isValid = ((Game) gameObject).makeMove(true, 2, 3);
		Assertions.assertTrue(isValid);

		gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		isValid = ((Game) gameObject).makeMove(true, 3, 2);
		Assertions.assertTrue(isValid);

		gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		isValid = ((Game) gameObject).makeMove(true, 4, 5);
		Assertions.assertTrue(isValid);
	}

	@Test
	@DisplayName("invalid first moves of black are not possible")
	@Order(5)
	public void blackPlayerMovingFirstInvalid() {

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		for (int x = -1; x < 9; x++) {
			for (int y = -1; y < 9; y++) {
				// skip valid positions
				if (x == 2 && y == 3 || x == 3 && y == 2 || x == 4 && y == 5 || x == 5 || y == 4) {
					continue;
				}

				boolean isValid = ((Game) gameObject).makeMove(true, x, y);
				Assertions.assertFalse(isValid);
			}
		}
	}

	@Test
	@DisplayName("possible moves of white at the first turn")
	@Order(6)
	public void possibleMovesOfWhiteFirstTurn() {

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		List<Move> moveList = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNull(moveList);
	}

	@Test
	@DisplayName("possible moves of black at the first turn")
	@Order(7)
	public void possibleMovesOfBlackFirstTurn() {

		// the possible moves of black at the first turn
		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(2, 3));
		moveList.add(new Move(3, 2));
		moveList.add(new Move(4, 5));
		moveList.add(new Move(5, 4));

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		for (Move move : possibleMoves) {
			int index = -1;
			for (int i = 0; i < moveList.size(); i++) {
				Move m = moveList.get(i);
				if (move.x == m.x && move.y == m.y) {
					index = i;
					break;
				}
			}
			Assertions.assertNotEquals(-1, index);
			moveList.remove(index);
		}
		Assertions.assertEquals(0, moveList.size());
	}

	@Test
	@DisplayName("possible moves of black after black played at (2,3)")
	@Order(8)
	public void possibleMovesOfBlackSecondTurn() {

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		boolean isValid = ((Game) gameObject).makeMove(true, 2, 3);
		Assertions.assertTrue(isValid);

		List<Move> moveList = ((Game) gameObject).getPossibleMoves(true);

		Assertions.assertNull(moveList);
	}

	@Test
	@DisplayName("possible moves of white after black played at (2,3)")
	@Order(9)
	public void possibleMovesOfWhiteSecondTurn() {

		// the possible moves of white at the second turn given black plays at (2,3)
		List<Move> moveList = new ArrayList<>();
		moveList.add(new Move(2, 2));
		moveList.add(new Move(4, 2));
		moveList.add(new Move(2, 4));

		Object gameObject = TestUtils.getNewOthelloObject();
		Assertions.assertNotNull(gameObject);

		boolean isValid = ((Game) gameObject).makeMove(true, 2, 3);
		Assertions.assertTrue(isValid);

		List<Move> possibleMoves = ((Game) gameObject).getPossibleMoves(false);

		Assertions.assertNotNull(possibleMoves);
		Assertions.assertEquals(moveList.size(), possibleMoves.size());

		for (Move move : possibleMoves) {
			int index = -1;
			for (int i = 0; i < moveList.size(); i++) {
				Move m = moveList.get(i);
				if (move.x == m.x && move.y == m.y) {
					index = i;
					break;
				}
			}
			Assertions.assertNotEquals(-1, index);
			moveList.remove(index);
		}
		Assertions.assertEquals(0, moveList.size());
	}

}
