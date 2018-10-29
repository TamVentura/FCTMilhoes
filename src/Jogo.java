
import java.util.Locale;

/**
 *
 * @author Tiago Ventura
 */
public class Jogo {

    /*
        Constantes
     */
    public static final float PERCENTAGEM1 = .432f;
    public static final float PERCENTAGEM2 = .0415f;
    public static final float PERCENTAGEM3 = .0192f;
    public static final float PERCENTAGEM4 = .0145f;
    public static final float PERCENTAGEM5 = .0148f;
    public static final float PERCENTAGEM6 = .0167f;
    public static final float PERCENTAGEM7 = .0138f;
    public static final float PERCENTAGEM8 = .0175f;
    public static final float PERCENTAGEM9 = .0285f;
    public static final float PERCENTAGEM10 = .041f;
    public static final float PERCENTAGEM11 = .0495f;
    public static final float PERCENTAGEM12 = .1385f;
    public static final float PERCENTAGEM13 = .1725f;
    public static final Locale LINGUA = Locale.US;

    /*
        Variáveis de instância
     */
    private int[] nivel;
    private float dinheiro;
    private Key chave;

    /**
     * Cria um novo jogo
     */
    public Jogo() {
        nivel = new int[13];
        reset();
        this.dinheiro = 0;
    }

    /**
     * Inicia um novo jogo
     *
     * @param dinheiro dinheiro a colocar no jogo
     * @pre: dinheiro>0
     * @return 1 o dinheiro seja maior que 0, 0 caso contrário
     */
    public int newGame(float dinheiro) {
        if (dinheiro <= 0) {
            return 0;
        }
        this.dinheiro += dinheiro;
        chave = new Key();
        return 1;
    }

    /**
     * Escreve o resultado do jogo na consola
     */
    public void printResults() {
        for (int i = 0; i < 13; i++) {
            printLevel(i);
        }
    }

    /**
     * Escreve o resultado de cada nivel na consola
     *
     * @param level o nivel a ser escrito na consola
     * @pre: level >=0 && level <13
     */
    public void printLevel(int level) {
        if (nivel[level] == 0) {
            System.out.println("Nivel: " + (level + 1) + " Jogadores: 0");
        } else {
            System.out.println("Nivel: " + (level + 1) + " Jogadores: " + nivel[level]
                    + " Valor premio: " + String.format(LINGUA, "%.2f", (dinheiro * getPercentagem(level)) / nivel[level]) + " Euros");
        }
    }

    /**
     * Faz a jogada especificada no comando
     *
     * @param jogada comando de jogada
     * @return -1 caso exista um erro no parametro jogada, 0 caso nao se
     * encontre am nenhum nivel, nivel caso contrário
     */
    public int makePlay(int[] numeros, int... estrelas) {
        Jogada jogada = new Jogada(numeros, estrelas);
        if (!jogada.isValid()) {
            return -1;
        }
        int lvl = jogada.getNivelJogada(chave);
        if (lvl != 0) {
            nivel[lvl - 1]++;
        }

        return jogada.getNivelJogada(chave);
    }

    /**
     * Sai do jogo
     */
    public void exitGame() {
        float dinheiroPremios = 0;

        for (int i = 0; i < 13; i++) {
            dinheiroPremios += dinheiro * (getPercentagem(i) * convertToInt(nivel[i]));
        }

        dinheiro -= dinheiroPremios;
        reset();
    }

    /*
        Reinicia os valores da quantidade de jogadas em cada nivel
     */
    public void reset() {
        for (int i = 0; i < 13; i++) {
            nivel[i] = 0;
        }
    }

    /**
     * Obtem o valor do dinheiro
     *
     * @return dinheiro
     */
    public float getDinheiro() {
        return dinheiro;
    }

    /**
     * Obtem o valor do dinheiro formatado
     *
     * @return dinheiro com 2 casas decimais
     */
    public String getDinheiroString() {
        return String.format(LINGUA, "%.2f", dinheiro);
    }

    /**
     * Obtem a percentagem correspondente ao nivel
     *
     * @param level o nivel a obter a percentagem
     * @pre: level >= 0 && level < 13
     * @return a percentagem correspondente
     */
    public float getPercentagem(int level) {
        switch (level) {
            case 0:
                return PERCENTAGEM1;
            case 1:
                return PERCENTAGEM2;
            case 2:
                return PERCENTAGEM3;
            case 3:
                return PERCENTAGEM4;
            case 4:
                return PERCENTAGEM5;
            case 5:
                return PERCENTAGEM6;
            case 6:
                return PERCENTAGEM7;
            case 7:
                return PERCENTAGEM8;
            case 8:
                return PERCENTAGEM9;
            case 9:
                return PERCENTAGEM10;
            case 10:
                return PERCENTAGEM11;
            case 11:
                return PERCENTAGEM12;
            case 12:
                return PERCENTAGEM13;
            default:
                return 0;
        }
    }

    /**
     * Obtem um numero e converte para outro
     *
     * @param number numero a converter
     * @return 1 caso diferente de 0, 0 caso contrário
     */
    public int convertToInt(int number) {
        if (number != 0) {
            return 1;
        } else {
            return 0;
        }
    }

}