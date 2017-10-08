import java.util.Map;
import java.util.AbstractMap;
import java.util.Scanner;
import java.util.List; 

/**
 * A binary search tree modified to incorporate least a recently used queue based on 
 * elements accessed in the tree. 
 * @author Tomal Hossain
 * @version 0.1 2015-11-13
 */

public class BSTWithLRU<K extends Comparable<? super K>> implements CountMapWithLRU <K>
{
    public Node<K> root;
    public Node<K> head; 
    public Node<K> tail; 
    public int size;

/////////////////////////////////////////////////////////////////////////////////

     /**
     * Adds the given count to the count for the given key.  If the key is
     * not present in this map then the return value is false, otherwise
     * the return value is true.
     *
     * @param key a key
     * @param count a positive integer
     * @return true if and only if the given key was already in this map
     */
    public boolean update(K key, int count)
    {
    	// start at root
	Node<K> curr = root;

	// keep track of parent of current and last direction travelled
	Node<K> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or containing interval
	while (curr != null && (dir = key.compareTo(curr.data)) != 0)
	    {
		parCurr = curr;
		if (dir < 0)
		    {
			curr = curr.left;
		    }
		else
		    {
			curr = curr.right;
		    }
		depth++;
	    }
	// key is not present in tree, return false 
	if (curr == null)
	    {
		return false; 
	    }
	// the key already existed in the tree, so its count is incremented by count    
	else
	    {
		curr.count += count; 
		return true; 
	    }
    }
    
/////////////////////////////////////////////////////////////////////////////////

    /**
     * Adds the given count to the count for the given key.  If the key is
     * not present in this map then a new entry is added.
     *
     * @param key a key
     * @param count a positive integer
     */

    public void add (K key, int amount)
    {
	// start at root
	Node<K> curr = root;

	// keep track of parent of current and last direction travelled
	Node<K> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or containing interval
	while (curr != null && (dir = key.compareTo(curr.data)) != 0)
	    {
		parCurr = curr;
		if (dir < 0)
		    {
			curr = curr.left;
		    }
		else
		    {
			curr = curr.right;
		    }
		depth++;
	    }

	if (curr == null)
	    {
		// didn't find value; create new node
		Node<K> newNode = new Node<K>(key, parCurr);
		newNode.count += amount; 

		size++;

		// link from parent according to last direction
		if (parCurr == null)
		    {
			root = newNode;
			head = newNode; 
			tail = newNode;
			newNode.prev = null; 
			newNode.next = null;    
		    }

		else
			{
		// move head pointer to point to newly added node and adjust 
		// prev/next pointers of said node (only working at front of list)
	
		    Node<K> tempHead = head; 
		    head = newNode;
		    head.prev = tempHead;  
		    head.next = null; 
		    tempHead.next = head; 
	
			if (dir < 0)
			    {
				parCurr.left = newNode;
		
			    }
			else
			    {
				parCurr.right = newNode;
			    }
		    }

		// recompute heights on path back to root
		recomputeHeights(parCurr);
		
	    }
	// the key already existed in the tree, so its count is incremented by count    
	else
	    {
		curr.count += amount; 
	    }
    }

/////////////////////////////////////////////////////////////////////////////////

      /**
     * Removes the given key from this map and returns the old entry.
     * If the key was not present then the return value is null.
     *
     * @param key a key
     * @return the (key, value) pair that was removed, or null if the key was
     * not present
     */
    public Map.Entry<K, Integer> remove(K key)
    {
	// start at root
	Node<K> curr = root;

	// keep track of parent of current and last direction travelled
	Node<K> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or value
	while (curr != null && (dir = key.compareTo(curr.data)) != 0)
	    {
		parCurr = curr;
		if (dir < 0)
		    {
			curr = curr.left;
		    }
		else
		    {
			curr = curr.right;
		    }
		depth++;
	    }
	
	if (curr == null)
	    {
	    return null; 
	    }
	else
	    {
		Map.Entry<K, Integer> pair = new AbstractMap.SimpleEntry(curr.data, curr.count); 
		delete(curr);
		return pair;  
	    }
    }

/////////////////////////////////////////////////////////////////////////////////

    /**
     * Removes the least recently used key and it associated count from this
     * map and returns them.
     *
     * @return the removed (key, count) pair
     */
    public Map.Entry<K, Integer> removeLRU()
    {

    	Map.Entry<K, Integer> pair = new AbstractMap.SimpleEntry(tail.data, tail.count);
    	delete(tail); 
		return pair;  

    }

/////////////////////////////////////////////////////////////////////////////////

    /**
     * Deletes the given node from this tree
     *
     * @param curr a node in this tree
     */
    public void delete (Node<K> curr)
    {

    //System.out.println(tail.next.data); 
    //modifying the doubly linked list 
    
    if (curr == head) 
    {
    	head = null;
    	tail = null; 
    } 

    else if (curr == tail)
    {
    	//System.out.println(tail); 
    	tail = tail.next;
    	tail.prev = null;  

    	//System.out.println("tail" + this.tail);
    }
    else {

    curr.prev.next = curr.next; 
    curr.next.prev = curr.prev; 

    }
    /*
	Node<K> tempHead = this.head;
    head = curr;
    curr.prev = tempHead;  
    curr.next = null; 
    tempHead.next = curr;  
	*/
	size--; 

	// modifying the parent/child pointers 
	if (curr.left == null && curr.right == null)
	    {
		Node<K> parent = curr.parent;
		if (curr.isLeftChild())
		    {
			parent.left = null;
			recomputeHeights(parent);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = null;
			recomputeHeights(parent);
		    }
		else
		    {
			// deleting the root
			root = null;
		    }
	    }
	else if (curr.left == null)
	    {
		// node to delete has only right child
		Node<K> parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.right;
			curr.right.parent = parent;
			recomputeHeights(parent);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.right;
			curr.right.parent = parent;
			recomputeHeights(parent);
		    }
		else
		    {
			root = curr.right;
			root.parent = null;
		    }
	    }
	else if (curr.right == null)
	    {
		// node to delete only has left child
		Node<K> parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.left;
			curr.left.parent = parent;
			recomputeHeights(parent);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.left;
			curr.left.parent = parent;
			recomputeHeights(parent);
		    }
		else
		    {
			root = curr.left;
			root.parent = null;
		    }
	    }
	else
	    {
		// node to delete has two children
		// find inorder successor (leftmost in right subtree)
		Node<K> replacement = curr.right;
		while (replacement.left != null)
		    {
			replacement = replacement.left;
		    }

		Node<K> replacementChild = replacement.right;
		Node<K> replacementParent = replacement.parent;

		// stitch up
		if (curr.isLeftChild())
		    {
			curr.parent.left = replacement;
		    }
		else if (curr.isRightChild())
		    {
			curr.parent.right = replacement;
		    }
		else
		    {
			root = replacement;
		    }
		  
		if (replacement.parent != curr)
		    {
			replacement.parent.left = replacementChild;
			if (replacementChild != null)
			    {
				replacementChild.parent = replacement.parent;
			    }

			replacement.right = curr.right;
			curr.right.parent = replacement;
		    }

		replacement.left = curr.left;
		curr.left.parent = replacement;

		replacement.parent = curr.parent;

		// recompute heights (node we deleted is on the path
		// of nodes whose heights is recomputes because
		// replacementParent is a descendant of curr)
		if (replacementParent != curr)
		    {
			recomputeHeights(replacementParent);
		    }
		else
		    {
			recomputeHeights(replacement);
		    }
	    }
	}

/////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the count associated with the given key, or zero if the key
	 * is not present in this map.
	 *
	 * @param key a key
	 * @return the value associated with that key
	 */

	public int get(K key)
	{

	// start at root
	Node<K> curr = root;

	// keep track of parent of current and last direction travelled
	Node<K> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or containing interval
	while (curr != null && (dir = key.compareTo(curr.data)) != 0)
	    {
		parCurr = curr;
		if (dir < 0)
		    {
			curr = curr.left;
		    }
		else
		    {
			curr = curr.right;
		    }
		depth++;
	    }

	// the key was not present in the tree so a value of 0 is returned     
	if (curr == null)
	    {
		return 0; 
	    }
	// the key exists in the tree and its associated count is returned 
	else
	    {
		if (curr == tail)
		    {
		    	//System.out.println(tail); 
		    	tail = tail.next;
		    	tail.prev = null;  
		    	//System.out.println("tail" + this.tail);
		    }
		else 
		    {
		    curr.prev.next = curr.next; 
		    curr.next.prev = curr.prev; 
		    }
			
		Node<K> tempHead = head;
	    head = curr;
	    curr.prev = tempHead;  
	    curr.next = null; 
	    tempHead.next = curr; 

	    return head.count; 

	    }
	}

/////////////////////////////////////////////////////////////////////////////////

	/**
	* Returns the number of keys in this map.
	*
	* @return the number of keys in this map
	*/

	public int size() 
	{
		return this.size;
	}

/////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds all the (key, count) pairs in this map to the given list.
	 *
	 * @param l a list
	 */
	public void addToList(List<Map.Entry<K, Integer>> l) 
	{
		addInOrder (l, this.root); 
		 
	}   

/////////////////////////////////////////////////////////////////////////////////

	/**
	 * Helper function for addToList
	 * @param node a node 
	 * @param l a list
	 */

	public void addInOrder(List<Map.Entry<K, Integer>> l, Node<K> node)
	{
		if (node != null) 
		{
			addInOrder(l, node.left);
			Map.Entry<K, Integer> pair = new AbstractMap.SimpleEntry<K, Integer>(node.data, node.count); 
			l.add(pair); 
			addInOrder(l, node.right);
		}
	}

/////////////////////////////////////////////////////////////////////////////////

    /**
     * Recomputes the heights for nodes on the path from the given node
     * back to the root.
     *
     * @param n a node in this tree
     */
	public void recomputeHeights(Node<K> n)
    {
	while (n != null)
	    {
		n.recomputeHeight();
		n = n.parent;
	    }
    }

/////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns a printable representation of this tree.
     *
     * @return a printable representation of this tree
     */
    public String toString()
    {
	StringBuilder s = new StringBuilder();
	buildOutput(root, s, 0);
	return s.toString();
    }

/////////////////////////////////////////////////////////////////////////////////

    /**
     * Appends a printable representation of the subtree rooted at the
     * given node to the given string builder.
     *
     * @param curr a node in this tree
     * @param s a string builder
     * @param depth the depth of curr
     */
    public void buildOutput(Node<K> curr, StringBuilder s, int depth)
    {
	if (curr != null)
	    {
		buildOutput(curr.left, s, depth + 1);
		s.append(depth + "/" + curr.height + " " + curr.data + "\n");
		buildOutput(curr.right, s, depth + 1);
	    }
    }

/////////////////////////////////////////////////////////////////////////////////

    public void validate()
    {
	validate(root, null, null, 0);
    }

    private void validate(Node<K> curr, K lowerBound, K upperBound, int dir)
    {
	if (curr != null)
	    {
		assert (dir == 0 && curr.parent == null) || (dir == -1 && curr.parent.left == curr) || (dir == 1 && curr.parent.right == curr) : "Parent is incorrect at " + curr.data;

		assert (lowerBound == null || lowerBound.compareTo(curr.data) < 0) : "value violates lower bound: " + curr.data + "<=" + lowerBound;

		assert (upperBound == null || upperBound.compareTo(curr.data) > 0) : "value violates upper bound: " + curr.data + ">=" + upperBound;

		assert curr.height == 1 + Math.max(curr.leftHeight(), curr.rightHeight()) :  "height is incorrect at " + curr.data + " : " + curr.height;

		validate(curr.left, lowerBound, curr.data, -1);
		validate(curr.right, curr.data, upperBound, 1);
	    }
    }

/////////////////////////////////////////////////////////////////////////////////

	public void printDeque (Node<K> node) 
	{ 
		while (node != null) 
		{
			System.out.print(node.data + " ");
			node = node.prev; 
		}
		System.out.println();
	}

/////////////////////////////////////////////////////////////////////////////////

    public static class Node<K>
    {
		/**
		 * References to the parents and children of this node.
		 */
		private Node<K> parent;
		private Node<K> left;
		private Node<K> right;

		/**
		 * References to the next and previous nodes in the LRU queue. 
		 */
		private Node<K> prev; 
		private Node<K> next;


		/**
		 * The data stored in this node (specifically the key of the node)
		 */
		private K data;

		/**
		 * The count of a given key (data)
		 */
		private int count;

		/**
		 * The height of the subtree rooted at this node.
		 */
		private int height;

		/**
		 * Creates a node holding the given data with the given parent
		 * parent reference.  The parent's child references are not updated
		 * to refer to the new node.
		 *
		 * @param d the data to store in the new node
		 * @param p the parent of the new node
		 */
		private Node(K d, Node<K> p)
		    {
			data = d;
			parent = p;
			height = 1;
			count = 0; 
		    }

		/**
		 * Determines if this node is a left child.
		 *
		 * @return true if and only if this node is its parent's left child
		 */
		private boolean isLeftChild()
		{
		    return (parent != null && parent.left == this);
		}

		public K getKey ()
		{
			return this.data; 
		}

		/**
		 * Determines if this node is a right child.
		 *
		 * @return true if and only if this node is its parent's right child
		 */
		private boolean isRightChild()
		{
		    return (parent != null && parent.right == this);
		}

		/**
		 * Returns the height of the left subtree of this node.  The height
		 * of an empty subtree is defined to be 0.
		 *
		 * @return the height of the left subtree of this node
		 */
		private int leftHeight()
		{
		    return (left != null ? left.height : 0);
		}

		/**
		 * Returns the height of the right subtree of this node.  The height
		 * of an empty subtree is defined to be 0.
		 *
		 * @return the height of the right subtree of this node
		 */
		private int rightHeight()
		{
		    return (right != null ? right.height : 0);
		}

		/**
		 * Recomputes this node's height.  Intended to be used when the
		 * heights of the children have possibly changed.
		 */
		private void recomputeHeight()
		{
		    height = 1 + Math.max(leftHeight(), rightHeight());
		}
	}

/*
	public static void printDeque (Node<String> bst) 
	{

		Node<String> pointer = bst.head; 
		while (pointer != null) 
		{
			System.out.print(pointer.data + " ");
			pointer = pointer.prev; 
		}
		System.out.println();
	}
	*/

	public static void main(String[] args)
    {		
		BSTWithLRU<String> t = new BSTWithLRU<String>();

		Node<String> head = t.head;

		t.add("bob", 1);
		t.add("apple", 1);
		t.add("ear", 1);
		t.add("car", 1); 
		t.add("dad", 1);
		t.add("jack", 1);
		t.add("hat", 1);
		t.add("face", 1);
		t.add("igloo", 1);
		t.add("garbage", 1);
		t.validate();
		System.out.println(t);
		t.printDeque(head);

		System.out.println("GET");

		t.get("bob");
		t.printDeque(head);
		System.out.println(t);
		t.get("apple");
		t.printDeque(head);
		System.out.println(t);
		t.get("ear");
		t.printDeque(head);
		System.out.println(t);
		t.get("car");
		t.printDeque(head);
		System.out.println(t);
		t.get("dad");
		t.printDeque(head);
		System.out.println(t);
		t.get("jack");
		t.printDeque(head);
		System.out.println(t);
	    t.get("hat");
		t.printDeque(head);
		System.out.println(t);
		t.get("face");
		t.printDeque(head);
		System.out.println(t);
		t.get("igloo");
		t.printDeque(head);
		System.out.println(t);
		t.get("garbage");
		t.printDeque(head);
		System.out.println(t);
		
		System.out.println("REMOVE");

		t.remove("apple");
		t.printDeque(head);
		System.out.println(t);
		t.remove("bob");
		t.printDeque(head);
		System.out.println(t);
		t.remove("jack");
		t.printDeque(head);
		System.out.println(t);
		t.remove("car");
		t.printDeque(head);
		System.out.println(t);
		t.remove("dad");
		t.printDeque(head);
		System.out.println(t);
		t.remove("ear");
		t.printDeque(head);
		System.out.println(t);
		t.remove("igloo");
		t.printDeque(head);
		System.out.println(t);
		t.remove("face");
		t.printDeque(head);
		System.out.println(t);
		t.remove("hat");
		t.printDeque(head);
		System.out.println(t);
		t.remove("garbage");
		t.printDeque(head);
		System.out.println(t);
	}
}