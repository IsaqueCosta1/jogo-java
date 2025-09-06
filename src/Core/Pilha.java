// =============================================
// Classe Pilha
// Implementação de pilha estática (tamanho fixo)
// Gerencia um histórico LIFO (Last In First Out)
// Usada para controle de navegação entre questões
// =============================================

package Core;

public class Pilha {
    // Constantes
    private static final int MAXTAM = 100;  // Tamanho máximo da pilha

    // Atributos
    private int[] elementos;    // Array para armazenar os elementos
    private int topo;          // Índice do topo da pilha

    /**
     * Construtor: inicializa uma pilha vazia
     */
    public Pilha() {
        this.elementos = new int[MAXTAM];
        this.topo = -1;        // -1 indica pilha vazia
    }

    /**
     * Verifica se a pilha está cheia
     * @return true se a pilha estiver cheia, false caso contrário
     */
    public boolean estaCheia() {
        return topo == MAXTAM - 1;
    }

    /**
     * Verifica se a pilha está vazia
     * @return true se a pilha estiver vazia, false caso contrário
     */
    public boolean estaVazia() {
        return topo == -1;
    }

    /**
     * Adiciona um elemento no topo da pilha
     * @param elemento elemento a ser empilhado
     */
    public void empilhar(int elemento) {
        if (estaCheia()) {
            throw new IllegalStateException("Erro: Pilha está cheia!");
        }
        elementos[++topo] = elemento;
    }

    /**
     * Remove e retorna o elemento do topo da pilha
     * @return elemento removido do topo
     * @throws IllegalStateException se a pilha estiver vazia
     */
    public int desempilhar() {
        if (estaVazia()) {
            throw new IllegalStateException("Erro: Pilha está vazia!");
        }
        return elementos[topo--];
    }

    /**
     * Retorna o elemento do topo sem removê-lo
     * @return elemento do topo
     * @throws IllegalStateException se a pilha estiver vazia
     */
    public int verTopo() {
        if (estaVazia()) {
            throw new IllegalStateException("Erro: Pilha está vazia!");
        }
        return elementos[topo];
    }

    /**
     * Limpa todos os elementos da pilha
     */
    public void limpar() {
        topo = -1;
    }

    /**
     * Retorna o número de elementos na pilha
     * @return quantidade de elementos ou -1 se vazia
     */
    public int tamanho() {
        if (estaVazia()) {
            return -1;
        }
        return topo + 1;
    }

    /**
     * Retorna uma representação string da pilha
     * @return String com os elementos da pilha (do topo para a base)
     */
    @Override
    public String toString() {
        if (estaVazia()) {
            return "Pilha vazia!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Elementos da pilha (do topo para a base): ");
        for (int i = topo; i >= 0; i--) {
            sb.append(elementos[i]).append(" ");
        }
        return sb.toString();
    }
}
