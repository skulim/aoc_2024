package pl.mikie.aoc.day10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	record Coords(int x, int y) {
	};

	int[][] map;
	List<Coords> startPoints = new ArrayList<Coords>();
	Set<Coords> nines = new HashSet<Coords>();

	@Override
	public String dayNo() {
		return "10";
	}

	@Override
	public long part1() {

		return startPoints.stream()
				.peek(p -> nines.clear())
				.flatMap(p -> calculateNextMovePoint(p))
				.mapToLong(p -> nextMove(p, 1, false))
				.sum();
	}

	@Override
	public long part2() {
		return startPoints.stream()
				.flatMap(p -> calculateNextMovePoint(p))
				.mapToLong(p -> nextMove(p, 1, true))
				.sum();
	}

	// returns how many 9 has been reached
	private long nextMove(final Coords point, int nextNumber, boolean withRating) {
		if (map[point.x][point.y] == nextNumber) {
			if (nextNumber == 9) {
				if (!withRating && nines.contains(point)) {
					return 0;
				} else {
					nines.add(point);
					return 1;
				}
			}
			return calculateNextMovePoint(point).mapToLong(p -> nextMove(p, nextNumber + 1, withRating)).sum();
		}
		return 0;
	}

	private Stream<Coords> calculateNextMovePoint(Coords point) {
		List<Coords> nextPoints = new ArrayList<Coords>();
		Coords newPoint = new Coords(point.x - 1, point.y);
		if (isPointOnMap(newPoint)) {
			nextPoints.add(newPoint);
		}
		newPoint = new Coords(point.x + 1, point.y);
		if (isPointOnMap(newPoint)) {
			nextPoints.add(newPoint);
		}
		newPoint = new Coords(point.x, point.y - 1);
		if (isPointOnMap(newPoint)) {
			nextPoints.add(newPoint);
		}
		newPoint = new Coords(point.x, point.y + 1);
		if (isPointOnMap(newPoint)) {
			nextPoints.add(newPoint);
		}
		return nextPoints.stream();
	}

	private boolean isPointOnMap(Coords point) {
		return point.x >= 0 && point.x < map.length && point.y >= 0 && point.y < map[0].length;
	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);

		int i = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (map == null) {
				map = new int[(int) inputData.lines().count()][line.length()];
			}
			map[i] = line.chars().map(c -> c - 48).toArray();
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					startPoints.add(new Coords(i, j));
				}
			}
			i++;
		}
		scanner.close();
	}

}
