package be.mira.jongeren.mailinglist.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionListValidationTest extends BeanValidatorTest{

    @Test
    void subscriptionListWithTitleIsValid(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle("main-sequence");

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertTrue(violations.isEmpty());
    }

    @Test
    void subscriptionListWithEmptyTitleGivesValidationErrors(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle("");

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertEquals(1, violations.size());
    }

    @Test
    void subscriptionListWithTitleNullGivesValidationErrors(){
        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setTitle(null);

        Set<ConstraintViolation<SubscriptionList>> violations = validator().validate(subscriptionList);

        assertEquals(1, violations.size());
    }


}
