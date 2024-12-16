package pl.mikie.aoc.day10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import pl.mikie.aoc.AOCDayTask;
import pl.mikie.aoc.DayTaskTestUtils;

public class DayTaskTest {
	AOCDayTask aocDayTask = new DayTask();
	static String inputData;
	
	@SneakyThrows
	@BeforeAll
	public static void beforeAll() {
		inputData = DayTaskTestUtils.loadInputData("10");
	}
	
	@Test
	public void part1Test() {
		aocDayTask.parseInputData(inputData);
		long result = aocDayTask.part1();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(36, result);
	}
	
	@Test
	public void part2Test() {
		aocDayTask.parseInputData(inputData);
		long result = aocDayTask.part2();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(81, result);
	}
}
