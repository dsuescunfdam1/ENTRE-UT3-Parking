/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un n� elevado de horas y se benefician de esta 
 * tarifa m�s econ�mica
 * (leer enunciado)
 * @David Suescun
 */
public class Parking
{
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 = 5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA = (6 * 60);
    private final int HORA_FIN_ENTRADA_TEMPRANA = (8 * 60 + 30);
    private final int HORA_INICIO_SALIDA_TEMPRANA = (15 * 60);
    private final int HORA_FIN_SALIDA_TEMPRANA = (18 * 60);
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.00;
    private final double PRECIO_MEDIA_COMERCIAL = 3.00;
    
    private String nombre;
    private int cliente;
    private double importeTotal;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clienteMaximoComercial;
    private double importeMaximoComercial;
    /**
     * Inicializa el parking con el nombre indicada por el par�metro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
       nombre =  queNombre;
       clientesLunes = 0;
       clientesSabado = 0;
       clientesDomingo = 0;
       clienteMaximoComercial = 0;
       importeMaximoComercial = 0;
       regular = 0;
       comercial = 0;
       importeTotal = 0;
       cliente = 0;
    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
        return nombre;
         
    }
    
    /**
     * mutador para el nombre del parking
     *  
     */
    public void cambiarNombre(String queNombre) {
        nombre = queNombre;
    }

    /**
     *  Recibe cuatro par�metros que supondremos correctos:
     *    tipoTarifa - un car�cter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida � hora de salida del parking
     *    dia � n� de d�a de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos par�metros el m�todo debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizar� adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro m�todo) las estad�sticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo d�a
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        cliente ++;
        double importe = 0;
        String tipoDeTarifa = "";
        
        int entradaHoras = entrada / 100;
        int entradaMinutos = entrada % 100;
        int entradaTodoMinutos = entradaHoras * 60 + entradaMinutos;
        
        int salidaHoras = salida / 100;
        int salidaMinutos = salida % 100;
        int salidaTodoMinutos = salidaHoras * 60 + salidaMinutos;
        int minutosAparcados = salidaTodoMinutos - entradaTodoMinutos;
        switch(tipoTarifa){
            
            case 'R': 
            if((entradaTodoMinutos > HORA_INICIO_ENTRADA_TEMPRANA && entradaTodoMinutos < HORA_FIN_ENTRADA_TEMPRANA) && (salidaTodoMinutos > HORA_INICIO_SALIDA_TEMPRANA && salidaTodoMinutos < HORA_FIN_SALIDA_TEMPRANA))
                { importe += PRECIO_TARIFA_PLANA_REGULAR;
                  tipoDeTarifa += "Plana y Regular";  
                 }
            else{
                if(entrada <1100){
                    if (salida > 1100){
                        importe += ((11 * 60 - entradaTodoMinutos) / 30 * PRECIO_MEDIA_REGULAR_HASTA11) + ((salidaTodoMinutos - 11 * 60) / 30 * PRECIO_MEDIA_REGULAR_DESPUES11); 
                    }
                    else{
                        importe += minutosAparcados / 30 * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else{
                    importe += minutosAparcados  / 30 * PRECIO_MEDIA_REGULAR_HASTA11;
        }
        tipoDeTarifa += "Regular";
    }
    
            regular ++;
            break;
    
            case 'C':
    if(minutosAparcados < 180){
        importe = PRECIO_PRIMERAS3_COMERCIAL;
        tipoDeTarifa += "Comercial de las primeras 3 horas";
    }
    else{
        importe = (minutosAparcados - 180) / 30 * PRECIO_MEDIA_COMERCIAL + PRECIO_PRIMERAS3_COMERCIAL;
        tipoDeTarifa += "Comercial de las primeras 3 horas m�s media comercial";
    }
    comercial ++;
    break;
  }

    System.out.println("************************************");
    System.out.println("N� de Cliente:" + cliente);
    System.out.println("Hora de entrada:" + entradaHoras + ":" + entradaMinutos);
    System.out.println("Hora de salida:" + salidaHoras + ":" + salidaMinutos);
    System.out.println("Tarifa a aplicar:" + tipoDeTarifa);
    System.out.println("Importe a pagar:" + importe + "�");
    System.out.println("************************************");
    
    importeTotal = importeTotal + importe;
    if(importeMaximoComercial < importe){
        importeMaximoComercial = importe;
        clienteMaximoComercial = cliente;
    }
    switch(dia){
        case 1: clientesLunes ++;
        break;
        
        case 6:clientesSabado ++;
        break;
        
        case 7: clientesDomingo ++;
        break;
    }
}
    /**
     * Muestra en pantalla las estad�sticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstad�sticas() {
        System.out.println("************************************");
        System.out.println("Importe total entre todos los clientes:" + importeTotal + "�");
        System.out.println("N� de clientes tarifa regular:" + regular);
        System.out.println("N� de clientes tarifa comercial:" + comercial);
        System.out.println("Cliente tarifa COMERCIAL con factura maxima fue el n� 2" + 
        clienteMaximoComercial + "y pago" + importeMaximoComercial + "�");
        System.out.println("************************************");
    }

    /**
     *  Calcula y devuelve un String que representa el nombre del d�a
     *  en el que m�s clientes han utilizado el parking - "S�BADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        int numeroDeClientes = 0;
        String diaMayorNumeroClientes = "";
        if (numeroDeClientes < clientesLunes){
            diaMayorNumeroClientes = "Lunes";
            numeroDeClientes = clientesLunes;
        }
        else if (numeroDeClientes < clientesSabado){
            diaMayorNumeroClientes = "Sabado";
            numeroDeClientes = clientesSabado;
        }
        else if (numeroDeClientes < clientesDomingo){
            diaMayorNumeroClientes = "Domingo";
            numeroDeClientes = clientesDomingo;
        }
        return  diaMayorNumeroClientes;   
    }

}
