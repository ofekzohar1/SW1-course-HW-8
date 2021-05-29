package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**
 * This class represents histogram dataframe using HashMap.
 * @param <T> Type of the keys (categories)
 */
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T> {

    private static final Integer NOT_EXIST = 0; // Not exist value

    private HashMap<T, Integer> histogramMap; // Hash map stores the data on each category

    /**
     * Empty Constructor
     */
    public HashMapHistogram(){
        histogramMap = new LinkedHashMap<>();
    }

    /**
     * Constructor builds the histogram from a given collection.
     */
    public HashMapHistogram(Collection<T> items){
        histogramMap = new LinkedHashMap<>();
        addAll(items);
    }

    /**
     * Gets an iterator for HashMapHistogram object (HashMapHistogramIterator)
     * @return Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new HashMapHistogramIterator<>(histogramMap.entrySet());
    }

    /**
     * Add one item's occurrences to the histogram
     * @param item The item to be added
     */
    @Override
    public void addItem(T item) {
        try {
            addItemKTimes(item, 1); // Using addItemKTimes with k == 1
        } catch (IllegalKValueException e) { // Catch ignored, k == 1 case
            e.printStackTrace();
        }
    }

    /**
     * Remove one item's occurrences from the histogram
     * @param item The item to remove from
     */
    @Override
    public void removeItem(T item) throws IllegalItemException {
        try {
            removeItemKTimes(item, 1); // Using removeItemKTimes with k == 1
        } catch (IllegalKValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add to k occurrences to a given item.
     * if item not exist add it with k value.
     * @param item Item to be added
     * @param k Number of occurrences to add
     * @throws IllegalKValueException if k < 1
     */
    @Override
    public void addItemKTimes(T item, int k) throws IllegalKValueException {
        if (k < 1) // Error - can't add less than one
            throw new IllegalKValueException(k);

        Integer currItemValue;
        currItemValue = histogramMap.putIfAbsent(item, k); // Put Item with k if not exist in map
        if (currItemValue != null) { // Item exist in map
            histogramMap.put(item, currItemValue + k);
        }
    }

    /**
     * Remove k item's occurrences from the histogram.
     * if item's occurrences == k, remove item from the histogram
     * @param item The item to remove from
     * @param k Number of occurrences
     * @throws IllegalItemException Invalid k param - k < 1 or k > item's occurrences
     * @throws IllegalKValueException Item not exist in the histogram
     */
    @Override
    public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
        if (k < 1) // Error - can't remove less than one
            throw new IllegalKValueException(k);
        Integer currItemValue = histogramMap.get(item);
        if (currItemValue == null) // Item not exist, can't remove from map
            throw new IllegalItemException();

        if (currItemValue > k)
            histogramMap.put(item, currItemValue - k); // Decrease Item's appearances by k
        else if (currItemValue == k)
            histogramMap.remove(item); // Item appears exactly k times, remove entirely from map
        else // currItemValue < k, Error - can't remove more than item's value
            throw new IllegalKValueException(k);
    }

    /**
     * Get item's value (occurrences) in the histogram.
     * @param item The item to get his value
     * @return item's value (occurrences), if item not exist return NOT_EXIST value
     */
    @Override
    public int getCountForItem(T item) {
        return histogramMap.getOrDefault(item, NOT_EXIST); // Return item's value, if not exist return NOT_EXIST value
    }

    /**
     * Add entire collection to the histogram.
     * @param items A collection of items to be added
     */
    @Override
    public void addAll(Collection<T> items) {
        for (T item : items) { // Add every item in items to the histogram
            addItem(item);
        }
    }

    /**
     * Clear the histogram.
     */
    @Override
    public void clear() {
        histogramMap.clear(); // Empty the histogram
    }

    /**
     * Get set of the items (categories) in the histogram.
     * @return Set object contain all the items (keys)
     */
    @Override
    public Set<T> getItemsSet() {
        return histogramMap.keySet(); // Return all items (==keys) in the histogram as set
    }

    /**
     * Update this histogram using another histogram data.
     * @param anotherHistogram Another histogram data to add to this histogram
     */
    @Override
    public void update(IHistogram<T> anotherHistogram) {
        for (T item : anotherHistogram.getItemsSet()) {
			try {
				this.addItemKTimes(item, anotherHistogram.getCountForItem(item));
			} catch (IllegalKValueException e) {
				System.out.format("Item %s has invalid value. Update ignored!", item);
			}
		}
    }

    /**
     * Get the number of items (keys) in the histogram.
     * @return Number of different items
     */
    public int size() {
        return histogramMap.size();
    }

}
