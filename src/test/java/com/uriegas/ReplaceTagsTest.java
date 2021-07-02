package com.uriegas;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;

import com.uriegas.Model.Utilities;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
/**
 * Test correctness of txt vars instantiation
 */
//TODO: Add more test iterations, and abstract the actual value from the setUp to the parameters
@RunWith(Parameterized.class)
public class ReplaceTagsTest {
    String txt;//Template with vars txt
    List<String> actual; //Instantiated txt's
    List<String> expected; //Expected instantiated txt's
    ArrayList<List<String>> table;
    /**
     * Before instantiating this class run this setUp
     * Creates the template txt in which vars will be replaced
     */
    @Before
    public void setUp(){
        txt = "Hi <NOMBRE>, I'm glad you visited us, your quarter score is: <PROMEDIO ENERO ABRIL 2021>";
    }
    /**
     * Constructor with expected value injection
     * @param table for instantiation
     * @param exp expected instantiated txts
     */
    public ReplaceTagsTest(ArrayList<List<String>> table, List<String> exp){
        this.table = table;
        this.expected = exp;
    }
    /**
     * Parameters to add to the constructor @see{@link com.uriegas.Model.Utilities#readExcel(ArrayList)}
     * @return Collection of tables and expected txts
     */
    @Parameterized.Parameters
    public static Collection<Object[]> getTestData(){
        ArrayList<List<String>> table = new ArrayList<List<String>>();

        table.add(Arrays.asList("NUM", "MATRÍCULA", "NOMBRE", "PROMEDIO ENERO ABRIL 2021", "MATERIAS REPROBADAS ENERO ABRIL 2021"));//First line
        table.add(Arrays.asList("216.0", "1930315.0", "MORENO MOCTEZUMA JOSUE RAUL", "67.33333", "3.0"));//Last line
        List<String> txts = Arrays.asList("Hi MORENO MOCTEZUMA JOSUE RAUL, I'm glad you visited us, your quarter score is: 67.33333");
        return Arrays.asList(new Object[][]{
            {table, txts}
        });
    }

    /**
     * Test if the instantiated txts are correctly instantiated
     * @throws IOException
     */
    @Test
    public void testReplacement() throws IOException{
        actual = Utilities.instantiateTable(txt, table);
        for(int i = 0; i < actual.size(); i++)
            assertEquals(actual.get(i), expected.get(i));
    }
}