package com.example.RateLimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author jutekar
 *
 */

@RestController
@RequestMapping("/test")
public class Controller {

	@GetMapping("/abc")
	public void test() {
		System.out.println("test method called ");

	}

	@GetMapping("/xyz")
	public void test1() {
		System.out.println("test1 method called ");

	}
}
