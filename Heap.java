package dualcoresimulator;

import java.util.*;

/**
 * This class models an array-based binary heap that implements the 
 * HeapAPI interface. The array holds objects that implement the 
 * parameterized Comparable interface.
 * @author Dylan Dubois
 * @param <E> the heap element type
 * @date 09-24-2017
 */
public class Heap<E extends Comparable<E>> implements HeapAPI<E>
{    
   /**
    * A complete tree stored in an array list representing this 
    * binary heap
    */
   private ArrayList<E> tree;
    
   /**
    * Constructs an empty heap
   */
   public Heap()
   {
      tree = new ArrayList<E>();
   }

   /**
    * Determine whether the Heap is empty.
    * @return this method returns true if the heap is empty;
    * otherwise, it returns false if the heap contains at least one item.
    */
   public boolean isEmpty()
   {
	   return tree.size() == 0;
   }

   /**
    * Inserts an item into the Heap.
    * @param item the value to be inserted.
    */
   public void insert(E obj)
   {
	  if (isEmpty())
		  tree.add(obj);
	  else{
		  tree.add(obj);
		  int index = size() - 1, parent = (index - 1) /2;
		  while (index > 0 && tree.get(index).compareTo(tree.get(parent))  > 0){
			  	swap(index, parent);
			  	index = parent;
			  	parent = (index - 1)/2;
		  }
		  
	  }
   }

   /**
    * An exception is generated if this method is invoked
    * by an empty heap. The maximum/minimum value is removed
    * from the heap if the heap is not empty and its effective
    * size is reduced by 1.
    * @return the maximum (in the case of a maxheap) or the
    * minimum (in the case of a minheap) on the heap.
     * @throws HeapException when the heap is empty
    */
   public E remove() throws HeapException
   {
	if (isEmpty())
		throw new HeapException("Heap is empty");
	E temp = tree.get(0);
	this.tree.remove(0);
	reheapify(0);
	return temp;

   }

   /**
    * An exception is generated if this method is invoked
    * by an empty heap
    * @return the maximum (in the case of a maxheap) or the
    * minimum (in the case of a minheap) on the heap.
     * @throws HeapException when the heap is empty
    */
   public E peek() throws HeapException
   {
	 if (isEmpty())
		throw new HeapException("Heap is empty");
	return this.tree.get(0);

   }      

   /**
    * Gives the size of this heap
    * @return the size of the heap; the effective size of the
    * heap.
    */
   public int size()
   {
	return this.tree.size();
   }
   
   /**
    * Swaps a parent and child elements of this heap at the specified indices
    * @param place an index of the child element on this heap
    * @param parent an index of the parent element on this heap
    */
   public void swap(int place, int parent)
   {
      Collections.swap(this.tree, place, parent);
   }

   /**
    * Rebuilds the heap to ensure that the heap property of the tree is preserved.
    * @param root the root index of the subtree to be rebuilt
    */
   public void reheapify(int root)
   {
	int left = 2 * root + 1, right = 2 * root +2;
	if (left >= size()){
		return;
		}
	if (tree.get(root).compareTo(tree.get(left)) < 0){
		swap(root, left);
		}
	if (right < size() && tree.get(root).compareTo(tree.get(right)) < 0){
		swap(root, right);
		}
	reheapify(left);
	reheapify(right);	   
   } 
   
}
