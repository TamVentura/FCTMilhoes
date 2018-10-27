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

    private int[] nivel;

    private float dinheiro;
    private Key chave;

    public Jogo() {
        nivel = new int[13];
        reset();
        this.dinheiro = 0;
    }

    public int newGame(float dinheiro) {
        if (dinheiro<=0) {
            return 0;
        }
        this.dinheiro += dinheiro;
        chave = new Key();
        return 1;
    }

    public void printResults() {
        for (int i = 0; i < 13; i++) {
            printLevel(i);
        }
    }

    public void printLevel(int level) {
        if (nivel[level] == 0) {
            System.out.println("Nivel: " + (level + 1) + " Jogadores: 0");
        } else {
            System.out.println("Nivel: " + (level + 1) + " Jogadores: " + nivel[level]
                    + " Valor premio: " + (dinheiro * getPercentagem(level)) + " Euros");
        }
    }

    public int makePlay(String jogada) {
        String[] entradas = jogada.split(" ");

        if (entradas.length < 8) {
            return -1;
        }

        int[] numeros = new int[5];
        int[] estrelas = new int[2];

        for (int i = 0; i < 5; i++) {
            numeros[i] = Integer.valueOf(entradas[i + 1]);
        }
        for (int i = 0; i < 2; i++) {
            estrelas[i] = Integer.valueOf(entradas[i + 6]);
        }

        for (int i = 0; i < 5; i++) {

            if (numeros[i] > 50 || numeros[i] < 1) {
                return -1;
            }
            for (int j = 0; j < 5; j++) {
                if (numeros[i] == numeros[j] && i!=j) {
                    return -1;
                }
            }
        }

        
        for (int i = 0; i < 2; i++) {

            if (estrelas[i] > 12 || estrelas[i] < 1) {
                return -1;
            }
            for (int j = 0; j < 2; j++) {
                if (estrelas[i] == estrelas[j] && i!=j) {
                    return -1;
                }
            }
        }


        return getNivel(getNumberEqual(numeros, chave.criaIteratorNumbers(), 5), getNumberEqual(estrelas, chave.criaIteratorStars(), 2));
    }

    public float getDinheiro() {
        return dinheiro;
    }

    public String getDinheiroString() {
        return String.format("%.2f", dinheiro);
    }

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

    public int toBinnary(int number) {
        if (number != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void exitGame() {
        //TODO
        float dinheiroPremios = dinheiro * (PERCENTAGEM1 * toBinnary(nivel[0]))
                + dinheiro * (PERCENTAGEM2 * toBinnary(nivel[1]))
                + dinheiro * (PERCENTAGEM3 * toBinnary(nivel[2]))
                + dinheiro * (PERCENTAGEM4 * toBinnary(nivel[3]))
                + dinheiro * (PERCENTAGEM5 * toBinnary(nivel[4]))
                + dinheiro * (PERCENTAGEM6 * toBinnary(nivel[5]))
                + dinheiro * (PERCENTAGEM7 * toBinnary(nivel[6]))
                + dinheiro * (PERCENTAGEM8 * toBinnary(nivel[7]))
                + dinheiro * (PERCENTAGEM9 * toBinnary(nivel[8]))
                + dinheiro * (PERCENTAGEM10 * toBinnary(nivel[9]))
                + dinheiro * (PERCENTAGEM11 * toBinnary(nivel[10]))
                + dinheiro * (PERCENTAGEM12 * toBinnary(nivel[11]))
                + dinheiro * (PERCENTAGEM13 * toBinnary(nivel[12]));

        dinheiro -= dinheiroPremios;
        reset();
    }

    public void reset() {
        for (int i = 0; i < 13; i++) {
            nivel[i] = 0;
        }
    }

}
