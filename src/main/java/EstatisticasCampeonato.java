import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EstatisticasCampeonato {

    public static void main(String[] args) {
        String caminhoArquivoFull = "src/main/resources/campeonato-brasileiro-full.csv";
        String caminhoArquivoCartoes = "src/main/resources/campeonato-brasileiro-cartoes.csv";
        String caminhoArquivoGols = "src/main/resources/campeonato-brasileiro-gols.csv";
        String caminhoArquivoEstatisticas = "src/main/resources/campeonato-brasileiro-estatisticas-full.csv";

        LeitorCSV leitorCSV = new LeitorCSV();
        List<String[]> dadosFull = leitorCSV.lerArquivo(caminhoArquivoFull);
        List<String[]> dadosCartoes = leitorCSV.lerArquivo(caminhoArquivoCartoes);
        List<String[]> dadosGols = leitorCSV.lerArquivo(caminhoArquivoGols);
        List<String[]> dadosEstatisticas = leitorCSV.lerArquivo(caminhoArquivoEstatisticas);

        imprimirRespostas(dadosFull, dadosCartoes, dadosGols);

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Deseja ver os dados de menores valores? (sim/não): ");
        System.out.println();
        String resposta = scanner.nextLine().trim().toLowerCase();

        if (resposta.equals("sim")) {
            imprimirRespostasInvertidas(dadosFull, dadosCartoes, dadosGols);
        } else {
            System.out.println("Aplicação encerrada, até logo!.");
        }

    }

    public static void imprimirRespostas(List<String[]> dadosFull, List<String[]> dadosCartoes, List<String[]> dadosGols) {

        List<String[]> tabelaDados = new ArrayList<>();

        String timeComMaisVitorias2008 = timeComMaisVitorias(dadosFull, 2008, true);
        tabelaDados.add(new String[]{"Time com mais vitórias em 2008", timeComMaisVitorias2008});

        String estadoComMenosJogos = estadoComMaisOuMenosJogos(dadosFull, true);
        tabelaDados.add(new String[]{"Estado com menos jogos entre 2003 e 2022", estadoComMenosJogos});

        String jogadorMaisGols = jogadorComMaisOuMenosGols(dadosGols, true);
        tabelaDados.add(new String[]{"Jogador que mais fez gols", jogadorMaisGols});

        String jogadorMaisPenaltis = jogadorComMaisOuMenosPenaltis(dadosGols, true);
        tabelaDados.add(new String[]{"Jogador que mais fez gols de pênaltis", jogadorMaisPenaltis});

        String jogadorMaisGolsContra = jogadorComMaisOuMenosGolsContra(dadosGols, true);
        tabelaDados.add(new String[]{"Jogador que mais fez gols contra", jogadorMaisGolsContra});

        String jogadorMaisAmarelos = jogadorComMaisOuMenosCartoes(dadosCartoes, "Amarelo", true);
        tabelaDados.add(new String[]{"Jogador que mais recebeu cartões amarelos", jogadorMaisAmarelos});

        String jogadorMaisVermelhos = jogadorComMaisOuMenosCartoes(dadosCartoes, "Vermelho", true);
        tabelaDados.add(new String[]{"Jogador que mais recebeu cartões vermelhos", jogadorMaisVermelhos});

        String partidaComMaisGols = partidaComMaisOuMenosGols(dadosFull, dadosGols, true);
        tabelaDados.add(new String[]{"Partida com mais gols", partidaComMaisGols});

        TabelaASCII.imprimirTabelaASCII(tabelaDados);
    }


    public static void imprimirRespostasInvertidas(List<String[]> dadosFull, List<String[]> dadosCartoes, List<String[]> dadosGols) {

        List<String[]> tabelaDados = new ArrayList<>();

        String timeComMenosVitorias2008 = timeComMaisVitorias(dadosFull, 2008, false);
        tabelaDados.add(new String[]{"Time com menos vitórias em 2008", timeComMenosVitorias2008});

        String estadoComMaisJogos = estadoComMaisOuMenosJogos(dadosFull, false);
        tabelaDados.add(new String[]{"Estado com mais jogos entre 2003 e 2022", estadoComMaisJogos});

        String jogadorMenosGols = jogadorComMaisOuMenosGols(dadosGols, false);
        tabelaDados.add(new String[]{"Jogador que menos fez gols", jogadorMenosGols});

        String jogadorMenosPenaltis = jogadorComMaisOuMenosPenaltis(dadosGols, false);
        tabelaDados.add(new String[]{"Jogador que menos fez gols de pênaltis", jogadorMenosPenaltis});

        String jogadorMenosGolsContra = jogadorComMaisOuMenosGolsContra(dadosGols, false);
        tabelaDados.add(new String[]{"Jogador que menos fez gols contra", jogadorMenosGolsContra});

        String jogadorMenosAmarelos = jogadorComMaisOuMenosCartoes(dadosCartoes, "Amarelo", false);
        tabelaDados.add(new String[]{"Jogador que menos recebeu cartões amarelos", jogadorMenosAmarelos});

        String jogadorMenosVermelhos = jogadorComMaisOuMenosCartoes(dadosCartoes, "Vermelho", false);
        tabelaDados.add(new String[]{"Jogador que menos recebeu cartões vermelhos", jogadorMenosVermelhos});

        String partidaComMenosGols = partidaComMaisOuMenosGols(dadosFull, dadosGols, false);
        tabelaDados.add(new String[]{"Partida com menos gols", partidaComMenosGols});

        TabelaASCII.imprimirTabelaASCII(tabelaDados);
    }


    public static String timeComMaisVitorias(List<String[]> dados, int ano, boolean maior) {
        Map<String, Integer> vitoriasPorTime = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        for (String[] linha : dados) {
            try {
                LocalDate dataPartida = LocalDate.parse(linha[2], formatter);
                int anoPartida = dataPartida.getYear();

                if (anoPartida == ano) {
                    String resultado = linha[13];
                    String timeMandante = linha[4];
                    if (resultado.equals("vitoria_mandante")) {
                        vitoriasPorTime.put(timeMandante, vitoriasPorTime.getOrDefault(timeMandante, 0) + 1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return maior ?
                vitoriasPorTime.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum time encontrado")
                :
                vitoriasPorTime.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum time encontrado");
    }

    public static String estadoComMaisOuMenosJogos(List<String[]> dados, boolean menor) {
        Map<String, Integer> jogosPorEstado = new HashMap<>();
        for (String[] linha : dados) {
            if (linha.length > 14) {
                String estadoMandante = linha[14];
                if (estadoMandante != null && !estadoMandante.isEmpty()) {
                    jogosPorEstado.put(estadoMandante, jogosPorEstado.getOrDefault(estadoMandante, 0) + 1);
                }

                if (linha.length > 15) {
                    String estadoVisitante = linha[15];
                    if (estadoVisitante != null && !estadoVisitante.isEmpty()) {
                        jogosPorEstado.put(estadoVisitante, jogosPorEstado.getOrDefault(estadoVisitante, 0) + 1);
                    }
                }
            }
        }

        return menor ?
                jogosPorEstado.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum estado encontrado")
                :
                jogosPorEstado.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum estado encontrado");
    }


    public static String jogadorComMaisOuMenosGols(List<String[]> dadosGols, boolean maior) {
        Map<String, Integer> golsPorJogador = new HashMap<>();
        for (String[] linha : dadosGols) {
            String jogador = linha[3];
            if (jogador != null && !jogador.isEmpty()) {
                golsPorJogador.put(jogador, golsPorJogador.getOrDefault(jogador, 0) + 1);
            }
        }
        return maior ?
                golsPorJogador.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado")
                :
                golsPorJogador.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado");
    }

    public static String jogadorComMaisOuMenosPenaltis(List<String[]> dadosGols, boolean maior) {
        Map<String, Integer> penaltisPorJogador = new HashMap<>();
        for (String[] linha : dadosGols) {
            String tipoGol = linha[5];
            if (tipoGol != null && tipoGol.equalsIgnoreCase("Penalty")) {
                String jogador = linha[3];
                if (jogador != null && !jogador.isEmpty()) {
                    penaltisPorJogador.put(jogador, penaltisPorJogador.getOrDefault(jogador, 0) + 1);
                }
            }
        }
        return maior ?
                penaltisPorJogador.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado")
                :
                penaltisPorJogador.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado");
    }

    public static String jogadorComMaisOuMenosGolsContra(List<String[]> dadosGols, boolean maior) {
        Map<String, Integer> golsContraPorJogador = new HashMap<>();
        for (String[] linha : dadosGols) {
            String tipoGol = linha[5];
            if (tipoGol != null && tipoGol.equalsIgnoreCase("Gol Contra")) {
                String jogador = linha[3];
                if (jogador != null && !jogador.isEmpty()) {
                    golsContraPorJogador.put(jogador, golsContraPorJogador.getOrDefault(jogador, 0) + 1);
                }
            }
        }
        return maior ?
                golsContraPorJogador.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado")
                :
                golsContraPorJogador.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado");
    }

    public static String jogadorComMaisOuMenosCartoes(List<String[]> dadosCartoes, String tipoCartao, boolean maior) {
        Map<String, Integer> cartoesPorJogador = new HashMap<>();
        for (String[] linha : dadosCartoes) {
            String tipo = linha[3];
            if (tipo != null && tipo.equalsIgnoreCase(tipoCartao)) {
                String jogador = linha[4];
                if (jogador != null && !jogador.isEmpty()) {
                    cartoesPorJogador.put(jogador, cartoesPorJogador.getOrDefault(jogador, 0) + 1);
                }
            }
        }
        return maior ?
                cartoesPorJogador.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado")
                :
                cartoesPorJogador.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhum jogador encontrado");
    }

    public static String partidaComMaisOuMenosGols(List<String[]> dadosFull, List<String[]> dadosGols, boolean maior) {
        Map<String, Integer> golsPorPartida = new HashMap<>();
        for (String[] linha : dadosGols) {
            String partidaId = linha[0];
            golsPorPartida.put(partidaId, golsPorPartida.getOrDefault(partidaId, 0) + 1);
        }

        String partidaId = maior ?
                golsPorPartida.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhuma partida encontrada")
                :
                golsPorPartida.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhuma partida encontrada");

        for (String[] linha : dadosFull) {
            if (linha[0].equals(partidaId)) {
                return "ID da partida: " + linha[0] + ", Mandante: " + linha[4] + " (" + linha[9] + ") x Visitante: " + linha[5] + " (" + linha[10] + ")";
            }
        }

        return "Nenhuma partida encontrada";
    }


}
