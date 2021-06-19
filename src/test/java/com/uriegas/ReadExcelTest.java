package com.uriegas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ReadExcelTest {
    String path;//The path to read
    ReadExcel read;
    ArrayList<List<String>> expected;

    @Before
    public void setUp(){
        path = System.getProperty("user.dir") + "/src/main/resources/ITI-lista.xlsx";
        read = new ReadExcel();
        expected = new ArrayList<List<String>>();

//        List<List<Exp>> values = new ArrayList<List<Exp>>();
//        values.add(new ArrayList<Exp>());
//        values.add(new ArrayList<Exp>());
//        values.get(0).add(new Exp.NumberNode("0.0"));
//        values.get(0).add(new Exp.NumberNode("1.0"));
//        values.get(0).add(new Exp.NumberNode("2.0"));
//        values.get(1).add(new Exp.NumberNode("10.0"));
//        values.get(1).add(new Exp.NumberNode("9.0"));
//        values.get(1).add(new Exp.NumberNode("8.0"));
//
//        expected.define("x_1", values.get(0));
//        expected.define("x_2", values.get(1));
    }
    public ReadExcelTest(ArrayList<List<String>> e){
        expected = e;
    }
    @Parameterized.Parameters
    public static Collection<ArrayList<List<String>>> getTestData(){
        ArrayList<List<String>> data = new ArrayList<List<String>>();

        data.add(Arrays.asList("NUM", "MATRÍCULA", "NOMBRE", "PROMEDIO ENERO ABRIL 2021", "MATERIAS REPROBADAS ENERO ABRIL 2021"));//First line
        data.add(Arrays.asList("216", "1930315", "MORENO MOCTEZUMA JOSUE RAUL", "67,33333", "3"));//Last line
        Collection<ArrayList<List<String>>> ret = Arrays.asList(data);
        return ret;
    }

    /**
     * Test load of variables into an environment
     * @throws Exception
     */
    @Test
    public void testExcel() throws IOException{
        System.out.println(path);
        ArrayList<List<String>> actual = read.readFile(path);//Get list

        assertEquals(expected.get(0), actual.get(0));//Compare first row
        assertEquals(expected.get(expected.size()-1), actual.get(actual.size()-1));//Compare last row
        //Print list
        for(List<String> i : actual){
            for(String j : i)
                System.out.print(j + ' ');
            System.out.println();
        }
        //environment = read.readFile("archivo1.xlsx", interpreter);
        //assertTrue("Error", environment.equals(expected));
    }
}