package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;
	
	

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				/*******************/
				//your code goes here!
				/*******************/
			}
		}
		/*******************/
		//your code goes here!
		/*******************/
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		//your code goes here!
		return 0; //replace this with the actual returned value
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		//your code goes here!
		return 0; //replace this with the actual returned value

	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		//your code goes here!
		return 0; //replace this with the actual returned value
	}
	
	
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		//your code goes here!
		return null; //replace this with the actual returned value
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		//your code goes here!
		return null; //replace this with the actual returned value
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		//your code goes here!
		return null; //replace this with the actual returned value
	}

}
