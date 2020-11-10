package util;

import java.util.HashSet;
import java.util.Set;

public class Math {
	
	public static boolean rangesIntersect(int s1, int e1, int s2, int e2) {
		
		Set<Integer> range1 = new HashSet<Integer>();
		
		for(int i = s1; i <= e1; i++) {
			range1.add(i);
		}
		
		Set<Integer> range2 = new HashSet<Integer>();
		
		for(int i = s2; i <= e2; i++) {
			range2.add(i);
		}
		
		range1.retainAll(range2);
		
		return range1.size() != 0;
	}

}
