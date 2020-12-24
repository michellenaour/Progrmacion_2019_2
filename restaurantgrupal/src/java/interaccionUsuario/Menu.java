package interaccionUsuario;
import contextoProblema.Tienda;
import contextoProblema.TipoPlato;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	public Menu(){
	}

	public void desplegarMenu(){
		Tienda tienda = new Tienda();
		boolean salir = false;
		int opcion;
		while(!salir) {
			System.out.println("------Menu------");
			System.out.println("1. Mostrar Boletas");
			System.out.println("2. Hacer Pedido");
			System.out.println("3. Salir");

			opcion= elegirOpcion();
			switch (opcion) {
				case 1:
					tienda.mostrarTalonario();
					break;
				case 2:
					hacerPedido(tienda);
					break;
				case 3:
					System.out.println("Salir");
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 4");
			}


		}
	}

	private void hacerPedido(Tienda tienda) {
		boolean salir=false;
		tienda.abrirBoleta();
		System.out.println("Que quiere pedir?");
		while(!salir) {
			mostrarOpciones();
			int opcion = elegirOpcion();
			if(opcion>0&&opcion<6) {
				tienda.getBoletActual().agregarPlato(opcion-1);
				System.out.println("Quiere pedir algo mas?");
			}
			else if(opcion==6){
				salir=true;
			}
			else {
				System.out.println("Opcion no valida");
			}
		}
		tienda.emitirBoleta();
	}

	private void mostrarOpciones() {
		for(int i=0;i<5;i++) {
			System.out.println("[" + (i+1) + "] "+TipoPlato.get(i));
		}
		System.out.println("[6] Salir");
	}

	private int elegirOpcion() {
		int opcion=0;
		try {
			opcion= intentarElegir();
		}
		catch (InputMismatchException e) {
			System.out.println("Debes insertar un número");
			elegirOpcion();
		}
		return opcion;
	}

	private int intentarElegir() {
		Scanner sn = new Scanner(System.in);
		return sn.nextInt();
	}
}