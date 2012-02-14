package com.pl.projectfiles;

import org.apache.log4j.*;

import com.pl.exception.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;





@NamedNativeQueries({
	@NamedNativeQuery(
	name = "findStockByStockCodeNativeSQL",
	query = "select * from book",
        resultClass = Book.class
	)
})
@Entity
public class Book {

	
	@Id
	@GeneratedValue
	public int ID;
	
	@OneToOne
	@JoinColumn(name="Book_ID")
	private Book book;	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	private Customer owner;
	
	public String title;
	public int price;
	public BookType booktype;
	
	private static Logger logger = Logger.getLogger(Book.class);

	
	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}	

	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}
	
	
	public Book(String title, int price, BookType booktype) {
		this.title = title;
		this.price = price;
		this.booktype = booktype;
	}

	public String printBooks() {
		String tempStorePrintBooks = "\t" + title + " | Price: " + price + "ZL" + " | BookType: " + booktype;
		System.out.println(tempStorePrintBooks);
		return tempStorePrintBooks;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookType getBookType() {
		return booktype;
	}
	
	public void setBookType(BookType booktype) {
		this.booktype = booktype;
	}
	
	public int getPrice() {
		return price;
	}

	public int setPrice(int price) throws PriceLessThanZeroException {
		if(price < 0) {
			throw new PriceLessThanZeroException("Price cannot be equal or lower than zero");
		}else{
			logger.info("New book: " + title + "added to list" + " Price:" + price);
			return this.price = price;
		}
	}

}

