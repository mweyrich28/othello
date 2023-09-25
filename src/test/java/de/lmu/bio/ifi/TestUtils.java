package de.lmu.bio.ifi;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Constructor;
import java.text.ParseException;

public class TestUtils {

	public static ClassInfoList getClassesImplementingGameInterface() {
		try (ScanResult scanResult = new ClassGraph()
				.enableAllInfo().acceptPackages("de.lmu.bio.ifi").scan()) {

			return scanResult.getClassesImplementing("de.lmu.bio.ifi.Game");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Class<?> getOthelloClass() {

		ClassInfoList widgetClasses = getClassesImplementingGameInterface();

		if (widgetClasses == null || widgetClasses.size() != 1) {
			return null;
		}

		try {
			return Class.forName(widgetClasses.get(0).getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Object getNewOthelloObject() {

		Class<?> iClass = getOthelloClass();

		try {

			Constructor<?> constr = iClass.getConstructor();

			return constr.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int[][] parseOthelloString(String boardString) throws ParseException {

		int[][] board = new int[8][8];

		String[] lines = boardString.split("\n");

		if (lines.length != 8) {
			throw new ParseException("not 8 lines", 0);
		}

		for (int i = 0; i < lines.length; i++) {

			String[] lineItems = lines[i].split(" ");

			if (lineItems.length != 8) {
				throw new ParseException("not 8 columns in row" + i, 0);
			}

			for (int j = 0; j < lineItems.length; j++) {

				switch (lineItems[j]) {
					case ".":
						board[i][j] = 0;
						break;
					case "X":
						board[i][j] = 1;
						break;
					case "O":
						board[i][j] = 2;
						break;
					default:
						throw new ParseException("contains invalid character: " + lineItems[j], 0);
				}
			}
		}

		return board;
	}
}
