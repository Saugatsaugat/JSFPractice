
import Controller.PasswordHashController;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author saugat
 */
@RunWith(JUnit4.class)
public class PasswordHashControllerTest {

    @Test
    public void generateHash() {
        String text1 = "Hello";
        String result = new PasswordHashController().getPasswordHash(text1);
        assertTrue(!result.isEmpty());

        String text2 = null;
        String result2 = new PasswordHashController().getPasswordHash(text2);
        assertEquals(null, result2);

    }

}
