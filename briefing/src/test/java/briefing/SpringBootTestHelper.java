package briefing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(value = "/schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class SpringBootTestHelper {

}
