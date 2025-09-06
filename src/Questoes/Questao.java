// =============================================
// Classe Questao (Abstrata)
// Base para todos os tipos de questões do sistema
// Define interface comum e comportamentos básicos
// Serve como template para questões específicas
// =============================================

package Questoes;

import enums.NivelDificuldade;
import enums.TipoQuestao;

public abstract class Questao {
    protected String enunciado;
    protected NivelDificuldade nivel;
    protected TipoQuestao tipo;
    protected String explicacao;

    public Questao(String enunciado, NivelDificuldade nivel, TipoQuestao tipo, String explicacao) {
        this.enunciado = enunciado;
        this.nivel = nivel;
        this.tipo = tipo;
        this.explicacao = explicacao;
    }

    // Getters
    public NivelDificuldade getNivel() { return nivel; }
    public TipoQuestao getTipo() { return tipo; }
    public String getExplicacao() { return explicacao; }

    // Métodos abstratos que devem ser implementados pelas subclasses
    public abstract boolean verificarResposta(String resposta);
    public abstract String exibirQuestao();
    public abstract String getCorreta();
}
