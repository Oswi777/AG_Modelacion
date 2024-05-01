package com.ag2;

import java.util.Arrays;

public class Generacion {
    int num = 4;
    Individuo[] poblacion = new Individuo[num];

    public Generacion(String[] entrada) {
        char[][] x = new char[entrada.length][];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < entrada.length; j++) {
                x[j] = entrada[j].toCharArray();
            }
            poblacion[i] = new Individuo(x);
        }
    }

    public void ordenar() {
        Arrays.sort(poblacion, (a, b) -> Integer.compare(b.calificar(), a.calificar()));
    }

    private void desordenarParejas() {
        for (int i = 0; i < num / 2; i++) {
            int x = i;
            for (int j = i - 1; j >= 0; j--) {
                if (Math.random() < 0.5) {
                    Individuo temp = poblacion[x];
                    poblacion[x] = poblacion[j];
                    poblacion[j] = temp;
                    x = j;
                }
            }
        }
    }

    public int letras(int i) {
        int cont = 0;
        for (char j : poblacion[0].secuencias[i]) {
            if (Character.isLetter(j))
                cont++;
        }
        return cont;
    }

    public Individuo hijo(int izq, int medio, int der) {
        int letras = 0, n = 0;
        String[] secHijo  = new String[poblacion[0].secuencias.length];
        boolean x;

        int[][] particiones = new int[2][poblacion[0].secuencias.length];
        for (int i = 0; i < poblacion[0].secuencias.length; i++) {
            particiones[0][i] = (int) (letras(i) / 3);
            particiones[1][i] = (int) (2 * letras(i) / 3);
        }

        for (int i = 0; i < 3; i++) {
            letras = 0;
            x = true;
            String secuencia = "";
            for (int j = 0; j < poblacion[izq].secuencias[i].length; j++) {
                if (x) {
                    char letra = poblacion[izq].secuencias[i][j];
                    secuencia += letra;
                    if (Character.isLetter(letra)) {
                        letras++;
                    }
                    if (letras == particiones[0][i]) {
                        x = false;
                    }
                }
            }

            boolean y = false;
            x = true;
            letras = 0;
            for (int j = 0; j < poblacion[medio].secuencias[i].length; j++) {
                char letra = poblacion[medio].secuencias[i][j];
                if (Character.isLetter(letra)) {
                    letras++;
                }
                if (x && y) {
                    secuencia += letra;
                    if (letras == particiones[1][i]) {
                        x = false;
                    }
                }
                if (letras == particiones[0][i]) {
                    y = true;
                }
            }

            y = false;
            letras = 0;
            for (int j = 0; j < poblacion[der].secuencias[i].length; j++) {
                char letra = poblacion[der].secuencias[i][j];
                if (Character.isLetter(letra)) {
                    letras++;
                }
                if (y) {
                    secuencia += letra;
                }
                if (letras == particiones[1][i]) {
                    y = true;
                }
            }
            secHijo[n] = secuencia;
            n++;
        }

        char[][] y = new char[secHijo.length][];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < secHijo.length; j++) {
                y[j] = secHijo[j].toCharArray();
            }
        }
        return new Individuo(y);
    }

    public void reproducir() {
        ordenar();
        desordenarParejas();
        Individuo[] hijos = new Individuo[num];
        int n = 0;
        for (int i = 0; i < num / 2; i += 2) {
            hijos[n] = hijo(i, i, i + 1);
            n++;
            hijos[n] = hijo(i, i + 1, i);
            n++;
            hijos[n] = hijo(i, i + 1, i + 1);
            n++;
            hijos[n] = hijo(i + 1, i, i);
            n++;
        }
        poblacion = hijos;
    }
}
