package pl.mikie.aoc.day6;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	static int[][] directionStep = new int[4][2];
	static {
		directionStep[0] = new int[] { -1, 0 };
		directionStep[1] = new int[] { 0, 1 };
		directionStep[2] = new int[] { 1, 0 };
		directionStep[3] = new int[] { 0, -1 };
	}

	record Coords(int row, int col) {
	};

	record CoordsWithDirection(Coords coords, int direction) {
	};

	private CoordsWithDirection guardStart;
	private char[][] map;

	@Override
	public String dayNo() {
		return "6";
	}

	@Override
	public long part1() {
		Set<Coords> positions = process(false);
		System.out.println("Visited positions: " + positions.size());
		return positions.size();
	}

	@Override
	public long part2() {
		Set<Coords> positions = process(false);
		int cycleCount = 0;

		for (Coords c : positions) {
			map[c.row][c.col] = '#';

			positions = process(true);
			cycleCount += positions.size();

			map[c.row][c.col] = '.';
		}

		System.out.println("Obstruction Positions: " + cycleCount);
		return cycleCount;
	}

	private Set<Coords> process(boolean checkLoop) {
		Set<Coords> visited = new HashSet<>();
		Set<CoordsWithDirection> visitedWithDirection = new HashSet<>();

		Coords currentPosition = guardStart.coords;
		int currentDirection = guardStart.direction;
		int nextRow = -1, nextCol = -1;
		do {
			nextRow = currentPosition.row + directionStep[currentDirection][0];
			nextCol = currentPosition.col + directionStep[currentDirection][1];

			if (nextRow < 0 || nextCol < 0 || nextRow >= map.length || nextCol >= map[0].length) {
				break;
			}

			if (map[nextRow][nextCol] == '#') {
				currentDirection = (currentDirection + 1) % 4;
			} else {
				currentPosition = new Coords(nextRow, nextCol);
				if (!checkLoop) {
					visited.add(new Coords(nextRow, nextCol));
				}
			}

			if (checkLoop) {
				CoordsWithDirection cd = new CoordsWithDirection(new Coords(nextRow, nextCol), currentDirection);
				if (visitedWithDirection.contains(cd)) {
					visited.add(new Coords(nextRow, nextCol));
					break;
				}
				visitedWithDirection.add(cd);
			}
		} while (true);

		return visited;

	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);
		map = new char[Math.toIntExact(inputData.lines().count())][inputData.lines().findFirst().map(l -> l.length())
				.get()];
		int row = 0;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			char[] arr = line.toCharArray();
			map[row++] = arr;

			if (line.contains("^")) {
				guardStart = new CoordsWithDirection(new Coords(row, line.indexOf("^")), 0);
			}
		}

		scanner.close();
	}

}
