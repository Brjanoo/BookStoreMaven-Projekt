package com.pl.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.pl.projectfiles.*;
import com.pl.exception.*;

public class CustomerTest {
	
	private static Customer testCustomer;
	private static Book book;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testCustomer = new Customer("Jan", "Kowalski");
		book = new Book("TEST", 111, BookType.Criminal);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testCustomer.addBook(book); 
	}

	@After
	public void tearDown() throws Exception {
		testCustomer.removeAllBooks();
	}

	@Test
	public void test_addBook() throws PriceLessThanZeroException {		
		assertTrue(testCustomer.getBooksList().size() > 0);
		assertSame(book, testCustomer.getBooksList().get(0));
		assertNotNull(testCustomer.getBooksList());
	}	
	
	@Test
	public void test_printCustomer() {
		String expectedResult = "Name: Jan Surname: Kowalski";
		assertEquals(expectedResult, testCustomer.printCustomers());	
	}
	
	@Test
	public void test_getName() {
		assertTrue(testCustomer.getName().equals("Jan"));
	}	

	@Test
	public void test_setName() {
		testCustomer.setName("JanTest");
		assertTrue(testCustomer.getName().equals("JanTest"));
	}	

	@Test
	public void test_getSurname() {
		assertTrue(testCustomer.getSurname().equals("Kowalski"));
	}	

	@Test
	public void test_setSurname() {
		testCustomer.setSurname("KowalskiTest");
		assertTrue(testCustomer.getSurname().equals("KowalskiTest"));
	}	
	
	@Test
	public void test_getBooksList() {
		assertNotNull(testCustomer.getBooksList());
	}
	
	@Test
	public void test_removeBook() throws PriceLessThanZeroException {
		testCustomer.removeBook(testCustomer.findBookByTitle("TEST"));
		assertTrue(testCustomer.getBooksList().size() == 0); 
	}
	
	@Test
	public void test_removeAllBooks() throws PriceLessThanZeroException {
		testCustomer.removeAllBooks();
		assertTrue(testCustomer.getBooksList().size() == 0);
	}

	@Test
	public void test_findBookByTitle() {
		assertSame(testCustomer.findBookByTitle("TEST"), testCustomer.getBooksList().get(0));
	}
	
	@Test(expected=PriceLessThanZeroException.class, timeout=100)
	public void testPriceLessThanZeroException() throws PriceLessThanZeroException {
		book.setPrice(-8);
	}
}

