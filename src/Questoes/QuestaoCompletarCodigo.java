// =============================================
// Classe QuestaoCompletarCodigo
// Implementa questões do tipo completar código
// Gerencia templates de código, respostas esperadas
// e variações aceitas de respostas
// =============================================

package Questoes;

import enums.NivelDificuldade;
import enums.TipoQuestao;

public class QuestaoCompletarCodigo extends Questao {
    private String templateCodigo;
    private String respostaEsperada;
    private String[] palavrasChave;
    private String[] alternativasAceitas; // Para múltiplas respostas válidas

    public QuestaoCompletarCodigo(String enunciado, NivelDificuldade nivel,
                                  String template, String resposta, String explicacao) {
        super(enunciado, nivel, TipoQuestao.COMPLETAR, explicacao);
        this.templateCodigo = template;
        this.respostaEsperada = resposta;
        this.palavrasChave = resposta.split("\\s+");

        // Gera alternativas aceitas (variações da resposta esperada)
        gerarAlternativasAceitas();
    }

    // Construtor alternativo para múltiplas respostas aceitas
    public QuestaoCompletarCodigo(String enunciado, NivelDificuldade nivel,
                                  String template, String resposta, String[] alternativas, String explicacao) {
        super(enunciado, nivel, TipoQuestao.COMPLETAR, explicacao);
        this.templateCodigo = template;
        this.respostaEsperada = resposta;
        this.palavrasChave = resposta.split("\\s+");
        this.alternativasAceitas = alternativas.clone();
    }

    // Gera variações aceitas da resposta esperada
    private void gerarAlternativasAceitas() {
        // Cria variações comuns da resposta (minúscula, maiúscula, com/sem espaços)
        String resposta = respostaEsperada.trim();
        alternativasAceitas = new String[]{
                resposta.toLowerCase(),
                resposta.toUpperCase(),
                resposta, // original
                resposta.replace(" ", ""), // sem espaços
                resposta.replace("_", " "), // underscores para espaços
                resposta.replace(" ", "_")  // espaços para underscores
        };
    }

    @Override
    public boolean verificarResposta(String resposta) {
        if (resposta == null || resposta.trim().isEmpty()) {
            return false;
        }

        String respostaLimpa = resposta.trim();

        // Verifica resposta exata
        if (respostaLimpa.equalsIgnoreCase(respostaEsperada.trim())) {
            return true;
        }

        // Verifica contra todas as alternativas aceitas
        for (String alternativa : alternativasAceitas) {
            if (alternativa != null && respostaLimpa.equalsIgnoreCase(alternativa.trim())) {
                return true;
            }
        }

        // Verifica se contém todas as palavras-chave essenciais
        return verificarPalavrasChave(respostaLimpa);
    }

    // Verifica se a resposta contém as palavras-chave essenciais
    private boolean verificarPalavrasChave(String resposta) {
        if (palavrasChave == null || palavrasChave.length == 0) {
            return false;
        }

        String respostaMinus = resposta.toLowerCase();
        int palavrasEncontradas = 0;

        for (String palavra : palavrasChave) {
            if (palavra != null && !palavra.trim().isEmpty()) {
                String palavraMinus = palavra.trim().toLowerCase();
                if (respostaMinus.contains(palavraMinus)) {
                    palavrasEncontradas++;
                }
            }
        }

        // Considera correto se encontrou pelo menos 60% das palavras-chave
        double percentualAcerto = (double) palavrasEncontradas / palavrasChave.length;
        return percentualAcerto >= 0.6;
    }

    @Override
    public String exibirQuestao() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(60)).append("\n");
        sb.append("QUESTÃO [").append(tipo.getDescricao()).append(" - ").append(nivel.getDescricao()).append("]\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append(enunciado).append("\n\n");
        sb.append("CÓDIGO PARA COMPLETAR:\n");
        sb.append("-".repeat(40)).append("\n");
        sb.append(templateCodigo).append("\n");
        sb.append("-".repeat(40)).append("\n");
        sb.append("💡 Dica: Complete o código com a palavra ou expressão correta\n");
        sb.append("📝 Exemplo: se vê ______, digite apenas a palavra que vai no lugar");
        return sb.toString();
    }

    public String getDica() {
        switch (nivel) {
            case FACIL:
                return "💡 Dica: Pense na palavra-chave básica relacionada ao conceito";
            case MEDIO:
                return "💡 Dica: Considere a sintaxe correta em Java";
            case DIFICIL:
                return "💡 Dica: Analise o contexto completo do código";
            default:
                return "💡 Pense com calma e revise os conceitos";
        }
    }

    @Override
    public String getCorreta() {
        return respostaEsperada;
    }
}

