package com.uriegas;

import static org.junit.Assert.assertEquals;
import java.io.*;
import java.util.*;

import com.uriegas.Model.Utilities;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
/**
 * Test all read and writing for all the data models
 */
@RunWith(Parameterized.class)
public class SerializationTest {
    String path;//The path to read
    ArrayList<List<String>> expected;
    /**
     * Before instantiating this class run this setUp
     */
    @Before
    public void setUp(){
        path = System.getProperty("user.dir") + "/src/main/resources/ITI-lista.xlsx";
    }
    /**
     * Constructor with expected value injection
     * @param e expected table
     */
    public SerializationTest(ArrayList<List<String>> e){
        expected = e;
    }
    /**
     * Parameters to add to the constructor {@code ArrayList<List<String>> e}
	 * <pre>
	 * public class JavadocTest {
	 *   // indentation and line breaks are kept 
	 * 
	 *   &#64;SuppressWarnings
	 *   public List&#60;String&#62; generics(){
	 *     // '@', '<' and '>'  have to be escaped with HTML codes
	 *     // when used in annotations or generics
	 *   }
	 * } 
	 * </pre>
     * @return Collection of tables
     */
    @Parameterized.Parameters
    public static Collection<ArrayList<List<String>>> getTestData(){
        ArrayList<List<String>> data = new ArrayList<List<String>>();

        data.add(Arrays.asList("NUM", "MATRÍCULA", "NOMBRE", "PROMEDIO ENERO ABRIL 2021", "MATERIAS REPROBADAS ENERO ABRIL 2021"));//First line
        data.add(Arrays.asList("216.0", "1930315.0", "MORENO MOCTEZUMA JOSUE RAUL", "67.33333", "3.0"));//Last line
        Collection<ArrayList<List<String>>> ret = Arrays.asList(data);
        return ret;
    }

    /**
     * Test the correctness of the first and last expected rows, it would be hard to check all the rows.
     * @throws IOException
     */
    @Test
    public void testExcel() throws IOException{
        System.out.println(path);
        ArrayList<List<String>> actual = Utilities.readExcel(path);//Get list
        //The Read Excel function doesnt work well yet, it is reading empty cells in columns
        assertEquals(expected.get(0), actual.get(0));//Compare first row
        assertEquals(expected.get(expected.size()-1), actual.get(actual.size()-1));//Compare last row
        //Print list
        for(List<String> i : actual){
            for(String j : i)
                System.out.print(j + ' ');
            System.out.println();
        }
    }
}
