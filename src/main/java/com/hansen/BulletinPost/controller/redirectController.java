package com.hansen.BulletinPost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class redirectController {
	@GetMapping("/")
	public String redirectToPosts(){		
		return "redirect:/posts";
	}
}
