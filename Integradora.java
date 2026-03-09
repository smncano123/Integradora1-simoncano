import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class Integradora {

    static Scanner scanner = new Scanner(System.in);

    static String nombreSorteo;
    static String descripcion;
    static double presupuesto;
    static LocalDate fecha;
    static String estado = "CREADO";

    static String[] participantes;
    static String[] amigo_secreto;

    public static void main(String[] args) {

        int opcion = 0;

        while (opcion != 6) {

            System.out.println("--- MENU SORTEO SECRETO ---");
            System.out.println("1. Registrar sorteo");
            System.out.println("2. Registrar participantes");
            System.out.println("3. Consultar participantes");
            System.out.println("4. Generar sorteo");
            System.out.println("5. Mostrar resumen");
            System.out.println("6. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    registrarSorteo();
                    break;

                case 2:
                    registrarParticipantes();
                    break;

                case 3:
                    mostrarParticipantes();
                    break;

                case 4:
                    generarSorteo();
                    break;

                case 5:
                    mostrarResumen();
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
    /*
    Contrato de este metodo: (registrarSorteo)
    De que trata: Registra la informacion basica del sorteo (nombre, descripcion, presupuesto y fecha).
    Entradas/parametros: No recibe parametros. Los datos se ingresan por teclado.
    Proceso: Pide los datos al usuario y guarda la informacion en las variables del programa.
    Salida/retorno: No retorna ningun valor.
    */
    public static void registrarSorteo() {

        System.out.println("Nombre del sorteo:");
        nombreSorteo = scanner.nextLine();

        System.out.println("Descripcion:");
        descripcion = scanner.nextLine();

        System.out.println("Presupuesto sugerido:");
        presupuesto = scanner.nextDouble();

        System.out.println("Año:");
        int year = scanner.nextInt();

        System.out.println("Mes:");
        int month = scanner.nextInt();

        System.out.println("Dia:");
        int day = scanner.nextInt();

        fecha = LocalDate.of(year, month, day);

        System.out.println("Sorteo registrado.");
    }
     /*
    Contrato de este metodo: (registrarParticipantes)
    De que trata: Permite registrar los participantes del sorteo.
    Entradas/parametros: No recibe parametros. El usuario ingresa el numero de participantes y sus nombres.
    Proceso: Crea los arreglos y usa un ciclo for para guardar los nombres verificando que no se repitan.
    Salida/retorno: No retorna ningun valor.
    */
    public static void registrarParticipantes() {

        System.out.println("Numero de participantes:");
        int n = scanner.nextInt();
        scanner.nextLine();

        participantes = new String[n];
        amigo_secreto = new String[n];

        for (int i = 0; i < n; i++) {

            System.out.println("Nombre participante " + (i + 1) + ":");
            String nombre = scanner.nextLine();

            if (existeParticipante(nombre, i)) {
                System.out.println("Ese nombre ya existe. Intente otro.");
                i--;
            } else {
                participantes[i] = nombre;
            }
        }
    }
    
     /*
    Contrato de este metodo: (existeParticipante)
    De que trata: Verifica si un participante ya existe en el arreglo.
    Entradas/parametros: nombre (String) y limite (int).
    Proceso: Recorre el arreglo y compara los nombres registrados.
    Salida/retorno: Retorna true si el nombre ya existe o false si no existe.
    */
    public static boolean existeParticipante(String nombre, int limite) {

        for (int i = 0; i < limite; i++) {
            if (participantes[i].equalsIgnoreCase(nombre)) {
                return true;
            }
        }

        return false;
    }
    /*
    Contrato de este metodo: (mostrarParticipantes)
    De que trata: Muestra la lista de participantes registrados.
    Entradas/parametros: No recibe parametros.
    Proceso: Verifica si hay participantes y recorre el arreglo para mostrarlos.
    Salida/retorno: No retorna ningun valor.
    */
    public static void mostrarParticipantes() {

        if (participantes == null) { 
            System.out.println("No hay participantes registrados.");
            return; //devuelve al menu//
        }

        for (int i = 0; i < participantes.length; i++) {
            System.out.println((i + 1) + ". " + participantes[i]);
        }
    }

    /*
    Contrato de este metodo: (generarSorteo)
    De que trata: Realiza el sorteo del amigo secreto.
    Entradas/parametros: No recibe parametros.
    Proceso: Usa Random para asignar un amigo secreto a cada participante sin repetirse.
    Salida/retorno: No retorna ningun valor.
    */

    public static void generarSorteo() {

        if (participantes == null) {
            System.out.println("Debe registrar participantes primero.");
            return;
        }

        Random random = new Random();

        boolean valido = false;

        while (!valido) {

            valido = true;

            for (int i = 0; i < participantes.length; i++) {
                int r = random.nextInt(participantes.length);
                amigo_secreto[i] = participantes[r];
            }

            for (int i = 0; i < participantes.length; i++) {

                if (participantes[i].equals(amigo_secreto[i])) {
                    valido = false;
                }

                for (int j = i + 1; j < participantes.length; j++) {

                    if (amigo_secreto[i].equals(amigo_secreto[j])) {
                        valido = false;
                    }
                }
            }
        }

        estado = "SORTEADO";
        System.out.println("Sorteo realizado correctamente.");
    }
    
    /*
    Contrato de este metodo: (mostrarResumen)
    De que trata: Muestra el resumen del sorteo realizado.
    Entradas/parametros: No recibe parametros.
    Proceso: Muestra el nombre del sorteo, la fecha y las asignaciones del amigo secreto.
    Salida/retorno: No retorna ningun valor.
    */
    public static void mostrarResumen() {

        if (!estado.equals("SORTEADO")) {
            System.out.println("El sorteo aún no se ha realizado.");
            return;
        }

        System.out.println("--- RESUMEN DEL SORTEO ---");
        System.out.println("Nombre: " + nombreSorteo);
        System.out.println("Fecha: " + fecha);

        for (int i = 0; i < participantes.length; i++) {
            System.out.println(participantes[i] + " -> " + amigo_secreto[i]);
        }
    }
}