
import Controller.ValidationController;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author saugat
 */
@RunWith(JUnit4.class)
public class ValidationControllerTest {

    @Test
    public void emailValidateTest() {
        String email1 = "email";
        String msg1 = new ValidationController().checkEmail(email1);
        assertEquals("Wrong Email Format", msg1);

        String email2 = "email@gmail.com";
        String msg2 = new ValidationController().checkEmail(email2);
        assertEquals(null, msg2);

    }

}
