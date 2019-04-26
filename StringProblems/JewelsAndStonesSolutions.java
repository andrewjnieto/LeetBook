import java.util.HashSet;
import java.util.Set;

public class JewelsAndStonesSolutions {
	public static int numJewelsInStones(String J, String S) {
		if (J.length() == 0 || S.length() == 0) {
			return 0;
		}
		char character;
		int count = 0;
		for (int i = 0; i < J.length(); i++) {
			character = J.charAt(i);
			for (int j = 0; j < S.length(); j++) {
				if (S.charAt(j) == character) {
					count++;
				}
			}
		}
		return count;
	}
	
    public int numJewelsInStonesHashing(String J, String S) {
        if (J.length() == 0 || S.length() == 0) {
			return 0;
		}
        Set<Character> set = new HashSet<Character>();
        for(Character jewel : J.toCharArray()){
            set.add(jewel);
        }
        int totalStoneCount = 0;
        for(Character stone : S.toCharArray()){
            if(set.contains(stone)){
                ++totalStoneCount;
            }
        }
        return totalStoneCount;
    }
}
