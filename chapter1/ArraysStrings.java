import java.util.Arrays;

public class ArraysStrings {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    static final String EQL_STR = " must be equal to ";

    /* 1-1 */
    static boolean isUniqueChars(String str) {
        if (str.length() > 128) return false;
        
        boolean[] charSet = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (charSet[val]) return false;
            charSet[val] = true;
        }
        
        return true;
    }

    /* 1-2 */
    private static String sortString(String str) {
        char[] charArr = str.toCharArray();
        Arrays.sort(charArr);
        return new String(charArr);
    }

    static boolean checkPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        return sortString(s1).equals(sortString(s2));
    }

    /* 1-3 */
    static void urlify(char[] str, int len) {
        int i, j, numSpaces = 0;

        for (i = 0; i < len; i++)
            if (str[i] == ' ') numSpaces++;
        
        j = len + 2*numSpaces - 1;
        for (i = len-1; i >= 0; i--) {
            if (str[i] != ' ') {
                str[j] = str[i];
                j--;
            } else {
                str[j] = '0';
                str[j-1] = '2';
                str[j-2] = '%';
                j -= 3;
            }
        }
    }

    /* 1-4 */
    private static boolean isAlphabet(Character c) {
        int val = Character.getNumericValue(c);
        return (Character.getNumericValue('a') <= val && val <= Character.getNumericValue('z'));
    }

    static boolean isPermutationOfPalindrome(String str) {
        int[] alphabet = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
        for (char c: str.toCharArray())
            if (isAlphabet(c)) alphabet[Character.toLowerCase(c) - 'a']++;
        
        boolean oddChars = false;
        for (int x: alphabet) {
            if (x % 2 != 0) {
                if (oddChars) return false;
                oddChars = true;
            }
        }
        return true;
    }

    /* 1-5 */
    static boolean checkLessThanOneEdit(String str1, String str2) {
        if (Math.abs(str1.length() - str2.length()) > 1) return false;
        String s1 = str1.length() > str2.length() ? str1 : str2;
        String s2 = str1.length() > str2.length() ? str2 : str1;

        boolean diff = false;
        for (int i = 0, j = 0; i < s1.length() && j < s2.length(); i++) {
            if (s1.charAt(i) != s2.charAt(j)) {
                if (diff) return false;
                diff = true;
                if (s1.length() == s2.length()) j++;
            } else {
                j++;
            }
        }
        return true;
    }

    /* 1-6 */
    static String compressString(String s) {
        StringBuilder ans = new StringBuilder();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            count++;
            if (i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)) {
                ans.append(s.charAt(i));
                ans.append(count);
                count = 0;
            } 
        }

        return ans.length() < s.length() ? ans.toString() : s;
    }

    /* 1-7 */
    static boolean rotateMatrix(int[][] mat) {
        if (mat.length == 0 || mat.length != mat[0].length) return false;
        int temp;
        for (int i = 0; i < mat.length/2; i++) {
            for (int j = i; j < mat.length-i-1; j++) {
                temp = mat[j][mat.length-i-1];
                mat[j][mat.length-i-1] = mat[i][j];
                mat[i][j] = mat[mat.length-j-1][i];
                mat[mat.length-j-1][i] = mat[mat.length-i-1][mat.length-j-1];
                mat[mat.length-i-1][mat.length-j-1] = temp;
            }
        }
        return true;
    }

    /* 1-8 */
    private static void nullify(int[][] mat, int idx, boolean opt) {
        int n = opt ? mat.length : mat[0].length;
        for (int i = 0; i < n; i++) {
            if (opt) mat[i][idx] = 0;
            else mat[idx][i] = 0;
        }
    }

    private static boolean checkFirstRowCol(int[][] mat, boolean opt) {
        int val, n = opt ? mat.length : mat[0].length;
        for (int i = 0; i < n; i++) {
            val = opt ? mat[i][0] : mat[0][i];
            if (val == 0) return true;
        }
        return false;
    }

    static void zeroMatrix(int[][] mat) {
        boolean rowHasZero = checkFirstRowCol(mat, false);
        boolean colHasZero = checkFirstRowCol(mat, true);

        for (int i = 1; i < mat.length; i++) {
            for (int j = 1; j < mat[0].length; j++) {
                if (mat[i][j] == 0) {
                    mat[i][0] = 0;
                    mat[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < mat.length; i++)
            if (mat[i][0] == 0) nullify(mat, i, false);

        for (int i = 1; i < mat[0].length; i++)
            if (mat[0][i] == 0) nullify(mat, i, true);

        if (rowHasZero) nullify(mat, 0, false);
        if (colHasZero) nullify(mat, 0, true);
    }

    /* 1-9 */
    static boolean isStringRotation(String s1, String s2) {
        if (s1.length() == 0 || s1.length() != s2.length()) return false;
        s1 += s1;
        return s1.contains(s2); 
    }

    public static void main(String[] args) {
        System.out.print("1-1: ");
        assert isUniqueChars("abcdef") : "'abcdef' is unique";
        assert !isUniqueChars("aa") : "'aa' is not unique";
        assert !isUniqueChars("abcc") : "'abcc' is not unique";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-2: ");
        assert checkPermutation("abc", "cba") : "'cba' is 'abc' permutation";
        assert !checkPermutation("abc", "def") : "'def' is not 'abc' permutation";
        assert !checkPermutation("abc", "cbad") : "'cbad' is not 'abc' permutation";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-3: ");
        char[] testString = "Mr John Smith    ".toCharArray();
        urlify(testString, 13);
        assert (new String(testString)).equals("Mr%20John%20Smith") : "result must equals to 'Mr%20John%20Smith'";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-4: ");
        assert isPermutationOfPalindrome("Tact Coa") : "'Tact Coa' is a permutation of a palindrome";
        assert !isPermutationOfPalindrome("Tactt Coa") : "'Tactt Coa' is not a permutation of a palindrome";
        assert !isPermutationOfPalindrome("code") : "'code' is not a permutation of a palindrome";
        assert isPermutationOfPalindrome("aab") : "'aab' is a permutation of a palindrome";
        assert isPermutationOfPalindrome("carerac") : "'carerac' is a permutation of a palindrome";
        assert isPermutationOfPalindrome("abab") : "'abab' is a permutation of a palindrome";
        assert isPermutationOfPalindrome("tactcoapapa") : "'tactcoapapa' is a permutation of a palindrome";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-5: ");
        assert checkLessThanOneEdit("pale", "ple") : "'pale' and 'ple' is one edit away";
        assert checkLessThanOneEdit("pales", "pale") : "'pales' and 'pale' is one edit away";
        assert checkLessThanOneEdit("pale", "bale") : "'pale' and 'bale' is one edit away";
        assert !checkLessThanOneEdit("pale", "bake") : "'pale' and 'bake' is more than one edit away";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-6: ");
        assert compressString("aabcccccaaa").equals("a2b1c5a3") : "'aabcccccaaa' will be compressed into 'a2b1c5a3'";
        assert compressString("aa").equals("aa") : "'aa' will be compressed into 'aa'";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-7: ");
        int[][] testData = {{1,2,3}, {4,5,6}, {7,8,9}};
        int[][] ans = {{7,4,1}, {8,5,2}, {9,6,3}};
        rotateMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        testData = new int[][]{{1,2}, {3,4}};
        ans = new int[][]{{3,1}, {4,2}};
        rotateMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        testData = new int[][]{{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
        ans = new int[][]{{13,9,5,1}, {14,10,6,2}, {15,11,7,3}, {16,12,8,4}};
        rotateMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-8: ");
        testData = new int[][]{{0,2,3}, {4,5,6}};
        ans = new int[][]{{0,0,0}, {0,5,6}};
        zeroMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        testData = new int[][]{{1,2,3}, {4,0,6}, {7,8,9}, {0,11,12}};
        ans = new int[][]{{0,0,3}, {0,0,0}, {0,0,9}, {0,0,0}};
        zeroMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        testData = new int[][]{{1,2,0,4,5}, {6,7,8,9,10}, {0,12,13,14,15}};
        ans = new int[][]{{0,0,0,0,0}, {0,7,0,9,10}, {0,0,0,0,0}};
        zeroMatrix(testData);
        assert Arrays.deepEquals(testData, ans) : Arrays.deepToString(testData) + EQL_STR + Arrays.deepToString(ans);
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("1-9: ");
        assert isStringRotation("waterbottle", "erbottlewat") : "'watterbottle' is a rotation of 'erbottlewat'";
        assert !isStringRotation("waterbottl", "erbottlewa") : "'watterbottl' is not a rotation of 'erbottlewa'";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}