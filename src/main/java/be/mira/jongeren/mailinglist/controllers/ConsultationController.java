package be.mira.jongeren.mailinglist.controllers;

import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import be.mira.jongeren.mailinglist.service.MailConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/consult")
public class ConsultationController {

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Autowired
    private MailConfiguration mailConfiguration;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("consult/index");
        mav.addObject("subscriptionLists", subscriptionListRepository.findAll());
        mav.addObject("configurationEmail", mailConfiguration.fromMailAddress);
        return mav;
    }

    @RequestMapping(method= RequestMethod.GET, value="/history/{id}")
    public ModelAndView history(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("consult/history");
        SubscriptionList list = subscriptionListRepository.getOne(id);
        mav.addObject("subscriptionEvents", subscriptionEventRepository.findBySubscriptionList(list));
        return mav;
    }
}
