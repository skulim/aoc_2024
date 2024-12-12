package pl.mikie.aoc.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	private List<calibrationData> calibrationEquations = new ArrayList<>();
	record calibrationData (long result, List<Long> numbers) {};
	
	@Override
	public String dayNo() {
		return "7";
	}

	@Override
	public long part1() {
		return calibrationEquations.stream()
				.mapToLong(
						e -> calculate(e.result(), e.numbers().get(0), 1, e.numbers(), false))
				.sum();
	}

	@Override
	public long part2() {
		return calibrationEquations.stream()
				.mapToLong(
						e -> calculate(e.result(), e.numbers().get(0), 1, e.numbers(), true))
				.sum();
	}

	record OperatorWithResult (long result, boolean multiply) {};
	
    private long calculate(long result, long partResult, int numberIndex, List<Long> numbers, boolean useConcat) {
		if( partResult > result ) {
			return 0;
		}
		
		if( numberIndex==numbers.size() ) {
			return partResult==result ? result : 0;
		}

    	long partResultWithAdd = calculate(result, partResult + numbers.get(numberIndex), numberIndex+1, numbers, useConcat);
    	
       	long partResultWithMul = calculate(result, partResult * numbers.get(numberIndex), numberIndex+1, numbers, useConcat);
       	
       	long partResultWithConcat = 0;
       	if(useConcat) {
       		partResultWithConcat = calculate(result, Long.valueOf("" + partResult + numbers.get(numberIndex)), numberIndex+1, numbers, useConcat);
       	}
		
		if(partResultWithAdd==result || partResultWithMul==result || partResultWithConcat==result) {
			return result;
		}
		return 0;
	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);
		
		while (scanner.hasNextLine()) {
			String[] numbers = scanner.nextLine().split(":");
			calibrationEquations.add(new calibrationData(Long.valueOf(numbers[0]), 
					Arrays.stream(
							numbers[1].split(" "))
					.map(n -> n.equals("") ? null : Long.parseLong(n))
					.filter(Objects::nonNull)
					.collect(Collectors.toList())));
		}

		scanner.close();
	}

}
