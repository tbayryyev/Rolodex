package rolodex;
//Tahyr Bayryyev
import java.util.ArrayList;

public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	// Constructor
	Rolodex() {
	     // an initial array to keep all 26 seperators since there are 26 letters in the alphabet
	    index = new Entry[26];
		
	    // using ascii to create the different letter Separators		
	    for (int i=0; i<26; i++){
	    	if (i == 0) {
	    		index[i] = new Separator(null,null,(char)65);
	    	}	
	    	else if (i == 25) {
	    		index[i] = new Separator(null, null,(char)90);
	    	}else {
	    		index[i] = new Separator(null, null,(char) (65+i));
	    	}
	    }
		// linking all the separators together to form a linked list
	    for (int i = 0; i <26; i++) {
	    	if (i == 0) {
	    		index[i].next = index[1];
	    		index[i].prev = index[25];
	    	}
	    	else if (i == 25 ) {
	    		index[i].next = index[0];
	    		index[i].prev = index[24];
	    	}else {
		    	index[i].next = index[i+1];
		    	index[i].prev = index[i-1];
	    	}
	    }
	}
	// helper function to get the next separator
	private Entry nextSep(String name) {
		int c = name.charAt(0) -65;
		Entry nextSep;
		
		if (c == 25) {
			nextSep = index[0];
		}else {
			nextSep = index[c+1];
			}
		return nextSep;
		
	}
	
	// goes through the linked list an determines whether the rolodex contains the given input parameter name
	public Boolean contains(String name) {
		int c = name.charAt(0) -65;
		if (c>25 || c<0) {
			return false;
		}
		
		Entry current = index[c];
		while(current != nextSep(name)){
			if(name.equals(current.getName()) && !current.isSeparator()) {
				break;
			}
			current = current.next;
		}
		return current != nextSep(name);
	}
	// returns the size of the rolodex only cards are counted, separators do not count
	public int size() {
		int count = 0;
		Entry current = index[0];
		
		while(current.next!= index[0]) {
			if(!current.isSeparator()) {
				count++;
			}
			current = current.next;
		}
		return count;
	}
	
	//returns an ArrayList with all the cellphones of name (in the order in which they occur in the Rolodex)
	//If name is not in the Rolodex, then an IllegalArgumentException with the message "lookup: name not found" is thrown
	public ArrayList<String> lookup(String name) {
		if(!contains(name)) {
	    	throw new IllegalArgumentException("lookup: name not found");
	    }
		ArrayList<String> l = new ArrayList<String>();
		int c = name.charAt(0) - 65;
		Entry current = index[c];
		    
		while(current != nextSep(name)) {
		    if(name.equals(current.getName()) && !current.isSeparator()) {
		    	l.add(Card.class.cast(current).getCell());
		    	}
		    current = current.next;
		    }
		return l;
	}
	// toString method to visualize the rolodex 
	public String toString() {
		Entry current = index[0];

		StringBuilder b = new StringBuilder();
		while (current.next!=index[0]) {
			b.append(current.toString()+"\n");
			current=current.next;
		}
		b.append(current.toString()+"\n");		
		return b.toString();
	}
	
	// method adds a new card with the specified infor-mation to the Rolodex 
	// If the card already exists (with the same name and cell), then a IllegalArgumentException with the message "addCard: duplicate entry" is thrown
	public void addCard(String name, String cell) {
		int c = name.charAt(0) -65;
		
		if (c>25 || c<0 || !name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("addCard: Invalid name entry");
		}
		
		if(!cell.matches("[0-9]+")) {
			throw new IllegalArgumentException("addCard: Invalid cell entry");
		}
	
		if (contains(name) && lookup(name).contains(cell)) {
			throw new IllegalArgumentException("addCard: duplicate entry");
		}
	
		Entry current = index[c];
		
		while(current != nextSep(name)) {
			if (current.next.isSeparator() && name.compareTo(current.getName()) >=0 ) {
				Card C = new Card(current, current.next,name,cell);	
				Entry n = current.next;
				current.next = C;
				n.prev = C;
				break;
					
				}
			if (name.compareTo(current.getName()) >=0 && name.compareTo(current.next.getName()) <= 0) {
				Card C = new Card(current, current.next,name,cell);
				
				Entry n = current.next;
				
				current.next = C;
				n.prev = C;
				break;
				
			}
			current = current.next;
		}
		
	}
	//removes the specified card
	//throws an IllegalArgumentException with message "removeCard: name does not exist" if there is not card for that name
	// If there is a card with that name but with a different cell-phone number, it throws an IllegalArgumentException with the message "removeCard: cell for that name does not exist"
	public void removeCard(String name, String cell) {
		
		if(!contains(name)) {
			throw new IllegalArgumentException("removeCard: name does not exist");
		}
		
		if(!lookup(name).contains(cell)) {
			throw new IllegalArgumentException("removeCard: cell for that name does not exist");
		}
		
		int c = name.charAt(0) -65;

		
		Entry current = index[c];
		
		while(current != nextSep(name)) {
			if (name.equals(current.getName()) && !current.isSeparator() && cell.equals(Card.class.cast(current).getCell())){
				
				Entry p = current.prev;
				Entry n = current.next;
				
				p.next = n;
				n.prev = p;
			}
			current = current.next;
		}

	}
	
	//removes all cards for name
	//Throws anIllegalArgumentException with message "removeAllCards: name does not exist" if the name is not in the Rolodex
	public void removeAllCards(String name) {
		if(!contains(name)) {
			throw new IllegalArgumentException("removeAllCards: name does not exist");
		}
		
		ArrayList<String> l = lookup(name);
		
		for(int i = 0; i<l.size();i++) {
			removeCard(name,l.get(i));
			
		}
	}

	// Cursor operations
	
	// sets the cursor field to the separator for “A”
	public void initializeCursor() {
		cursor = index[0];
	}
	//moves cursor to the next separator
	public void nextSeparator() {
		cursor = cursor.next;
		while(!cursor.isSeparator()) {
			cursor = cursor.next;
		}
	}
	//moves cursor to the next entry, which could be card or a separator
	public void nextEntry() {
		cursor = cursor.next;
	}
	//returns the string representation of the current entry pointed to by the cursor
	public String currentEntryToString() {
		return cursor.toString();
	}

}
