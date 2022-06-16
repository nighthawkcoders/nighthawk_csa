package com.nighthawk.csa.mvc.typingGame;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class typingController {

	@GetMapping("/mvc/typing")
	public String typing() {
		return "mvc/typing";
	}

}
