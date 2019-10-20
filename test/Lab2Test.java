import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The test class Lab2Test.
 *
 * @author  Scot Morse
 */
public class Lab2Test extends junit.framework.TestCase
{
	
    static
	{
        String[] args = {"./src/movies/movies_2011c.txt"};	//<------ !!!!!!! YOU'LL NEED TO CHANGE THIS PATH TO WHEREVER YOU STORED THIS FILE
													// "../.." means two folders up
		Main.main(args);
	}

    
    /* ------------------------------------------------------ */
    
    public void test01()
    {
        // placeholder for passing main() test
    }
    
	public void test0()
	{
		assertEquals(234658,Main.getNumberOfVertices());
	}
	
	public void test1()
	{
		//assertEquals(254035,Main.getNumberOfEdges());
		assertEquals(254051,Main.getNumberOfEdges());
	}
	
    public void test2()
    {
        String actor = "Sean Bean";
        String[] results = {"Age of Heroes (2011)",
                            "Any Day (2014)",
                            "Cleanskin (2012)",
                            "Devil's Peak (????)",
                            "Enemy of Man (2014)",
                            "Hooligan (2012/II)",
                            "Indian Summer (????/II)",
                            "Jupiter Ascending (2014)",
                            "Lego the Lord of the Rings: The Video Game (2012)",
                            "Mirror Mirror (2012/I)",
                            "Percy Jackson: Sea of Monsters (2013)",
                            "Scorched Earth (????)",
                            "Silent Hill: Revelation 3D (2012)",
                            "Soldiers of Fortune (2012)",
                            "The 4th Reich (2014)",
                            "Wicked Blood (2013)",
                            "Wild in the Streets (2012)"};
        List<String> movies = Main.getMovies( actor );
        for( int i = 0; i < results.length; ++i )
        {
            assertEquals( results[i], movies.get(i) );
        }
    } 
    
    public void test3()
    {
        String actor = "sean bean";
        String[] results = {"Age of Heroes (2011)",
                            "Any Day (2014)",
                            "Cleanskin (2012)",
                            "Devil's Peak (????)",
                            "Enemy of Man (2014)",
                            "Hooligan (2012/II)",
                            "Indian Summer (????/II)",
                            "Jupiter Ascending (2014)",
                            "Lego the Lord of the Rings: The Video Game (2012)",
                            "Mirror Mirror (2012/I)",
                            "Percy Jackson: Sea of Monsters (2013)",
                            "Scorched Earth (????)",
                            "Silent Hill: Revelation 3D (2012)",
                            "Soldiers of Fortune (2012)",
                            "The 4th Reich (2014)",
                            "Wicked Blood (2013)",
                            "Wild in the Streets (2012)"};
        List<String> movies = Main.getMovies( actor );
        for( int i = 0; i < results.length; ++i )
        {
            assertEquals( results[i], movies.get(i) );
        }
    } 
    
    // catch for if they didn't do it alphabetically
    public void test4()
    {
        String actor = "sean bean";
        String[] results = {"Age of Heroes (2011)",
                            "Any Day (2014)",
                            "Cleanskin (2012)",
                            "Devil's Peak (????)",
                            "Enemy of Man (2014)",
                            "Hooligan (2012/II)",
                            "Indian Summer (????/II)",
                            "Jupiter Ascending (2014)",
                            "Lego the Lord of the Rings: The Video Game (2012)",
                            "Mirror Mirror (2012/I)",
                            "Percy Jackson: Sea of Monsters (2013)",
                            "Scorched Earth (????)",
                            "Silent Hill: Revelation 3D (2012)",
                            "Soldiers of Fortune (2012)",
                            "The 4th Reich (2014)",
                            "Wicked Blood (2013)",
                            "Wild in the Streets (2012)"};
        List<String> movies = Main.getMovies( actor );
        for( int i = 0; i < results.length; ++i )
        {
            assertTrue( movies.contains(results[i]) );
        }
    }
    
    public void test5()
    {
        String[] results = {"Tatasciore, Fred",
                            "Lowenthal, Yuri",
                            "Blum, Steve (IX)",
                            "Robinson, Mindy",
                            "Cowlin, Chris",
                            "Olsen, Maria (I)",
                            "O'Brien, Liam (V)",
                            "Yen-Pfister, Pascal",
                            "Matthews, Stuart (II)",
                            "Boaz, Donny"};
        List<String> actors = Main.getTopPerformers( 10 );
        boolean firstOrderCorrect = true;
        for( int i = 0; i < results.length; ++i )
        {
            if( !actors.get(i).startsWith(results[i]) )
            { 
                firstOrderCorrect = false;
                break;
            }
        }
        
        String[] results2 = {"Tatasciore, Fred",
                            "Blum, Steve (IX)",
                            "Lowenthal, Yuri",
                            "Robinson, Mindy",
                            "Cowlin, Chris",
                            "Olsen, Maria (I)",
                            "O'Brien, Liam (V)",
                            "Yen-Pfister, Pascal",
                            "Matthews, Stuart (II)",
                            "Boaz, Donny"};
        boolean secondOrderCorrect = true;
        for( int i = 0; i < results.length; ++i )
        {
            if( !actors.get(i).startsWith(results2[i]) )
            { 
                secondOrderCorrect = false;
                break;
            }
        }
        assertTrue( firstOrderCorrect || secondOrderCorrect );
    }  
    
    public void test6()
    {
        String[] results = {"A Broken Code (2012)",
                            "The Eschatrilogy: Book of the Dead (2012)",
                            "Welcome to Essex (2014)",
                            "Dead in 5 Heartbeats (2013)",
                            "Broken Faith (2012)",
                            "Caravaggio and My Mother the Pope (2013)",
                            "Star Wars: The Old Republic (2011)",
                            "The Dark Knight Rises (2012)",
                            "L.A. Noire (2011)"};
        List<String> movies = Main.getTopMovies( 9 );
        for( int i = 0; i < results.length; ++i )
        {
            assertTrue( movies.get(i).startsWith(results[i]) );
        }
    } 
    
    public void test7()
    {
        String actor = "Emma Tate";
        String[] results = {"Angelina Ballerina: Dance Around the World (2013)",
                            "Angelina Ballerina: Dancing on Ice (2011)",
                            "Angelina Ballerina: Dreams Do Come True (2012)",
                            "Angelina Ballerina: Shining Star Trophy Movie (2011)",
                            "Angelina Ballerina: Sweet Valentine (2012)",
                            "Bob the Builder: Big Dino Dig (2011)",
                            "Broken Sword: The Serpent's Curse (2013)",
                            "Dragon Age II (2011)",
                            "Harry Potter and the Deathly Hallows: Part II (2011)",
                            "Moshi Monsters: The Movie (2013)",
                            "Risen 2: Dark Waters (2012)"};
        List<String> movies = Main.getMovies( actor );
        for( int i = 0; i < results.length; ++i )
        {
            assertEquals( results[i], movies.get(i) );
        }
    }

    
}
