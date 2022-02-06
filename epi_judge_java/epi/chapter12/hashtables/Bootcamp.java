package epi.chapter12.hashtables;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Anagrams.
 * "eleven plus two" is an anagram for "twelve plus one"
 * Given a set of words, return groups of anagrams for those words.
 * Each group must contain at least two words.
 * For example, given "debitcard", "elvis", "silent", "badcredit", "lives"
 * "freedom", "listen", "levis", "money", the output should be
 * - group 1: "debitcard", "badcredit",
 * - group 2: "elvis", "lives", "levis"
 * - group 3: "silent","listen"
 * Note that "money" does not appear in any group, since it has no anagrams in the set.
 *
 * Variant: Design an O(nm) algorithm for the same problem, assuming strings are made up
 * of lower case English characters.
 */
public class Bootcamp {

    /**
     * Suppose all the groups have different number of characters
     *
     * @param dictionary
     * @return
     */
    private List<List<String>> findAnagrams(List<String> dictionary) {
        Map<Integer, List<String>> results = new HashMap<>();
        Map<Integer, Map<Character, Integer>> charsSizeHashMaps = new HashMap<>();
        for (String word: dictionary) {
            // Check the hash table
            // If it is a word with new size
            if (!charsSizeHashMaps.containsKey(word.length())) {
                charsSizeHashMaps.put(word.length(), createCharsHashMap(word));
                storeWordInResultsList(word, results);
            } else {
                // If the characters already exist
                Map<Character, Integer> characterIntegerMap = charsSizeHashMaps.get(word.length());
                Map<Character, Integer> newWordCharacterIntegerMap = createCharsHashMap(word);
                if (characterIntegerMap.equals(newWordCharacterIntegerMap)) {
                    // Store it in the list of results
                    storeWordInResultsList(word, results);
                }
            }
        }

        List<List<String>> anagrams = new ArrayList<>();
        for (Integer key: results.keySet()) {
            if (results.get(key).size() > 1) {
                anagrams.add(results.get(key));
            }
        }
        return anagrams;
    }

    private void storeWordInResultsList(String word, Map<Integer, List<String>> results) {
        List<String> specificResultGroup;
        if (!results.containsKey(word.length())) {
            specificResultGroup = new ArrayList<>();
        } else {
            specificResultGroup = results.get(word.length());
        }
        specificResultGroup.add(word);
        results.put(word.length(), specificResultGroup);
    }

    private Map<Character, Integer> createCharsHashMap(String word) {
        Map<Character, Integer> charsHashMap = new HashMap<>();
        for (char character: word.toCharArray()) {
            if (!charsHashMap.containsKey(character)) {
                charsHashMap.put(character, 1);
            } else {
                charsHashMap.put(character, charsHashMap.get(character) + 1);
            }
        }
        return charsHashMap;
    }

    @Test
    public void testFindAnagrams() {
        // Given
        List<String> input = Arrays.asList("debitcard", "elvis", "silent", "badcredit", "lives",
                "freedom", "listen", "levis", "money");

        // When
        List<List<String>> result = findAnagrams(input);

        // Then
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("elvis", "lives", "levis"),
                Arrays.asList("silent", "listen"),
                Arrays.asList("debitcard", "badcredit"));
        assertEquals(expected, result);
    }

    /**
     * Book solution 1
     *
     * Time complexity:
     *  - It contains n calls to sort and n insertions into the hash table
     *  -- each sort has time complexity of O(nmlogm)
     *  -- each insertion is O(nm)
     *  Final complexity: O(nm log m)
     *
     */
    public static List<List<String>> bookSol1FindAnagrams(List<String> dictionary) {
        Map<String, List<String>> sortedStringToAnagrams = new HashMap<>();
        for (String s: dictionary) {
            // Sorts the string, uses it as a key, and then appends
            // the original string as another value in the hash table.
            String sortedStr =
                    Stream.of(s.split("")).sorted().collect(Collectors.joining());
            sortedStringToAnagrams.putIfAbsent(sortedStr, new ArrayList<>());
            sortedStringToAnagrams.get(sortedStr).add(s);
        }

        return sortedStringToAnagrams.values()
                .stream()
                .filter(group -> group.size() >= 2)
                .collect(Collectors.toList());
    }

    @Test
    public void testBookSol1FindAnagrams() {
        // Given
        List<String> input = Arrays.asList("debitcard", "elvis", "silent", "badcredit", "lives",
                "freedom", "listen", "levis", "money");

        // When
        List<List<String>> result = bookSol1FindAnagrams(input);

        // Then
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("silent", "listen"),
                Arrays.asList("debitcard", "badcredit"),
                Arrays.asList("elvis", "lives", "levis"));
        assertEquals(expected, result);
    }

}
