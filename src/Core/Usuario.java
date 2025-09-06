// =============================================
// Classe Usuario
// Representa o usuário do sistema de aprendizado
// Armazena nome e gerencia estatísticas da sessão
// Mantém o estado do usuário durante a execução
// =============================================

package Core;

public class Usuario {
    // Nome do usuário
    private String nome;
    // Estatísticas de desempenho do usuário
    private Estatisticas estatisticas;

    // Construtor: inicializa o nome e as estatísticas
    public Usuario(String nome) {
        this.nome = nome;
        this.estatisticas = new Estatisticas();
    }

    // Retorna o nome do usuário
    public String getNome() {
        return nome;
    }

    // Permite alterar o nome do usuário
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o objeto de estatísticas do usuário
    public Estatisticas getEstatisticas() {
        return estatisticas;
    }
}
