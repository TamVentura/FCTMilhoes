
import java.util.Locale;

/**
 *
 * @author Tiago Ventura
 */
public class Jogo {

    /*
        Constantes
     */
    public static final int NUMBER_ESTRELAS = 2;
    public static final int NUMBER_NUMEROS = 5;
    
    public static final int MAX_NUMERO = 50;
    public static final int MAX_ESTRELA = 12;
    
    public static final int NUMBER_LEVELS = 13;
    
    public static final int[][] COMBINACOES = {{5,2},{5,1},{5,0},{4,2},{4,1},{3,2},{4,0},{2,2},{3,1},{3,0},{1,2},{2,1},{2,0}};
    public static final float[] PERCENTAGEM = {.432f,.0415f,.0192f,.0145f,.0148f,.0167f,.0138f,.0175f,.0285f,.041f,.0495f,.1385f,.1725f};
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
                    + " Valor premio: " + String.format("%.2f", (dinheiro * getPercentagem(level)) / nivel[level]) + " Euros");
        }
    }

    /**
     * Faz a jogada especificada no comando
     *
     * @param jogada comando de jogada
     * @return -1 caso exista um erro no parametro jogada, 0 caso nao se
     * encontre am nenhum nivel, nivel caso contrário
     */
    public int makePlay(String jogada) {
        String[] entradas = jogada.split(" ");

        if (entradas.length < NUMBER_ESTRELAS + NUMBER_NUMEROS + 1) {
            return -1;
        }

        int[] numeros = new int[NUMBER_NUMEROS];
        int[] estrelas = new int[NUMBER_ESTRELAS];

        for (int i = 0; i < NUMBER_NUMEROS; i++) {
            numeros[i] = Integer.valueOf(entradas[i + 1]);
        }
        for (int i = 0; i < NUMBER_ESTRELAS; i++) {
            estrelas[i] = Integer.valueOf(entradas[i + 1 + NUMBER_NUMEROS]);
        }

        for (int i = 0; i < NUMBER_NUMEROS; i++) {

            if (numeros[i] > MAX_NUMERO || numeros[i] < 1) {
                return -1;
            }
            for (int j = 0; j < NUMBER_NUMEROS; j++) {
                if (numeros[i] == numeros[j] && i != j) {
                    return -1;
                }
            }
        }

        for (int i = 0; i < NUMBER_ESTRELAS; i++) {

            if (estrelas[i] > MAX_ESTRELA || estrelas[i] < 1) {
                return -1;
            }
            for (int j = 0; j < NUMBER_ESTRELAS; j++) {
                if (estrelas[i] == estrelas[j] && i != j) {
                    return -1;
                }
            }
        }

        return getNivel(getNumberEqual(numeros, chave.criaIteratorNumbers(), 5), getNumberEqual(estrelas, chave.criaIteratorStars(), 2));
    }

    /**
     * Sai do jogo
     */
    public void exitGame() {
        float dinheiroPremios = 0;

        for (int i = 0; i < NUMBER_LEVELS; i++) {
            dinheiroPremios += dinheiro * (getPercentagem(i) * convertToInt(nivel[i]));
        }

        dinheiro -= dinheiroPremios;
        reset();
    }

    /*
        Reinicia os valores da quantidade de jogadas em cada nivel
     */
    public void reset() {
        for (int i = 0; i < NUMBER_LEVELS; i++) {
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
        return String.format("%.2f", dinheiro);
    }

    /**
     * Obtem a percentagem correspondente ao nivel
     *
     * @param level o nivel a obter a percentagem
     * @pre: level >= 0 && level < NUMBER_LEVELS
     * @return a percentagem correspondente
     */
    public float getPercentagem(int level) {
        if (level<NUMBER_LEVELS&&level>=0) {
            return PERCENTAGEM[level];
        }else{
            return 0;
        }
    }

    /**
     * Obtem o numero de numeros iguais no vetor lista e no IteratorInt it
     *
     * @param lista lista de inteiros a comparar
     * @param it iterator a comparar
     * @param size numero de elementos a verificar
     * @return numero de numeros iguais
     */
    public int getNumberEqual(int[] lista, IteratorInt it, int size) {
        int result = 0;
        for (int item : lista) {
            for (int i = 0; i < size; i++) {
                if (item == it.nextInt()) {
                    result++;
                }
            }
            it.reinitialize();
        }
        return result;
    }

    /**
     * Indica o nivel correspondente ao numero de numeros e de estrelas certos
     *
     * @param nNumeros numero de numeros certos
     * @param nEstrelas numero de estrelas certas
     * @return nivel da jogada
     */
    public int getNivel(int nNumeros, int nEstrelas) {
        for (int i = 0; i < 13; i++) {
            if (getNumeros(i)==nNumeros&&getEstrelas(i)==nEstrelas) {
                nivel[i]++;
                return i+1;
            }
        }
        return 0;
    }
    
    /**
     * Indica o numero de numeros correspondente ao nivel
     * 
     * @param level nivel
     * @pre: level >= 0 && level < NUMBER_LEVELS
     * @return numero de numeros correspondentes ao nivel
     */
    public int getNumeros(int level){
        return COMBINACOES[level][0];
    }
    
    
    /**
     * Indica o numero de estrelas correspondentes ao nivel
     * 
     * @param level nivel
     * @pre: level >= 0 && level < NUMBER_LEVELS
     * @return numero de estreas correspondentesao nivel
     */
    public int getEstrelas(int level){
        return COMBINACOES[level][1];
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
