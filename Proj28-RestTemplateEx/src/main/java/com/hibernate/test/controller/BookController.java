package com.hibernate.test.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.DTO.Employee;
import com.hibernate.test.entity.Book;

import com.hibernate.test.service.BookService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private BookService bookService;
    
    OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/savebook", method = RequestMethod.POST)
    @ResponseBody
    public Book saveBook(@RequestBody Book book) {
        Book bookResponse = bookService.saveBook(book);
        return bookResponse;
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public Book getBookDetails(@PathVariable int bookId) {
        Book bookResponse = bookService.findByBookId(bookId);

        return bookResponse;
    }

    //okHttp GET request
    @GetMapping("/fetchEmployee")
	public String getlistofEmplyees() throws IOException {
		Request request = new Request.Builder().url("http://localhost:8081/api/employees").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
    
    //okHttp POST request
    @PostMapping("/addEmployee")
    public String addEmployee(@RequestBody Employee employee) throws IOException {
    	String url="http://localhost:8081/api/employees";
    	ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employee);
    	okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, json);
    	Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
		
    }
    
    //okHttp DELETE request
    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) throws IOException {
    	String url="http://localhost:8081/api/employees/"+id;
    	ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(id);
    	okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, json);
    	Request request = new Request.Builder().url(url).delete(body).build();
		client.newCall(request).execute();
    	return "employee deleted successfully";
    }
    
    //okHttp PUT request
    @PutMapping("/updateEmployee")
    public String updateEmployee(@RequestBody Employee employee) throws IOException {
    	String url="http://localhost:8081/api/employees/";
    	ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employee);
    	okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, json);
    	Request request = new Request.Builder().url(url).put(body).build();
    	Response response = client.newCall(request).execute(); 
    	return response.body().string();
    }
}