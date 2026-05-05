package com.osama.bookapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.osama.bookapi.entity.Book;

//Repository layer → handles database operations
// Extends JpaRepository to Provides built-in CRUD operations (save, findById, findAll, deleteById, etc)
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	
	
 // ===================== Custom method in Repository =====================
	
	//1)- Standard Naming Convention
	
	// fetch books by author
    // Spring automatically generates query based on method name
	List<Book> findByAuthor(String author);
	
	// fetch book by author AND title
    Optional<Book> findByAuthorAndTitle(String author, String title);
    
    // fetch books where price is less than given value 
  	List<Book> findByPriceLessThan(double price);
  	
  	// fetch books between price range
  	List<Book> findByPriceBetween(double startRange, double endRange);
  	
// ===================== CUSTOM JPQL QUERIES =====================

  	// 2)- Using @Query
  	
  	// Fetch all available books (availability = true)
  	@Query("SELECT b FROM Book b WHERE b.availability = true")
  	List<Book> getBookByAvailability();
  	
    // Fetch books by published year (positional parameter)
  	@Query("Select b from Book b where b.publishedYear=?1")
  	List<Book> getBookByPublishedYear(Integer year);
  	
  	// Fetch books by genre (named parameter)
  	@Query("Select b from Book b where b.genre=:genre")
  	List<Book> getBookByGenre(String genre);
}