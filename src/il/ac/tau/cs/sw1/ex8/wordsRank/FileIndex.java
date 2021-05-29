package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**
 * This class implements Index data structure from given files.
 */
public class FileIndex {

    public static final int UNRANKED_CONST = 30;
    public static final int INVALID_GET_TYPE = -1; // Value to return on invalid getType enum value
    private static final String UNRECOGNIZED_WORD = null; // Word not exist in all the files
    public static enum getType {count, rank, averageRank} // Enum for types of data to return to the user

    private HashMap<String, HashMapHistogram<String>> filesAndHistWords; // Index for file and his words in a histogram
    private HashMap<String, RankedWord> wordsAndRankedMap; // Map for words and his RankedWord referenced object
    private RankedWord unrecognizedWordRank;

    /**
     * FileIndex empty constructor.
     */
    public FileIndex() {
        filesAndHistWords = new HashMap<>();
        wordsAndRankedMap = new HashMap<>();
    }

    /**
     * This function index files into a hashmap data structures according to their content.
     * @param folderPath Folder path contain the files to be indexed.
     * @pre the directory is no empty, and contains only readable text files
     */
    public void indexDirectory(String folderPath) {
        HashMapHistogram<String> wordsFromFileHist;
        HashSet<String> allWordsSet = new HashSet<>();  // Stores all the different words from all files
        List<String> fileTokens;

        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            // for every file in the folder
            if (file != null && file.isFile()) {
                try {
                    fileTokens = FileUtils.readAllTokens(file); // Read tokens from the file
                    wordsFromFileHist = new HashMapHistogram<>(fileTokens); // Build histogram from all the tokens
                    allWordsSet.addAll(fileTokens);
                    filesAndHistWords.put(file.getName(), wordsFromFileHist); // Add to the main map file-wordsHistogram
                } catch (IOException e) {
                    System.out.format("The file %s is not readable.", file);
                }
            }
        }

        buildRankedWords(allWordsSet); // Build rank words datastructures using private method
    }

    /*
    Build rank words datastructures for every word in the Index. useful for future calculations.
     */
    private void buildRankedWords(HashSet<String> allWordsSet) {
        Set<Entry<String, HashMapHistogram<String>>> files = filesAndHistWords.entrySet();
        HashMapHistogram<String> fileHist;
        HashMap<String, HashMap<String, Integer>> wordsRanksForFiles = new HashMap<>();
        HashMap<String, Integer> ranksForFiles;
        RankedWord rankWord;

        for (String word : allWordsSet) { // Initial wordsRanksForFiles
            wordsRanksForFiles.put(word, new HashMap<>());
        }

        ranksForFiles = new HashMap<>(); // Build ranks for a word not exist in all files
        for (Entry<String, HashMapHistogram<String>> file : files) { // Calc word's rank for every file in Index
            fileHist = file.getValue(); // Get file histogram
            int fileHistSize = fileHist.size();
            ranksForFiles.put(file.getKey(), fileHistSize + UNRANKED_CONST);

            for (String word : allWordsSet) { // Initial with default rank - size + UNRANKED_CONST
                wordsRanksForFiles.get(word).put(file.getKey(), fileHistSize + UNRANKED_CONST);
            }

            int rankCounter = 0;
            for (String word : fileHist) { // Give for every word the rank in the file
                rankCounter++;
                wordsRanksForFiles.get(word).put(file.getKey(), rankCounter);
            }

        }

        unrecognizedWordRank = new RankedWord(UNRECOGNIZED_WORD, ranksForFiles);
        for (String word : allWordsSet) { // Make RankedWord object from ranksForFiles for every word
            ranksForFiles = wordsRanksForFiles.get(word);
            rankWord = new RankedWord(word, ranksForFiles);
            wordsAndRankedMap.put(word, rankWord);
        }
    }

    /*
    Get word's info by the desired type.
     @throws FileIndexException File not exist in the Index
     */
    private int getByType(String filename, String word, getType gType) throws FileIndexException {
        if (gType != getType.averageRank && !filesAndHistWords.containsKey(filename)) // gType.averageRank don't throw an error
            throw new FileIndexException("File " + filename + "not exist in Index.");
        word = word.toLowerCase();
        switch (gType) {
            case count:
                return filesAndHistWords.get(filename).getCountForItem(word);
            case rank:
                return wordsAndRankedMap.getOrDefault(word, unrecognizedWordRank).getRanksForFile().get(filename); // If word not exist use unrecognizedWordRank
            case averageRank:
                return wordsAndRankedMap.getOrDefault(word, unrecognizedWordRank).getRankByType(rankType.average);// If word not exist use unrecognizedWordRank
            default:
                System.out.println("Invalid getType value.");
                return INVALID_GET_TYPE; // Default value
        }
    }

    /**
     * Get number of occurrences of word in a given file.
     * @param filename File to check in
     * @param word The word to look for
     * @return Number of word's occurrences in filename
     * @throws FileIndexException File not exist in the Index
     * @pre the index is initialized
     * @pre filename is a name of a valid file
     * @pre word is not null
     */
    public int getCountInFile(String filename, String word) throws FileIndexException {
        return getByType(filename, word, getType.count);
    }

    /**
     * Get word's rank in a given file.
     * @param filename File to check in
     * @param word The word to look for its rank
     * @return word's rank in filename
     * @throws FileIndexException File not exist in the Index
     * @pre the index is initialized
     * @pre filename is a name of a valid file
     * @pre word is not null
     */
    public int getRankForWordInFile(String filename, String word) throws FileIndexException {
        return getByType(filename, word, getType.rank);
    }

    /**
     * Gets word's average rank (over all files in the Index)
     * @param word The word to look for its average rank
     * @return word's average rank
     * @pre the index is initialized
     * @pre word is not null
     */
    public int getAverageRankForWord(String word) {
        int average = unrecognizedWordRank.getRankByType(rankType.average); // Default value
        try {
            average = getByType(null, word, getType.averageRank); // filename == null
        } catch (FileIndexException e) { // Should never occur, getByType doesn't throw FileIndexException for rankType.average
            e.printStackTrace();
        }
        return average;
    }

    /*
     * Get all the words theirs rankType less than k
     * @param k The condition
     * @param cType The rankType criteria
     * @return List of words satisfies the condition
     */
    private List<String> getWordsWithRankTypeSmallerThanK(int k, rankType cType) {
        LinkedList<String> wordsListSmallerThanK = new LinkedList<>();

        for (RankedWord rankWord : wordsAndRankedMap.values()) {
            if (rankWord.getRankByType(cType) < k)
                wordsListSmallerThanK.add(rankWord.getWord());
        }
        return wordsListSmallerThanK;
    }

    /**
     * Get all the words theirs average rank less than k
     * @param k The condition
     * @return List of words satisfies the condition
     */
    public List<String> getWordsWithAverageRankSmallerThanK(int k) {
        return getWordsWithRankTypeSmallerThanK(k, rankType.average);
    }

    /**
     * Get all the words theirs Min rank less than k
     * @param k The condition
     * @return List of words satisfies the condition
     */
    public List<String> getWordsWithMinRankSmallerThanK(int k) {
        return getWordsWithRankTypeSmallerThanK(k, rankType.min);
    }

    /**
     * Get all the words theirs Max rank less than k
     * @param k The condition
     * @return List of words satisfies the condition
     */
    public List<String> getWordsWithMaxRankSmallerThanK(int k) {
        return getWordsWithRankTypeSmallerThanK(k, rankType.max);
    }

}
