package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class implements an iterator for HashMapHistogram object.
 * The iterator iterate over the histogram in dessecnding order according to item's frequent.
 * @param <T> Items type
 */
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T> {

    private List<Entry<T, Integer>> listOfKeysAndValues; // List to iterate over
    private Iterator<Entry<T, Integer>> listIter; // Iterate using a list iterator

    /**
     * HashMapHistogramIterator constructor.
     * @param histogramKeysAndValuesSet A set of pairs keys-values from a given histogram.
     */
    public HashMapHistogramIterator(Set<Entry<T, Integer>> histogramKeysAndValuesSet) {
        listOfKeysAndValues = new ArrayList<>(histogramKeysAndValuesSet);
        listOfKeysAndValues.sort(new KeyValueComparator().reversed()); // Sort the list using the  KeyValueComparator in reversed order
        listIter = listOfKeysAndValues.listIterator(); // Iterator for the list
    }

    /**
     * Inner class implements a comparator for Entry<T, Integer> type.
     * Compare by item's value, if items values equals compare by key in reversed order.
     */
    private class KeyValueComparator implements Comparator<Entry<T, Integer>> {
        @Override
        public int compare(Entry<T, Integer> item1, Entry<T, Integer> item2) {
            int comp = Integer.compare(item1.getValue(), item2.getValue());
            if (comp == 0) // Equal values
                return item2.getKey().compareTo(item1.getKey()); // Compare keys in reverse order
            return comp;
        }
    }

    /**
     * Check if there is another element to iterate on.
     * @return False - reached the end of the list, else True
     */
    @Override
    public boolean hasNext() {
        return listIter.hasNext();
    }

    /**
     * Get next element in the list.
     * @return Next item's key
     */
    @Override
    public T next() {
        return listIter.next().getKey();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(); //no need to change this
    }
}
