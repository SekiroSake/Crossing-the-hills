import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The test class Lab3Test.
 *
 * @author Scot Morse
 */
public class Lab3Test extends junit.framework.TestCase {
    /* !!!!!!! Hardcoded location of test files, edit for your setup !!!!!!!! */
    static final String MOVIE_FILE = "./src/movies/movies_2011c.txt";
    static final String KB_FILE = "./src/movies/KBn.txt";      // "./" means current directory

    static {
        String[] args = {MOVIE_FILE};
        Main.main(args);
    }

    /**
     * Default constructor for test class Lab1Test
     */
    public Lab3Test() {
    }

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     */
    protected void setUp() {

    }

    /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     */
    protected void tearDown() {
    }

    /* ------------------------------------------------------ */

    public void test0() {
        assertEquals(234658, Main.getNumberOfVertices());
    }

    public void test1() {
        //assertEquals(254035,Main.getNumberOfEdges());
        assertEquals(254051, Main.getNumberOfEdges());
    }

    public void kb(int lineN) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(KB_FILE), "UTF-8");
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        int lineNumber = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (lineNumber++ != lineN)
                continue;

            //System.out.printf("Line number = %d\n",++lineNumber);
            String[] a = line.split("\\|");
            String actor1 = a[0];
            String actor2 = a[1];
            boolean pathExists = Boolean.parseBoolean(a[2]);
            int kbNumber = Integer.parseInt(a[3]);
            try {
                List<String> results = Main.getKBNumber(actor1, actor2, false);
                assertEquals(kbNumber, results.size());
            } catch ( PathNotFoundException e ) {
                if (pathExists)
                    fail();
            } catch ( ActorNotFoundException e ) {
                fail();
            }
        }

        sc.close();
    }

    public void test21() {
        kb(1);
    }

    public void test22() {
        kb(2);
    }

    public void test23() {
        kb(3);
    }

    public void test24() {
        kb(4);
    }

    public void test25() {
        kb(5);
    }

    public void test26() {
        kb(6);
    }

    public void test27() {
        kb(7);
    }

    public void test28() {
        kb(8);
    }

    public void test29() {
        kb(9);
    }

    public void test3() {
        String actor1 = "Your name here";
        String actor2 = "Bacon, Kevin (I)";
        try {
            List<String> results = Main.getKBNumber(actor1, actor2, false);
            fail();
        } catch ( PathNotFoundException e ) {
            fail();
        } catch ( ActorNotFoundException e ) {
            // pass
        }
    }

    public void test31() {
        String actor1 = null;
        String actor2 = null;
        try {
            List<String> results = Main.getKBNumber(actor1, actor2, false);
            fail();
        } catch ( PathNotFoundException e ) {
            fail();
        } catch ( ActorNotFoundException e ) {
            // pass
        } catch ( NullPointerException e ) {
            fail();
        }
    }

    public void test32() {
        String actor1 = "";
        String actor2 = "";
        try {
            List<String> results = Main.getKBNumber(actor1, actor2, false);
            fail();
        } catch ( PathNotFoundException e ) {
            fail();
        } catch ( ActorNotFoundException e ) {
            // pass
        } catch ( NullPointerException e ) {
            fail();
        }
    }

    public void test4() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("ABCD", "EFGHI", 2);
        assertEquals(c1, c2);
    }

    public void test5() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("ABCD", "EFGH", 2);
        assertFalse(c1.equals(c2));
    }

    public void test6() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("ABCD", "EFGHI", 5);
        assertEquals(c1, c2);
    }

    public void test7() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("EFGHI", "ABCD", 2);
        assertEquals(c1, c2);
    }

    // note ordering
    public void test8() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("EFGHI", "ABCD", 5);
        assertTrue(c1.compareTo(c2) > 0);
    }

    public void test9() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("EFGHI", "ABCD", 5);
        assertTrue(c2.compareTo(c1) < 0);
    }

    public void test10() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("EFGHI", "ABCD", 2);
        assertTrue(c1.compareTo(c2) == 0);
    }

    public void test11() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("ABCD", "EFGHI", 5);
        assertTrue(c1.hashCode() == c2.hashCode());
    }

    public void test12() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("EFGHI", "ABCD", 5);
        assertTrue(c1.hashCode() == c2.hashCode());
    }

    public void test13() {
        Colleagues c1 = new Colleagues("ABCD", "EFGHI", 2);
        Colleagues c2 = new Colleagues("1235234", "ABCD", 2);
        assertFalse(c1.hashCode() == c2.hashCode());
    }

    // findTopActorPairings() will be worth more than 4 tests
    public void test14() {
        List<Colleagues> results = Main.findTopActorPairings();
        assertNotNull(results);
    }

    public void test15() {
        List<Colleagues> results = Main.findTopActorPairings();
        assertTrue(results.size() == 1);
    }

    public void test16() {
        List<Colleagues> results = Main.findTopActorPairings();
        assertTrue(results.get(0).numMoviesShared == 38);
    }

    public void test17() {
        List<Colleagues> results = Main.findTopActorPairings();
        Colleagues c = results.get(0);
        boolean t1 = c.actor1.equals("Blum, Steve (IX)") && c.actor2.equals("Tatasciore, Fred");
        boolean t2 = c.actor2.equals("Blum, Steve (IX)") && c.actor1.equals("Tatasciore, Fred");
        assertTrue(t1 || t2);
    }
}
