import java.util.ArrayList;
import java.util.Calendar;

public class Boleta {
    private int nroID;
    private int nroMesa;

    private Calendar fecha=Calendar.getInstance();
    private ArrayList<TipoPlato> consumo= new ArrayList<>();



    Boleta(int nroBoletas,int nroMesa) {
        this.nroID=nroBoletas;
        this.nroMesa=nroMesa;
    }

    ArrayList<TipoPlato> getConsumo() {
        return consumo;
    }

    public double getTotal() {
        double total=0;
        for(TipoPlato plato:consumo){
            total+=plato.getPrecio();
        }
        return total;
    }

    public int getNroID() {
        return nroID;
    }

    private String getFecha(){
        int year       = fecha.get(Calendar.YEAR);
        int month      = fecha.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = fecha.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth+"/"+month+"/"+year;
    }

    public String toString(){
        return "Fecha: "+getFecha()+ " N°: "+nroID+
                "\n"+consumo+
                "Total:"+this.getTotal();
    }

    public String toCSV() {
        return getNroID()+","+getFecha()+","+tienda.getMesa(nroMesa).consumoCSV()+this.getTotal();
    }
