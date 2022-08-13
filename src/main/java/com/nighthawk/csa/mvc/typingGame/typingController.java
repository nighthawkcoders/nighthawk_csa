package com.nighthawk.csa.mvc.typingGame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class typingController {

	@GetMapping("/mvc/typing")
	public String typing() {
		return "mvc/typing";
	}

}
