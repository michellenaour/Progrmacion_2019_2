import java.io.IOException;
import java.util.ArrayList;

public class lectordeprueba {
    static String TEXTO= "{Boletas:[{id:1,fecha:4,consumo:[1300,200,4500],total:6000},{id:2,fecha:marzo,consumo:[2000,3000],total:5000},{id:3,fecha:mayo,consumo:[2000,3000,1200,3800],total:10000}]}";

    public static void main(String[]Args){

        String texto=TEXTO;

        ArrayList<String> bole= separaBoletas(texto);

        /*for (int i=0;i<bole.size();i++){
            System.out.println(bole.get(i));
        } */

        obtenerId(bole.get(1));
    }

    public static int obtenerId (String boleta){
        String[] str=boleta.split(",");
        String[] str2 = str[0].split(":");
        return stringAInt(str2[1]);
    }

    public static int stringAInt(String myString){
        int num;
        try {
            num = Integer.parseInt(myString);
        }
        catch (NumberFormatException e)
        {
            num = 0;
        }
        return num;
    }


    public  static  ArrayList<String> separaBoletas(String texto){
        String[] boletasplus= texto.split("[\\{\\}]");
        int nro_boletas=(boletasplus.length-2)/2;
        ArrayList <String> boletas= new ArrayList<String>();
        for (int i=2; i<boletasplus.length;i=i+2){
            int j=0;
            boletas.add(boletasplus[i]);
            j++;

        }

        return boletas;
    }


}
