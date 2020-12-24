package contextoProblema;

public enum TipoPlato {
	Empanada(1000),
	Pizza(1500),
	Churro(800),
	Papas_Fritas(600),
	Humita(1200);

	private double precio;
	private TipoPlato(double precio) {
		this.precio=precio;
	}

	public double getPrecio(){
		return this.precio;
	}

	public static TipoPlato get(int index){
		switch (index) {
			case 0: return Empanada;
			case 1: return Pizza;
			case 2: return Churro;
			case 3: return Papas_Fritas;
			case 4: return Humita;
			default: return null;
		}
	}

	public String toString(){
		return name()+": $"+getPrecio();
	}
	public String toStringLista(){
		return name()+": $"+getPrecio()+"\n";
	}

}