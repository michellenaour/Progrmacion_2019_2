package contextoProblema;

import datos.ControlCSV;

import javax.naming.ldap.Control;
import java.util.ArrayList;


public class Tienda {
	private ArrayList<Boleta> talonario = new ArrayList<>();
	private int nroBoletas=talonario.size();


	public void emitirBoleta() {
		ControlCSV ctrl = new ControlCSV();
		ctrl.escribirBoleta(getBoletActual());
		nroBoletas++;
	}

	public Tienda() {
	}

	public void abrirBoleta() {
		Boleta boleta = new Boleta(nroBoletas);
		talonario.add(boleta);
		ControlCSV ctrl = new ControlCSV();
		ctrl.crearArchivo(boleta);
	}

	public void mostrarTalonario() {
		for (Boleta boleta : talonario) {
			System.out.println(boleta);
		}
	}

	public Boleta getBoletActual(){
		return talonario.get(talonario.size()-1);
	}
}