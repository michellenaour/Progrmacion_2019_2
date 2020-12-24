
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * Clase de Gestión de Archivos
 *
 * @autor Lucas Sandoval Jorge Echeverria Sebastian Contreras
 * @version 2.0
 */

public class JSONGESTOR {

    /**
     * Verifica si recetario.json existe, de no existir lo crea.
     */

    public static void crearRecetarioVacio(){

        if(new File("recetario.json").exists()){}
        else{

            try{

                Files.write(Paths.get("recetario.json"), new String().getBytes());

            }
            catch(IOException e) {}

        }
    }

    /**
     * Añade ingredientes o instrucciones a un JSONArray.
     *
     * @param array Contiene ingredientes o instrucciones.
     * @param obj Ingrediente o instruccion.
     */

    public static void llenarJSONArray(JSONArray array, Object obj) {

        array.add(obj);

    }

    /**
     * Serializa una receta a formato JSONObject.
     *
     * @param nombre Nombre de receta.
     * @param ranking Valoración hecha por los usuarios.
     * @param votos Cantidad de veces que se rankeo la receta.
     * @param ingredientes Listado de ingredientes en formato JSON.
     * @param instrucciones Listado de pasos en formato JSON.
     * @return JSONObject Receta en formato JSON.
     */

    public static JSONObject encode(String nombre, String ranking,String votos, JSONArray ingredientes, JSONArray instrucciones) {

        JSONObject obj = new JSONObject();
        obj.put("nombre", nombre);
        obj.put("ranking", ranking);
        obj.put("ingredientes", ingredientes);
        obj.put("instrucciones", instrucciones);
        obj.put("votos", votos);
        return obj;

    }

    /**
     * Agrega el texto almacenado en el JSONObject en un archivo .json y lo guarda.
     *
     * @param obj Receta en formato JSON.
     */

    public static void saveFile(JSONObject obj) throws IOException {

        //Los salto de linea los hago de esta forma para que funcionen bien en cualquier
        //tipo de archivo o sistema operativo
        String saltoLinea = System.getProperty("line.separator");

        ArrayList<String> lineas = vectorLineas();
        String textoViejo = "";

        for (int x = 0; x < lineas.size(); x++) {
            textoViejo += lineas.get(x) + saltoLinea;
        }

        try (FileWriter file = new FileWriter("recetario.json")) {

            file.write(textoViejo + obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deserializa las lineas obtenidas de un .json y genera una Receta.
     *
     * @param lineas Recetas en formato String.
     * @param n Cantidad de lineas del .json.
     * @return Receta Receta como objeto de la clase del mismo nombre.
     */

    public static Receta decode(ArrayList<String> lineas, int n) {

        ArrayList<Ingrediente> ingredientesAL = new ArrayList();
        ArrayList<Instruccion> instruccionesAL = new ArrayList();
        Receta r = new Receta();

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(lineas.get(n));

            JSONObject jsonObject = (JSONObject) obj;
            String nombre = (String) jsonObject.get("nombre");
            String ranking = ((String) jsonObject.get("ranking"));
            String votos = ((String) jsonObject.get("votos"));

            r.setNombre(nombre);
            r.setRanking(Double.parseDouble(ranking));
            r.setVotos(Double.parseDouble(votos));

            ingredientes(jsonObject, ingredientesAL, r);
            instrucciones(jsonObject, instruccionesAL, r);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return r;

    }

    /**
     * Cuenta la cantidad de lineas dentro del archivo .json.
     *
     * @return int Cantidad de lineas del archivo .json.
     */

    public static int contarLineas() throws FileNotFoundException, IOException {
        int numLineas = 0;
        String fichero = new File("").getAbsolutePath() + File.separator + "recetario.json";
        BufferedReader reader = new BufferedReader(new FileReader(fichero));
        String linea = reader.readLine();

        while (linea != null) {
            numLineas = numLineas + 1;
            linea = reader.readLine();
        }
        return numLineas;
    }

    /**
     * Convierte el .json en texto plano.
     *
     * @return  ArrayList Contiene el contenido del .json, de manera que cada linea es un String en el ArrayList.
     *
     */

    public static ArrayList<String> vectorLineas() throws FileNotFoundException, IOException {

        int numLineas = 0;
        int contador = 0;
        String fichero = new File("").getAbsolutePath() + File.separator + "recetario.json";

        BufferedReader reader = new BufferedReader(new FileReader(fichero));
        String linea = reader.readLine();

        numLineas = contarLineas();

        ArrayList<String> datos = new ArrayList<String>();

        while (linea != null && contador < numLineas) {
            datos.add(linea);
            linea = reader.readLine();
            contador++;
        }
        return datos;
    }

    /**
     * Genera Recetario con el contenido del archivo .json.
     *
     * @param lineas Contiene el contenido del .json, de manera que cada linea es un String en el ArrayList.
     * @return Recetario Recetario que contiene todas las recetas del .json.
     *
     */

    public static Recetario generarRecetario(ArrayList<String> lineas) {

        Recetario recetario = new Recetario();

        recetario.recetas.clear();

        for (int x = 0; x < lineas.size(); x++) {

            recetario.recetas.add(decode(lineas, x));

        }
        recetario.ordenarRecetas();

        return recetario;

    }

    /**
     * Añande una receta al archivo .json.
     *
     * @param receta Receta para ser agregada al .json.
     *
     */

    public static void agregarRecetaArchivo(Receta receta) throws IOException {

        JSONArray ingredientes = new JSONArray();
        JSONArray instrucciones = new JSONArray();

        String nombre = receta.getNombre();

        String ranking = String.valueOf(receta.getRanking());
        String votos = String.valueOf(receta.getVotos());

        for (int x = 0; x < receta.getIngredientes().size(); x++) {
            llenarJSONArray(ingredientes, receta.getIngredientes().get(x).getNombre());
        }

        for (int x = 0; x < receta.getInstrucciones().size(); x++) {
            llenarJSONArray(instrucciones, receta.getInstrucciones().get(x).getPaso());
        }

        saveFile(encode(nombre, ranking,votos, ingredientes, instrucciones));
    }

    /**
     * Elimina una receta del .json, pero no afecta a las demás recetas.
     *
     * @param nombre Nombre de la receta a eliminar.
     *
     */

    public static void borrarRecetaArchivo(String nombre) throws IOException{

        ArrayList<Receta> recetario = Recetario.recetas;

        for(int i=0; i<recetario.size(); i++){

            if(recetario.get(i).getNombre().equals(nombre)){

                recetario.remove(i);

            }

        }

        vaciarRecetario("recetario.json");

        for(int i=0; i<recetario.size(); i++){

            agregarRecetaArchivo(recetario.get(i));

        }

    }

    /**
     * Elimina completamente el contenido del .json.
     *
     * @param dir Ruta del archivo .json.
     *
     */

    public static void vaciarRecetario(String dir){

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(dir));
            bw.write("");
            bw.close();
        }catch(IOException e){}

    }

    /**
     * Encargado de llenar el ArrayList ingredientes de una Receta desde un JSONObject.
     *
     * @param jsonObject Contiene los ingredientes en formato JSON.
     * @param ingredientesAL Contiene los ingredientes en formato Ingrediente.
     * @param r Receta que posee como atributo a ingredientesAL.
     *
     */

    public static void ingredientes(JSONObject jsonObject, ArrayList ingredientesAL, Receta r) {

        JSONArray ingredientesJA = (JSONArray) jsonObject.get("ingredientes");
        Iterator<String> iteratorING = ingredientesJA.iterator();

        while (iteratorING.hasNext()) {
            Ingrediente ing = new Ingrediente();
            ing.setNombre(iteratorING.next());
            ingredientesAL.add(ing);
        }

        r.setIngredientes(ingredientesAL);

    }

    /**
     * Encargado de llenar el ArrayList instrucciones de una Receta desde un JSONObject.
     *
     * @param jsonObject Contiene los instrucciones en formato JSON.
     * @param instruccionesAL Contiene las instrucciones en formato Instruccion.
     * @param r Receta que posee como atributo a instruccionesAL.
     *
     */

    public static void instrucciones(JSONObject jsonObject, ArrayList instruccionesAL, Receta r) {

        JSONArray instruccionesJA = (JSONArray) jsonObject.get("instrucciones");
        Iterator<String> iteratorINS = instruccionesJA.iterator();

        while (iteratorINS.hasNext()) {
            Instruccion ins = new Instruccion();
            ins.setPaso(iteratorINS.next());
            instruccionesAL.add(ins);
        }

        r.setInstrucciones(instruccionesAL);

    }

}

