package pl.mikie.aoc.day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	record Coords (int x, int y) {};
	
	private Map<Character, List<Coords>> map = new HashMap<>();
	private Set<Coords> allAntenaCoords = new HashSet<>();
	private Set<Coords> anitnodeCoords = new HashSet<>();
	private long maxX, maxY;
	
	@Override
	public String dayNo() {
		return "8";
	}

	@Override
	public long part1() {
		map.entrySet().stream().forEach(e -> {
			for(int i=0; i<e.getValue().size()-1; i++) {
				for(int j=i; j<e.getValue().size()-1; j++) {
					anitnodeCoords.addAll(getAntinodes(e.getValue().get(i),e.getValue().get(j+1), false));
				}
			}
		});
		
		return anitnodeCoords.size();
	}

	@Override
	public long part2() {
		map.entrySet().stream().forEach(e -> {
			for(int i=0; i<e.getValue().size()-1; i++) {
				for(int j=i; j<e.getValue().size()-1; j++) {
					anitnodeCoords.addAll(getAntinodes(e.getValue().get(i),e.getValue().get(j+1), true));
				}
			}
		});
		
		return anitnodeCoords.size();
	}

	private Set<Coords> getAntinodes(Coords antena1, Coords antena2, boolean useResonant){
		Set<Coords> anitnodeCoords = new HashSet<>();
		
		int antinodeXDiff = antena1.x - antena2.x;
		int antinodeYDiff = antena1.y - antena2.y;
		int antinodeX = antena1.x + antinodeXDiff;
		int antinodeY = antena1.y + antinodeYDiff;
		
		while(antinodeX>=0 && antinodeX<maxX && antinodeY>=0 && antinodeY<maxY) {
			anitnodeCoords.add(new Coords(antinodeX, antinodeY));	
			
			if( !useResonant ) break;
			antinodeX += antinodeXDiff;
			antinodeY += antinodeYDiff;
		}
		
		antinodeXDiff = antena2.x - antena1.x;
		antinodeYDiff = antena2.y - antena1.y;
		antinodeX = antena2.x + antinodeXDiff;
		antinodeY = antena2.y + antinodeYDiff;
		
		while(antinodeX>=0 && antinodeX<maxX && antinodeY>=0 && antinodeY<maxY) {
			anitnodeCoords.add(new Coords(antinodeX, antinodeY));			
			
			if( !useResonant ) break;
			antinodeX += antinodeXDiff;
			antinodeY += antinodeYDiff;
		}
		
		if( useResonant ) {
			anitnodeCoords.add(antena1);			
			anitnodeCoords.add(antena2);			
		}
		return anitnodeCoords;
	}
	
	
	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);
		
		int x=0;
		maxX = inputData.lines().count();
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			maxY = line.length();
			for(int y=0; y<line.length(); y++) {
				if(line.charAt(y)!='.') {
					if(map.get(line.charAt(y))==null) {
						map.put(line.charAt(y), new ArrayList<>());
					}
					map.get(line.charAt(y)).add(new Coords(x, y));
					allAntenaCoords.add(new Coords(x, y));
				}
			}
			x++;
		}
		
		scanner.close();
	}

}
