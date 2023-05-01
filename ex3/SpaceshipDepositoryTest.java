import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite test class that runs all the tests.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses(
        {SpaceshipTest.class,
        LockerTest.class,
        LongTermTest.class}
)

public class SpaceshipDepositoryTest {

}
