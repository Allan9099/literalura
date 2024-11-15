package com.alura.literalura.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {

    public static int leerEnteroValidado(Scanner lectura, int min, int max) {
        int numero = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                numero = lectura.nextInt();
                if (numero >= min && numero <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("----------------------------------------------" +
                            "\nError, ingrese un número válido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("----------------------------------------------" +
                        "\nError, ingrese un número válido.");
                lectura.next();
            }
        }
        return numero;
    }

    public static String leerCodigoIdioma(Scanner lectura) {
        String idioma;
        boolean esValido = false;

        do {
            idioma = lectura.nextLine().toLowerCase();

            if (idioma.matches("[A-Za-z]+")) {
                esValido = true;
            } else {
                System.out.println("Código de idioma inválido (solo se aceptan letras sin símbolos)");
            }
        } while (!esValido);
        return idioma;
    }
}