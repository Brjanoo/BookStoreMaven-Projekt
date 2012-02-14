package com.pl.projectfiles;

import com.pl.exception.*;
import com.pl.report.SampleReport;
import com.pl.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {

	static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws PriceLessThanZeroException {

		PropertyConfigurator.configure("log4j.properties");
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
		SampleReport obj = new SampleReport();
		obj.generateReport();


	
	
		List<Book> books = new ArrayList<Book>();
		List<Book> books2 = new ArrayList<Book>();
		List<Book> books3 = new ArrayList<Book>();
		
		

		Customer customer1 = new Customer("Stefan", "Kowalski", books);
			try {
				customer1.addBook(new Book("Joe Alex", -11, BookType.Criminal));
				customer1.addBook(new Book("Death to us Part", 42, BookType.Criminal));
				customer1.addBook(new Book("Alexander", 29, BookType.Biography));
				customer1.addBook(new Book("Surgery", 19, BookType.Criminal));
				customer1.addBook(new Book("Dracula", 44, BookType.Horror));

			}catch(PriceLessThanZeroException e){
				logger.error(e);
			}

			customer1.printCustomers();
			customer1.printBooks();
			System.out.println("There are: " + books.size() + " books on the list");
			System.out.println("\n");


		Customer customer2 = new Customer("John", "Smiths", books2);

			//Useless exception - for the lulz
			int exceptionRand = (int)(Math.random() * (3 - 1) + 1);
			
			System.out.println("exceptionRand = " + exceptionRand);
			try {
				if(exceptionRand == 1) {
					throw new IOException("Unexpected error in our system - sorry for inconvenience\n");
				}else{
					System.out.println("Thank you for adding product to your cart");
				}
			}catch(IOException exc){
				System.err.println("\nERROR: "+ exc.getMessage());
			}

			customer2.addBook(new Book("Dark Water", 35, BookType.Criminal));

			customer2.printCustomers();
			customer2.printBooks();

			customer2.removeAllBooks();
			customer2.printBooks();
			customer2.addBook(new Book("Dark Water", 35, BookType.Criminal));
			customer2.addBook(new Book("Alexnder", 39, BookType.Biography));
			customer2.addBook(new Book("Joe Alex", 45, BookType.Criminal));
			customer2.addBook(new Book("Death to us Part", 39, BookType.Criminal));
			customer2.addBook(new Book("Surgery", 35, BookType.Criminal));
			customer2.addBook(new Book("Dracula", 40, BookType.Horror));
			customer2.findBookByTitle("Dark Water").printBooks();
			customer2.removeBook(customer2.findBookByTitle("Dark Water"));
			
			System.out.println("-------------------------------");
			customer2.printSearchResult(customer2.findAllBooksByBookType(BookType.Horror));
			System.out.println("-------------------------------");
			customer2.editBookPrice(customer2.findAllBooksByBookType(BookType.Criminal), 666);
			System.out.println("-------------------------------");
			customer2.printSearchResult(customer2.findAllBooksByBookType(BookType.Biography));
			
			
			
	//HIBERNATE		
			
			List<Customer> owners= new ArrayList<Customer>();
			
			SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
			Session session=sessionFactory.openSession();
			session.beginTransaction();			
			
			/*session.save(p);
			for(Car car :cars)
			{
				session.save(car);
			}*/
			session.persist(customer1);

			
			
			
			
			session.save(customer2);
			
			List<Customer> customers= session.getNamedQuery("Customer.all").list();
			
			
			//session.save(garage2);
			//session.save(garage);
			session.get(Customer.class, 1000);
			session.getTransaction().commit();
			int i=1;
			do
				{
				owners.add((Customer)session.get(Customer.class,i));
				i++;
				}
			while(session.get(Customer.class,i)!=null);
			
			
			
			

			session.close();
			System.out.println(owners.size());
			
			for(Customer customer :customers)
			{
				System.out.print(customer.getName());
			}	
		
			
			
	}


}