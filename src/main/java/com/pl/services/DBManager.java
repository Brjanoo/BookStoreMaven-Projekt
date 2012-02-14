package com.pl.services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.pl.projectfiles.*;


public class DBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addBookToCustomerStmt;
	private PreparedStatement deleteAllBookFromCustomerStmt;
	private PreparedStatement getBookCustomerStmt;
	private PreparedStatement deleteAllCustomerBooksStmt;
	
	public DBManager() {
		
		Properties props = new Properties();
		
		try {
			
			try {	
				props.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}		

		
			conn = DriverManager.getConnection(props.getProperty("url"));
			stmt = conn.createStatement();
			boolean customer_bookTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while(rs.next()) {
				if("customer_book".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					customer_bookTableExists = true;
					break;
				}
			}


			if(!customer_bookTableExists) {
				stmt.executeUpdate("" +
						"CREATE TABLE customer_book(customer_id int, book_id int, " +
						"CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (id), " +
						"CONSTRAINT book_id_fk FOREIGN KEY (manga_id) REFERENCES book (id))" +
						"");
			}

			addBookToCustomerStmt = conn.prepareStatement("" +
					"INSERT INTO customer_book (customer_id, book_id) VALUES (?, ?)" +
					"");

			deleteAllBookFromCustomerStmt = conn.prepareStatement("" +
					"DELETE FROM customer_book WHERE customer_id = ?" +
					"");
			
			getBookCustomerStmt = conn.prepareStatement("SELECT Book.title, Book.price, Book.booktype FROM book, customer_book WHERE customer_id = ? and book_id = book.id");

			deleteAllCustomerBooksStmt = conn.prepareStatement("DELETE FROM customer_book");			

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	public Connection getConnection() {
		return conn;
	}	

	public void addBookToCustomer(List<Integer> listCustomerId, List<Integer> listBookId) {
		try {
			for (Integer customerID : listCustomerId) {
				for (Integer bookID : listBookId) {
					addBookToCustomerStmt.setInt(1, customerID);
					addBookToCustomerStmt.setInt(2, bookID);
					addBookToCustomerStmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteAllBookFromCustomer (List<Integer> listCustomerId) {
		try {
			for (Integer customerID : listCustomerId) {
				deleteAllBookFromCustomerStmt.setInt(1, customerID);
				deleteAllBookFromCustomerStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}			


	public List<Book> getCustomerBook (List<Integer> listCustomerId) {
		List<Book> books = new ArrayList<Book>();
		
		try {
			for (Integer customerID : listCustomerId)
			{
				getBookCustomerStmt.setInt(1, customerID);
				ResultSet rs = getBookCustomerStmt.executeQuery();
				while (rs.next()) 
				{
					BookType booktype = null;
					if(rs.getString("booktype").equalsIgnoreCase("horror"))
						books.add(new Book(rs.getString("title"), rs.getInt("price"),BookType.Horror));
					else if(rs.getString("booktype").equalsIgnoreCase("biography"))
						books.add(new Book(rs.getString("title"), rs.getInt("price"),BookType.Biography));
					else if(rs.getString("booktype").equalsIgnoreCase("criminal"))
						books.add(new Book(rs.getString("title"), rs.getInt("price"),BookType.Criminal));

				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;

	
	}
	
	public void deleteAllCustomerBooks ()
	{
		try
		{
			deleteAllCustomerBooksStmt.executeUpdate();
		}
		catch (SQLException e)
		{

			e.printStackTrace();
		}

	}	
	

}