public class LenLongestSub {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }
        StringBuilder current = new StringBuilder();
        int globalLength = -1;
        for(int index = 0; index < s.length(); index++){
            char curChar = s.charAt(index);
            if(current.indexOf(curChar + "") == -1){
                current.append(curChar);
            }else{
                if(current.length() > globalLength){
                    globalLength = current.length();
                }
                current = current.delete(0, current.indexOf(curChar+ "")+1);
                current.append(curChar);
            }
        }
        return (current.length() > globalLength) ? current.length() : globalLength;
    }
}
