package controller;

import javax.servlet.http.HttpSession;

import dto.ConsultationDto;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;


import entity.Greeting;
import entity.HelloMessage;

@Controller
public class GreetingController {

    @GetMapping(value = "/wTest")
    @Order(value = 1)
    public String displayMenu( Model model) {
        return "wtst";
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