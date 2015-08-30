package org.semki.dutchtreat.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.mvc.dto.Event;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventsController {
	static List<Event> events = new ArrayList<>();
	static {
		Event e1 = new Event();
		e1.id = 1;
		e1.name = "Байкал";
		events.add(e1);
		Event e2 = new Event();
		e2.id = 2;
		e2.name = "Бухатон";
		events.add(e2);
	}
	
	@RequestMapping("/eventos")
	public List<Event> getEvents() {
		return events;
	}
	
	@RequestMapping("/eventos/{eventId}")
	public Event getEventById(@PathVariable Integer eventId) {
		Event result = null;
		for (Event e : events) {
			if (e.id == eventId) {
				result = e;
			}
		}
		return result;
	}
}
