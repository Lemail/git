package logicmk2;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Test;
public class LogicTests {
    private TestSuite testSuite = new TestSuite(CorrectReaderTest.class, IncorrectFactInRuleTest.class,
            IncorrectFileNameTest.class, IncorrectOperationTest.class, IncorrectRuleTest.class, NullArgumentTest.class,
            SkipLineTest.class, SpaceVariableTest.class, InputFactCheck.class);
    private TestResult result = new TestResult();

    @Test
    public void logicTests() {
        testSuite.setName("Logic Tests");
        System.out.println("Name of Test Suite: " + testSuite.getName());
        System.out.println("Amount of test cases: " + testSuite.countTestCases());
        testSuite.run(result);

    }
}
