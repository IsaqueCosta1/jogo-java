// =============================================
// Classe Estatisticas
// Gerencia os dados de desempenho do usuário durante a sessão
// Armazena acertos, erros, pulos, tópicos estudados e tempo de sessão
// =============================================
package Core;

import java.util.ArrayList;

public class Estatisticas {
    private long inicioSessao;
    private long fimSessao;
    private ArrayList<String> topicosEstudados;
    private int acertos;
    private int erros;
    private int pulos;
    private int questoesRespondidas;
    private String topicoAtual;

    public Estatisticas() {
        this.topicosEstudados = new ArrayList<>();
        this.acertos = 0;
        this.erros = 0;
        this.pulos = 0;
        this.questoesRespondidas = 0;
    }

    // Inicia a contagem do tempo de sessão
    public void iniciar() {
        this.inicioSessao = System.currentTimeMillis();
        this.fimSessao = 0; // Reset caso já tenha sido finalizada
    }

    // Finaliza a contagem do tempo de sessão
    public void finalizar() {
        this.fimSessao = System.currentTimeMillis();
    }

    // Registra um acerto e incrementa o total de questões respondidas
    public void registrarAcerto() {
        this.acertos++;
        this.questoesRespondidas++;
    }

    // Registra um erro e incrementa o total de questões respondidas
    public void registrarErro() {
        this.erros++;
        this.questoesRespondidas++;
    }

    // Registra um pulo de questão
    public void registrarPulo() {
        this.pulos++;
        // Note: pulos não contam como questões respondidas
    }

    // Adiciona um tópico estudado à lista, evitando duplicidade
    public void adicionarTopicoEstudado(String topico) {
        if (topico != null && !topico.trim().isEmpty()) {
            String topicoFormatado = topico.trim().toLowerCase();
            if (!topicosEstudados.contains(topicoFormatado)) {
                topicosEstudados.add(topicoFormatado);
            }
            this.topicoAtual = topicoFormatado;
        }
    }

    public double calcularPercentualAcertos() {
        if (questoesRespondidas == 0) {
            return 0.0;
        }
        return (double) acertos / questoesRespondidas * 100.0;
    }

    // Calcula o tempo total da sessão em segundos
    public long calcularTempoSessao() {
        if (inicioSessao == 0) {
            return 0; // Sessão não foi iniciada
        }

        long tempoFinal;
        if (fimSessao > 0) {
            // Sessão foi finalizada
            tempoFinal = fimSessao;
        } else {
            // Sessão ainda está ativa
            tempoFinal = System.currentTimeMillis();
        }

        return (tempoFinal - inicioSessao) / 1000; // Converte para segundos
    }

    // Gera um resumo textual das estatísticas da sessão
    public String getResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(50)).append("\n");
        sb.append("         ESTATÍSTICAS DA SESSÃO\n");
        sb.append("=".repeat(50)).append("\n");
        sb.append("Questões respondidas: ").append(questoesRespondidas).append("\n");
        sb.append("Acertos: ").append(acertos).append("\n");
        sb.append("Erros: ").append(erros).append("\n");
        sb.append("Pulos: ").append(pulos).append("\n");
        sb.append("Percentual de acertos: ").append(String.format("%.1f", calcularPercentualAcertos())).append("%\n");
        sb.append("Tempo de sessão: ").append(calcularTempoSessao()).append(" segundos\n");
        sb.append("Tópicos estudados: ").append(topicosEstudados.size()).append("/5\n");
        if (!topicosEstudados.isEmpty()) {
            sb.append("Tópicos: ").append(String.join(", ", topicosEstudados)).append("\n");
        }
        sb.append("=".repeat(50));
        return sb.toString();
    }

    // Reseta todos os contadores e limpa os tópicos estudados
    public void resetarEstatisticas() {
        this.acertos = 0;
        this.erros = 0;
        this.pulos = 0;
        this.questoesRespondidas = 0;
        this.topicosEstudados.clear();
        this.topicoAtual = null;
        // Não resetamos os tempos de sessão para preservar a sessão atual
    }

    // Calcula o progresso geral do usuário em porcentagem
    public double getProgressoGeral() {
        // Progresso baseado nos tópicos estudados (5 tópicos no total)
        double progressoTopicos = (double) topicosEstudados.size() / 5.0 * 100.0;

        // Se houver questões respondidas, considera também o percentual de acertos
        if (questoesRespondidas > 0) {
            double bonusDesempenho = calcularPercentualAcertos() * 0.1; // 10% do percentual de acertos como bônus
            progressoTopicos = Math.min(100.0, progressoTopicos + bonusDesempenho);
        }

        return progressoTopicos;
    }

    // Getters para acesso aos dados
    public int getAcertos() { return acertos; }
    public int getErros() { return erros; }
    public int getPulos() { return pulos; }
    public int getQuestoesRespondidas() { return questoesRespondidas; }
    public ArrayList<String> getTopicosEstudados() { return new ArrayList<>(topicosEstudados); }
    public String getTopicoAtual() { return topicoAtual; }

    // Getters adicionais úteis
    public long getInicioSessao() { return inicioSessao; }
    public long getFimSessao() { return fimSessao; }

    // Método para formatar tempo de sessão de forma mais legível
    public String getTempoSessaoFormatado() {
        long segundos = calcularTempoSessao();
        long minutos = segundos / 60;
        long segundosRestantes = segundos % 60;

        if (minutos > 0) {
            return String.format("%dm %ds", minutos, segundosRestantes);
        } else {
            return String.format("%ds", segundos);
        }
    }

    // Método para verificar se a sessão está ativa
    public boolean isSessaoAtiva() {
        return inicioSessao > 0 && fimSessao == 0;
    }

    // Método para obter estatísticas resumidas em uma linha
    public String getEstatisticasResumo() {
        return String.format("Questões: %d | Acertos: %d (%.1f%%) | Erros: %d | Pulos: %d | Tempo: %s | Tópicos: %d/5",
                questoesRespondidas, acertos, calcularPercentualAcertos(), erros, pulos,
                getTempoSessaoFormatado(), topicosEstudados.size());
    }
}