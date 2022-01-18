package com.nighthawk.csa.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Search Controller (Billy and Sarah)
public class SearchController {

    @GetMapping("/search/search1")
    public String searchSearch() {
            return "search/search1";
        }

}

