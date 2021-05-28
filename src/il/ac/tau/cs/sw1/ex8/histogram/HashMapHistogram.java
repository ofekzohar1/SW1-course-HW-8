package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	@Override
	public Iterator<T> iterator() {
		//your code goes here!
		return null;
	}

	@Override
	public void addItem(T item) {
		//your code goes here!
		
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		//your code goes here!
		
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		//your code goes here!
		
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		//your code goes here!
		
	}

	@Override
	public int getCountForItem(T item) {
		//your code goes here!
		return 0; //replace this with the actual returned value
	}

	@Override
	public void addAll(Collection<T> items) {
		//your code goes here!
		
	}

	@Override
	public void clear() {
		//your code goes here!
		
	}

	@Override
	public Set<T> getItemsSet() {
		//your code goes here!
		return null; //replace this with the actual returned value
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {
		//your code goes here!		
	}

	
}
