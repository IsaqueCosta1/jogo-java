// =============================================
// Classe SistemaAprendizado
// Gerencia o fluxo principal do sistema
// Controla menus, navegação e interação com usuário
// Coordena exercícios e exibe estatísticas
// =============================================

import Core.ExercicioTopico;
import Core.Usuario;
import Exceptions.NavegacaoException;
import Exceptions.QuestaoException;
import Questoes.Questao;
import enums.TipoQuestao;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SistemaAprendizado {
    public static final int QUESTOES_POR_TOPICO = 15;
    public static final String VERSAO_SISTEMA = "1.0.0";

    private Usuario usuario;
    private boolean executando;
    private Scanner scanner;

    public SistemaAprendizado() {
        this.executando = true;
        this.scanner = new Scanner(System.in);
    }


    public void iniciarSessao() {
        exibirCabecalho();

        // Solicite o nome do usuário e valida a entrada
        System.out.print("Digite seu nome para iniciar: ");
        String nome = scanner.nextLine().trim();
        while (nome.isEmpty()) {
            System.out.print("Nome não pode estar vazio. Digite seu nome: ");
            nome = scanner.nextLine().trim();
        }

        // Crie o usuário e inicializa estatísticas
        usuario = new Usuario(nome);
        usuario.getEstatisticas().iniciar();
        System.out.println("\n🎉 Bem-vindo(a), " + nome + "! Vamos aprender Programação Orientada a Objetos juntos!");

        // Loop principal: mantém o sistema rodando até o usuário sair
        while (executando) {
            try {
                mostrarMenuPrincipal();
            } catch (Exception e) {
                gerenciarExcecoes(e);
            }
        }

        // Finaliza a sessão e exibe mensagem
        usuario.getEstatisticas().finalizar();
        System.out.println("\n👋 Obrigado por usar o Sistema de Aprendizado, " + usuario.getNome() + "!");
        System.out.println("Até a próxima sessão de estudos!");
        scanner.close();
    }

    // Menu principal
    public void mostrarMenuPrincipal() throws NavegacaoException {
        if (!usuario.getEstatisticas().isSessaoAtiva()) {
            usuario.getEstatisticas().iniciar();
        }
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  SISTEMA DE APRENDIZADO DE PROGRAMAÇÃO v" + VERSAO_SISTEMA);
        System.out.println("  Usuário: " + usuario.getNome() + " | Tempo: " + usuario.getEstatisticas().getTempoSessaoFormatado());
        System.out.println(usuario.getEstatisticas().getEstatisticasResumo());
        System.out.println("=".repeat(60));
        System.out.println("Olá, " + usuario.getNome() + "! O que deseja fazer hoje?");
        System.out.println();
        System.out.println("1. 📚 Aprender Java (POO)");
        System.out.println("2. 📊 Consultar Estatísticas");
        System.out.println("3. 🔄 Reiniciar Progresso");
        System.out.println("4. 🚪 Sair do Sistema");
        System.out.println();
        System.out.print("Escolha uma opção (1-4): ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    mostrarMenuJava();
                    break;
                case 2:
                    mostrarEstatisticas();
                    break;
                case 3:
                    resetarProgresso();
                    break;
                case 4:
                    executando = false;
                    break;
                default:
                    System.out.println("❌ Opção inválida! Escolha entre 1-4.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Digite apenas números!");
            scanner.nextLine();
        }
    }

    // Menu de tópicos
    public void mostrarMenuJava() throws NavegacaoException {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        MÓDULO: APRENDER JAVA (POO)");
        System.out.println("=".repeat(50));
        System.out.println("Escolha o tópico que deseja estudar:");
        System.out.println();
        System.out.println("1. 📚 Encapsulamento");
        System.out.println("2. 📚 Herança");
        System.out.println("3. 📚 Interface");
        System.out.println("4. 📚 Polimorfismo");
        System.out.println("5. 📚 Abstração");
        System.out.println("6. ↩️  Voltar ao Menu Principal");
        System.out.println();
        System.out.print("Escolha uma opção (1-6): ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            String[] topicos = {"", "encapsulamento", "herança", "interface", "polimorfismo", "abstração"};
            if (opcao >= 1 && opcao <= 5) {
                iniciarExercicioTopico(topicos[opcao]);
            } else if (opcao == 6) {
                return;
            } else {
                System.out.println("❌ Opção inválida! Escolha entre 1-6.");
                mostrarMenuJava();
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Digite apenas números!");
            scanner.nextLine();
            mostrarMenuJava();
        }
    }

    // Inicia exercícios do tópico escolhido
    private void iniciarExercicioTopico(String topico) {
        if (!usuario.getEstatisticas().isSessaoAtiva()) {
            usuario.getEstatisticas().iniciar();
        }
        try {
            usuario.getEstatisticas().adicionarTopicoEstudado(topico); // Marca o tópico
            ExercicioTopico exercicio = new ExercicioTopico(topico, usuario.getEstatisticas());
            String modoOrdenacao = escolherModoOrdenacao(); // Escolha de ordenação
            exercicio.aplicarOrdenacao(modoOrdenacao);
            exercicio.carregarQuestoes(); // Carrega questões
            executarExercicios(exercicio); // Executa perguntas
        } catch (QuestaoException e) {
            System.out.println("❌ Erro ao carregar questões: " + e.getMessage());
        } catch (NavegacaoException e) {
            System.out.println("❌ Erro de navegação: " + e.getMessage());
        }
    }

    // Permite escolher como as questões serão organizadas
    private String escolherModoOrdenacao() {
        System.out.println("\n📋 Como deseja organizar as questões?");
        System.out.println("1. 🔀 Embaralhadas (ordem aleatória)");
        System.out.println("2. ⬆️  Crescente (fácil → difícil)");
        System.out.println("3. ⬇️  Decrescente (difícil → fácil)");
        System.out.print("Escolha (1-3): ");
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            switch (opcao) {
                case 1: return "embaralhadas";
                case 2: return "crescente";
                case 3: return "decrescente";
                default:
                    System.out.println("⚠️  Opção inválida. Usando ordem aleatória.");
                    return "embaralhadas";
            }
        } catch (InputMismatchException e) {
            System.out.println("⚠️  Entrada inválida. Usando ordem aleatória.");
            scanner.nextLine();
            return "embaralhadas";
        }
    }

    // Executa o fluxo de perguntas e respostas do exercício
    private void executarExercicios(ExercicioTopico exercicio) throws NavegacaoException {
        System.out.println("\n🎯 Iniciando exercícios de " + exercicio.getTituloTopico().toUpperCase() + "!");
        System.out.println("Total de questões: " + QUESTOES_POR_TOPICO);

        // Loop para percorrer todas as questões do tópico
        while (exercicio.obterQuestaoAtual() != null) {
            Questao questaoAtual = exercicio.obterQuestaoAtual();

            // Exibe a questão atual
            System.out.println(questaoAtual.exibirQuestao());
            System.out.println("\nProgresso: " + exercicio.exibirBarraProgresso());

            // Mostra comandos especiais para navegação
            System.out.println("\n📝 Digite sua resposta ou escolha uma opção:");
            if (questaoAtual.getTipo() == TipoQuestao.MULTIPLA ||
                    questaoAtual.getTipo() == TipoQuestao.IDENTIFICAR_ERRO) {
                System.out.println("💡 Para múltipla escolha, digite a letra (A, B, C, D)");
            }

            System.out.println("⌨️  Comandos especiais:");
            if (exercicio.temQuestaoAnterior()) {
                System.out.println("   'V' ou 'VOLTAR' - Voltar questão anterior");
            }
            if (exercicio.temProximaQuestao()) {
                System.out.println("   'P' ou 'PULAR' - Pular questão atual");
            }
            System.out.println("   'M' ou 'MENU' - Voltar ao menu");
            System.out.println("\nProgresso da sessão: " + usuario.getEstatisticas().getEstatisticasResumo());
            System.out.print("\nSua escolha: ");

            String entrada = scanner.nextLine().trim().toUpperCase();

            // Processa comandos de navegação ou resposta
            if (entrada.equals("V") || entrada.equals("VOLTAR")) {
                if (exercicio.temQuestaoAnterior()) {
                    exercicio.voltar();
                    continue;
                } else {
                    System.out.println("❌ Não há questão anterior!");
                    continue;
                }
            } else if (entrada.equals("P") || entrada.equals("PULAR")) {
                exercicio.pularQuestao();
                if (exercicio.temProximaQuestao()) {
                    exercicio.avancar();
                } else {
                    break; // Última questão
                }
                continue;
            } else if (entrada.equals("M") || entrada.equals("MENU")) {
                exercicio.irParaMenu();
                return;
            } else {
                // Processa resposta do usuário
                boolean acertou = exercicio.responderAtual(entrada);

                // Pausa para o usuário ver o resultado
                System.out.print("\nPressione ENTER para continuar...");
                scanner.nextLine();

                // Avança para próxima questão se houver
                if (exercicio.temProximaQuestao()) {
                    exercicio.avancar();
                } else {
                    break; // Última questão concluída
                }
            }
        }

        // Ao final, mostra resumo do desempenho no tópico
        System.out.println(exercicio.gerarResumoDesempenho());
        System.out.println("\n🎉 Parabéns! Você concluiu o tópico: " + exercicio.getTituloTopico().toUpperCase());

        System.out.print("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    // Exibe estatísticas do usuário
    private void mostrarEstatisticas() {
        System.out.println(usuario.getEstatisticas().getResumo());

        System.out.println("\n📈 PROGRESSO DETALHADO:");
        System.out.println("Progresso geral: " + String.format("%.1f", usuario.getEstatisticas().getProgressoGeral()) + "%");

        // Exibe barra de progresso visual
        int progressoGeral = (int) (usuario.getEstatisticas().getProgressoGeral() / 10);
        System.out.print("Tópicos: [");
        for (int i = 0; i < 10; i++) {
            if (i < progressoGeral) {
                System.out.print("█");
            } else {
                System.out.print("░");
            }
        }
        System.out.println("] " + usuario.getEstatisticas().getTopicosEstudados().size() + "/5 tópicos");

        System.out.print("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    // Exibe cabeçalho inicial do sistema
    private void exibirCabecalho() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("            🎓 SISTEMA DE APRENDIZADO DE PROGRAMAÇÃO 🎓");
        System.out.println("               Java - Programação Orientada a Objetos");
        System.out.println("                      Versão " + VERSAO_SISTEMA);
        System.out.println("=".repeat(70));
        System.out.println("📚 Aprenda os conceitos fundamentais de POO de forma interativa!");
        System.out.println("🎯 Tópicos: Encapsulamento | Herança | Interface | Polimorfismo | Abstração");
        System.out.println("=".repeat(70));
    }

    public void gerenciarExcecoes(Exception e) {
        if (e instanceof NavegacaoException) {
            System.out.println("❌ Erro de navegação: " + e.getMessage());
        } else if (e instanceof QuestaoException) {
            System.out.println("❌ Erro na questão: " + e.getMessage());
        } else {
            System.out.println("❌ Erro inesperado: " + e.getMessage());
        }
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void resetarProgresso() {
        System.out.print("\n⚠️ Tem certeza que deseja reiniciar todo o progresso? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();
        if (confirmacao.equals("S")) {
            usuario.getEstatisticas().resetarEstatisticas();
            System.out.println("\n✅ Progresso reiniciado com sucesso!");
        }
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}

