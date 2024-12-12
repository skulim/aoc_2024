package pl.mikie.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.mikie.aoc.AOCTaskRunner;

public class DayTaskTestUtils {
	
	public static String loadInputData(String dayNo) throws IOException {
	    StringBuilder inputData = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(AOCTaskRunner.class.getResourceAsStream("/day"+dayNo+"/input_test.txt")))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	inputData.append(line);
	        	inputData.append(System.lineSeparator());
	        }
	    }
		
		return inputData.toString();
	}

}
