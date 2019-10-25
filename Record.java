
public class Record {
	
	private Pair recordKey;
	private String recordData;

	public Record (Pair key, String data) {
		
		this.recordKey = key;
		this.recordData = data;
		
	}
	
	public Pair getKey() {
		
		return this.recordKey;
		
	}
	
	public String getData() {
		
		return this.recordData;
		
	}
	
}
