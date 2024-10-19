import java.util.List;

public class TabelaASCII {

    public static void imprimirTabelaASCII(List<String[]> dados) {

        String[] headers = { "Pergunta", "Resposta" };

        String linhaDivisoria = "+-----------------------------------------------+--------------------------------------------------------------------------------------------+";

        System.out.println(linhaDivisoria);
        System.out.printf("| %-45s | %-90s |%n", headers[0], headers[1]);
        System.out.println(linhaDivisoria);

        for (String[] dado : dados) {
            System.out.printf("| %-45s | %-90s |%n", dado[0], dado[1] != null ? dado[1] : "");
        }

        System.out.println(linhaDivisoria);
    }
}
