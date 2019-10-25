
public class Node {
	
	private Record nodeRecord;
	private Node leftChild, rightChild, parent;
	
	public Node () {
		
		this.nodeRecord = null;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		
	}

	public Node (Record object) {
		
		this.nodeRecord = object;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		
	}
	
	public Record getNodeRecord() {
		
		return this.nodeRecord;
		
	}
	
	public void setNodeRecord(Record nodeRecord) {
		
		this.nodeRecord = nodeRecord;
		
	}
	
	public Node getLeftChild() {
		
		return this.leftChild;
		
	}
	
	public void setLeftChild(Node child) {
		
		this.leftChild = child;
		
	}
	
	public Node getRightChild() {
		
		return this.rightChild;
		
	}
	
	public void setRightChild(Node child) {
		
		this.rightChild = child;
		
	}
	
	public void setParent(Node parent) {
		
		this.parent = parent;
		
	}
	
	public Node getParent() {
		
		return this.parent;
		
	}
	
	public boolean isLeaf() {
		
		return (leftChild == null && rightChild == null);
				
	}
	
}
