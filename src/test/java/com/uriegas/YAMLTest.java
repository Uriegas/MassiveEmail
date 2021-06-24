package com.uriegas;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.yaml.snakeyaml.Yaml;
/**
 * Test the YAML configuration file
 */
@RunWith(Parameterized.class)
public class YAMLTest {
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
    public YAMLTest(ArrayList<List<String>> e){
        expected = e;
    }
    /**
     * Parameters to add to the constructor @see{@link com.uriegas.Utilities#readExcel(ArrayList)}
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
		Yaml yaml = new Yaml();
		Configuration config = yaml.load("/src/main/resources/configuration.yaml");
		System.out.println(config.toString());
    }
}
