package it.salvatorevirzi.spring.common;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.salvatorevirzi.spring.model.Comune;
import it.salvatorevirzi.spring.model.Provincia;
import lombok.Data;

@Data
public class Common {
	private static final Logger logger = LoggerFactory.getLogger(Common.class);

	private final String PATH_FILE_COMUNE = "D:\\salvo\\HDD\\epicode\\epic-energy-services\\comuni-italiani.csv";
	private final String PATH_FILE_PROVINCIA = "D:\\salvo\\HDD\\epicode\\epic-energy-services\\province-italiane.csv";

	private List<Provincia> listaProvincia;
	private List<Comune> listaComune;

	public Common() {
		this.listaProvincia = readFileProvincia();
		this.listaComune = readFileComuni();
	}

	private List<Provincia> readFileProvincia() {
		File fileProvincia = new File(PATH_FILE_PROVINCIA);
		String readFileProvincia;
		List<Provincia> lstProv = new ArrayList<>();
		try {
			readFileProvincia = FileUtils.readFileToString(fileProvincia, "UTF-8");
			List<String> listFileProvincia = Arrays.asList(readFileProvincia.split("\n"));
			int n=0;
			for (String p : listFileProvincia) {
				if(n!=0) {
				String[] split = p.split(";");
				Provincia provincia = new Provincia();
				provincia.setSigla(split[0]);
				provincia.setNome(split[1]);
				lstProv.add(provincia);
				}else {
					n++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lstProv;
	}

	private List<Comune> readFileComuni() {
		File fileComune = new File(PATH_FILE_COMUNE);
		String readFileComune;
		List<Comune> listaComune = new ArrayList<>();
		try {
			readFileComune = FileUtils.readFileToString(fileComune, "UTF-8");
			List<String> listFileComune = Arrays.asList(readFileComune.split("\n"));
			int n=0;
			for (Provincia p : readFileProvincia()) {
				
				for (String c : listFileComune) {
					if(n!=0) {
					String[] splitC = c.split(";");
					if (splitC[3].equals(p.getNome())) {
						Comune comune = new Comune();
						comune.setNome(splitC[2]);
						comune.setProvincia(p);
						listaComune.add(comune);
					}}	else {
						n++;
					}
				}
			}
		} catch (IOException e) {
			logger.error("Errore");
			e.printStackTrace();
		}
//        listaComune.stream().forEach(a -> System.out.println(a));
		logger.info("Lettura corretta");
		return listaComune;
	}
}