package Frag_adm_menu_res_aula;

/**
 * Created by Leony on 01/05/2017.
 */

public class Converter {

    public static String hora_deIntParaString (int hr, int min){
        return hr+":"+min;
    }

    public static int [] hora_deStringParaInt (String hr){
        int[] vetorHora = new int[2];
        if(hr.length()==4){
            if(String.valueOf(hr.charAt(1)).equals(":")){
                vetorHora[0] = Integer.parseInt(String.valueOf(hr.charAt(0)));
                vetorHora[1] = Integer.parseInt(String.valueOf(hr.charAt(2)+String.valueOf(hr.charAt(3))));
            }else{
                vetorHora[0] = Integer.parseInt(String.valueOf(hr.charAt(0)+String.valueOf(hr.charAt(1))));
                vetorHora[1] = Integer.parseInt(String.valueOf(hr.charAt(3)));
            }

        }else if (hr.length()==5){
            vetorHora[0] = Integer.parseInt(String.valueOf(hr.charAt(0)+String.valueOf(hr.charAt(1))));
            vetorHora[1] = Integer.parseInt(String.valueOf(hr.charAt(3))+String.valueOf(hr.charAt(4)));
        }
        return vetorHora;
    }

    public static String data_deInteParaString (int dia, int mes, int ano){
        return dia+"/"+mes+"/"+ano;
    }

    public static int [] data_deStringParaInt (String data){
        int[] vetorData = new int[3];

        if(data.length()==8){
            //dia
            vetorData[0] = Integer.parseInt(String.valueOf(data.charAt(0)));
            //mes
            vetorData[1] = Integer.parseInt(String.valueOf(data.charAt(2)));
            //ano
            vetorData[2] = Integer.parseInt(String.valueOf(
                    data.charAt(4)+String.valueOf(data.charAt(5)
                    +String.valueOf(data.charAt(6)
                    +String.valueOf(data.charAt(7))))));
        }else if (data.length()==9){
            if(String.valueOf(data.charAt(1)).equals("/")){
                //dia
                vetorData[0] = Integer.parseInt(String.valueOf(data.charAt(0)));
                //mes
                vetorData[1] = Integer.parseInt(String.valueOf(data.charAt(2)+String.valueOf(data.charAt(3))));
                //ano
                vetorData[2]= Integer.parseInt(String.valueOf(data.charAt(5)
                                +String.valueOf(data.charAt(6)
                                +String.valueOf(data.charAt(7)
                                +String.valueOf(data.charAt(8))))));

            }else{
                //dia
                vetorData[0] = Integer.parseInt(String.valueOf(data.charAt(0)+String.valueOf(data.charAt(1))));
                //mes
                vetorData[1] = Integer.parseInt(String.valueOf(data.charAt(3)));
                //ano
                vetorData[2]= Integer.parseInt(String.valueOf(data.charAt(5)
                        +String.valueOf(data.charAt(6)
                        +String.valueOf(data.charAt(7)
                        +String.valueOf(data.charAt(8))))));
            }
        }else if (data.length()==10){
            //dia
            vetorData[0] = Integer.parseInt(String.valueOf(data.charAt(0)+String.valueOf(data.charAt(1))));
            //mes
            vetorData[1] = Integer.parseInt(String.valueOf(data.charAt(3)+String.valueOf(data.charAt(4))));
            //ano
            vetorData[2]= Integer.parseInt(String.valueOf(data.charAt(6)
                    +String.valueOf(data.charAt(7)
                    +String.valueOf(data.charAt(8)
                    +String.valueOf(data.charAt(9))))));
        }

        return vetorData;
    }

}
