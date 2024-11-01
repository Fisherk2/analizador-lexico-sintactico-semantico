import intermedio.Cuarteto;
import lexico.Token;
import semantico.Sentencia;
import semantico.Simbolo;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Clase que genera codigo intermedio del analizador Lexico-Sintactico-Semantico.
 */
public class CodigoIntermedio {
    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    private final LinkedList<Cuarteto> TABLA_DE_CUARTETOS;
    private final Analizador ANALIZADOR;

    /**
     * Clase que genera codigo intermedio del analizador Lexico-Sintactico-Semantico.
     *
     * @param analizador Analizador Lexico-Sintactico-Semantico
     */
    public CodigoIntermedio(Analizador analizador) {
        ANALIZADOR = analizador;
        TABLA_DE_CUARTETOS = new LinkedList<>();

        generarCuartetosPosfija();
    }


    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ CODIGO INTERMEDIO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE CUARTETOS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Cuarteto cuarteto : TABLA_DE_CUARTETOS) {
            resultado += cuarteto + "\n";
        }

        return resultado;
    }

    /**
     * Metodo que genera los cuartetos del codigo fuente en base a la tabla de simbolos y de notaciones posfijas.
     */
    private void generarCuartetosPosfija() {

        int nTemps = 1;
        int tipoDatoTemp;
        String tempName = "temp" + nTemps;

        Simbolo simboloNulo = new Simbolo(new Token(""));

        Stack<Simbolo> pilaOperandos = new Stack<>();
        Simbolo operandoA;
        Simbolo operandoB;
        Simbolo resultado;

        for (Sentencia instruccion : ANALIZADOR.SEMANTICO.getTABLA_DE_NOTACIONES()) {

            if (ANALIZADOR.SEMANTICO.esDeclaracion(instruccion.TYPE_STATEMENT)) {

                // ◂ ◂ ◂ ◂ Generamos un cuarteto por cada variable declarada ▸ ▸ ▸ ▸ //
                for (Token operando : instruccion.getOperandos(true)) {
                    TABLA_DE_CUARTETOS.add(new Cuarteto(instruccion.getInstruccion(true), ANALIZADOR.SEMANTICO.getSimbolo(operando), simboloNulo, simboloNulo));
                }
            }

            if (ANALIZADOR.SEMANTICO.esAsignacion(instruccion.TYPE_STATEMENT)) {
                for (Token token : instruccion.POSFIJA) {

                    // ◂ ◂ ◂ ◂ ¿Es constante o variable? ▸ ▸ ▸ ▸ //
                    if (ANALIZADOR.LEXICO.esOperando(token.LEXEMA)) {
                        pilaOperandos.push(ANALIZADOR.SEMANTICO.getSimbolo(token));

                    } else if (ANALIZADOR.LEXICO.esOperador(token.LEXEMA)) {

                        // ◂ ◂ ◂ ◂ Generamos el tipo de dato entre el operando A y B ▸ ▸ ▸ ▸ //
                        operandoB = pilaOperandos.pop(); //Temps
                        operandoA = pilaOperandos.pop();
                        tipoDatoTemp = ANALIZADOR.obtenerResultado(operandoA.getATRIBUTO(), operandoB.getATRIBUTO(), token);

                        // ◂ ◂ ◂ ◂ Creamos otro cuarteto por cada operador encontrado ▸ ▸ ▸ ▸ //
                        resultado = new Simbolo(new Token(tempName,token.CLASIFICACION_LEXICA, token.ATRIBUTO, token.LINEA_DE_CODIGO));
                        resultado.setTipoDeDato(tipoDatoTemp);

                        if (ANALIZADOR.LEXICO.esAsignador(token.LEXEMA)) {

                            // ◂ ◂ ◂ ◂ El resultado sera el ultimo operando de la pila ▸ ▸ ▸ ▸ //
                            TABLA_DE_CUARTETOS.add(new Cuarteto(token,operandoB,simboloNulo,operandoA));

                        }else{
                            // ◂ ◂ ◂ ◂ El resultado sera el operando temporal ▸ ▸ ▸ ▸ //
                            TABLA_DE_CUARTETOS.add(new Cuarteto(token,operandoA,operandoB,resultado));

                        }

                        // ◂ ◂ ◂ ◂ Seguimos procesando la operacion ▸ ▸ ▸ ▸ //
                        pilaOperandos.push(resultado);
                        nTemps++;
                    }
                }
            }

            if(ANALIZADOR.SEMANTICO.esEntradaSalidaConsola(instruccion.TYPE_STATEMENT)){

                // ◂ ◂ ◂ ◂ Generamos un cuarteto por cada variable ▸ ▸ ▸ ▸ //
                for (Token operando : instruccion.getOperandos(true)) {
                    TABLA_DE_CUARTETOS.add(new Cuarteto(instruccion.getInstruccion(true), ANALIZADOR.SEMANTICO.getSimbolo(operando), simboloNulo, simboloNulo));
                }
            }

            // ◂ ◂ ◂ ◂ TODO: Crear condiciones para las demas tipos de sentencias ▸ ▸ ▸ ▸ //

        }
    }
}
