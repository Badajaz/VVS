package sut;

import java.util.*;
import java.lang.reflect.Array;

/**
 * @author Joao Pedro Neto
 *
 * @param <T> The element's type that the tree contains 
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T> {

	private int     capacity;        	// maximum number of children per tree node
	private boolean empty;              // is the tree empty?
	private T       data;               // the root's data
	private int     nChildren;          // number of successors
	private ArrayNTree<T>[] children;
	
	/**
	 * Creates an empty tree 
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(int capacity) {
		this.empty     = true;
		this.children  = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
		this.nChildren = 0;
		this.capacity  = capacity;
	}

	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(T elem, int capacity) {
		this(capacity);
		this.empty     = false;
		this.data      = elem;
	}
	
	/**
	 * Creates a tree with the elements inside the given list
	 * @param elem     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(List<T> list, int capacity) {
		this(capacity);
		for(T elem : list)
			insert(elem);
	}
		
	/////////////////////////////////////

	public boolean isEmpty() {
		return empty;
	}

    /////////////////////////////////////

	public boolean isLeaf() {
		return !empty && nChildren == 0;
	}

    /////////////////////////////////////
	//colocar no relatório que apanhamos um erro  quando é uma árvore vazia(devolve 1 e devia devolver 0)
	public int size() {
		int sum=0;
		if (empty) {
			return 0;
		}
		for(NTree<T> brt : children)
			if (brt!=null)
			    sum += brt.size();
		return 1+sum;
	}

    /////////////////////////////////////

	public int countLeaves() {
		if (isLeaf())
			return 1;
		
		int sum=0;
		for(NTree<T> brt : children)
			if (brt!=null)
				sum += brt.countLeaves();
		return sum;
	}

    /////////////////////////////////////

	public int height() {
		if (isEmpty())
			return 0;
		
		int maxHeight=0;
		for(NTree<T> brt : children) {
			if (brt==null)
				continue;
			int currentHeight = brt.height();
			if (currentHeight>maxHeight)
				maxHeight = currentHeight;
		}
		return 1+maxHeight;
	}

    /////////////////////////////////////
	
	public T min() {
		return data;
	}
	
    /////////////////////////////////////
	
	public T max() {
		if (isLeaf())
			return data;
		
		return children[nChildren-1].max();
	}

    /////////////////////////////////////

	public boolean contains(T elem) {
		if (isEmpty())
			return false;
		
		if (data.compareTo(elem)==0) // elem == root
			return true;
		
		// if there are no elements left, or the smallest child is > elem,
		// then the tree does not contain elem
		if (isLeaf() || elem.compareTo(children[0].data)<0) 
			return false;
		
		int position = proposePosition(elem);
		
		if(position==nChildren)  // elem>all children: need to look at last child
			position = nChildren-1;
		
		return children[position].data.compareTo(elem)==0 || children[position].contains(elem);
	}

    /////////////////////////////////////
	
	public void insert(T elem) {
		//p1(c1)
		if(isEmpty()) { //node 1
			data=elem; //Node 2
			empty=false; 
			return; 
		}
		//p2(c2)
		if (contains(elem)) // will not insert repetitions //node 3
			return; //Node 4
		
		//p3(c3)
		// if elem<data, elem should be at root, and we re-insert data
		if (data.compareTo(elem)>0) { //Node 5 
			T tmp = data; data = elem; elem = tmp; // swap values // Node 6
		}
		//p4(c4)
		if(isLeaf()) { //Node 7
			insertAt(elem, 0);	//Node 8
			return;
		}
		
		int position = proposePosition(elem);//Node 9
		//p5(c5)
		if (position==-1) {
			// element 'elem' is smaller than all children
			// then we place it at index 0, and insert the previous children[0] below 'elem'
			T previousValue = children[0].data;//Node 10
			children[0].data = elem;
			this.insert(previousValue);
		} 
		//p6(c6 && c7)
		else if (nChildren<capacity && children[position] == null) { //Node 11
			// there's space available, and elem > all children
			//p7(c8)
			if (elem.compareTo(children[position-1].max())>0) //Node 12
				// if elem is also larger than all children of the last child, place it here 
				insertAt(elem, position); //Node 13
			else 
				// otherwise, place it below last child
				children[position-1].insert(elem); //Node 14
		}
		//p8(c9 && c10)
		else if (nChildren<capacity && elem.compareTo(children[position].max())>0) { //Node 15
			// element can be placed after an existing node N (there's space and it's larger
			// than all children of N) but we must shift all those on the right
			insertAt(elem, position+1); //Node 16
		}
		//p9(c11 || c12)
		else if (nChildren==capacity || elem.compareTo(children[position].max())<0) { //Node 17
			// if the node's capacity is full, and elem is larger than all children
			// place it below the last child
			//p10(c13)
			if (position==capacity) //Node 18
				children[position-1].insert(elem); //Node 20
			else
				// otherwise, it must go under the proposed child's position
				children[position].insert(elem); //Node 21
		}
		//Node 19
		
	}

    /////////////////////////////////////
	
	public void delete(T elem) {
		
		// the minimum value is at the root, if something smaller
		// appears, the tree does not contain it
	
		if(isEmpty() || data.compareTo(elem)>0)	//node 1
			return; //node 2
		
		if(isLeaf()) { //node 3
			empty = data.compareTo(elem)==0;
			return; //node 4
		}
		
		// is elem in the root?
		if (data.compareTo(elem)==0) { //node 5
			// we need to replace it with the lowest child (children[0])
			// and repeat it (to avoid duplicates) until we reach a leaf
			data = children[0].data; //node 6
			children[0].delete(data);
		} else {
			 int position = proposePosition(elem); //node 7
			// if elem < all children, the element does not exist in the tree
			if (position<0)
				return;//node 8
			// if elem>all children: need to look below last child
			if(position==nChildren) //node 9 
				position--; //node 10
			children[position].delete(elem); //node 11			
		}
		// if we are at the tree's bottom, the last deletion
		// will produce an empty tree, so we might need to compact the array
		// to eliminate these empty nodes
		compact(children); //node 12		
	}

    /////////////////////////////////////

	// find an eventual single empty tree, and shift all next elements to the left
	private void compact(ArrayNTree<T>[] children) {//Node 1
		for(int i=0; i<nChildren; i++)//Node 2
			if (children[i].isEmpty()) {//Node 4
				for(int j=i+1; j<nChildren; j++)//Node 5
					children[j-1] = children[j];//Node 6
				children[nChildren-1] = null;//Node 7
				nChildren--;
				break;
			}
		//Node 3
	}

	// shift all indexes i>position one index to the right
	// and insert element at index position
	private void insertAt(T elem, int position) {
		for(int i=nChildren-1; i>=position; i--)
			children[i+1] = children[i];
		children[position] = new ArrayNTree<>(elem, capacity);
		nChildren++;
	}

	// pre: nChildren>0
	// propose a position to insert/search element
	// returns the index of the largest of all the values < elem
	// if the element is the smallest, it returns -1
	private int proposePosition(T elem) {
		int index = 0; //Node 1
		for(int i=0; i<capacity; i++) { //Node 2
			if (children[i] == null || children[i].data.compareTo(elem)==0) //Node 4
				// found an empty slot or the element, return current index
				break;
			if (children[i].data.compareTo(elem)>0) { //Node 5
				// element should not be place here or ahead, go back one position, and end search
				index--;
				break;
			}
			if (children[i].data.compareTo(elem)<0) {
				//Node 6
				index++;
			}
				// this child is still smaller, check next one 
				
		}
		
		return index; //Node 3
	}

	/////////////////////////////////////

	
	/**
	 * Is this tree equal to another object?
	 * Two NTrees are equal iff they have the same values
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return this == other ||
			     other instanceof NTree &&
			     equalTrees(this, ((NTree<T>) other));
	}

	// compares the elements between to NTrees
	private boolean equalTrees(NTree<T> one, NTree<T> other) {
		
		if (one == other)
			return true;
				
		if (one != null && other != null) {
			Iterator<T> it1 = one.iterator();
			Iterator<T> it2 = other.iterator();
			
			while(it1.hasNext() && it2.hasNext())
				if(!it1.next().equals(it2.next()))
					return false;
			
			if(!it1.hasNext() && !it2.hasNext())
				return true;
		}
		
		return false;
	}
	
	/////////////////////////////////////
	// corrigido para o caso em que o elemento é null
	public List<T> toList() {
		List<T> list = new LinkedList<>();
		for(T elem : this)
			if (elem != null) {
				list.add(elem);
			}
        return list;  // already sorted		
	}
	
	/////////////////////////////////////
	
	/**
	 * @returns a new tree with the same elements of this
	 */
	public ArrayNTree<T> clone() {
		List<T> list = toList();
		Collections.shuffle(list); // probably produces a more balanced tree	
		return new ArrayNTree<T>(list, capacity);
	}

	/////////////////////////////////////

	public String toString() {
		if (isEmpty())
			return "[]";

		if (isLeaf())
			return "["+data+"]";
		
		StringBuilder sb = new StringBuilder();
		sb.append("["+data+":");
		
		for(NTree<T> brt : children)
			if (brt!=null)
				sb.append(brt.toString());
		
		return sb.append("]").toString();				
	}
	
	// more detailed information about tree structure 
	public String info() {
		return this + ", size: " + size() + ", height: " + height() + ", nLeaves: " + countLeaves();
	}

	/////////////////////////////////////

	@Override
	public Iterator<T> iterator() {
		return new PrefixIterator(this);
	}
	
	private class PrefixIterator implements Iterator<T> {
		
		/**
		 * Stack of nodes whose descendants are currently being visited
		 */
		private LinkedList<ArrayNTree<T>> stack;
	
		/**
		 * Constructor
		 */
		public PrefixIterator(ArrayNTree<T> tree) {
			stack = new LinkedList<>();
			stack.push(tree);
		}
	
		/**
		 * Verifies if iterator still has elements
		 * @return true if the iterator still has elements, false otherwise
		 */
		public boolean hasNext() {
			return !stack.isEmpty();
		}
	
		/**
		 * Returns the next element
		 * @return - next element
		 * @Throws NoSuchElementException if !hasNext()
		 */
		public T next() {
			if (stack.isEmpty()) 
				throw new NoSuchElementException();

			ArrayNTree<T> tree = stack.peek();
			stack.pop();
			for(int i=tree.nChildren-1; i>=0; i--)
				stack.push(tree.children[i]);
			
			return tree.data;
		}
	}
}
