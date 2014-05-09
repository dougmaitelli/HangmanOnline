/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JF;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dougmaitelli
 */
public class JF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        int option = 0;
        do {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Server:");
            System.out.println("2 - Cliente:");

            Character opt;
            do {
                opt = (char) br.read();
            } while (!Character.isDigit(opt));

            option = Integer.parseInt(Character.toString(opt));
        } while (option < 1 || option > 2);

        if (option == 1) {
            ServerSocket server = new ServerSocket(5001);

            Socket cliente = server.accept();
            BufferedReader ci = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintStream co = new PrintStream(cliente.getOutputStream());

            int vidas = 5;

            System.out.print("Digite uma palavra: ");

            String palavra;
            do {
                palavra = br.readLine();
            } while (palavra.isEmpty());

            List<Character> array = new ArrayList<Character>();
            for (int i = 0; i < palavra.length(); i++) {
                array.add('_');
            }

            do {
                for (Character c : array) {
                    co.print(c);
                }

                co.println("     Vidas: " + vidas);

                Character letra;
                do {
                    letra = (char) Character.toLowerCase(ci.read());
                } while (!Character.isLetter(letra));

                if (palavra.toLowerCase().contains(letra.toString())) {
                    for (int i = 0; i < palavra.length(); i++) {
                        if (Character.toLowerCase(palavra.charAt(i)) == letra) {
                            array.set(i, palavra.charAt(i));
                        }
                    }
                } else {
                    vidas--;
                }
            } while (array.indexOf('_') >= 0 && vidas > 0);

            co.println("close");
        } else if (option == 2) {
            System.out.println("Digite o ip: ");

            String ip;
            do {
                ip = br.readLine();
            } while (ip.isEmpty());

            Socket server = new Socket(ip, 5001);
            BufferedReader ci = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream co = new PrintStream(server.getOutputStream());

            do {
                String texto = ci.readLine();

                if (texto.equals("close")) {
                    break;
                }
                
                System.out.println(texto);
                
                System.out.print("Escolha uma letra: ");

                Character letra;
                do {
                    letra = (char) br.read();
                } while (!Character.isLetter(letra));

                co.println(letra);
            } while (true);
        }
    }
}
