package datos;

import contextoProblema.Boleta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ControlCSV {

	public ControlCSV() {
	}

	public void crearArchivo(Boleta boleta) {
		try {
			File file = new File("Boleta_"+boleta.getNroID()+".csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			else{
				file.delete();
				file.createNewFile();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void escribirBoleta(Boleta boletActual) {
		try {
			File file = new File("Boleta_"+boletActual.getNroID()+".csv");
			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n"+boletActual.toCSV());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}