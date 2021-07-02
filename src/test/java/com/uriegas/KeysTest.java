package com.uriegas;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import com.uriegas.Model.Keys;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
/**
 * Prueba la correcta encriptación y desencriptación de la llave maestra
 */
@RunWith(Parameterized.class)
public class KeysTest{
	String s;//String to encrypt
	Keys keys;
    /**
     * Before instantiating this class run this setUp
     */
    @Before
    public void setUp() throws Exception{
		keys = new Keys("Hola");
    }
    /**
     * Constructor with expected value injection
     * @param e expected table
     */
    public KeysTest(String parameter){
		s = parameter;
    }
    /**
     * Parameters to add to the test: array of strings to test encryption
     * @return Collection of tables
     */
    @Parameterized.Parameters
    public static Collection<String> getTestData(){
        return Arrays.asList("a", "x", "Hola como estan", "xdw1233f32f#@fewc!wc!", "Que pasion", "I love LPY");
    }
	/**
	 * Test de encriptado de contraseña de cuenta
	 * Compara si la llave encriptada fue encriptada correctamente
	 */
    @Test
    public void testEncryption() throws Exception {
		System.out.println(s + "   " + keys.desencriptar(keys.encriptar(s)));
		assertEquals(s, keys.desencriptar(keys.encriptar(s)));
	}

	/**
	 * Test de desencriptado de contraseña de cuenta
	 * Comprueba si la llave desencriptada es igual a la llave guardada
	 */
    // @Test
    // public void testDesencriptadoPasswordCuenta(){
	// 	String cadenaEncriptada = "0ULUEuDYsWjeZkvF/KcdUg==";
	// 	String desencriptada = Keys.desencriptar(cadenaEncriptada);//Comparar passowrd con lo ya guardardo
	// 	System.out.println(desencriptada);
	// 	assertEquals(desencriptada, "Pepe");
	// }



	/**
	 * Test de encriptado de llave maestra
	 * Compara si la llave encriptada fue encriptada correctamente
	 */
	// @Test
	// public void testCorrectMasterKey(){
	// 	String llaveMaestra = "PabloNailedANail";
	// 	Keys.almacenarLlave(llaveMaestra); //Se establece la clave de encriptado
	// 	Boolean EsCorrecta = Keys.comparar(llaveMaestra);
	// 	System.out.println(EsCorrecta);
	// 	assertTrue(EsCorrecta);//Debe regresar verdadero
	// }


	/**
	 * Test: comprueba que el método Keys.comparar devuelve falso si la llave indicada es incorrecta
	 */
    // @Test
    // public void testIncorrectMasterKey(){
	// 	String incorrretPassowrd = "Toñito";
	// 	Boolean EsIncorrecta = Keys.comparar(incorrretPassowrd);
	// 	System.out.println(EsIncorrecta);
	// 	assertFalse(EsIncorrecta);//Debe regresar falso
	// }
}
