import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HangmanOnline {

    public static void main(String[] args) throws Exception {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        int option = 0;
        do {
            System.out.println("Choose an Option:");
            System.out.println("1 - Server");
            System.out.println("2 - Client");

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

            int lifes = 5;

            System.out.print("Choose a word: ");

            String word;
            do {
            	word = br.readLine();
            } while (word.isEmpty());

            List<Character> array = new ArrayList<Character>();
            for (int i = 0; i < word.length(); i++) {
                array.add('_');
            }

            do {
                for (Character c : array) {
                    co.print(c);
                }

                co.println("     Life: " + lifes);

                Character letter;
                do {
                	letter = (char) Character.toLowerCase(ci.read());
                } while (!Character.isLetter(letter));

                if (word.toLowerCase().contains(letter.toString())) {
                    for (int i = 0; i < word.length(); i++) {
                        if (Character.toLowerCase(word.charAt(i)) == letter) {
                            array.set(i, word.charAt(i));
                        }
                    }
                } else {
                    lifes--;
                }
            } while (array.indexOf('_') >= 0 && lifes > 0);

            co.println("close");
        } else if (option == 2) {
            System.out.println("Enter the IP: ");

            String ip;
            do {
                ip = br.readLine();
            } while (ip.isEmpty());

            Socket server = new Socket(ip, 5001);
            BufferedReader ci = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream co = new PrintStream(server.getOutputStream());

            do {
                String text = ci.readLine();

                if (text.equals("close")) {
                    break;
                }
                
                System.out.println(text);
                
                System.out.print("Choose a letter: ");

                Character letter;
                do {
                	letter = (char) br.read();
                } while (!Character.isLetter(letter));

                co.println(letter);
            } while (true);
        }
    }
}
