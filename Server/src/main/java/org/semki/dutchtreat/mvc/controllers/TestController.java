package org.semki.dutchtreat.mvc.controllers;

import org.semki.dutchtreat.mvc.models.TestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping(value="/tst",method = RequestMethod.GET)
	public @ResponseBody TestModel TestController()
	{
		TestModel test = new TestModel();
		
		test.Name = "Name";
		test.Desc = "DESCRIPTION";
		
		return test;
	}
}
