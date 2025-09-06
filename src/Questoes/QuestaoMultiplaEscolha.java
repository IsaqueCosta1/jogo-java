// =============================================
// Classe QuestaoMultiplaEscolha
// Implementa questões de múltipla escolha
// Gerencia alternativas e verifica resposta correta
// Formato tradicional com opções A, B, C, D
// =============================================

package Questoes;

import enums.NivelDificuldade;
import enums.TipoQuestao;

public class QuestaoMultiplaEscolha extends Questao {
    private String[] alternativas;
    private String correta;

    public QuestaoMultiplaEscolha(String enunciado, NivelDificuldade nivel,
                                  String[] alternativas, String correta, String explicacao) {
        super(enunciado, nivel, TipoQuestao.MULTIPLA, explicacao);
        this.alternativas = alternativas.clone();
        this.correta = correta;
    }

    @Override
    public boolean verificarResposta(String resposta) {
        return resposta != null && resposta.trim().equalsIgnoreCase(correta.trim());
    }

    @Override
    public String exibirQuestao() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(60)).append("\n");
        sb.append("QUESTÃO [").append(tipo.getDescricao()).append(" - ").append(nivel.getDescricao()).append("]\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append(enunciado).append("\n\n");

        char opcao = 'A';
        for (String alternativa : alternativas) {
            sb.append(opcao).append(") ").append(alternativa).append("\n");
            opcao++;
        }
        sb.append("\n").append("-".repeat(60));
        return sb.toString();
    }

    @Override
    public String getCorreta() {
        return correta;
    }
}
