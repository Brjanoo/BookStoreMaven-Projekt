package com.pl.projectfiles;

import com.pl.exception.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;






@NamedNativeQueries({
	@NamedNativeQuery(
	name = "Customer.all",
	query = "select * from customers",
        resultClass = Customer.class
	)
})
@Entity
@Table(name="Customers")
public class Customer {

	@Id
	@GeneratedValue
	private int ID;	
	
	public String name;
	public String surname;

	@OneToMany(mappedBy="owner",cascade=javax.persistence.CascadeType.PERSIST)
	public List<Book> books = new ArrayList<Book>();

	
	public Customer()
	{}
	
	private static Logger logger = Logger.getLogger(Customer.class);

	public void addBook(Book book) throws PriceLessThanZeroException {
		if (book.getPrice()>0)
			books.add(book);
	}	
	
	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}	
	
	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.books = new ArrayList<Book>();
	}

	public Customer(String name, String surname, List<Book> books) {
		this.name = name;
		this.surname = surname;
		this.books = books;
	}


	public String printCustomers() {
		String tempStorePrintCustomers = "Name: " + name + " Surname: " + surname;
		System.out.println(tempStorePrintCustomers);
		return tempStorePrintCustomers;
	}


	public void printBooks() {
			for(Book m : this.books) {
				m.printBooks();
			}
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
		
	}

	public List<Book> getBooksList() {
		return books;
	}
	
	public void removeBook(Book book) {	
		books.remove(book);
		System.out.println("Book: " + book.getTitle() + " | removed");
	}


	public void removeAllBooks() {
		books.clear();
		System.out.println("All books were removed");
		logger.info("Books deleted by method rmvAllMangas");
	}	

	public Book findBookByTitle(String title) {
		for (Book book : books) {
			if (book.getTitle().equals(title)) {
				return book;
			}
		}
		return null;
	}	
	
	public List<Book> findAllBooksByBookType(BookType booktype) {
		List<Book> results = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getBookType().equals(booktype)) {
				results.add(book);
			}
		}
		return results;
	}
	
	public void printSearchResult (List<Book> list){
		for (Book book : list)
			System.out.println("\t" + book.getTitle() + " | Price: " + book.getPrice() + "ZL" + " | BookType: " + book.getBookType());
	}	
	
	public void editBookPrice (List<Book> list, int price) throws PriceLessThanZeroException {
		for (Book book : list) {
			book.setPrice(price);
		}
	}
	
	public void editBookBookType (List<Book> list, BookType booktype) throws PriceLessThanZeroException {
		for (Book book : list) {
			book.setBookType(booktype);
		}
	}

}