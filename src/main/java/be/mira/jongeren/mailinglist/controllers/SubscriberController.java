package be.mira.jongeren.mailinglist.controllers;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import be.mira.jongeren.mailinglist.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView subscriptionPage(){
        ModelAndView mav = new ModelAndView("subscribers/index");
        List<SubscriptionList> allLists = subscriptionListRepository.findAll();
        mav.addObject("lists", allLists);
        return mav;
    }

    @RequestMapping(method= RequestMethod.POST)
    public ModelAndView addSubscriber(Subscriber subscriber, @RequestParam(value = "lists", required = false) String[] lists){
        if(lists == null || lists.length == 0){
            ModelAndView mav = new ModelAndView("/subscribers/index");
            mav.addObject("subscriber", subscriber);
            mav.addObject("lists", subscriptionListRepository.findAll());
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }

        this.subscriberService.subscribe(subscriber, lists);

        ModelAndView mav = new ModelAndView("redirect:/activate/"+subscriber.getId());
        return mav;
    }

    @RequestMapping(path = "/activate/{id}", method= RequestMethod.GET)
    public ModelAndView activationPage(){
        ModelAndView mav = new ModelAndView("subscribers/activate");
        return mav;
    }

    @RequestMapping(path = "/activate/{id}", method= RequestMethod.POST)
    public ModelAndView activateSubscriber(@PathVariable("id") Long id,
                                         @RequestParam("token") String token)
    {
        boolean activated = subscriberService.activate(id, token);

        if(activated) {
            ModelAndView mav = new ModelAndView("redirect:/");
            return mav;
        }
        else{
            ModelAndView mav = activationPage();
            mav.setStatus(HttpStatus.I_AM_A_TEAPOT);
            return mav;
        }
    }

}
