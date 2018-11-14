
import java.util.Scanner;

/**
 *
 * @author Tiago Ventura
 */
public class Main {

    private static final String COMMAND_SAI = "SAI";
    private static final String COMMAND_AJUDA = "AJUDA";
    private static final String COMMAND_NOVO = "NOVO";
    private static final String COMMAND_JOGA = "JOGA";
    private static final String COMMAND_FIM = "FIM";

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        Game game = new Game();
        while (game.isRunning()) {
            if (game.isInGame()) {
                executeCommandInGame(sc, game);
            } else {
                executeCommandOutGame(sc, game);
            }
            sc.nextLine();
        }

        sc.close();
    }

    private static String readCommand(Scanner sc) {
        return sc.next().toUpperCase();
    }

    private static void executeCommandOutGame(Scanner sc, Game game) {
        System.out.print("> ");
        String command = readCommand(sc);

        switch (command) {
            case COMMAND_AJUDA:
                executeAjudaOut();
                break;
            case COMMAND_SAI:
                executeSai(game);
                break;
            case COMMAND_NOVO:
                executeNovo(sc.nextFloat(), game);
                break;
            default:
                System.out.println("Comando inexistente.");
                break;
        }
    }

    private static void executeCommandInGame(Scanner sc, Game game) {
        System.out.print("FCTMILHOES> ");
        String command = readCommand(sc);

        switch (command) {
            case COMMAND_AJUDA:
                executeAjudaIn();
                break;
            case COMMAND_JOGA:
                executeJoga(sc, game);
                break;
            case COMMAND_FIM:
                executeFim(game);
                break;
            default:
                System.out.println("Comando inexistente.");
                break;
        }
    }

    private static void executeAjudaOut() {
        System.out.println("novo - Novo jogo dando um valor inicial\n"
                + "sai - Termina a execucao do programa\n"
                + "ajuda - Mostra os comandos existentes");
    }

    private static void executeAjudaIn() {
        System.out.println("joga - Simula uma aposta, dando uma chave\n"
                + "fim - Termina o jogo em curso\n"
                + "ajuda - Mostra os comandos existentes");
    }

    private static void executeSai(Game game) {
        game.endProgram();

        System.out.printf("Valor acumulado: %.2f Euros. Ate a proxima.\n", game.getDinheiro());
    }

    private static void executeNovo(float dinheiro, Game game) {
        if (game.newGame(dinheiro) != 0) {
            System.out.printf("Jogo iniciado. Valor do premio: %.2f Euros.\n", game.getDinheiro());
        } else {
            System.out.println("Valor incorrecto.");
        }
    }

    private static void executeJoga(Scanner sc, Game game) {
        int[] numeros = new int[5];
        int[] estrelas = new int[2];

        for (int i = 0; i < 5; i++) {
            numeros[i] = sc.nextInt();
        }

        for (int i = 0; i < 2; i++) {
            estrelas[i] = sc.nextInt();
        }

        int nivel = game.makePlay(numeros, estrelas);
        switch (nivel) {
            case -1:
                System.out.println("Chave incorrecta.");
                break;
            case 0:
                System.out.println("Obrigado pela aposta.");
                break;
            default:
                System.out.println("Obrigado pela aposta. Premio de nivel: " + nivel);
                break;
        }
    }

    private static void executeFim(Game game) {
        for (int i = 0; i < 13; i++) {
            System.out.println(game.printLevel(i));
        }
        game.exitGame();
        System.out.printf("Valor acumulado: %.2f Euros\n", game.getDinheiro());
    }

}
