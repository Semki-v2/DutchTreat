package org.semki.dutchtreat.mvc.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AngularAppStaticController {
	protected static Logger logger = LoggerFactory.getLogger(AngularAppStaticController.class);
	
	@RequestMapping(value="/app/events/**", method=RequestMethod.GET)
	public String redirectToIndex(Map<String, Object> model) {
		return "/app/index.html";
	}
}
