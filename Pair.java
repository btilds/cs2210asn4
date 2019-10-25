
public class Pair {
	
	private String pairWord;
	private String pairType;
	
	public Pair(String word, String type) {
		
		this.pairWord = word.toLowerCase();
		this.pairType = type.toLowerCase();
	}
	
	public String getWord() {
		
		return this.pairWord;
		
	}
	
	public String getType() {
		
		return this.pairType;
		
	}
	
	public int compareTo(Pair k) {
		
		int wordComparison = this.pairWord.compareTo(k.pairWord);
		int typeComparison = this.pairType.compareTo(k.pairType);
		
		if(wordComparison == 0) {
			if(typeComparison == 0) {
				return 0;
			}
			else if(typeComparison < 0) {
				return -1;
			}
			else {
				return 1;
			}
		}
		else if(wordComparison < 0){
			return -1;
		}
		else {
			return 1;
		}
	}
	
}
