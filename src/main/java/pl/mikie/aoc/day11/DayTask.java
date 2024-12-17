package pl.mikie.aoc.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	long[] stones;

	Map<Long, long[]> numOfStonesByNumber = new HashMap<Long, long[]>();

	@Override
	public String dayNo() {
		return "11";
	}

	@Override
	public long part1() {
		Arrays.stream(stones).forEach(sn -> processStone(Long.valueOf(sn), 1, 25));

		return Arrays.stream(stones).map(sn -> numOfStonesByNumber.get(Long.valueOf(sn))[24]).sum();
	}

	@Override
	public long part2() {
		Arrays.stream(stones).forEach(sn -> processStone(Long.valueOf(sn), 1, 75));

		return Arrays.stream(stones).map(sn -> numOfStonesByNumber.get(Long.valueOf(sn))[74]).sum();
	}

	private long[] processStone(Long stoneNumber, int stepNo, int maxSteps) {
		long[] r = getNumberOfStonesByNumber(stoneNumber, 1 + maxSteps - stepNo);
		if (r != null) {
			return r;
		}

		List<Long> s1 = new ArrayList<Long>();
		if (stoneNumber == 0) {
			s1.add(1L);
		} else if (stoneNumber.toString().length() % 2 == 0) {
			String numStr = stoneNumber.toString();
			s1.add(Long.parseLong(numStr.substring(0, numStr.length() / 2)));
			s1.add(Long.parseLong(numStr.substring(numStr.length() / 2)));
		} else {
			s1.add(stoneNumber * 2024);
		}

		long[] result = new long[1 + maxSteps - stepNo];
		result[0] = s1.size();

		if (stepNo < maxSteps) {
			long[] ss = s1.stream().map(sn -> {
				long[] r1 = getNumberOfStonesByNumber(sn, 1 + maxSteps - stepNo);
				return r1 != null ? r1 : processStone(sn, stepNo + 1, maxSteps);
			}).reduce(new long[1 + maxSteps - stepNo - 1], (r1, r2) -> {
				for (int i = 0; i < r1.length; i++) {
					r1[i] = r1[i] + r2[i];
				}
				return r1;
			});
			for (int i = 0; i < ss.length; i++) {
				result[i + 1] = ss[i];
			}
		}

		if (numOfStonesByNumber.get(stoneNumber) == null
				|| numOfStonesByNumber.get(stoneNumber).length < result.length) {
			numOfStonesByNumber.put(stoneNumber, result);
		}

		return result;
	}

	private long[] getNumberOfStonesByNumber(long number, int numberOfSteps) {
		if (numOfStonesByNumber.get(number) != null && numOfStonesByNumber.get(number).length >= numberOfSteps) {
			return Arrays.stream(numOfStonesByNumber.get(number), 0, numberOfSteps).toArray();
		}
		return null;
	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			stones = Arrays.stream(line.split(" ")).filter(n -> !n.equals("")).mapToLong(n -> Long.parseLong(n))
					.toArray();
		}
		scanner.close();
	}

}
