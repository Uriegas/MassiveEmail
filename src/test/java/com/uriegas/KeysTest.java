package com.uriegas;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Prueba la correcta encriptación y desencriptación de la llave maestra
 */
public class KeysTest{
	/**
	 * Test de encriptado de llave maestra
	 * Compara si la llave encriptada fue encriptada correctamente
	 */
    @Test
    public void testEncriptado(){
		String password = "Pepe";//Pepe
		Keys.definircontra(password);
		String descriptado = Keys.desencriptar();//Pepe
		assertEquals(password, descriptado);
	}
	/**
	 * Test de desencriptado de llave maestra
	 * Comprueba si la llave desencriptada es igual a la llave guardada
	 */
    @Test
    public void testDesencriptado(){
		String password = "Pepe";//Pepe
		Boolean funciona = Keys.comparar(password);//Comparar passowrd con lo ya guardardo
		assertTrue(funciona);
	}
	/**
	 * Test: comprueba que el método Keys.comparar devuelve falso si la llave indicada es incorrecta
	 */
    @Test
    public void testIncorrectPassword(){
		//Aqui puedes añadir un test para comprobar que el método Keys.comparar te devuelve falso si la llave indicada es incorrecta
		String incorrretPassowrd = "Toñito";
		Boolean nodebefuncionar = Keys.comparar(incorrretPassowrd)
		assertFalse(nodebefuncionar);//Debe regresar falso
	}
}
