// =============================================
// Classe QuickSort
// Implementa algoritmo de ordenação para questões
// Ordena questões por nível de dificuldade
// Permite ordenação crescente, decrescente e aleatória
// =============================================

package Core;

import Questoes.Questao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuickSort {
    private static Random random = new Random();

    public static void ordenar(ArrayList<Questao> questoes, String criterio) {
        if (questoes == null || questoes.size() <= 1) return;

        switch (criterio.toLowerCase()) {
            case "crescente":
                ordenarCrescente(questoes, 0, questoes.size() - 1);
                break;
            case "decrescente":
                ordenarDecrescente(questoes, 0, questoes.size() - 1);
                break;
            case "embaralhadas":
            case "aleatorio":
                embaralhar(questoes);
                break;
            default:
                System.out.println("Critério de ordenação não reconhecido. Usando ordem aleatória.");
                embaralhar(questoes);
        }
    }

    public static void ordenarCrescente(ArrayList<Questao> questoes, int inicio, int fim) {
        if (inicio < fim) {
            int pivotIndex = particionar(questoes, inicio, fim, true);
            ordenarCrescente(questoes, inicio, pivotIndex - 1);
            ordenarCrescente(questoes, pivotIndex + 1, fim);
        }
    }

    public static void ordenarDecrescente(ArrayList<Questao> questoes, int inicio, int fim) {
        if (inicio < fim) {
            int pivotIndex = particionar(questoes, inicio, fim, false);
            ordenarDecrescente(questoes, inicio, pivotIndex - 1);
            ordenarDecrescente(questoes, pivotIndex + 1, fim);
        }
    }

    public static void embaralhar(ArrayList<Questao> questoes) {
        Collections.shuffle(questoes, random);
    }

    public static int particionar(ArrayList<Questao> questoes, int inicio, int fim, boolean crescente) {
        int pivotValue = questoes.get(fim).getNivel().getValor();
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            int currentValue = questoes.get(j).getNivel().getValor();

            boolean shouldSwap = crescente ? currentValue <= pivotValue : currentValue >= pivotValue;

            if (shouldSwap) {
                i++;
                trocar(questoes, i, j);
            }
        }

        trocar(questoes, i + 1, fim);
        return i + 1;
    }

    public static void trocar(ArrayList<Questao> questoes, int i, int j) {
        Questao temp = questoes.get(i);
        questoes.set(i, questoes.get(j));
        questoes.set(j, temp);
    }
}
