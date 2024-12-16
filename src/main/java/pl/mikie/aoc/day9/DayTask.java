package pl.mikie.aoc.day9;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.LongStream;

import pl.mikie.aoc.AOCDayTask;

public class DayTask implements AOCDayTask {

	record Coords(int x, int y) {
	};

	private int[] disks;
	Map<Integer, Integer> fileMoved = new HashMap<Integer, Integer>();

	@Override
	public String dayNo() {
		return "9";
	}

	@Override
	public long part1() {
		int[] part1DiskData = disks.clone();
		long sum = 0;
		int fileIdNo = 0, leftIdx = 0,
				rightFileIdx = part1DiskData.length % 2 == 0 ? part1DiskData.length - 2 : part1DiskData.length - 1, num, num1;
		boolean isFile = true;

		while (leftIdx <= rightFileIdx) {
			num = part1DiskData[leftIdx];
			if (isFile) {
				final long diskNo = (leftIdx / 2); 
				sum += diskNo * (LongStream.range(fileIdNo, fileIdNo + num).sum());
				fileIdNo += num;
			} else {
				while (num > 0 && rightFileIdx > leftIdx) {
					num1 = part1DiskData[rightFileIdx];
					part1DiskData[rightFileIdx] = Math.max(0, num1 - num);
					final long diskNo = (rightFileIdx / 2); 
					sum += diskNo * (LongStream.range(fileIdNo, fileIdNo + Math.min(num1, num)).sum());
					fileIdNo += Math.min(num1, num);
					num -= num1;
					if (part1DiskData[rightFileIdx] == 0) {
						rightFileIdx -= 2;
					}
				}
			}
			leftIdx++;
			isFile = !isFile;
		}
		return sum;
	}

	@Override
	public long part2() {
		int[] part2DiskData = disks.clone();
		long sum = 0;
		int fileIdNo = 0, leftIdx = 0,
				rightFileIdx = part2DiskData.length % 2 == 0 ? part2DiskData.length - 2 : part2DiskData.length - 1, rightIdx, num, num1;
		boolean isFile = true;

		while (leftIdx <= rightFileIdx) {
			num = part2DiskData[leftIdx];
			if (isFile) {
				if(num==0) {
					if( fileMoved.get(leftIdx)!=null) {
						fileIdNo += fileMoved.get(leftIdx);
					}
				} else {
					final long diskNo = (leftIdx / 2); 
					sum += diskNo * (LongStream.range(fileIdNo, fileIdNo + num).sum());
					fileIdNo += num;
				}
			} else {
				rightIdx = rightFileIdx;
				while (num > 0 && rightIdx > leftIdx) {
					num1 = part2DiskData[rightIdx];
					if(num>=num1 && num1>0) {
						part2DiskData[rightIdx] = Math.max(0, num1 - num);
						fileMoved.put(rightIdx, num1);
						final long diskNo = (rightIdx / 2); 
						sum += diskNo * (LongStream.range(fileIdNo, fileIdNo + Math.min(num1, num)).sum());
						fileIdNo += Math.min(num1, num);
						num -= num1;
						if (rightIdx == rightFileIdx) {
							rightFileIdx -= 2;
						}
					}
					rightIdx -= 2;
				}
				if(num>0) {
					fileIdNo += num;
				}
			}
			leftIdx++;
			isFile = !isFile;
		}
		return sum;
	}

	@Override
	public void parseInputData(String inputData) {
		Scanner scanner = new Scanner(inputData);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (disks == null) {
				disks = new int[line.length()];
			}
			disks = line.chars().map(c -> c - 48).toArray();

		}
		scanner.close();
	}

}
