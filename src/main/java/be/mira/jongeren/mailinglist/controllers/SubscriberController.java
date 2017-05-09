package be.mira.jongeren.mailinglist.controllers;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView subscriptionPage(){
        ModelAndView mav = new ModelAndView("subscribers/index");
        return mav;
    }

    @RequestMapping(method= RequestMethod.POST)
    public ModelAndView addSubscriber(Subscriber subscriber){
        this.subscriberService.subscribe(subscriber);
        ModelAndView mav = new ModelAndView("redirect:/activate/"+subscriber.getId());
        return mav;
    }

    @RequestMapping(path = "/activate/{id}", method= RequestMethod.GET)
    public ModelAndView activationPage(){
        ModelAndView mav = new ModelAndView("subscribers/activate");
        return mav;
    }

    @RequestMapping(path = "/activate/{id}", method= RequestMethod.POST)
    public ModelAndView activateSubriber(@PathVariable("id") Long id,
                                         @RequestParam("token") String token){
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
