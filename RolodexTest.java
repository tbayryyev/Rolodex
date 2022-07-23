package rolodex;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RolodexTest {
	
	

	@Test
	void containsTest() {
		Rolodex r = new Rolodex();
		
		
		r.addCard("Anothny", "123");
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23"); 
		r.addCard("Zion", "123");
		
		assertTrue(r.contains("Chloe"));
		
		assertTrue(r.contains("Cris"));
		
		assertTrue(r.contains("Chad"));
		
		assertFalse(r.contains("Chris"));
		
		assertFalse(r.contains("Ava"));
		
		assertTrue(r.contains("Zion"));
		
		assertTrue(r.contains("Anothny"));
		
		
		
	}
	
	@Test
	void sizeTest() {
		Rolodex r = new Rolodex();
		
		
		
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23"); 
		
		assertEquals(r.size(),6);
		
		r.addCard("Tahyr", "12323");
		r.addCard("Tahyrb", "123");
		
		assertEquals(r.size(),8);
		
	}
	
	@Test
	void lookupTest() {
		Rolodex r = new Rolodex();
		
		
		
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23"); 
		
		assertTrue(r.lookup("Cris").contains("3"));
		assertTrue(r.lookup("Cris").contains("5"));
		assertFalse(r.lookup("Cris").contains("6"));
		assertEquals(r.lookup("Maddie").size(),1);
		assertEquals(r.lookup("Cris").size(),3);
		
	
	}
	

	@Test
	void removeTest() {
		Rolodex r = new Rolodex();
		
		
		
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23");
		r.addCard("Zion", "23");
		r.addCard("Zwade", "212321");
		r.addCard("Zmoney", "4444");
		r.addCard("Zmans", "12343");
		
		assertTrue(r.contains("Chloe"));
		r.removeCard("Chloe", "123");
		
		assertFalse(r.contains("Chloe"));
		
		assertTrue(r.contains("Zion"));
		
		r.removeCard("Zion", "23");
		
		assertFalse(r.contains("Zion"));
		
		
		
		
		
	}
	
	@Test
	void removeAllTest() {
		Rolodex r = new Rolodex();
		
		
		
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23");
		r.addCard("Maddie","124");
		r.addCard("Maddie","125");
		r.addCard("Maddie","128");
		r.addCard("Maddie","122");
		r.addCard("Zion", "23");
		r.addCard("Zion", "212321");
		r.addCard("Zion", "4444");
		r.addCard("Zion", "12343");
		
		
		assertTrue(r.contains("Cris"));
		
		r.removeAllCards("Cris");
		
		assertFalse(r.contains("Cris"));
		
		assertTrue(r.contains("Maddie"));
		
		r.removeAllCards("Maddie");
		
		assertFalse(r.contains("Maddie"));
		
		r.removeAllCards("Zion");
		
		assertFalse(r.contains("Zion"));
	
		
	}
	
	@Test
	void cursorTest() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		for (int i=0; i<15; i++) { 
			r.nextEntry();
		}
		
		assertEquals(r.currentEntryToString(),"Separator P");
		
		r.addCard("Chloe", "123");
		r.addCard("Chad", "23"); 
		r.addCard("Cris", "3");
		r.addCard("Cris", "4"); 
		r.addCard("Cris", "5");
		r.initializeCursor();
		for (int i=0; i<15; i++) { 
			r.nextEntry();
		}
		
		assertEquals(r.currentEntryToString(),"Separator K");
		

		r.initializeCursor();
		for (int i=0; i<15; i++) { 
			r.nextSeparator();
		}
		assertEquals(r.currentEntryToString(),"Separator P");

		
		
		
	
	}
	
	
	

}
