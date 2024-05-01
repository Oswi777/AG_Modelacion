package com.ag2;

public class Individuo {
    char[][] secuencias;
    int maxAgregar = 3;

    public Individuo(char[][] entrada) {
        secuencias = new char[entrada.length][];
        int c = 0;
        for (char[] i : entrada) {
            secuencias[c] = new char[i.length];
            for (int j = 0; j < i.length; j++) {
                secuencias[c][j] = i[j];
            }
            c++;
        }
        mutar();
    }

    public int calificar() {
        int cal = 0;
        String letras = "";
        for (int i = 0; i < secuencias[0].length; i++) {
            for (int j = 0; j < secuencias.length; j++) {
                if (secuencias[j][i] == '-') {
                    cal--;
                } else if (letras.contains(secuencias[j][i] + "")) {
                    cal += 5;
                } else {
                    letras += secuencias[j][i];
                    cal++;
                }
            }
            letras = "";
        }
        return cal;
    }

    private void mutar() {
        int[] gaps = null;
        for (int i = 0; i < secuencias.length; i++) {
            String stringGaps = "";
            for (int j = 0; j < secuencias[i].length; j++) {
                if (secuencias[i][j] == '-')
                    stringGaps += j + "%";
            }

            String[] intGaps = stringGaps.split("%");
            gaps = new int[intGaps.length - 1];
            for (int j = 0; j < gaps.length; j++) {
                gaps[j] = Integer.parseInt(intGaps[j]);
            }

            int max = (int) (Math.random() * gaps.length / 2);
            String cacheEliminar = "";
            for (int j = 0; j < max; j++) {
                int x = (int) (Math.random() * gaps.length);
                if (!cacheEliminar.contains("%" + gaps[x] + " ")) {
                    cacheEliminar += "%" + gaps[x] + " ";
                }
            }

            max = maxAgregar;
            String cacheAgregar = "";
            for (int j = 0; j < max; j++) {
                int x = (int) (Math.random() * (secuencias[i].length));
                if (!cacheAgregar.contains("%" + x + " ")) {
                    cacheAgregar += "%" + x + " ";
                }
            }
            modificarGaps(i, cacheEliminar, cacheAgregar);
        }
        alinear();
        eliminar();
    }

    private void modificarGaps(int i, String eliminar, String agregar) {
        char[] elim;
        char[] res;
        if (eliminar.compareTo("") == 0) {
            elim = new char[secuencias[i].length];
        } else {
            elim = new char[secuencias[i].length - eliminar.split("%").length + 1];
        }

        if (agregar.compareTo("") == 0) {
            res = new char[elim.length];
        } else {
            res = new char[elim.length + agregar.split("%").length - 1];
        }

        int cont = 0;
        for (int j = 0; j < secuencias[i].length; j++) {
            if (!eliminar.contains("%" + j + " ")) {
                elim[cont] = secuencias[i][j];
                cont++;
            }
        }

        cont = 0;
        for (int j = 0; j < res.length; j++) {
            if (!agregar.contains("%" + j + " ")) {
                try {
                    res[j] = elim[cont];
                    cont++;
                } catch (Exception ex) {
                    res[j] = '-';
                }
            } else {
                res[j] = '-';
            }
        }
        secuencias[i] = res;
    }

    private void alinear() {
        int max = secuencias[0].length;
        for (int i = 1; i < secuencias.length; i++) {
            if (secuencias[i].length > max) {
                max = secuencias[i].length;
            }
        }
        for (int i = 0; i < secuencias.length; i++) {
            char res[] = new char[max];
            for (int j = 0; j < secuencias[i].length; j++) {
                res[j] = secuencias[i][j];
            }
            for (int j = secuencias[i].length; j < max; j++) {
                res[j] = '-';
            }
            secuencias[i] = res;
        }
    }

    private void eliminar() {
        for (int i = 0; i < secuencias[0].length; i++) {
            boolean x = true;
            for (int j = 0; j < secuencias.length; j++) {
                if (secuencias[j][i] != '-') {
                    x = false;
                }
            }
            if (x) {
                for (int j = 0; j < secuencias.length; j++) {
                    modificarGaps(j, "%" + i + " ", "");
                }
                i--;
            }
        }
    }

    public Individuo cruzar(Individuo padre2) {
        char[][] hijoSecuencias = new char[secuencias.length][];
        for (int i = 0; i < secuencias.length; i++) {
            hijoSecuencias[i] = new char[secuencias[i].length];
            for (int j = 0; j < secuencias[i].length; j++) {
                if (Math.random() < 0.5) {
                    hijoSecuencias[i][j] = secuencias[i][j];
                } else {
                    hijoSecuencias[i][j] = padre2.secuencias[i][j];
                }
            }
        }
        return new Individuo(hijoSecuencias);
    }
}
