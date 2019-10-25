
public class OrderedDictionary implements OrderedDictionaryADT {
	
	public Node rootNode;
	
	public OrderedDictionary() {
		
		this.rootNode = null;
		
	}
	
	private Record getRecord(Pair k, Node root) {
		
		if(root == null) {
			return null;
		}
		else if(k.compareTo(root.getNodeRecord().getKey()) == 0) {
			return root.getNodeRecord();
		}
		else if(k.compareTo(root.getNodeRecord().getKey()) < 0) {
			return getRecord(k, root.getLeftChild());
		}
		else if(k.compareTo(root.getNodeRecord().getKey()) > 0){
			return getRecord(k, root.getRightChild());
		}
		else {
			return null;
		}
	}

	public Record get(Pair k) {
		
		return getRecord(k, rootNode);
		
	}
	
	private Node putRecord(Record r, Node root) throws DictionaryException {
		
		if(root == null) {
			root = new Node(r);
			Node leftChild = null;
			Node rightChild = null;
			root.setLeftChild(leftChild);
			root.setRightChild(rightChild);
			return root;
		}
		if(r.getKey().compareTo(root.getNodeRecord().getKey()) < 0) {
			root.setLeftChild(putRecord(r, root.getLeftChild()));
			root.getLeftChild().setParent(root);
		}
		else if(r.getKey().compareTo(root.getNodeRecord().getKey()) > 0) {
			root.setRightChild(putRecord(r, root.getRightChild()));
			root.getRightChild().setParent(root);
		}
		else {
			throw new DictionaryException("A record with the same key as r is already in the dictionary");
		}
		return root;
	}

	public void put(Record r) throws DictionaryException {
		
		rootNode = putRecord(r, rootNode);
		
	}
	
	
	private Node removeRecord(Pair k, Node root) throws DictionaryException {
		
		if(root == null) {
			throw new DictionaryException("Record not in the dictionary");
		}
		if(k.compareTo(root.getNodeRecord().getKey()) < 0) {
			root.setLeftChild(removeRecord(k, root.getLeftChild()));
		}
		else if(k.compareTo(root.getNodeRecord().getKey()) > 0){
			root.setRightChild(removeRecord(k, root.getRightChild()));
		}
		else {
			if(root.getLeftChild() == null) {
				return root.getRightChild();
			}
			else if(root.getRightChild() == null) {
				return root.getLeftChild();
			}
			root.setNodeRecord(getSmallest(root.getRightChild()));
			root.setRightChild(removeRecord(root.getNodeRecord().getKey(), root.getRightChild()));
		}
		return root;
	}

	public void remove(Pair k) throws DictionaryException {
		
		rootNode = removeRecord(k, rootNode);
		
	}
	
	private Record getSuccessor(Pair k, Node root) {
		
		if(root == null) {
			return null;
		}
		
		if(k.compareTo(root.getNodeRecord().getKey()) == 0) {
			if(root.getRightChild() != null) {
				return getSmallest(root.getRightChild());
			}
			else {
				Node parent = root.getParent();
				while(parent != null && root == parent.getRightChild()) {
					root = parent;
					parent = parent.getParent();
				}
				return parent.getNodeRecord();
			}
		}
		
		else if(root.isLeaf()) {
			Node parent = root.getParent();
			while(parent != null && root == parent.getRightChild()) {
				root = parent;
				parent = parent.getParent();
			}
			if(parent != null) {
				return parent.getNodeRecord();
			}
			else {
				return null;
			}
		}
		
		else if(k.compareTo(root.getNodeRecord().getKey()) > 0) {
			return getSuccessor(k, root.getRightChild());
		}
		else {
			return getSuccessor(k, root.getLeftChild());
		}
	}

	public Record successor(Pair k) {
		
		return getSuccessor(k, rootNode);
		
	}
	
	private Record getPredecessor(Pair k, Node root, Node prec) {
		
		if(root == null) {
			if(prec != null) {
				return prec.getNodeRecord();
			}
			return null;
		}
		
		else if(k.compareTo(root.getNodeRecord().getKey()) == 0) {
			if(root.getLeftChild() != null) {
				return getLargest(root.getLeftChild());
			}
		}
		
		else if(k.compareTo(root.getNodeRecord().getKey()) < 0) {
			return getPredecessor(k, root.getLeftChild(), prec);
		}
		else {
			prec = root;
			return getPredecessor(k, root.getRightChild(), prec);
		}
		return prec.getNodeRecord();
	}

	public Record predecessor(Pair k) {
		
		return getPredecessor(k, rootNode, null);
		
	}

	private Record getSmallest(Node root) {
		
		if(root == null) {
			return null;
		}
		else {
			Node current = root;
			while (current.getLeftChild() != null) {
				current = current.getLeftChild();
			}
			return current.getNodeRecord();
		}
	}
	
	public Record smallest() {
		
		return getSmallest(rootNode);
	}
	
	private Record getLargest(Node root) {
		
		if(root == null) {
			return null;
		}
		else {
			Node current = root;
			while (current.getRightChild() != null) {
				current = current.getRightChild();
			}
			return current.getNodeRecord();
		}
	}
	
	public Record largest() {
		
		return getLargest(rootNode);
		
	}
}
