package be.mira.jongeren.mailinglist.domain;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubscriberValidationTest extends BeanValidatorTest {

    @Test
    public void emailAddressWithCorrectSyntaxIsValid(){
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("luke.skywalker@rebellion.org");

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void emailAddressWithInvalidSyntaxGivesValidationError(){
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("notvalid");

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressEmptyGivesValidationError(){
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("");

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressNullGivesValidationError(){
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(null);

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertEquals(1, violations.size());
    }

    @Test
    public void tokenWithLengthEqualTo16IsValid(){
        Subscriber subscriber = new Subscriber("luke@rebellion.org");
        subscriber.setToken("123456789ABCDEFG");
        assertEquals(16, subscriber.getToken().length());

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void tokenWithLengthLargerThan16GivesValidationError(){
        Subscriber subscriber = new Subscriber("luke@rebellion.org");
        subscriber.setToken("123456789ABCDEFGH");

        assertEquals(17, subscriber.getToken().length());

        Set<ConstraintViolation<Subscriber>> violations = validator().validate(subscriber);

        assertEquals(1, violations.size());
    }
}
