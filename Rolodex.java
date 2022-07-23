package rolodex;
//Tahyr Bayryyev
import java.util.ArrayList;

public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	// Constructor
	Rolodex() {
	    index = new Entry[26];
				
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

	public void initializeCursor() {
		cursor = index[0];
	}
	public void nextSeparator() {
		cursor = cursor.next;
		while(!cursor.isSeparator()) {
			cursor = cursor.next;
		}
	}
	public void nextEntry() {
		cursor = cursor.next;
	}
	public String currentEntryToString() {
		return cursor.toString();
	}

}
