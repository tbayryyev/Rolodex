package rolodex;
//I pledge my honor that I have abided by the Stevens Honor System.
//Tahyr Bayryyev
import java.util.ArrayList;


public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	// Constructor

	Rolodex() {
		
		index = new Entry[26];
				
	    for (int i=0; i<26; i++){
	    	index[i] = new Separator(null, null,(char) (65+i));
	    }
	    
	    for (int i = 0; i <26; i++) {
			// when at the beginning of the list the previous pointer must be pointing to the end of the list to make it circular list
	    	if (i == 0) {
	    		index[i].next = index[1];
	    		index[i].prev = index[25];
	    	}
			// when at the end of the list the next pointer must be pointing to the start of the list to make it circular list
	    	else if (i == 25 ) {
	    		index[i].next = index[0];
	    		index[i].prev = index[24];
	    	}else {
		    	index[i].next = index[i+1];
		    	index[i].prev = index[i-1];
	    	}
			
	    }
	}
	
	public Boolean contains(String name) {

		Entry current = index[0];	

		while(current.next != index[0]){
			current = current.next;
			if(name.equals(current.getName()) && !current.isSeparator()) {
				return true;
			}
		}

		return false;

	}
	
	public int size() {
		int count = 0;
		Entry current = index[0];
		
		while(current.next!= index[0]) {
			current = current.next;
			if(!current.isSeparator()) {
				count++;
			}
		}
		
		return count;
		
	}
	
	public ArrayList<String> lookup(String name) {
		if(!contains(name)) {
	    	throw new IllegalArgumentException("lookup: name not found");
	    }
	    
		ArrayList<String> l = new ArrayList<String>();
		
						
		Entry current = index[0];
		    
		while(current.next != index[0]) {
			current = current.next;
		    if(name.equals(current.getName()) && !current.isSeparator()) {
		    	l.add(Card.class.cast(current).getCell());
		    	
		    	}
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
		
		if (!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("addCard: Invalid name entry");
		}
		
		if(!cell.matches("[0-9]+")) {
			throw new IllegalArgumentException("addCard: Invalid cell entry");
		}
		
		if (contains(name) && lookup(name).contains(cell)) {
			throw new IllegalArgumentException("addCard: duplicate entry");
		}
		

		Entry current = index[0];
		
		while(current.next != index[0]) {

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
				
		Entry current = index[0];
		
		while(current.next != index[0]) {
			current = current.next;

			if (name.equals(current.getName()) && !current.isSeparator() && cell.equals(Card.class.cast(current).getCell())){
				
				Entry p = current.prev;
				Entry n = current.next;
				
				p.next = n;
				n.prev = p;
			}
			
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
