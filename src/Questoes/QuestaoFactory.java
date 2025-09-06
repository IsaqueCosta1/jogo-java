// =============================================
// Classe QuestaoFactory
// Implementa padrão Factory Method para criação de questões
// Mantém banco de questões organizado por tópicos
// Responsável pela validação e construção de diferentes tipos
// =============================================

package Questoes;

import Exceptions.QuestaoException;
import enums.NivelDificuldade;
import enums.TipoQuestao;

import java.util.ArrayList;

public class QuestaoFactory {

    public static Questao criarQuestao(TipoQuestao tipo, NivelDificuldade nivel,
                                       String enunciado, String[] dados, String explicacao)
            throws QuestaoException {
        if (!validarDados(tipo, dados)) {
            throw new QuestaoException("Dados inválidos para criação da questão do tipo: " + tipo);
        }

        switch (tipo) {
            case MULTIPLA:
                return new QuestaoMultiplaEscolha(enunciado, nivel,
                        new String[]{dados[0], dados[1], dados[2], dados[3]}, dados[4], explicacao);

            case COMPLETAR:
                return new QuestaoCompletarCodigo(enunciado, nivel, dados[0], dados[1], explicacao);

            case IDENTIFICAR_ERRO:
                return new QuestaoIdentificarErro(enunciado, nivel, dados[0],
                        new String[]{dados[1], dados[2], dados[3], dados[4]}, dados[5], explicacao);

            default:
                throw new QuestaoException("Tipo de questão não suportado: " + tipo);
        }
    }

    public static ArrayList<Questao> criarQuestoesPorTopico(String topico) throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        switch (topico.toLowerCase()) {
            case "encapsulamento":
                questoes.addAll(criarQuestoesEncapsulamento());
                break;
            case "herança":
                questoes.addAll(criarQuestoesHeranca());
                break;
            case "interface":
                questoes.addAll(criarQuestoesInterface());
                break;
            case "polimorfismo":
                questoes.addAll(criarQuestoesPolimorfismo());
                break;
            case "abstração":
                questoes.addAll(criarQuestoesAbstracao());
                break;
            default:
                throw new QuestaoException("Tópico não encontrado: " + topico);
        }

        return questoes;
    }

    public static boolean validarDados(TipoQuestao tipo, String[] dados) {
        if (dados == null) return false;

        switch (tipo) {
            case MULTIPLA:
                return dados.length == 5; // 4 alternativas + resposta correta
            case COMPLETAR:
                return dados.length == 2; // template + resposta
            case IDENTIFICAR_ERRO:
                return dados.length == 6; // código + 4 alternativas + justificativa
            default:
                return false;
        }
    }

    // =============================================
    // QUESTÕES DE ENCAPSULAMENTO
    // =============================================
    private static ArrayList<Questao> criarQuestoesEncapsulamento() throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        // 6 questões FÁCEIS
        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que é encapsulamento em POO?",
                new String[]{"Ocultar dados e métodos", "Criar múltiplas classes", "Herdar características", "Implementar interfaces", "A"},
                "Encapsulamento é o princípio de ocultar os detalhes internos de uma classe e controlar o acesso aos seus atributos."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual palavra-chave torna um atributo privado em Java?",
                new String[]{"public", "private", "protected", "static", "B"},
                "A palavra-chave 'private' torna o atributo acessível apenas dentro da própria classe."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Para que servem os métodos getter e setter?",
                new String[]{"Para herança", "Para acessar atributos privados", "Para polimorfismo", "Para abstração", "B"},
                "Getters e setters são métodos públicos que permitem acessar e modificar atributos privados de forma controlada."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual a vantagem do encapsulamento?",
                new String[]{"Maior velocidade", "Controle de acesso aos dados", "Menos memória usada", "Código mais longo", "B"},
                "O encapsulamento oferece controle sobre como os dados são acessados e modificados, protegendo a integridade do objeto."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Em Java, qual modificador permite acesso apenas na mesma classe?",
                new String[]{"public", "protected", "private", "default", "C"},
                "O modificador 'private' restringe o acesso apenas à própria classe onde foi declarado."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que acontece se não definirmos métodos getter/setter para atributos privados?",
                new String[]{"Erro de compilação", "Os atributos ficam inacessíveis externamente", "Nada acontece", "Performance melhora", "B"},
                "Sem getters/setters, atributos privados não podem ser acessados ou modificados por outras classes."));

        // 5 questões MÉDIAS
        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete o método getter para o atributo 'nome': public String getNome() { return ______; }",
                new String[]{"public String getNome() { return ______; }", "nome"},
                "O método getter deve retornar o valor do atributo privado correspondente."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete o método setter: public void setIdade(int idade) { this.______ = idade; }",
                new String[]{"public void setIdade(int idade) { this.______ = idade; }", "idade"},
                "O método setter atribui o valor recebido como parâmetro ao atributo da classe usando 'this'."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Qual é a melhor prática para validação em setters?",
                new String[]{"Não fazer validação", "Validar antes de atribuir", "Validar depois de atribuir", "Usar apenas getters", "B"},
                "É importante validar os dados antes de atribuí-los aos atributos para manter a integridade do objeto."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete a validação no setter: public void setIdade(int idade) { if(idade >= 0) this.idade = ______; }",
                new String[]{"public void setIdade(int idade) { if(idade >= 0) this.idade = ______; }", "idade"},
                "O setter deve validar o parâmetro e, se válido, atribuir seu valor ao atributo da classe."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Por que usar 'this' nos setters?",
                new String[]{"É obrigatório", "Para distinguir atributo do parâmetro", "Para herança", "Para polimorfismo", "B"},
                "'this' é usado para referenciar explicitamente o atributo da classe quando há ambiguidade com parâmetros de mesmo nome."));

        // 4 questões DIFÍCEIS
        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Identifique o erro na implementação de encapsulamento:",
                new String[]{"public class Pessoa { public String nome; private int idade; }",
                        "Atributo 'nome' deveria ser privado", "Falta construtor", "Falta método main", "Classe deveria ser abstract", "A"},
                "Para garantir encapsulamento adequado, todos os atributos devem ser privados e acessados via getters/setters."));

        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Qual problema existe neste setter?",
                new String[]{"public void setSalario(double salario) { salario = salario; }",
                        "Não usa 'this' para distinguir atributo do parâmetro", "Falta validação", "Deveria ser private", "Parâmetro errado", "A"},
                "Sem 'this', o setter está atribuindo o parâmetro a ele mesmo, não ao atributo da classe."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.DIFICIL,
                "Qual a melhor estratégia para atributos de coleção (List, Set)?",
                new String[]{"Retornar a referência direta", "Retornar uma cópia da coleção", "Não criar getters", "Usar apenas arrays", "B"},
                "Para manter encapsulamento com coleções, deve-se retornar uma cópia para evitar modificações externas não controladas."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.DIFICIL,
                "Complete o getter seguro para List: public List<String> getNomes() { return new ArrayList<>(______); }",
                new String[]{"public List<String> getNomes() { return new ArrayList<>(______); }", "nomes"},
                "Para proteger a coleção interna, o getter deve retornar uma nova instância copiando os elementos da coleção original."));

        return questoes;
    }

    // =============================================
    // QUESTÕES DE HERANÇA
    // =============================================
    private static ArrayList<Questao> criarQuestoesHeranca() throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        // 6 questões FÁCEIS
        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que é herança em POO?",
                new String[]{"Criar objetos", "Uma classe herdar características de outra", "Esconder métodos", "Implementar interfaces", "B"},
                "Herança é o mecanismo onde uma classe filha herda atributos e métodos de uma classe pai."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual palavra-chave é usada para herança em Java?",
                new String[]{"implements", "extends", "inherits", "super", "B"},
                "A palavra-chave 'extends' é usada para estabelecer herança entre classes em Java."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Como chamamos a classe que herda de outra?",
                new String[]{"Classe pai", "Superclasse", "Classe filha", "Classe base", "C"},
                "A classe que herda é chamada de classe filha, subclasse ou classe derivada."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Quantas classes uma classe Java pode estender diretamente?",
                new String[]{"Nenhuma", "Uma", "Duas", "Ilimitadas", "B"},
                "Java permite herança simples - uma classe pode estender apenas uma superclasse diretamente."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que significa 'super' em Java?",
                new String[]{"Classe atual", "Referência à superclasse", "Método privado", "Atributo estático", "B"},
                "'super' é uma palavra-chave que referencia a classe pai, permitindo acessar seus membros."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Membros privados da superclasse são herdados?",
                new String[]{"Sim, sempre", "Não, nunca", "Só com super", "Depende do contexto", "B"},
                "Membros privados não são acessíveis nas subclasses, embora existam no objeto herdado."));

        // 5 questões MÉDIAS
        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete a herança: class Cachorro ______ Animal { }",
                new String[]{"class Cachorro ______ Animal { }", "extends"},
                "A palavra-chave 'extends' estabelece a relação de herança entre as classes."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete o construtor da subclasse: public Cachorro(String nome) { ______(nome); }",
                new String[]{"public Cachorro(String nome) { ______(nome); }", "super"},
                "super() chama o construtor da classe pai, inicializando adequadamente o objeto herdado."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "O que acontece se não chamarmos super() explicitamente no construtor?",
                new String[]{"Erro de compilação", "Java chama super() automaticamente", "O objeto não é criado", "Nada acontece", "B"},
                "Se não especificarmos, Java automaticamente chama super() sem parâmetros no início do construtor."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Qual modificador permite acesso às subclasses mas não a outras classes?",
                new String[]{"private", "public", "protected", "default", "C"},
                "O modificador 'protected' permite acesso à própria classe, subclasses e classes do mesmo pacote."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Para acessar método da superclasse: ______.metodo();",
                new String[]{"Para acessar método da superclasse: ______.metodo();", "super"},
                "super.metodo() chama especificamente a versão do método definida na classe pai."));

        // 4 questões DIFÍCEIS
        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Identifique o erro na herança:",
                new String[]{"class A extends B, C { }",
                        "Java não permite herança múltipla de classes", "Falta palavra-chave implements", "Sintaxe incorreta da classe", "Deveria usar super", "A"},
                "Java não suporta herança múltipla de classes. Uma classe pode estender apenas uma superclasse."));

        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Qual erro existe neste construtor?",
                new String[]{"public Filho() { this.nome = nome; super(); }",
                        "super() deve ser a primeira instrução", "Falta parâmetro", "this incorreto", "Deveria ser private", "A"},
                "A chamada super() deve ser sempre a primeira instrução no construtor da subclasse."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.DIFICIL,
                "O que acontece com sobrescrita de métodos e herança?",
                new String[]{"Métodos não podem ser sobrescritos", "Subclasse pode redefinir métodos da superclasse", "Apenas métodos private podem ser sobrescritos", "Sobrescrita quebra herança", "B"},
                "Sobrescrita permite que a subclasse forneça implementação específica para métodos herdados da superclasse."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.DIFICIL,
                "Complete a anotação para sobrescrita: ______ public void metodo() { }",
                new String[]{"______ public void metodo() { }", "@Override"},
                "@Override indica que o método está sobrescrevendo um método da superclasse, auxiliando na detecção de erros."));

        return questoes;
    }

    // =============================================
    // QUESTÕES DE INTERFACE
    // =============================================
    private static ArrayList<Questao> criarQuestoesInterface() throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        // 6 questões FÁCEIS
        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que é uma interface em Java?",
                new String[]{"Uma classe especial", "Um contrato que define métodos", "Um tipo de herança", "Um modificador de acesso", "B"},
                "Interface é um contrato que especifica quais métodos uma classe deve implementar, sem definir como."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual palavra-chave é usada para implementar uma interface?",
                new String[]{"extends", "implements", "interface", "abstract", "B"},
                "A palavra-chave 'implements' é usada para que uma classe implemente uma ou mais interfaces."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Métodos em interfaces são, por padrão:",
                new String[]{"private", "public abstract", "protected", "static", "B"},
                "Por padrão, métodos em interfaces são public abstract, devendo ser implementados pelas classes."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Uma classe pode implementar quantas interfaces?",
                new String[]{"Apenas uma", "No máximo duas", "Ilimitadas", "Depende do compilador", "C"},
                "Uma classe pode implementar múltiplas interfaces, separadas por vírgula."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Interfaces podem ter atributos?",
                new String[]{"Não, nunca", "Sim, mas apenas public static final", "Sim, qualquer tipo", "Apenas private", "B"},
                "Interfaces podem ter atributos, mas são implicitamente public, static e final (constantes)."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Como declaramos uma interface?",
                new String[]{"class MinhaInterface", "interface MinhaInterface", "abstract MinhaInterface", "public MinhaInterface", "B"},
                "Interfaces são declaradas com a palavra-chave 'interface' seguida do nome."));

        // 5 questões MÉDIAS
        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete a implementação: class Cachorro ______ Animal { }",
                new String[]{"class Cachorro ______ Animal { }", "implements"},
                "Uma classe usa 'implements' para implementar uma interface, comprometendo-se a definir todos os seus métodos."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "O que acontece se uma classe não implementar todos os métodos de uma interface?",
                new String[]{"Compila normalmente", "Erro de compilação", "Executa com warning", "Métodos ficam vazios", "B"},
                "Se uma classe não implementar todos os métodos da interface, ocorre erro de compilação."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete múltiplas interfaces: class Pessoa implements Trabalhador, ______ { }",
                new String[]{"class Pessoa implements Trabalhador, ______ { }", "Estudante"},
                "Múltiplas interfaces são implementadas separando-as por vírgula após 'implements'."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Desde Java 8, interfaces podem ter:",
                new String[]{"Apenas métodos abstratos", "Métodos default e static", "Construtores", "Atributos privados", "B"},
                "Java 8 introduziu métodos default (com implementação) e static em interfaces."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete o método default: ______ void metodo() { // implementação }",
                new String[]{"______ void metodo() { // implementação }", "default"},
                "Métodos default em interfaces têm implementação e podem ser usados pelas classes sem implementação obrigatória."));

        // 4 questões DIFÍCEIS
        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Identifique o erro na interface:",
                new String[]{"interface Animal { private void dormir(); }",
                        "Métodos em interface não podem ser private", "Falta palavra abstract", "Deveria usar class", "Falta implementação", "A"},
                "Métodos em interfaces são implicitamente public, não podem ser private."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.DIFICIL,
                "Qual a diferença entre classe abstrata e interface?",
                new String[]{"Não há diferença", "Interface permite herança múltipla", "Classes abstratas são mais rápidas", "Interface tem construtores", "B"},
                "Interfaces permitem uma forma de herança múltipla, enquanto classes abstratas seguem herança simples."));

        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Qual problema existe neste código?",
                new String[]{"interface A { public A(); }",
                        "Interfaces não podem ter construtores", "Construtor deve ser private", "Falta palavra-chave new", "Deveria ser static", "A"},
                "Interfaces não podem declarar construtores, apenas classes podem tê-los."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.DIFICIL,
                "Para resolver conflito entre métodos default: NomeInterface.______.metodo();",
                new String[]{"Para resolver conflito entre métodos default: NomeInterface.______.metodo();", "super"},
                "Quando há conflito entre métodos default, usa-se NomeInterface.super.metodo() para especificar qual implementação usar."));

        return questoes;
    }

    // =============================================
    // QUESTÕES DE POLIMORFISMO
    // =============================================
    private static ArrayList<Questao> criarQuestoesPolimorfismo() throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        // 6 questões FÁCEIS
        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que é polimorfismo?",
                new String[]{"Ter múltiplas classes", "Mesma interface, comportamentos diferentes", "Herança múltipla", "Encapsulamento de dados", "B"},
                "Polimorfismo permite que objetos de diferentes tipos respondam à mesma interface de maneiras específicas."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Quais são os tipos principais de polimorfismo em Java?",
                new String[]{"Estático e dinâmico", "Público e privado", "Simples e múltiplo", "Local e global", "A"},
                "Polimorfismo estático (sobrecarga) ocorre em tempo de compilação, dinâmico (sobrescrita) em tempo de execução."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Sobrecarga de métodos é um exemplo de polimorfismo:",
                new String[]{"Dinâmico", "Estático", "Múltiplo", "Abstrato", "B"},
                "Sobrecarga é polimorfismo estático, resolvido pelo compilador com base nos parâmetros."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que permite a sobrescrita de métodos?",
                new String[]{"Encapsulamento", "Herança", "Abstração", "Composição", "B"},
                "Sobrescrita é possível através de herança, onde subclasses redefinem métodos da superclasse."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Em polimorfismo dinâmico, quando é decidido qual método executar?",
                new String[]{"Em tempo de compilação", "Em tempo de execução", "Durante a escrita do código", "Nunca é decidido", "B"},
                "No polimorfismo dinâmico, a JVM decide qual implementação chamar baseada no tipo real do objeto."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual anotação indica sobrescrita de método?",
                new String[]{"@Overload", "@Override", "@Polymorphism", "@Super", "B"},
                "@Override indica que o método sobrescreve um método da superclasse ou interface."));

        // 5 questões MÉDIAS
        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete o polimorfismo: Animal animal = new ______();",
                new String[]{"Animal animal = new ______();", "Cachorro"},
                "Polimorfismo permite referenciar objetos de subclasses através de referências da superclasse."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "O que é method dispatch dinâmico?",
                new String[]{"Erro de compilação", "JVM escolhe método baseado no objeto real", "Múltiplos métodos executados", "Método executado duas vezes", "B"},
                "Method dispatch dinâmico é quando a JVM seleciona qual versão do método executar baseada no tipo real do objeto."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Para verificar tipo em runtime: if(animal ______ Cachorro) { }",
                new String[]{"if(animal ______ Cachorro) { }", "instanceof"},
                "instanceof verifica se um objeto é instância de uma classe específica em tempo de execução."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Sobrecarga de métodos requer:",
                new String[]{"Mesmo nome, mesmos parâmetros", "Mesmo nome, parâmetros diferentes", "Nomes diferentes", "Apenas métodos estáticos", "B"},
                "Sobrecarga exige métodos com mesmo nome mas assinaturas (parâmetros) diferentes."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Cast para tipo específico: Cachorro dog = (______)animal;",
                new String[]{"Cachorro dog = (______)animal;", "Cachorro"},
                "Cast explícito converte referência de superclasse para subclasse, mas deve-se verificar o tipo primeiro."));

        // 4 questões DIFÍCEIS
        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Identifique o erro no polimorfismo:",
                new String[]{"Animal animal = new Animal(); Cachorro dog = (Cachorro) animal;",
                        "ClassCastException: Animal não é Cachorro", "Erro de sintaxe", "Falta importação", "Cast desnecessário", "A"},
                "Tentar fazer cast de superclasse para subclasse quando o objeto real não é da subclasse causa ClassCastException."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.DIFICIL,
                "O que acontece com variáveis em polimorfismo?",
                new String[]{"São sempre polimórficas", "Não são polimórficas - usam a classe da referência", "Dependem do método", "São abstratas", "B"},
                "Variáveis não são polimórficas - sempre usam a definição da classe da referência, não do objeto real."));

        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Qual problema existe nesta sobrecarga?",
                new String[]{"public void metodo(int a) { } public int metodo(int b) { }",
                        "Não pode diferir apenas pelo tipo de retorno", "Parâmetros devem ter nomes diferentes", "Falta modificador static", "Deveria ser private", "A"},
                "Sobrecarga não pode diferir apenas pelo tipo de retorno - deve haver diferença nos parâmetros."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.DIFICIL,
                "Para chamar método específico da superclasse: ______.metodoSobrescrito();",
                new String[]{"Para chamar método específico da superclasse: ______.metodoSobrescrito();", "super"},
                "super.metodo() chama especificamente a versão do método na superclasse, ignorando a sobrescrita."));

        return questoes;
    }

    // =============================================
    // QUESTÕES DE ABSTRAÇÃO
    // =============================================
    private static ArrayList<Questao> criarQuestoesAbstracao() throws QuestaoException {
        ArrayList<Questao> questoes = new ArrayList<>();

        // 6 questões FÁCEIS
        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "O que é abstração em POO?",
                new String[]{"Ocultar implementação, mostrar funcionalidade", "Criar muitas classes", "Usar apenas interfaces", "Herança múltipla", "A"},
                "Abstração é o processo de ocultar detalhes de implementação e expor apenas funcionalidades essenciais."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Qual palavra-chave define uma classe abstrata?",
                new String[]{"interface", "abstract", "virtual", "extends", "B"},
                "A palavra-chave 'abstract' é usada para declarar classes que não podem ser instanciadas diretamente."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Classes abstratas podem ser instanciadas?",
                new String[]{"Sim, sempre", "Não, nunca", "Apenas com new", "Depende dos métodos", "B"},
                "Classes abstratas não podem ser instanciadas diretamente - servem como base para outras classes."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Métodos abstratos têm implementação?",
                new String[]{"Sim, sempre", "Não, apenas assinatura", "Às vezes", "Depende da classe", "B"},
                "Métodos abstratos são declarados sem implementação, devendo ser implementados pelas subclasses."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Uma classe com método abstrato deve ser:",
                new String[]{"Interface", "Abstrata", "Final", "Static", "B"},
                "Se uma classe tem pelo menos um método abstrato, ela deve ser declarada como abstrata."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.FACIL,
                "Classes abstratas podem ter métodos concretos?",
                new String[]{"Não, apenas abstratos", "Sim, podem misturar", "Apenas static", "Apenas private", "B"},
                "Classes abstratas podem ter tanto métodos abstratos quanto métodos com implementação concreta."));

        // 5 questões MÉDIAS
        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Complete a classe abstrata: ______ class Animal { }",
                new String[]{"______ class Animal { }", "abstract"},
                "A palavra-chave 'abstract' antes de class torna a classe abstrata, impedindo instanciação direta."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Declare método abstrato: ______ void fazerSom();",
                new String[]{"______ void fazerSom();", "abstract"},
                "Métodos abstratos são declarados com 'abstract' e terminam com ponto e vírgula, sem implementação."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Qual a diferença entre classe abstrata e interface?",
                new String[]{"Não há diferença", "Classes abstratas podem ter construtores", "Interfaces são mais rápidas", "Classes abstratas usam implements", "B"},
                "Classes abstratas podem ter construtores, atributos de instância e métodos concretos, diferente de interfaces."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.MEDIO,
                "Subclasse de classe abstrata deve:",
                new String[]{"Ser também abstrata", "Implementar todos métodos abstratos ou ser abstrata", "Usar apenas métodos concretos", "Não usar herança", "B"},
                "Subclasses devem implementar todos os métodos abstratos herdados ou também serem declaradas abstratas."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.MEDIO,
                "Para implementar método abstrato: ______ public void metodo() { // implementação }",
                new String[]{"______ public void metodo() { // implementação }", "@Override"},
                "@Override indica que o método está implementando/sobrescrevendo um método abstrato da superclasse."));

        // 4 questões DIFÍCEIS
        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Identifique o erro na classe abstrata:",
                new String[]{"abstract class Animal { abstract void som() { System.out.println(\"Som\"); } }",
                        "Método abstrato não pode ter implementação", "Falta palavra-chave public", "Classe deveria ser interface", "Falta construtor", "A"},
                "Métodos abstratos são apenas assinaturas - não podem ter implementação na classe abstrata."));

        questoes.add(criarQuestao(TipoQuestao.MULTIPLA, NivelDificuldade.DIFICIL,
                "Quando usar classe abstrata vs interface?",
                new String[]{"Sempre use interface", "Classe abstrata para código compartilhado", "Não há critério", "Interface é sempre melhor", "B"},
                "Use classe abstrata quando quiser compartilhar código entre subclasses; interface para contratos puros."));

        questoes.add(criarQuestao(TipoQuestao.IDENTIFICAR_ERRO, NivelDificuldade.DIFICIL,
                "Qual problema existe aqui?",
                new String[]{"abstract class A { } class B extends A { abstract void metodo(); }",
                        "Classe concreta não pode ter métodos abstratos", "Herança incorreta", "Falta implements", "Deveria usar interface", "A"},
                "Classes concretas (não abstratas) não podem declarar métodos abstratos."));

        questoes.add(criarQuestao(TipoQuestao.COMPLETAR, NivelDificuldade.DIFICIL,
                "Construtor em classe abstrata: ______ Animal(String nome) { this.nome = nome; }",
                new String[]{"______ Animal(String nome) { this.nome = nome; }", "protected"},
                "Construtores de classes abstratas geralmente são protected, permitindo acesso apenas às subclasses."));

        return questoes;
    }
}

