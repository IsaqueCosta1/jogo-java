// =============================================
// Interface Navegavel
// Define contrato para navegação entre questões
// Permite avançar, voltar e retornar ao menu
// Padroniza comportamento de navegação no sistema
// =============================================

package Core;

import Exceptions.NavegacaoException;

public interface Navegavel {
    void avancar() throws NavegacaoException;
    void voltar() throws NavegacaoException;
    void irParaMenu() throws NavegacaoException;
}

