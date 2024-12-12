package pl.mikie.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.mikie.aoc.day8.DayTask;

public class AOCTaskRunner {

	public static void main(String[] args) throws IOException {
		AOCDayTask dayTask = new DayTask();
		
		String inputData = loadInputData(dayTask.dayNo());
		dayTask.parseInputData(inputData);
		
		long startTimeInNano = System.nanoTime();
		System.out.println("Day"+dayTask.dayNo()+" solutions\n");

		System.out.println("Part 1");
		System.out.println("result:"+dayTask.part1());
		System.out.println("process time:"+((System.nanoTime()-startTimeInNano)/1000000.0)+"ms");

		startTimeInNano = System.nanoTime();
		System.out.println("");
		System.out.println("Part 2");
		System.out.println("result:"+dayTask.part2());
		System.out.println("process time:"+((System.nanoTime()-startTimeInNano)/1000000.0)+"ms");
	}

	public static String loadInputData(String dayNo) throws IOException {
	    StringBuilder inputData = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(AOCTaskRunner.class.getResourceAsStream("/day"+dayNo+"/input.txt")))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	inputData.append(line);
	        	inputData.append(System.lineSeparator());
	        }
	    }
		
		return inputData.toString();
	}
}
