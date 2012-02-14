package com.pl.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pl.projectfiles.Customer;
import com.pl.services.CustomerDBManager;

public class CustomerDBManagerTest {

	CustomerDBManager CustomerDBManager = new CustomerDBManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		CustomerDBManager.addCustomer(new Customer("Bobby", "Dex"));
	}

	@After
	public void tearDown() throws Exception {
		CustomerDBManager.deleteAllCustomers();
	}	

	@Test
	public void testGetConnection() {
		assertNotNull(CustomerDBManager.getConnection());
	}

	@Test
	public void testAddCustomer() {
		CustomerDBManager.addCustomer(new Customer("Joan", "Tex"));
		assertTrue(CustomerDBManager.getAllCustomers().size() == 2);
		assertEquals(2, CustomerDBManager.getAllCustomers().size());
	}


	@Test
	public void testGetAllCustomers() {
		CustomerDBManager.addCustomer(new Customer("Test1", "Test1"));
		CustomerDBManager.addCustomer(new Customer("Test2", "Test2"));
		CustomerDBManager.addCustomer(new Customer("Test3", "Test3"));
		assertEquals(4, CustomerDBManager.getAllCustomers().size());
	}

	@Test
	public void testDeleteAllCustomers() {
		CustomerDBManager.addCustomer(new Customer("Test1", "Test1"));
		CustomerDBManager.addCustomer(new Customer("Test2", "Test2"));
		CustomerDBManager.addCustomer(new Customer("Test3", "Test3"));
		CustomerDBManager.deleteAllCustomers();
		assertEquals(0, CustomerDBManager.getAllCustomers().size());
	}

	@Test
	public void testSearchCustomerbySurname() {
		CustomerDBManager.addCustomer(new Customer("Test1", "Test1"));
		CustomerDBManager.addCustomer(new Customer("Test2", "Test2"));
		CustomerDBManager.addCustomer(new Customer("Test3", "Test3"));
		assertEquals(1, CustomerDBManager.searchCustomerbySurname("Test1").size());
		assertTrue(CustomerDBManager.searchCustomerbySurname("Test1").size() == 1);
	}

	@Test
	public void testDeleteCustomer() {
		CustomerDBManager.addCustomer(new Customer("Test1", "Test1"));
		CustomerDBManager.addCustomer(new Customer("Test2", "Test2"));
		CustomerDBManager.addCustomer(new Customer("Test3", "Test3"));
		assertTrue(CustomerDBManager.getAllCustomers().size() == 4);
		CustomerDBManager.deleteCustomer(CustomerDBManager.searchCustomerbySurname("Test2"));
		assertTrue(CustomerDBManager.getAllCustomers().size() == 3);
	}

}
