package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.BasicBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class BasicTest {

	/**
	 * Test if a class exists that contains the tictactoe model and implements the game interface.
	 */
	@Test
	@DisplayName("only one model class that implements the game interface exists")
	public void modelClassExists() {
		Assertions.assertNotNull(TestUtils.getOthelloClass());
	}

	/**
	 * Test if the model class extends the basic board class.
	 */
	@Test
	@DisplayName("model class extends the basic board class")
	public void modelClassExtendsBasicBoard() {
		Class<?> gameClass = TestUtils.getOthelloClass();
		Assertions.assertNotNull(gameClass);
		Assertions.assertTrue(BasicBoard.class.isAssignableFrom(gameClass));
	}

	/**
	 * Test the toString() method of the model class for format.
	 * Must return a string that contains 8 lines with 15 chars that consist of either [.XO] separated by a single space.
	 */
	@Test
	@DisplayName("toString() method returns board in correct format")
	public void testToString() {

		Object gameObject = TestUtils.getNewOthelloObject();

		String boardState = ((Game) gameObject).toString();

		Assertions.assertNotNull(boardState);

		String[] lines = boardState.split("\n");

		// string should contain exactly 8 lines (rows)
		Assertions.assertEquals(8, lines.length);

		// all lines must consist of exactly 15 chars, 8 times one of [.XO] and 7 spaces
		for (String line : lines) {
			Assertions.assertEquals(15, line.length());

			String[] lineItems = line.split(" ");

			Assertions.assertEquals(8, lineItems.length);

			for (String lineItem : lineItems) {
				switch (lineItem) {
					case ".":
					case "X":
					case "O":
						continue;
					default:
						Assertions.fail("print method contains invalid characters");
				}
			}
		}

		try {

			int[][] board = TestUtils.parseOthelloString(boardState);

			Assertions.assertNotNull(board);

		} catch (ParseException e) {
			e.printStackTrace();
			Assertions.fail("could not parse board");
		}
	}
}
