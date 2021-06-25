package com.uriegas;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Prueba la correcta encriptación y desencriptación de la llave maestra
 */
public class KeysTest{

	/**
	 * Test de encriptado de contraseña de cuenta
	 * Compara si la llave encriptada fue encriptada correctamente
	 */
    @Test
    public void testEncriptadoPasswordCuenta(){
		Keys.definirLlave("PabloNailedANail"); //Se establece la clave de encriptado
		String cadenaEncriptar = "Pepe";
		String encriptado = Keys.encriptar(cadenaEncriptar);//Pepe
		System.out.println(encriptado);
		assertNotEquals(cadenaEncriptar, encriptado);
	}

	/**
	 * Test de desencriptado de contraseña de cuenta
	 * Comprueba si la llave desencriptada es igual a la llave guardada
	 */
    @Test
    public void testDesencriptadoPasswordCuenta(){
		Keys.definirLlave("PabloNailedANail"); //Se establece la clave de desencriptado
		String cadenaEncriptada = "0ULUEuDYsWjeZkvF/KcdUg==";
		String desencriptada = Keys.desencriptar(cadenaEncriptada);//Comparar passowrd con lo ya guardardo
		System.out.println(desencriptada);
		assertEquals(desencriptada, "Pepe");
	}



	/**
	 * Test de encriptado de llave maestra
	 * Compara si la llave encriptada fue encriptada correctamente
	 */
	@Test
	public void testCorrectMasterKey(){
		String llaveMaestra = "PabloNailedANail";
		Keys.almacenarLlave(llaveMaestra); //Se establece la clave de encriptado
		Boolean EsCorrecta = Keys.comparar(llaveMaestra);
		System.out.println(EsCorrecta);
		assertTrue(EsCorrecta);//Debe regresar verdadero
	}


	/**
	 * Test: comprueba que el método Keys.comparar devuelve falso si la llave indicada es incorrecta
	 */
    @Test
    public void testIncorrectMasterKey(){
		String incorrretPassowrd = "Toñito";
		Boolean EsIncorrecta = Keys.comparar(incorrretPassowrd);
		System.out.println(EsIncorrecta);
		assertFalse(EsIncorrecta);//Debe regresar falso
	}
}
