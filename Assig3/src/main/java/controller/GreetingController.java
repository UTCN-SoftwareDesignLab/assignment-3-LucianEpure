package controller;

import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wTest")
public class GreetingController {

    @GetMapping
    @Order(value = 1)
    public String displayMenu( Model model) {
        return "wtest";
    }
/*
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");

    }
    @MessageMapping("/consult")
    @SendTo("/topic/greetings")
    public Greeting greeting(ConsultationDto consultationDto) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(String.valueOf(consultationDto.getPatientId())) + "!");
    }*/
}