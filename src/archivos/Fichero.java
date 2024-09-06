package archivos;

import lenguaje.Gramatica;
import com.google.gson.Gson;
import lenguaje.ProduccionGramatical;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Clase que abre y procesa archivos de texto.
 *
 * @author fisherk2
 */
public class Fichero {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private final String PATH_RELATIVE;
    private final File FILE;
    private final Gson GSON;
    private FileReader lector;

    /**
     * Clase que almacena las propiedades de un fichero almacenadas en los archivos del proyecto /src/archivos/[fichero].
     *
     * @param nombre_archivo Nombre del fichero con su respectiva extension [Ubicado en src/archivos/].
     */
    public Fichero(String nombre_archivo) {

        // ◂ ◂ ◂ ◂ Fichero que se encuentran en la carpeta archivos del proyecto ▸ ▸ ▸ ▸ //
        PATH_RELATIVE = "src/archivos/" + nombre_archivo;

        FILE = new File(PATH_RELATIVE);
        GSON = new Gson();
    }

    /**
     * Metodo que obtiene el contenido del fichero en forma de texto plano.
     *
     * @return Cadena en forma de texto plano.
     */
    public String leer_texto_plano() {
        String cadena;
        String texto_plano = "";

        try {
            lector = new FileReader(FILE);
            BufferedReader lectura = new BufferedReader(lector);

            // ◂ ◂ ◂ ◂ Si no hay vacios en nuestro archivos, entonces se tiene que seguir leyendo el archivo ▸ ▸ ▸ ▸ //
            while ((cadena = lectura.readLine()) != null) {
                texto_plano += cadena + "\n";
            }

            // ◂ ◂ ◂ ◂ Cerrar buffer de lectura ▸ ▸ ▸ ▸ //
            lectura.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "ERROR, NO SE PUEDE ENCONTRAR EL ARCHIVO\nLa ruta " + PATH_RELATIVE + " no parece valida, verifique bien su ubicacion.",
                    "FILE NOT FOUND",
                    JOptionPane.WARNING_MESSAGE);
            return "";
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "ERROR, NO SE PUEDE LEER EL ARCHIVO",
                    "FILE CANNOT READ", JOptionPane.WARNING_MESSAGE);
            return "";
        }

        return texto_plano;
    }

    /**
     * Funcion que deserializa un archivo JSON dependiendo de la clase donde se va almacenar dichas propiedades del archivo.
     *
     * @param claseFormato Clase donde se almacenara el contenido del archivo JSON.
     * @return Objeto con un contenido JSON deserializado.
     */
    public Object deserializar_json(Class<?> claseFormato) {
        Object json_deserializado;

        try {

            lector = new FileReader(FILE);

            // ◂ ◂ ◂ ◂ Almacenamos el contenido del archivo JSON en este objeto de la clase del formato ▸ ▸ ▸ ▸ //
            json_deserializado = GSON.fromJson(lector, claseFormato);

            // ◂ ◂ ◂ ◂ Cerrar buffer de lectura ▸ ▸ ▸ ▸ //
            lector.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "ERROR, NO SE PUEDE ENCONTRAR EL ARCHIVO JSON\nLa ruta " + PATH_RELATIVE + " no parece valida, verifique bien su ubicacion.",
                    "FILE NOT FOUND",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "ERROR, NO SE PUEDE LEER EL ARCHIVO",
                    "FILE CANNOT READ", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return json_deserializado;
    }

    /**
     * Metodo que deserializa un archivo .txt con un formato de escritura gramatical para analizador sintactico.
     *
     * @return El objeto de la gramatica.
     * @implNote Las producciones gramaticales debe llevar este formato: id .- N -> [Simbolos NuT separado por espacios]
     * @deprecated Metodo poco practico y suceptible a errores de escritura en el archivo de texto.
     */
    public Gramatica deserializar_gramatica() {

        LinkedList<String> lineas_produccion = separarPorLineas(leer_texto_plano());
        int id;
        String noTerminal;
        String simbolosSinSeparar;
        LinkedList<String> produccion_dinamica = new LinkedList<>();
        String[] NuT;
        String simbolo = "";
        LinkedList<ProduccionGramatical> producciones_dinamicas = new LinkedList<>();
        ProduccionGramatical[] P;

        LinkedList<String> noTerminalesDinamico = new LinkedList<>();
        String[] N;

        LinkedList<String> terminalesDinamico = new LinkedList<>();
        String[] T;

        String S = "";

        // ◂ ◂ ◂ ◂ Generar producciones gramaticales ▸ ▸ ▸ ▸ //
        for (String linea : lineas_produccion) {

            if (!linea.isEmpty()) {
                id = Integer.parseInt(linea.split("→|->")[0].split("\\.-")[0].trim());
                noTerminal = linea.split("→|->")[0].split("\\.-")[1].trim();
                simbolosSinSeparar = linea.split("→|->")[1].trim();

                // ◂ ◂ ◂ ◂ Separamos cada simbolo terminal y no terminal de la parte derecha de la produccion ▸ ▸ ▸ ▸ //
                for (int i = 0; i < simbolosSinSeparar.toCharArray().length; i++) {

                    // ◂ ◂ ◂ ◂ Se generara simbolos hasta que encuentre un espacio ▸ ▸ ▸ ▸ //
                    if (Character.isLetterOrDigit(simbolosSinSeparar.toCharArray()[i]) || simbolosSinSeparar.toCharArray()[i] == '_') {
                        simbolo += String.valueOf(simbolosSinSeparar.toCharArray()[i]);

                    } else if (Character.isWhitespace(simbolosSinSeparar.toCharArray()[i])) {
                        if (!simbolo.isEmpty()) {
                            produccion_dinamica.add(simbolo);
                            simbolo = "";
                        }

                    } else {
                        if (!simbolo.isEmpty()) {
                            produccion_dinamica.add(simbolo);
                        }
                        // ◂ ◂ ◂ ◂ Se trata como simbolo en caso de que sea un caracter especial ▸ ▸ ▸ ▸ //
                        produccion_dinamica.add(String.valueOf(simbolosSinSeparar.toCharArray()[i]));
                        simbolo = "";
                    }
                }

                // ◂ ◂ ◂ ◂ Se generara el ultimo simbolo en caso de que no este vacio ▸ ▸ ▸ ▸ //
                if (!simbolo.isEmpty()) {
                    produccion_dinamica.add(simbolo);
                    simbolo = "";
                }

                // ◂ ◂ ◂ ◂ Generamos la produccion de la gramatica ▸ ▸ ▸ ▸ //
                if (produccion_dinamica.isEmpty()) {
                    producciones_dinamicas.add(new ProduccionGramatical(id, noTerminal, new String[]{""}));
                } else {

                    // ◂ ◂ ◂ ◂ Generamos arreglo estatico NuT de la produccion gramatical ▸ ▸ ▸ ▸ //
                    NuT = new String[produccion_dinamica.size()];
                    for (int i = 0; i < NuT.length; i++) {

                        // ◂ ◂ ◂ ◂ Generamos produccion en caso de que derive a vacio con el simbolo Epsilon ▸ ▸ ▸ ▸ //
                        if (produccion_dinamica.get(0).equals("ε")) {
                            NuT = new String[]{""};
                            break;
                        }
                        NuT[i] = produccion_dinamica.get(i);
                    }

                    // ◂ ◂ ◂ ◂ Creamos la produccion ▸ ▸ ▸ ▸ //
                    producciones_dinamicas.add(new ProduccionGramatical(id, noTerminal, NuT));
                }

                produccion_dinamica.clear();
            }
        }

        // ◂ ◂ ◂ ◂ Generar arreglo estatico de producciones gramaticales ▸ ▸ ▸ ▸ //
        P = new ProduccionGramatical[producciones_dinamicas.size()];
        for (int i = 0; i < P.length; i++) {
            P[i] = producciones_dinamicas.get(i);
        }

        // ◂ ◂ ◂ ◂ Generamos arreglo de simbolos no terminales sin duplicados ▸ ▸ ▸ ▸ //
        for (ProduccionGramatical produccion : P) {
            noTerminalesDinamico.add(produccion.N);
        }

        N = generarArregloSinDuplicados(noTerminalesDinamico);

        // ◂ ◂ ◂ ◂ Generamos arreglo de simbolos terminales sin duplicados ▸ ▸ ▸ ▸ //
        for (ProduccionGramatical produccion : P) {
            for (String simboloNuT : produccion.NuT) {

                // ◂ ◂ ◂ ◂ Verificamos que el simbolo NuT sea terminal ▸ ▸ ▸ ▸ //
                if (!noTerminalesDinamico.contains(simboloNuT) && !simboloNuT.isEmpty()) {
                    terminalesDinamico.add(simboloNuT);
                }
            }
        }

        T = generarArregloSinDuplicados(terminalesDinamico);

        // ◂ ◂ ◂ ◂ Determinamos cual es el simbolo inicial de la gramatica ▸ ▸ ▸ ▸ //
        boolean hayRepetido;
        for (String simboloNoTerminal : N) {
            hayRepetido = false;
            for (ProduccionGramatical produccion : P) {
                for (String simboloNuT : produccion.NuT) {

                    // ◂ ◂ ◂ ◂ Verificamos que el simbolo NuT sea igual al no terminal ▸ ▸ ▸ ▸ //
                    if (!simboloNuT.equals(simboloNoTerminal)) {
                        S = simboloNoTerminal;
                    } else {
                        S = "";
                        hayRepetido = true;
                        break;
                    }
                }

                // ◂ ◂ ◂ ◂ Si encuentra el simbolo no terminal en la parte derecha de la produccion, no es un simbolo inicial ▸ ▸ ▸ ▸ //
                if (hayRepetido) {
                    break;
                }
            }
        }

        return new Gramatica(N, T, S, P);
    }

    /**
     * Funcion que genera una pila con un archivo de texto plano separado por saltos de linea.
     *
     * @param texto Texto plano.
     * @return Pila de un texto separado por saltos de linea.
     */
    private LinkedList<String> separarPorLineas(String texto) {

        // ◂ ◂ ◂ ◂ Escaneamos el texto ▸ ▸ ▸ ▸ //
        Scanner escaner = new Scanner(texto);
        LinkedList<String> lineas = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Seguira almacenando sin espacios al principio y final hasta que llegue al final del archivo ▸ ▸ ▸ ▸ //
        while (escaner.hasNextLine()) {
            lineas.add(escaner.nextLine().trim());
        }

        // ◂ ◂ ◂ ◂ Cerramos buffer de lectura ▸ ▸ ▸ ▸ //
        escaner.close();
        return lineas;
    }

    /**
     * Funcion que retorna un arreglo de cadenas sin elementos duplicados a partir de otro que si tiene duplicados.
     *
     * @param elementos_duplicados Lista con elementos duplicados.
     * @return Arreglo de cadenas sin duplicados.
     */
    private String[] generarArregloSinDuplicados(LinkedList<String> elementos_duplicados) {
        LinkedList<String> arregloDinamicoSinDuplicados = new LinkedList<>();

        if (elementos_duplicados.isEmpty()) {
            return new String[]{""};
        }

        // ◂ ◂ ◂ ◂ Almacenamos elementos sin duplicar ▸ ▸ ▸ ▸ //
        for (String elemento : elementos_duplicados) {
            if (!arregloDinamicoSinDuplicados.contains(elemento)) {
                arregloDinamicoSinDuplicados.add(elemento);
            }
        }

        // ◂ ◂ ◂ ◂ Generamos arreglo estatico sin duplicados ▸ ▸ ▸ ▸ //
        String[] arregloSinDuplicados = new String[arregloDinamicoSinDuplicados.size()];
        for (int i = 0; i < arregloSinDuplicados.length; i++) {
            arregloSinDuplicados[i] = arregloDinamicoSinDuplicados.get(i);
        }

        return arregloSinDuplicados;
    }

}
