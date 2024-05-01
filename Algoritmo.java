package com.ag2;

public class Algoritmo {
    public static void main(String[] args) {
        String s1 = "C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\monkeyPoxVirus.fasta";
        String s2 = "C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\Rhabdoviridae.fasta";
        String s3 = "C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\coronaVirus.fasta";

        String[] secuencias = {s1, s2, s3};
        Generacion obj = new Generacion(secuencias);

        for (Individuo x : obj.poblacion) {
            for (char[] y : x.secuencias) {
                for (int i = 0; i < y.length; i++) {
                    System.out.print(y[i] + " ");
                }
                System.out.println("");
            }
            System.out.println(x.calificar() + "\n");
        }

        for (int z = 0; z < 10; z++) {
            System.out.println("╔══════════════════╗");
            System.out.println("   REPRODUCCION"     );
            System.out.println("╚══════════════════╝");

            obj.reproducir();
            obj.ordenar();

            for (Individuo x : obj.poblacion) {
                for (char[] y : x.secuencias) {
                    for (int i = 0; i < y.length; i++) {
                        System.out.print(y[i] + " ");
                    }
                    System.out.println("");
                }
                System.out.println(x.calificar() + "\n");
            }
        }
    }
}
