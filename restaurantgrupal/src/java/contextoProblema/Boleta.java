package contextoProblema;

import java.util.ArrayList;
import java.util.Calendar;



public class Boleta {
	private int nroID;

	private Calendar fecha=Calendar.getInstance();
	private double _total=0;
	private ArrayList<TipoPlato> consumo = new ArrayList<>();
	Boleta(int nroBoletas) {
		this.nroID=nroBoletas;
	}

	private double getTotal() {
		return this._total;
	}

	public int getNroID() {
		return nroID;
	}

	public void agregarPlato(int opcion) {
		TipoPlato plato = TipoPlato.get(opcion);
		consumo.add(plato);
		assert plato != null;
		_total+=plato.getPrecio();
	}

	public String toString(){
		return "Fecha: "+fecha.getTime()+ " N°: "+nroID+
				"\n"+getConsumo()+
				"Total:"+getTotal();
	}

	private String getConsumo() {
		StringBuilder platos = new StringBuilder();
		for (TipoPlato tipoPlato : consumo) {
			platos.append(tipoPlato.toStringLista());
		}
		return platos.toString();
	}

	private String consumoCSV(){
		StringBuilder platos = new StringBuilder();
		for (TipoPlato tipoPlato : consumo) {
			platos.append(tipoPlato).append(",");
		}
		return platos.toString();
	}

	public String toCSV() {
		return getNroID()+","+fecha.getTime()+","+consumoCSV()+getTotal();
	}
}