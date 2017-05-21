package be.mira.jongeren.mailinglist.domain;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubscriptionListValidationTest extends BeanValidatorTest{

    @Test
    public void subscriptionListWithTitleIsValid(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle("main-sequence");

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void subscriptionListWithEmptyTitleGivesValidationErrors(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle("");

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertEquals(1, violations.size());
    }

    @Test
    public void subscriptionListWithTitleNullGivesValidationErrors(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle(null);

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertEquals(1, violations.size());
    }


}
