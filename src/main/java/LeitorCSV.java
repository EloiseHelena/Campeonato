import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LeitorCSV {

    public List<String[]> lerArquivo(String caminhoArquivo) {
        List<String[]> linhas = null;
        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
            linhas = reader.readAll();
            linhas.remove(0);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return linhas;
    }
}
