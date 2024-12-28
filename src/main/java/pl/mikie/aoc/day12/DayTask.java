package pl.mikie.aoc.day12;

import java.util.Arrays;
import java.util.Scanner;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	int[][] map, originMap;

	@Override
	public String dayNo() {
		return "12";
	}

	@Override
	public long part1() {
		long sum = 0;
		map = Arrays.stream(originMap).map(int[]::clone).toArray(int[][]::new);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (map[x][y] < 100) {
					long[] gardenParams = processGarden(map[x][y], x, y, false);
					sum += gardenParams[0] * gardenParams[1];
				}
			}
		}
		return sum;
	}

	@Override
	public long part2() {
		long sum = 0;
		map = Arrays.stream(originMap).map(int[]::clone).toArray(int[][]::new);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (map[x][y] < 100) {
					long[] gardenParams = processGarden(map[x][y], x, y, true);
					sum += gardenParams[0] * gardenParams[1];
				}
			}
		}
		return sum;
	}

	private long[] processGarden(int gardenNo, int x, int y, boolean slides) {
		long[] gardenParams = new long[] { 1, 0 };
		long[] gardenParamsFromRecursive;

		long perimeterForCurrent = 4;
		map[x][y] = 100 + gardenNo;

		if (x > 0 && map[x - 1][y] % 100 == gardenNo) {
			if (map[x - 1][y] == gardenNo) {
				gardenParamsFromRecursive = processGarden(gardenNo, x - 1, y, slides);
				gardenParams[0] += gardenParamsFromRecursive[0];
				gardenParams[1] += gardenParamsFromRecursive[1];
			}
			perimeterForCurrent--;
		}
		if (x < map.length - 1 && (map[x + 1][y] % 100) == gardenNo) {
			if (map[x + 1][y] == gardenNo) {
				gardenParamsFromRecursive = processGarden(gardenNo, x + 1, y, slides);
				gardenParams[0] += gardenParamsFromRecursive[0];
				gardenParams[1] += gardenParamsFromRecursive[1];
			}
			perimeterForCurrent--;
		}
		if (y > 0 && map[x][y - 1] % 100 == gardenNo) {
			if (map[x][y - 1] == gardenNo) {
				gardenParamsFromRecursive = processGarden(gardenNo, x, y - 1, slides);
				gardenParams[0] += gardenParamsFromRecursive[0];
				gardenParams[1] += gardenParamsFromRecursive[1];
			}
			perimeterForCurrent--;
		}
		if (y < map[0].length - 1 && map[x][y + 1] % 100 == gardenNo) {
			if (map[x][y + 1] == gardenNo) {
				gardenParamsFromRecursive = processGarden(gardenNo, x, y + 1, slides);
				gardenParams[0] += gardenParamsFromRecursive[0];
				gardenParams[1] += gardenParamsFromRecursive[1];
			}
			perimeterForCurrent--;
		}

		gardenParams[1] += slides ? slideNo(gardenNo, x, y) : perimeterForCurrent;

		return gardenParams;
	}

	public int slideNo(int gardenNo, int x, int y) {
		int slides = 0;
		//up
		if(!isGardenPart(gardenNo, x-1, y) && (!isGardenPart(gardenNo, x, y-1) || (isGardenPart(gardenNo, x, y-1) && isGardenPart(gardenNo, x-1, y-1)))) {
			slides++;
		}
		//right
		if(!isGardenPart(gardenNo, x, y+1) && (!isGardenPart(gardenNo, x-1, y) || (isGardenPart(gardenNo, x-1, y) && isGardenPart(gardenNo, x-1, y+1)))) {
			slides++;
		}
		//down
		if(!isGardenPart(gardenNo, x+1, y) && (!isGardenPart(gardenNo, x, y+1) || (isGardenPart(gardenNo, x, y+1) && isGardenPart(gardenNo, x+1, y+1)))) {
			slides++;
		}
		//left
		if(!isGardenPart(gardenNo, x, y-1) && (!isGardenPart(gardenNo, x+1, y) || (isGardenPart(gardenNo, x+1, y) && isGardenPart(gardenNo, x+1, y-1)))) {
			slides++;
		}
		
		return slides;
	}
	
	public boolean isGardenPart(int gardenNo, int x, int y) {
		return !(x<0 || y<0 || x>=map.length || y>=map[0].length || (map[x][y]!=gardenNo && map[x][y]!=(100+gardenNo)));
	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);

		int i = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (originMap == null) {
				originMap = new int[(int) inputData.lines().count()][line.length()];
			}
			originMap[i] = line.chars().toArray();
			i++;
		}
		scanner.close();
	}

}
