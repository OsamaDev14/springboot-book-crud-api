package com.osama.bookapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.osama.bookapi.dto.ResponseStructure;
import com.osama.bookapi.entity.Book;
import com.osama.bookapi.exception.IdNotFoundException;
import com.osama.bookapi.exception.NoRecordAvailableException;
import com.osama.bookapi.repository.BookRepository;

/**
 * Service layer containing business logic for Book operations.
 */
@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
// ===================== CREATE =====================
	
	// Save single book record
	public ResponseStructure<Book> saveBook(Book book){
		
		Book savedBook = bookRepository.save(book);
		
		// Prepare response
		ResponseStructure<Book> res = new ResponseStructure<>();
	    res.setStatusCode(HttpStatus.CREATED.value()); // 201
	    res.setMessage("Book record saved successfully");
	    res.setData(savedBook);
	    
	    return res;
	}
	
	// Save multiple books records
	public ResponseStructure<List<Book>> saveAllBook(List<Book> books){
		
		List<Book> savedBooks = bookRepository.saveAll(books);

        ResponseStructure<List<Book>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.CREATED.value()); // 201
        res.setMessage("All book records saved successfully");
        res.setData(savedBooks);
        
        return res;
	}
	
// ===================== READ =====================
	
	// Fetch all books
	public ResponseStructure<List<Book>> getAllBooks(){
		
		List<Book> books = bookRepository.findAll();
		
        // If list is not empty → return data
        if (!books.isEmpty()) {
        	ResponseStructure<List<Book>> res = new ResponseStructure<>();
        	res.setStatusCode(HttpStatus.OK.value()); // 200
            res.setMessage("Books fetched successfully");
            res.setData(books);

            return res;
        }
        else
        	// If empty → throw exception
        	throw new NoRecordAvailableException("No Record Available in DB");
	}
	
	// Fetch book by ID
	public ResponseStructure<Book> getBookById(Integer id) {
		
		// findById returns Optional (may contain value or be empty) 
    	//wraps the object into Optional Container
        Optional<Book> opt = bookRepository.findById(id);

        if (opt.isPresent()) {
        	ResponseStructure<Book> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Book found");
            res.setData(opt.get());

            return res;
        }
        else
        	throw new IdNotFoundException("Book record with Id : " + id + " does not exist");
	}
	
// ===================== UPDATE =====================
	
	public ResponseStructure<Book> updateBook(Book book) {
		
		ResponseStructure<Book> res = new ResponseStructure<>();

        // Case 1 → Check if ID is provided
        if (book.getId() == null) {
            res.setStatusCode(HttpStatus.BAD_REQUEST.value()); // 400
            res.setMessage("Id is required for update");
            res.setData(null);

            return res;
        }

        // Check if record exists in DB
        Optional<Book> opt = bookRepository.findById(book.getId());

        // Case 2 → Record exists → update
        if (opt.isPresent()) {
            Book updatedBook = bookRepository.save(book);

            res.setStatusCode(HttpStatus.OK.value()); // 200
            res.setMessage("Book updated successfully with ID: " + book.getId());
            res.setData(updatedBook);

            return res;
        }
        else
        	// Case 3 → Record not found
        	throw new IdNotFoundException("Book record with Id : " + book.getId() + " does not exist");
	}
	
	// ===================== DELETE =====================
	
	public ResponseStructure<String> deleteBook(Integer id){
		
		Optional<Book> opt = bookRepository.findById(id);


        if (opt.isPresent()) {
            bookRepository.deleteById(id);
            
            ResponseStructure<String> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value()); // 200
            res.setMessage("Book deleted successfully");
            res.setData("Book with ID " + id + " deleted");

            return res;
        }
        else
        	throw new IdNotFoundException("Book record with Id : " + id + " does not exist");
	}
	
// ===================== CUSTOM SEARCH =====================
	
	// Find books by author
	public ResponseStructure<List<Book>> getBookByAuthor(String author){
		
		List<Book> books = bookRepository.findByAuthor(author);
    	
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Books retrieved for author: " + author);
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("No books found for author: " + author);
	}
	
	// Find by author & title
	public ResponseStructure<Book> getBookByAuthorAndTitle(String author, String title){
		
		Optional<Book> book= bookRepository.findByAuthorAndTitle(author, title);
    	
    	if(book.isPresent()) {
    		ResponseStructure<Book> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Book found for author: " + author + " and title: " + title);
    		res.setData(book.get());
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Book not found for author: " + author + " and title: " + title);
	}
	
	// Find books with Price less than
	public ResponseStructure<List<Book>> getBookByPriceLessThan(double price){
		
		List<Book> books = bookRepository.findByPriceLessThan(price);
    		
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Books with price less than " + price + " retrieved");
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Books with price less than : " +price+ " does not exist");
	}
	
	// Find books within price range
	public ResponseStructure<List<Book>> getBookByPriceBetween(double startRange, double endRange){
		
		List<Book> books = bookRepository.findByPriceBetween(startRange, endRange);
    	
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Books with price range : " + startRange + " to " + endRange);
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Books with price range : " + startRange + " to " + endRange+ " does not exist");
	}
	
	// Find available books
	public ResponseStructure<List<Book>> getBookByAvailability(){
		
		List<Book> books = bookRepository.getBookByAvailability();
    	
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Book record with availability retrieved");
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Book record with availability does not exist");
	}
	
	// Find books by published year
	public ResponseStructure<List<Book>> getBookByPublishedYear(Integer year){
		
		List<Book> books = bookRepository.getBookByPublishedYear(year);
    	
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Book record with published year : " +year+ " retrieved");
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Book record with published year : " +year+ " does not exist");
	}
	
	// Find books by genre
	public ResponseStructure<List<Book>> getBookByGenre(String genre){
		
		List<Book> books = bookRepository.getBookByGenre(genre);
    	
    	if(!books.isEmpty()) {
    		ResponseStructure<List<Book>> res = new ResponseStructure<>();
    		res.setStatusCode(HttpStatus.OK.value());
    		res.setMessage("Book record with genre : " +genre+ " retrieved");
    		res.setData(books);
    		
    		return res;
    	}
    	else
    		throw new NoRecordAvailableException("Book record with genre : " +genre+ " does not exist");
	}
	
	
// ===================== PAGINATION =====================

	// Fetch books with pagination
	public ResponseStructure<Page<Book>> getBookByPagination(int pageNum, int pageSize) {

		if (pageNum < 0 || pageSize <= 0) {
	        throw new IllegalArgumentException("Invalid page number or page size");
	    }

	    // Create pageable object
	    Page<Book> page = bookRepository.findAll(PageRequest.of(pageNum, pageSize));

	    if (!page.isEmpty()) {
	        ResponseStructure<Page<Book>> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Page " + (page.getNumber() + 1) + " of " + page.getTotalPages() +" | Total Records: " + page.getTotalElements());
	        res.setData(page);

	        return res;
	    }
	    else
	    	throw new NoRecordAvailableException("No records found");
	}


// ===================== SORTING =====================

	// Fetch books sorted by field
	public ResponseStructure<List<Book>> getBookBySorting(String fieldName) {
		
		if (fieldName == null || fieldName.isEmpty()) {
	        throw new IllegalArgumentException("Field name cannot be empty");
	    }
		
	    // Sort in ascending order
	    List<Book> books = bookRepository.findAll(Sort.by(fieldName).ascending());

	    if (!books.isEmpty()) {
	        ResponseStructure<List<Book>> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Books sorted by: " + fieldName + " (ASC)");
	        res.setData(books);

	        return res;
	    }
	    else
	    	throw new NoRecordAvailableException("No records found");
	}


// ===================== PAGINATION + SORTING =====================

	// Fetch books with pagination and sorted by filed
	public ResponseStructure<Page<Book>> getBookByPaginationAndSorting(int pageNum, int pageSize, String fieldName) {

		if (pageNum < 0 || pageSize <= 0) {
	        throw new IllegalArgumentException("Invalid page number or page size");
	    }

	    if (fieldName == null || fieldName.isEmpty()) {
	        throw new IllegalArgumentException("Field name cannot be empty");
	    }
		
	    // Pageable with sorting (DESC)
	    PageRequest pageable = PageRequest.of(pageNum, pageSize, Sort.by(fieldName).descending());

	    Page<Book> page = bookRepository.findAll(pageable);

	    if (!page.isEmpty()) {
	        ResponseStructure<Page<Book>> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Page " + (page.getNumber() + 1) + " of " + page.getTotalPages()	+ " | Sorted by: " + fieldName + " (DESC)");
	        res.setData(page);

	        return res;
	    }
	    else
	    	throw new NoRecordAvailableException("No records found");
	}
}
