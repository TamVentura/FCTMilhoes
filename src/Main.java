
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private static boolean running = true;
    private static boolean inGame = false;

    public static void main(String args[]) {
        Scanner sc;
        try {
            //sc = new Scanner(System.in);
            sc = new Scanner(new File("E:\\Escola\\Tiago\\1Semestre\\IP\\TP1\\T10\\input"));
            Jogo game = new Jogo();
            while (running) {
                prepareCommand(sc, game);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String readCommand(Scanner sc) {
        return sc.nextLine().toUpperCase();
    }

    private static void prepareCommand(Scanner sc, Jogo game) {

        if (!inGame) {
            executeCommandOutGame(sc, game);
        } else {
            executeCommandInGame(sc, game);
        }
    }

    private static void executeCommandOutGame(Scanner sc, Jogo game) {

        System.out.print("> ");
        String command = readCommand(sc);

        String[] param = command.split(" ");
        switch (param[0]) {
            case COMMAND_AJUDA:
                executeAjudaOut();
                break;
            case COMMAND_SAI:
                executeSai(game);
                break;
            case COMMAND_NOVO:
                executeNovo(Float.valueOf(param[1]), game);
                break;
            default:
                System.out.println("Comando inexistente.");
                break;
        }
    }

    private static void executeCommandInGame(Scanner sc, Jogo game) {

        System.out.print("FCTMILHOES> ");

        String command = readCommand(sc);

        String[] param = command.split(" ");
        switch (param[0]) {
            case COMMAND_AJUDA:
                executeAjudaIn();
                break;
            case COMMAND_JOGA:
                executeJoga(command, game);
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

    private static void executeSai(Jogo game) {
        running = false;

        System.out.println("Valor acumulado: " + game.getDinheiroString() + " Euros. Ate a proxima.");
    }

    private static void executeNovo(float dinheiro, Jogo game) {

        if (game.newGame(dinheiro) != 0) {
            System.out.println("Jogo iniciado. Valor do premio: " + game.getDinheiroString() + " Euros.");
            inGame = true;
        } else {
            System.out.println("Valor incorrecto.");
        }
    }

    private static void executeJoga(String jogada, Jogo game) {
        int nivel = game.makePlay(jogada);
        if (nivel == -1) {
            System.out.println("Chave incorrecta.");
        } else if (nivel == 0) {
            System.out.println("Obrigado pela aposta.");
        } else {
            System.out.println("Obrigado pela aposta. Premio de nivel: " + nivel);
        }
    }

    private static void executeFim(Jogo game) {
        inGame = false;
        game.printResults();
        game.exitGame();
        System.out.println("Valor acumulado: " + game.getDinheiroString() + " Euros");
    }

}
