package Service;

import dataStructures.Predicate;


public class ServiceFilter implements Predicate<Service> {

    /**
     * Tag that will be the criteria to check
     */
    private final char[] tag;   //Array of chars of the tag

    //Array of integers that stores the biggest suffix that is also a prefix in that position to avoid redundant comparison
    private final int[] lps;

    /**
     * Constructor of a new Service Filter
     * @param tag: Word that, at least, one service received in their description
     */
    public ServiceFilter(char[] tag){

        this.tag = tag;
        this.lps = new int[tag.length];
        createLPS(tag);
    }

    /**
     * Converts the character given to its lowercase equivalent using ASCII
     * @param c: Char that can be upper or lower case
     * @return the lower case equivalent of the char
     */
    private char lowerCaseChar(char c){
        if(c >= 'A' && c <='Z') return (char)(c + 32);
        return c;
    }

    /**
     * Creates the Longest Prefix which is also Suffix(lps) with the tag of the Filter
     * Best Case: O(1);
     * Worst Case: O(n), n being the length of the pattern
     * Average Case: O(n), n being the length of the pattern
     * @param pattern: tag to filter
     */
    private void createLPS(char[] pattern){
        int length = 0;
        int i = 1;
        lps[0] = 0;
        while (i < pattern.length) {
            if (pattern[i] == pattern[length]) {    //Same chars
                length++;
                lps[i] = length;
                i++;
            } else {
                //Different chars
                if (length != 0) {  //Already have a Prefix that is a Suffix
                    length = lps[length - 1];
                } else {    //Char is a new char in the pattern
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    /**
     * Uses the KMP Search Algorithm to find if the tag exists in the text
     * Best Case: O(m), m being the length of the tag;
     * Worst Case: O(n + m), n being the length of the text and m the length of the text;
     * Average Case: O(n + m), n being the length of the text and m the length of the text;
     * @param text: Combination of all tags given to a service
     * @return true if the tag appears in the text, false otherwise
     */
    public boolean kmpSearchBoolean(String text) {
        if (text.isEmpty()) {
            return false;
        }
        int i = 0;  //text index
        int j = 0;  //tag index

        while (i < text.length()) {
            char cI = lowerCaseChar(text.charAt(i));
            char cJ = lowerCaseChar(tag[j]);
            if (cJ == cI) { //chars Match
                i++;
                j++;
            }

            if (j == tag.length) {  //Reach the end of the tag
                boolean validStart = (i - j == 0) || Character.isWhitespace(text.charAt(i - j - 1));
                boolean validEnd = (i == text.length()) || Character.isWhitespace(text.charAt(i));

                if (validStart && validEnd) {
                    //Verifies if the word is actually the tag and not a part of a bigger word
                    return true;
                }

                //Continues searching to find other occurrences, j restarts using LPS information
                j = lps[j - 1];
            } else if (i < text.length() && cJ != cI) {
                if (j != 0) {
                    //Doesn't go back to the beginning of the tag, uses LPS information to know where to continue the search
                    j = lps[j - 1];
                } else {
                    //Searches next char in the text
                    i++;
                }
            }
        }
        return false;
    }

    /**
     * Filter that an element needs to check
     * Best Case: O(m), m being the length of the tag;
     * Worst Case: O(n + m), n being the length of the text and m the length of the text;
     * Average Case: O(n + m), n being the length of the text and m the length of the text;
     * @param elem: Service that needs to be checked
     * @return if the tag was used in any of the services evaluations descriptions
     */
    @Override
    public boolean check(Service elem) {
        return kmpSearchBoolean(elem.getAllTags());
    }
}
