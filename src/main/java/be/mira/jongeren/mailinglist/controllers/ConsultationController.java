package be.mira.jongeren.mailinglist.controllers;

import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/consult")
public class ConsultationController {

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;


    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("consult/index");
        mav.addObject("subscriptionLists", subscriptionListRepository.findAll());
        return mav;
    }
}
