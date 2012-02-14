package com.pl.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pl.projectfiles.Customer;
import com.pl.projectfiles.Book;
import com.pl.projectfiles.BookType;
import com.pl.services.CustomerDBManager;
import com.pl.services.DBManager;
import com.pl.services.BookDBManager;

public class DBManagerTest {
	
	CustomerDBManager CustomerDBManager = new CustomerDBManager();
	BookDBManager BookDBManager = new BookDBManager();
	DBManager DBManager = new DBManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		CustomerDBManager.addCustomer(new Customer("Test1", "Test1"));
		CustomerDBManager.addCustomer(new Customer("Test2", "Test2"));
		BookDBManager.addBook(new Book("Alexander", 35, BookType.Biography));
		BookDBManager.addBook(new Book("Dracula", 40, BookType.Horror));
		DBManager.addBookToCustomer(CustomerDBManager.searchCustomerbySurname("Test1"), BookDBManager.searchBookByTitle("Alexander"));		
	}

	@After
	public void tearDown() throws Exception {
		DBManager.deleteAllCustomerBooks();
		CustomerDBManager.deleteAllCustomers();
		BookDBManager.deleteAllBooks();		
	}

	@Test
	public void testGetConnection() {
		assertNotNull(BookDBManager.getConnection()); 
	}

	@Test
	public void testAddBookToCustomer() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		DBManager.addBookToCustomer(CustomerDBManager.searchCustomerbySurname("Test2"), BookDBManager.searchBookByBookType(BookType.Criminal));
		assertEquals(1, DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test2")).size());
	}

	@Test
	public void testDeleteAllBookFromCustomer() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		DBManager.addBookToCustomer(CustomerDBManager.searchCustomerbySurname("Test2"), BookDBManager.searchBookByBookType(BookType.Criminal));
		
		assertTrue(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test2")).size() == 1);
		assertTrue(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test1")).size() == 1);
		DBManager.deleteAllBookFromCustomer(CustomerDBManager.searchCustomerbySurname("Test2"));
		assertTrue(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test1")).size() == 1);
		assertTrue(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test2")).size() == 0);
	}

	@Test
	public void testGetCustomerBook() {
		assertEquals(1, DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test1")).size());
	}
	
	@Test
	public void testDeleteAllCustomerBooks() {
		BookDBManager.addBook(new Book("Joe Alex", 45, BookType.Criminal));
		DBManager.addBookToCustomer(CustomerDBManager.searchCustomerbySurname("Test1"), BookDBManager.searchBookByBookType(BookType.Criminal));
		assertNotNull(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test1")));
		DBManager.deleteAllCustomerBooks();
		assertTrue(DBManager.getCustomerBook(CustomerDBManager.searchCustomerbySurname("Test1")).size() == 0);
	}	

}
