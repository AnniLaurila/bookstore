package com.example.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.CategoryRepository;

@Controller

public class BookController {

	@Autowired
	private BookRepository repository;
	@Autowired
	private CategoryRepository cRepository;
	
	@RequestMapping(value= {"/", "/index", "booklist"}, method=RequestMethod.GET)
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }
	
	@RequestMapping(value= {"restbooklist"}, method=RequestMethod.GET)
    public @ResponseBody List<Book> restBookList() {
		return (List<Book>) repository.findAll();
    }
	
	@RequestMapping(value= {"/restbook/{id}"}, method=RequestMethod.GET)
    public @ResponseBody Optional<Book> restBook(@PathVariable("id") Long bookId) {
		return repository.findById(bookId);
    }
	
	@RequestMapping(value="/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categorys", cRepository.findAll());
		return "addbook";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(Book book) {
		repository.save(book);
		return "redirect:booklist";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String editBook(@PathVariable("id") Long bookId, Model model) {
		model.addAttribute("book", repository.findById(bookId));
		model.addAttribute("categorys", cRepository.findAll());
		return "editbook";
	}
	
	/*@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public String edit(@ModelAttribute Book book) {
		repository.save(book);
		return "redirect:../booklist";
	}*/

	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long bookId, Model model) {
		repository.deleteById(bookId);
		return "redirect:../booklist";
	}
	
}
