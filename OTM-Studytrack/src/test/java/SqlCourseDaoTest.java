
import java.io.File;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.database.*;

public class SqlCourseDaoTest {

    public SqlCourseDaoTest() {
    }

    @Before
    public void setUp() throws SQLException {
        //Creates fresh db
        Database db = new Database("src/test/java/db/testdb.db");
        SqlTaskEntryDao entryDao = new SqlTaskEntryDao(db);
        SqlTaskTypeDao taskDao = new SqlTaskTypeDao(db, entryDao);
        SqlCourseDao courseDao = new SqlCourseDao(db, taskDao);
    }

    @After
    public void tearDown() {
        //Removes test db file
        File file = new File("src/test/java/db/testdb.db");
        file.delete();
    }

    @Test
    public void courseIsAddedCorrectly() {
        File file = new File("src/test/java/db/testdb.db");
        file.delete();

    }
}
