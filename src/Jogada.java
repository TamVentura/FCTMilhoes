

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tiago Ventura
 */
public class Jogada {

    private int[] numeros, estrelas;

    /**
     * Cria uma jogada
     *
     * @param numeros os numeros da jogada
     * @param estrelas as estrelas da jogada
     */
    public Jogada(int[] numeros, int... estrelas) {
        this.numeros = numeros;
        this.estrelas = estrelas;
    }

    /**
     * Diz se a jogada corrente é válida
     *
     * @return true se a jogada for valida, false caso contrario
     */
    public boolean isValid() {
        for (int i = 0; i < 5; i++) {

            if (numeros[i] > 50 || numeros[i] < 1) {
                return false;
            }
            for (int j = 0; j < 5; j++) {
                if (numeros[i] == numeros[j] && i != j) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 2; i++) {

            if (estrelas[i] > 12 || estrelas[i] < 1) {
                return false;
            }
            for (int j = 0; j < 2; j++) {
                if (estrelas[i] == estrelas[j] && i != j) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Diz qual o nivel da jogada
     *
     * @param chave - Chave do torneio corrente
     * @return 0 caso n esteja em nenhum nivel, nivel da jogada caso contrario
     */
    public int getNivelJogada(Key chave) {
        int nNumeros = getNumberEqual(numeros, chave.criaIteratorNumbers(), 5);
        int nEstrelas = getNumberEqual(estrelas, chave.criaIteratorStars(), 2);

        if (nNumeros == 5 && nEstrelas == 2) {
            return 1;
        } else if (nNumeros == 5 && nEstrelas == 1) {
            return 2;
        } else if (nNumeros == 5 && nEstrelas == 0) {
            return 3;
        } else if (nNumeros == 4 && nEstrelas == 2) {
            return 4;
        } else if (nNumeros == 4 && nEstrelas == 1) {
            return 5;
        } else if (nNumeros == 3 && nEstrelas == 2) {
            return 6;
        } else if (nNumeros == 4 && nEstrelas == 0) {
            return 7;
        } else if (nNumeros == 2 && nEstrelas == 2) {
            return 8;
        } else if (nNumeros == 3 && nEstrelas == 1) {
            return 9;
        } else if (nNumeros == 3 && nEstrelas == 0) {
            return 10;
        } else if (nNumeros == 1 && nEstrelas == 2) {
            return 11;
        } else if (nNumeros == 2 && nEstrelas == 1) {
            return 12;
        } else if (nNumeros == 2 && nEstrelas == 0) {
            return 13;
        }
        return 0;

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

}
