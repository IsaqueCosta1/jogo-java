// =============================================
// Classe NavegacaoException
// Exceção personalizada para erros de navegação
// Lançada quando há problemas na navegação entre questões
// Permite tratamento específico de erros de navegação
// =============================================

package Exceptions;

public class NavegacaoException extends Exception {
    public NavegacaoException(String mensagem) {
        super(mensagem);
    }

    public NavegacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

