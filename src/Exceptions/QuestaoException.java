// =============================================
// Classe QuestaoException
// Exceção personalizada para erros relacionados a questões
// Lançada quando há problemas na criação ou manipulação de questões
// Permite tratamento específico de erros em questões
// =============================================

package Exceptions;

public class QuestaoException extends Exception {
    public QuestaoException(String mensagem) {
        super(mensagem);
    }

    public QuestaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

