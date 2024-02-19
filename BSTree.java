package itec220.labs;

import static org.junit.Assert.fail;//remove once code is complete



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


@SuppressWarnings("unused")
public class BSTree <Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value>  {
	
	
	private Node root;	
	private Comparator<Key> comparator;	
	private int size = 0;

	
	public BSTree() {
		this.comparator = (k1 , k2) -> {return k1.compareTo(k2);};
	}
	
	public BSTree(Comparator<Key> comparator) {
		
		super();
		this.comparator = comparator;
	}
	
	private class Node
	{
				
		public Node(Key key, Value data) {
			// this is a leaf node
			super();			
			this.data = data;
			this.key = key;
			this.rightChild = null;
			this.leftChild = null;
		}

		 public Key key;		 
		 public Value data;		
		 public Node rightChild;		
		 public Node leftChild;
	}
	
	 
	public Value findRecursively(Key key) {
		Node searchvalue = root;
		Value answer = null;
		if(root == null || key == null) {
			return answer;
		}
		answer = findRecursiveBacktracker(searchvalue, key);
		return answer;
	}
	
	private Value findRecursiveBacktracker(Node position, Key key) {
		Value answer = null;
		if(position == null) {
			return answer;
		}else {
			if(position.key.compareTo(key) == 0) {
				return position.data;
			}else if(position.key.compareTo(key) > 0) {
				answer = findRecursiveBacktracker(position.leftChild, key);
			}else if(position.key.compareTo(key) < 0) {
				answer = findRecursiveBacktracker(position.rightChild, key);
			}
		}
		return answer;
	}
	 
	private Value findIteratively(Key key) {
		Node searchvalue = root;
		boolean solved = false;
		while(solved == false) {
			if(searchvalue == null) {
				return null;
			}
			if(searchvalue.key.compareTo(key) == 0) {
				solved = true;
			}else if(searchvalue.key.compareTo(key) > 0) {
				searchvalue = searchvalue.leftChild;
			}else if(searchvalue.key.compareTo(key) < 0) {
				searchvalue = searchvalue.rightChild;
			}
		}
		return searchvalue.data;
	}
	 
	
	@Override
	public boolean insert(Key key, Value value) {
		if(key == null || value == null) {
			return false;
		}
		if(contains(key) == true) {
			return false;
		}
		Node temp = new Node(key, value);
		
		if(size == 0) {
			root = temp;
		}else {
			Node position = root;
			boolean solved = false;
			while(solved == false) {
				if(position.key.compareTo(key) >= 0) {
					if(position.leftChild != null) {
						position = position.leftChild;
					}else {
						position.leftChild = temp;
						solved = true;
					}
				}else if(position.key.compareTo(key) < 0) {
					if(position.rightChild != null) {
						position = position.rightChild;
					}else {
						position.rightChild = temp;
						solved = true;
					}
				}
			}
		}
		size++;
		return true;

	}
	@Override
	public void printTree(Traversal order) {
		
	ArrayList<Value> printOutput = values(order);
	for (int i = 0; i < printOutput.size(); i++) {
		System.out.println(printOutput.get(i));
	}
		
	}
	
	@Override
	public void clear() {
		root = null;
		size = 0;
	}
	

	public boolean isEmpty()
	{
		if(size != 0) {
			return false;
		}
		return true;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Key key) {
		Value temp = find(key);
		if(temp != null) {
			return true;
		}
		return false;		
	}

	
	public Value remove(Key key) {
		// Complete for Programming Assignment 3
		if(key == null) {
			return null;
		}
		Value answer = null;
		Node temp = root;
		Node previous = null;
		Node successor = null;
		boolean solved = false;
		while(solved == false) {
			if(temp == null) {
				return null;
			}
			if(temp.key.compareTo(key) == 0) {
				solved = true;
			}else if(temp.key.compareTo(key) > 0) {
				temp = temp.leftChild;
			}else if(temp.key.compareTo(key) < 0) {
				temp = temp.rightChild;
			}
		}
		if(temp.key.compareTo(root.key) != 0) {
			previous = findPrevious(key);
		}
		if(temp.leftChild != null || temp.rightChild != null) {
			successor = findSuccessor(temp);
		}
		
		if(successor == null && previous == null) {
			answer = temp.data;
			temp.leftChild = null;
			temp.rightChild = null;
			temp = null;
			root.leftChild = null;
			root.rightChild = null;
			root = null;
		}else if(successor == null && previous != null) {
			if(previous.leftChild != null && previous.leftChild.equals(temp)) {
				answer = temp.data;
				temp = null;
				previous.leftChild = null;
			}else if(previous.rightChild != null && previous.rightChild.equals(temp)) {
				answer = temp.data;
				temp = null;
				previous.rightChild = null;
			}
		}else if(successor != null && previous != null) {
			if(temp.leftChild != null && temp.leftChild.key.equals(successor.key)) {
				successor.leftChild = null;
				successor.rightChild = temp.rightChild;
			}else if(temp.rightChild.key.equals(successor.key)) {
				successor.leftChild = temp.leftChild;
				successor.rightChild = null;
			}else {
				successor.leftChild = temp.leftChild;
				successor.rightChild = temp.rightChild;
			}
			Node presuccessor = findPrevious(successor.key);
			if(presuccessor.leftChild != null && presuccessor.leftChild.key.equals(successor.key)) {
				presuccessor.leftChild = null;
			}else if(presuccessor.rightChild != null && presuccessor.rightChild.key.equals(successor.key)) {
				presuccessor.rightChild = null;
			}
			if(previous.leftChild != null && previous.leftChild.equals(temp)) {
				previous.leftChild = successor;
			}else if(previous.rightChild != null && previous.rightChild.equals(temp)) {
				previous.rightChild = successor;
			}
			answer = temp.data;
			temp.leftChild = null;
			temp.rightChild = null;
			temp = null;
		}else if(successor != null && previous == null) {
			if(temp.leftChild != null && temp.leftChild.key.equals(successor.key)) {
				successor.leftChild = null;
				successor.rightChild = temp.rightChild;
			}else if(temp.rightChild != null && temp.rightChild.key.equals(successor.key)) {
				successor.leftChild = temp.leftChild;
				successor.rightChild = null;
			}else {
				successor.leftChild = temp.leftChild;
				successor.rightChild = temp.rightChild;
			}
			Node presuccessor = findPrevious(successor.key);
			if(presuccessor.leftChild != null && presuccessor.leftChild.key.equals(successor.key)) {
				presuccessor.leftChild = null;
			}else if(presuccessor.rightChild != null && presuccessor.rightChild.key.equals(successor.key)) {
				presuccessor.rightChild = null;
			}
			answer = temp.data;
			temp.leftChild = null;
			temp.rightChild = null;
			temp = null;
			root = successor;
		}
		size--;
		return answer;
	}
	
	private Node findPrevious(Key key) {
		boolean solved = false;
		Node searchvalue = root;
		while(solved == false) {
			if(searchvalue.leftChild != null && searchvalue.leftChild.key.compareTo(key) == 0 || searchvalue.rightChild != null && searchvalue.rightChild.key.compareTo(key) == 0) {
				solved = true;
			}else if(searchvalue.key.compareTo(key) > 0) {
				searchvalue = searchvalue.leftChild;
			}else if(searchvalue.key.compareTo(key) < 0) {
				searchvalue = searchvalue.rightChild;
			}
		}
		return searchvalue;
	}
	private Node findSuccessor(Node base) {
		boolean solved = false;
		boolean LeftRight = false;
		Node searchvalue = base;
		int length = 0;
		while(solved == false) {
			if(length == 0) {
				if(searchvalue.leftChild != null) {
					searchvalue = searchvalue.leftChild;
				}else if(searchvalue.rightChild != null && searchvalue.leftChild == null) {
					searchvalue = searchvalue.rightChild;
					LeftRight = true;
				}
			}else {
				if(LeftRight == false && searchvalue.rightChild != null) {
					searchvalue = searchvalue.rightChild;
				}else if(LeftRight == true && searchvalue.leftChild != null) {
					searchvalue = searchvalue.leftChild;
				}
			}
			
			if(LeftRight == false && searchvalue.rightChild == null) {
				solved = true;
			}else if(LeftRight == true && searchvalue.leftChild == null) {
				solved = true;
			}
			length++;
		}
		return searchvalue;
	}
	
	@Override
	public ArrayList<Value> values(Traversal order) {
		
		if(root == null) {
			return null;
		}
		ArrayList<Key> treeContents = new ArrayList<>();
		ArrayList<Value> answer = new ArrayList<>();
		Node position = root;
		if(order.equals(Traversal.PRE_ORDER)) {
			if (comparator.compare(position.key, position.rightChild.key) < 0) {
	            // Use left-first traversal
	            treeContents = preorderL(root, treeContents, comparator);
	        } else {
	            // Use right-first traversal
	            treeContents = preorderR(root, treeContents, comparator.reversed());
	        }
		}else if(order.equals(Traversal.IN_ORDER)) {
			treeContents = inorder(position, treeContents);
		}else if(order.equals(Traversal.POST_ORDER)) {
			if (comparator.compare(position.key, position.rightChild.key) < 0) {
	            // Use left-first traversal
	            treeContents = postorderL(root, treeContents, comparator);
	        } else {
	            // Use right-first traversal
	            treeContents = postorderR(root, treeContents, comparator.reversed());
	        }
		}else if(order.equals(Traversal.LEVEL_ORDER)) {
			int height = height(position);
			for (int i = 0; i <= height; i++) {
				treeContents = levelorder(position, treeContents, i);
			}
		}
		for (int i = 0; i < treeContents.size(); i++) {
			answer.add(find(treeContents.get(i)));
		}
		return answer;
	}
	@Override
	public Value find(Key key) {
		Value answer = findIteratively(key);
		return answer;
	}	
	
	
	private ArrayList<Key> preorderL(Node position, ArrayList<Key> contents, Comparator<Key> comparator){
		if(position == null) {
			return contents;
		}
		contents.add(position.key);
		
		preorderL(position.leftChild, contents, comparator);
		preorderL(position.rightChild, contents, comparator);
		
		return contents;
	}
	private ArrayList<Key> preorderR(Node position, ArrayList<Key> contents, Comparator<Key> comparator){
		if(position == null) {
			return contents;
		}
		contents.add(position.key);
		
		preorderR(position.rightChild, contents, comparator);
		preorderR(position.leftChild, contents, comparator);
		
		return contents;
	}
	
	private ArrayList<Key> inorder(Node position, ArrayList<Key> contents){
		if(position == null) {
			return contents;
		}
		inorder(position.leftChild, contents);
		contents.add(position.key);
		contents.sort(comparator);
		inorder(position.rightChild, contents);
		return contents;
	}

	
	private ArrayList<Key> postorderL(Node position, ArrayList<Key> contents, Comparator<Key> comparator){
		if(position == null) {
			return contents;
		}
		postorderL(position.leftChild, contents, comparator);
		postorderL(position.rightChild, contents, comparator);
		contents.add(position.key);
		return contents;
	}
	
	private ArrayList<Key> postorderR(Node position, ArrayList<Key> contents, Comparator<Key> comparator){
		if(position == null) {
			return contents;
		}
		postorderR(position.rightChild, contents, comparator);
		postorderR(position.leftChild, contents, comparator);
		contents.add(position.key);
		return contents;
	}
	
	private ArrayList<Key> levelorder(Node position, ArrayList<Key> contents, int height){
		if(position == null) {
			return contents;
		}
		if(height == 1) {
			contents.add(position.key);
		}else {
			levelorder(position.leftChild, contents, height -1);
			levelorder(position.rightChild, contents, height -1);
		}
		return contents;
	}
	
	private int height(Node position) {
		if(position == null) {
			return 0;
		}else {
			int lheight = height(position.leftChild);
			int rheight = height(position.rightChild);
			
			if(lheight>rheight) {
				return (lheight + 1);
			}else {
				return (rheight + 1);
			}
		}
	}

	

}
