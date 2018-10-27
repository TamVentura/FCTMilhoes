
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    public static final int NUMBER_ESTRELAS = 2;
    public static final int NUMBER_NUMEROS = 5;

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
                    + " Valor premio: " + String.format("%.2f", (dinheiro * getPercentagem(level))/nivel[level]) + " Euros");
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

            if (numeros[i] > 50 || numeros[i] < 1) {
                return -1;
            }
            for (int j = 0; j < NUMBER_NUMEROS; j++) {
                if (numeros[i] == numeros[j] && i != j) {
                    return -1;
                }
            }
        }

        for (int i = 0; i < NUMBER_ESTRELAS; i++) {

            if (estrelas[i] > 12 || estrelas[i] < 1) {
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
        
        for (int i = 0; i < 13; i++) {
            dinheiroPremios += dinheiro * (getPercentagem(i)*convertToInt(nivel[i]));
        }
        
//        float dinheiroPremios = dinheiro * (PERCENTAGEM1 * convertToInt(nivel[0]))
//                + dinheiro * (PERCENTAGEM2 * convertToInt(nivel[1]))
//                + dinheiro * (PERCENTAGEM3 * convertToInt(nivel[2]))
//                + dinheiro * (PERCENTAGEM4 * convertToInt(nivel[3]))
//                + dinheiro * (PERCENTAGEM5 * convertToInt(nivel[4]))
//                + dinheiro * (PERCENTAGEM6 * convertToInt(nivel[5]))
//                + dinheiro * (PERCENTAGEM7 * convertToInt(nivel[6]))
//                + dinheiro * (PERCENTAGEM8 * convertToInt(nivel[7]))
//                + dinheiro * (PERCENTAGEM9 * convertToInt(nivel[8]))
//                + dinheiro * (PERCENTAGEM10 * convertToInt(nivel[9]))
//                + dinheiro * (PERCENTAGEM11 * convertToInt(nivel[10]))
//                + dinheiro * (PERCENTAGEM12 * convertToInt(nivel[11]))
//                + dinheiro * (PERCENTAGEM13 * convertToInt(nivel[12]));

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
        return String.format("%.2f", dinheiro);
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
        if (nNumeros == 5 && nEstrelas == 2) {
            nivel[0]++;
            return 1;
        } else if (nNumeros == 5 && nEstrelas == 1) {
            nivel[1]++;
            return 2;
        } else if (nNumeros == 5 && nEstrelas == 0) {
            nivel[2]++;
            return 3;
        } else if (nNumeros == 4 && nEstrelas == 2) {
            nivel[3]++;
            return 4;
        } else if (nNumeros == 4 && nEstrelas == 1) {
            nivel[4]++;
            return 5;
        } else if (nNumeros == 3 && nEstrelas == 2) {
            nivel[5]++;
            return 6;
        } else if (nNumeros == 4 && nEstrelas == 0) {
            nivel[6]++;
            return 7;
        } else if (nNumeros == 2 && nEstrelas == 2) {
            nivel[7]++;
            return 8;
        } else if (nNumeros == 3 && nEstrelas == 1) {
            nivel[8]++;
            return 9;
        } else if (nNumeros == 3 && nEstrelas == 0) {
            nivel[9]++;
            return 10;
        } else if (nNumeros == 1 && nEstrelas == 2) {
            nivel[10]++;
            return 11;
        } else if (nNumeros == 2 && nEstrelas == 1) {
            nivel[11]++;
            return 12;
        } else if (nNumeros == 2 && nEstrelas == 0) {
            nivel[12]++;
            return 13;
        }
        return 0;
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
