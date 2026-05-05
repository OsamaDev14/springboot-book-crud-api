package com.osama.bookapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.osama.bookapi.dto.ResponseStructure;
import com.osama.bookapi.entity.Book;
import com.osama.bookapi.service.BookService;

/**
 * REST Controller for managing Book APIs.
 * Handles all HTTP requests related to Book operations.
 */

@RestController
@RequestMapping("/api/books") // Base URL for all APIs
public class BookController {
    
    @Autowired
    private BookService bookService; // Injecting BookService to handle business logic

// ===================== CREATE APIs =====================

    // POST → Save single book record
    // @RequestBody → takes JSON input from user
    @PostMapping
    public ResponseEntity<ResponseStructure<Book>> saveBook(@RequestBody Book book) {
    	
        ResponseStructure<Book> response = bookService.saveBook(book);
        
        // Return response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.CREATED); //201
    }

    
    // POST → Save multiple book records
    @PostMapping("/all")
    public ResponseEntity<ResponseStructure<List<Book>>> saveAllBook(@RequestBody List<Book> books) {

        return new ResponseEntity<>(bookService.saveAllBook(books), HttpStatus.CREATED); //201
    }

// ===================== READ APIs =====================

    // GET → Fetch all books
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks() {

    	return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK); // 200
    }

    // GET → Fetch book by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Book>> getBookById(@PathVariable Integer id) {
    	
    	return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }
  
    
// ===================== UPDATE API =====================

    // PUT → Update existing book
    @PutMapping
    public ResponseEntity<ResponseStructure<Book>> updateBook(@RequestBody Book book) {

        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
    }

// ===================== DELETE API =====================

    // DELETE → Remove book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable Integer id) {

        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }
    
// ===================== CUSTOM SEARCH APIs =====================
    
    // GET → Find books by author
    @GetMapping("/author/{author}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(@PathVariable String author){
    	
    	return new ResponseEntity<>(bookService.getBookByAuthor(author), HttpStatus.OK);
    	
    }
    
    // GET → Find by author + title
    // Example: /api/books/search?author=abc&title=xyz
    @GetMapping("/search")
    public ResponseEntity<ResponseStructure<Book>> getBookByAuthorAndTitle(@RequestParam String author, @RequestParam String title){
    	
    	return new ResponseEntity<>(bookService.getBookByAuthorAndTitle(author, title), HttpStatus.OK);
    }
    
    // GET → Books with price less than given value
    @GetMapping("/price/less/{price}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPriceLessThan(@PathVariable double price){
    	
    	return new ResponseEntity<>(bookService.getBookByPriceLessThan(price), HttpStatus.OK);
    }
    
    // GET → Books between price range
    @GetMapping("/price/between/{start}/{end}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPriceBetween(@PathVariable("start") double startRange,
    		@PathVariable("end") double endRange){
    	
    	return new ResponseEntity<>(bookService.getBookByPriceBetween(startRange, endRange), HttpStatus.OK);
    	
    }
    
    // GET → Available books only
    @GetMapping("/available")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByAvailability(){
    	
    	return new ResponseEntity<>(bookService.getBookByAvailability(), HttpStatus.OK);
    }
    
    // GET → Books by published year
    @GetMapping("/year/{year}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPublishedYear(@PathVariable Integer year){
    	
    	return new ResponseEntity<>(bookService.getBookByPublishedYear(year), HttpStatus.OK);
    }
    
    // GET → Books by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByGenre(@PathVariable String genre){
    	
    	return new ResponseEntity<>(bookService.getBookByGenre(genre), HttpStatus.OK);
    }
    
    
// ===================== PAGINATION =====================

    // GET → Fetch books with pagination
    // page → page number & size → number of records per page
    @GetMapping("/page")
    public ResponseEntity<ResponseStructure<Page<Book>>> getBookByPagination(@RequestParam(name = "page") int pageNum,
    																			@RequestParam(name = "size") int pageSize) {

        return new  ResponseEntity<>(bookService.getBookByPagination(pageNum, pageSize), HttpStatus.OK);
    }
    
// ===================== SORTING =====================
    
    // GET → Fetch books sorted by given field (e.g., price, title)
    @GetMapping("/sort")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookBySorting(
            @RequestParam String fieldName) {

        return new ResponseEntity<>(bookService.getBookBySorting(fieldName), HttpStatus.OK);
    }

    
// ===================== PAGINATION + SORTING =====================

    // GET → Fetch paginated + sorted data
    @GetMapping("/page-sort")
    public ResponseEntity<ResponseStructure<Page<Book>>> getBookByPaginationAndSorting(
            @RequestParam(name = "page") int pageNum, @RequestParam(name = "size") int pageSize, @RequestParam String fieldName) {

        return new ResponseEntity<>(bookService.getBookByPaginationAndSorting(pageNum, pageSize, fieldName), HttpStatus.OK);
    }
}