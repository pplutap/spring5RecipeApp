package com.pawel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pawel on 2017-10-24.
 */
@Controller
public class IndexController {

	@RequestMapping({"", "/", "index"})
	public String getIndexPage() {
		return "index";
	}
}
