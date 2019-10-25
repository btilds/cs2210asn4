import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserInterface {

	public static void main(String[] args) {
		
		OrderedDictionary dictionary = new OrderedDictionary();
		final String TEXT = "text";
		final String IMAGE = "image";
		final String AUDIO = "audio";
		SoundPlayer player = new SoundPlayer();
		PictureViewer viewer = new PictureViewer();
		StringReader keyboard = new StringReader();
		String data;
		String filename = args[0];
		int quit = 0;
		
		try {
			
			BufferedReader in = new BufferedReader(new FileReader(filename));
		    String word = in.readLine();
			while (word != null) {
				
			    try {
					data = in.readLine();
					if(data.endsWith(".wav") || data.endsWith(".mid")) {
						dictionary.put(new Record(new Pair(word.toLowerCase(),AUDIO),data));
					}
					else if(data.endsWith(".jpg") || data.endsWith(".gif")){
						dictionary.put(new Record(new Pair(word.toLowerCase(),IMAGE),data));
					}
					else {
						dictionary.put(new Record(new Pair(word.toLowerCase(),TEXT),data));
					}
					word = in.readLine();
			    }
			    
			    catch (Exception e) {
			    	System.out.println("File not found");
			    	System.exit(0);
			    }
			    
			}
		} 	
		catch (IOException e) {
			
			System.out.println("File not found");
	    	System.exit(0);
		}
		while(quit == 0) {
			
			String line = keyboard.read("Enter in a command: ");
			
			if(line.startsWith("get")) {
				
				int errors = 0;
				
				try {
					String audioFile = dictionary.get(new Pair(line.substring(4), AUDIO)).getData();
					player.play(audioFile);
				} catch (MultimediaException | NullPointerException e) {
					errors++;
				}
				
				try {
					String imageFile = dictionary.get(new Pair(line.substring(4), IMAGE)).getData();
					viewer.show(imageFile);
				}
				catch (MultimediaException | NullPointerException e) {
					errors++;
				}
				try {
					String text = dictionary.get(new Pair(line.substring(4), TEXT)).getData();
					System.out.println(text);
				}
				catch (NullPointerException e) {
					errors++;
				}
				if(errors == 3) {
					String precWord = "";
					if(dictionary.predecessor(new Pair(line.substring(4), TEXT)) != null) {
						precWord = dictionary.predecessor(new Pair(line.substring(4), TEXT)).getKey().getWord();
					}
					
					String sucWord = "";
					if(dictionary.successor(new Pair(line.substring(4), TEXT)) != null) {
						sucWord = dictionary.successor(new Pair(line.substring(4), TEXT)).getKey().getWord();
					}
					System.out.println(String.format("\nThe word %s is not in the dictionary.", line.substring(4)));
					System.out.println(String.format("Preceeding word: %s", precWord));
					System.out.println(String.format("Suceeding word: %s\n", sucWord));
				}
			}
			
			else if(line.startsWith("delete")) {
				
				String[] splited = line.split(" ");
				try {
				dictionary.remove(new Pair(splited[1], splited[2]));
				}
				catch (DictionaryException e) {
					System.out.println(String.format("\nNo record in the ordered dictionary has key (%s, %s).\n", splited[1], splited[2]));
				}
			}
			
			else if(line.startsWith("put")) {
				
				String[] splited = line.split(" ");
				int length = 6 + splited[1].length() + splited[2].length();
				try {
					dictionary.put(new Record(new Pair(splited[1], splited[2]), line.substring(length)));
				}
				catch(DictionaryException e) {
				}
			}
			
			else if(line.startsWith("list")) {
				String output = "";
				String prefix = line.substring(5);
					try {
						Pair input = new Pair(prefix, TEXT);
						Record successor = dictionary.successor(input);
						while(successor.getKey().getWord().startsWith(prefix)) {
							if(output.length() == 0) {
								output = successor.getKey().getWord();
							}
							else {
								output = output + ", " + successor.getKey().getWord();
							}
							successor = dictionary.successor(successor.getKey());
						}
					} 
					catch (NullPointerException e) {
					}
				if(output.length() == 0) {
					System.out.println(String.format("\nNo words in the ordered dictionary start with prefix %s.\n", prefix));
				}
				else {
					System.out.println(output);
				}
			}
			
			else if(line.startsWith("smallest")) {
				
				try {
					Record smallest = dictionary.smallest();
					String output = String.format("\n%s, %s, %s\n", smallest.getKey().getWord(), smallest.getKey().getType(), smallest.getData());
					System.out.println(output);
				} catch (NullPointerException e) {
				}
			}
			
			else if(line.startsWith("largest")) {
				
				try {
					Record largest = dictionary.largest();
					String output = String.format("\n%s, %s, %s.\n", largest.getKey().getWord(), largest.getKey().getType(), largest.getData());
					System.out.println(output);
				} catch (NullPointerException e) {
				}
			}
			
			else if(line.startsWith("finish")) {
				
				quit = 1;
				System.out.println("Exiting program.");
				System.exit(0);
			}
			
			else {
				System.out.println("Please enter a valid command.");
			}
		}
	}
}
