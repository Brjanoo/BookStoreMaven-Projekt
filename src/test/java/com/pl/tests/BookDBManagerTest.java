package com.pl.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pl.projectfiles.Book;
import com.pl.projectfiles.BookType;
import com.pl.services.BookDBManager;

public class BookDBManagerTest {
	
	BookDBManager BookDBManager = new BookDBManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		BookDBManager.addBook(new Book("Death to us Part", 99, BookType.Criminal));
	}

	@After
	public void tearDown() throws Exception {
		BookDBManager.deleteAllBooks();
	}

	@Test
	public void testGetConnection() {
		assertNotNull(BookDBManager.getConnection());
	}

	@Test
	public void testAddBook() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		BookDBManager.addBook(new Book("Alexander", 35, BookType.Biography));
		assertEquals(3, BookDBManager.getAllBooks().size());
	}

	@Test
	public void testGetAllBooks() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		assertEquals(2, BookDBManager.getAllBooks().size());
	}

	@Test
	public void testDeleteAllBooks() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		BookDBManager.deleteAllBooks();
		assertEquals(0, BookDBManager.getAllBooks().size());
		assertTrue(BookDBManager.getAllBooks().size() == 0);
	}

	@Test
	public void testSearchBookByTitle() {
		BookDBManager.addBook(new Book("Death to us Part", 40, BookType.Criminal));
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		assertTrue(BookDBManager.searchBookByTitle("Joe Alex").size() == 1);
	}

	@Test
	public void testSearchBookByBookType() {
		BookDBManager.addBook(new Book("Death to us Part", 40, BookType.Criminal));
		BookDBManager.addBook(new Book("Joe Alex", 45,BookType.Criminal));
		assertEquals(3, BookDBManager.searchBookByBookType(BookType.Criminal).size());
	}

	@Test
	public void testDeleteBook() {
		BookDBManager.addBook(new Book("Death to us Part", 40, BookType.Criminal));
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		BookDBManager.deleteBook(BookDBManager.searchBookByTitle("Joe Alex"));
		assertEquals(2, BookDBManager.getAllBooks().size()); 
	}

}
