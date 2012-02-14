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

public class BookTest {
	
	private static Customer testCustomer;
	private static Book book;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testCustomer = new Customer("Jan", "Kowalski");
		book = new Book("TEST", 45, BookType.Criminal);
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
	public void test_getTitle() {
		assertTrue(testCustomer.getBooksList().get(0).getTitle().equals("TEST"));
	}
	
	@Test
	public void test_setTitle() {
		testCustomer.getBooksList().get(0).setTitle("test2");
		assertTrue(testCustomer.getBooksList().get(0).getTitle().equals("test2"));
	}
	
	@Test
	public void test_getMangaType() {
		assertTrue(testCustomer.getBooksList().get(0).getBookType().equals(BookType.Criminal));
	}
	
	@Test
	public void test_setBookType() {
		testCustomer.getBooksList().get(0).setBookType(BookType.Biography);
		assertTrue(testCustomer.getBooksList().get(0).getBookType().equals(BookType.Biography));
	}	
	
	@Test
	public void test_getPrice() {
		assertTrue(testCustomer.getBooksList().get(0).getPrice() == 45);
	}
	
	@Test
	public void test_setPrice() throws PriceLessThanZeroException {
		testCustomer.getBooksList().get(0).setPrice(222);
		assertTrue(testCustomer.getBooksList().get(0).getPrice() == 222);
	}
	
}

