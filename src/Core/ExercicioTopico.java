// =============================================
// Classe ExercicioTopico
// Gerencia o fluxo de questões de um tópico de estudo
// Permite navegação, ordenação, resposta e resumo do desempenho
// =============================================
package Core;

import Exceptions.NavegacaoException;
import Exceptions.QuestaoException;
import Questoes.Questao;
import Questoes.QuestaoCompletarCodigo;
import Questoes.QuestaoFactory;

import java.util.ArrayList;

public class ExercicioTopico implements Navegavel {
    private String tituloTopico;
    private ArrayList<Questao> questoes;
    private int indiceAtual;
    private String modoOrdenacao;
    private Estatisticas estatisticasUsuario;
    private Pilha historicoNavegacao;

    public ExercicioTopico(String titulo, Estatisticas estatisticas) {
        this.tituloTopico = titulo;
        this.questoes = new ArrayList<>();
        this.indiceAtual = 0;
        this.modoOrdenacao = "embaralhadas";
        this.estatisticasUsuario = estatisticas;
        this.historicoNavegacao = new Pilha();
    }

    // Carrega as questões do tópico usando a fábrica de questões
    public void carregarQuestoes() throws QuestaoException {
        this.questoes = QuestaoFactory.criarQuestoesPorTopico(tituloTopico);
        aplicarOrdenacao(modoOrdenacao);
    }

    // Aplica o modo de ordenação escolhido às questões
    public void aplicarOrdenacao(String modo) {
        this.modoOrdenacao = modo;

        // Simulação de loading da ordenação
        System.out.println("\n🔄 Ordenando questões (" + modo + ")...");
        try {
            Thread.sleep(1000); // Simula processamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        QuickSort.ordenar(questoes, modo);
        System.out.println("✅ Questões ordenadas com sucesso!\n");
    }

    // Avança para a próxima questão, se houver
    @Override
    public void avancar() throws NavegacaoException {
        if (!temProximaQuestao()) {
            throw new NavegacaoException("Não há próxima questão disponível.");
        }

        historicoNavegacao.empilhar(indiceAtual);
        indiceAtual++;
    }

    // Volta para a questão anterior, se possível
    @Override
    public void voltar() throws NavegacaoException {
        if (!temQuestaoAnterior()) {
            throw new NavegacaoException("Não há questão anterior disponível.");
        }

        if (!historicoNavegacao.estaVazia()) {
            indiceAtual = historicoNavegacao.desempilhar();
        } else {
            indiceAtual--;
        }
    }

    // Retorna ao menu principal, resetando o exercício
    @Override
    public void irParaMenu() throws NavegacaoException {
        // Reset do exercício para voltar ao menu
        indiceAtual = 0;
        historicoNavegacao.limpar();
    }

    // Processa a resposta do usuário para a questão atual
    public boolean responderAtual(String resposta) {
        if (indiceAtual >= 0 && indiceAtual < questoes.size()) {
            Questao questaoAtual = questoes.get(indiceAtual);
            boolean acertou = questaoAtual.verificarResposta(resposta);

            if (acertou) {
                estatisticasUsuario.registrarAcerto();
                System.out.println("\n✅ CORRETO! Parabéns!");
            } else {
                estatisticasUsuario.registrarErro();
                System.out.println("\n❌ INCORRETO!");
                if (questaoAtual instanceof QuestaoCompletarCodigo) {
                    System.out.println(((QuestaoCompletarCodigo) questaoAtual).getDica());
                }
                System.out.println("Resposta correta: " + questaoAtual.getCorreta());
                System.out.println("Explicação: " + questaoAtual.getExplicacao());
            }

            return acertou;
        }
        return false;
    }

    // Permite pular a questão atual
    public void pularQuestao() {
        estatisticasUsuario.registrarPulo();
        System.out.println("\n⏭️ Questão pulada!");
    }

    // Calcula o progresso do usuário no tópico
    public double calcularProgresso() {
        if (questoes.isEmpty()) return 0.0;
        return (double) (indiceAtual + 1) / questoes.size() * 100;
    }

    // Retorna a questão atual
    public Questao obterQuestaoAtual() {
        if (indiceAtual >= 0 && indiceAtual < questoes.size()) {
            return questoes.get(indiceAtual);
        }
        return null;
    }

    // Verifica se há próxima questão
    public boolean temProximaQuestao() {
        return indiceAtual < questoes.size() - 1;
    }

    // Verifica se há questão anterior
    public boolean temQuestaoAnterior() {
        return indiceAtual > 0;
    }

    // Gera um resumo do desempenho do usuário no tópico
    public String gerarResumoDesempenho() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(50)).append("\n");
        sb.append("    RESUMO DO TÓPICO: ").append(tituloTopico.toUpperCase()).append("\n");
        sb.append("=".repeat(50)).append("\n");
        sb.append("Questões no tópico: ").append(questoes.size()).append("\n");
        sb.append("Progresso atual: ").append(String.format("%.1f", calcularProgresso())).append("%\n");
        sb.append("Modo de ordenação: ").append(modoOrdenacao).append("\n");
        sb.append("=".repeat(50));
        return sb.toString();
    }

    // Exibe uma barra visual de progresso do usuário no tópico
    public String exibirBarraProgresso() {
        int progresso = (int) (calcularProgresso() / 10); // escala 0-10
        StringBuilder barra = new StringBuilder();
        barra.append("[");
        for (int i = 0; i < 10; i++) {
            if (i < progresso) {
                barra.append("█");
            } else {
                barra.append("░");
            }
        }
        barra.append("] ");
        barra.append(String.format("%.1f", calcularProgresso())).append("% ");
        barra.append("(").append(indiceAtual + 1).append("/").append(questoes.size()).append(")");
        return barra.toString();
    }

    // Getters
    public String getTituloTopico() { return tituloTopico; }
}
