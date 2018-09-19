
import org.junit.Test;

import static org.junit.Assert.*;

public class AssertionTests {

    @Test
    public void shouldDemonstrateAssertionsTest() {
        Integer first = 1;
        Integer second = 2;
        Integer repeatFirst = 1;

        Integer[] expectedIntegerArray = {1, 2, 3, 4, 5};
        Integer[] resultIntegerArray = {1, 2, 3, 4, 5};

        assertEquals(first,repeatFirst);

        assertNotNull(second);

        assertSame(first, repeatFirst);

        assertArrayEquals(expectedIntegerArray, resultIntegerArray);
    }

}
