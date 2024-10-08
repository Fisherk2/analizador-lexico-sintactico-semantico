package semantico;


import lenguaje.Notaciones;
import lenguaje.Verificador;
import lexico.Token;

import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Clase que almacena las propiedades de un analizador semantico.
 */
public class Semantic {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    private final LinkedHashSet<Simbolo> TABLA_DE_SIMBOLOS;
    private final LinkedList<Sentencia> TABLA_DE_NOTACIONES;
    private final Notaciones VERIFICADOR_NOTACIONES;
    private final Verificador VERIFICADOR_DATOS;

    /**
     * Clase que almacena las propiedades de un analizador semantico.
     *
     * @param verificador_tipos_de_datos Verificador de datos del lenguaje que establece que tipo de datos son operacionalmente compatibles entre si.
     * @param verificador_notaciones     Verificador de IDs de la gramatica que pertenecen a las instrucciones o sentencias del programa.
     */
    public Semantic(Verificador verificador_tipos_de_datos, Notaciones verificador_notaciones) {
        TABLA_DE_SIMBOLOS = new LinkedHashSet<>();
        TABLA_DE_NOTACIONES = new LinkedList<>();
        VERIFICADOR_DATOS = verificador_tipos_de_datos;
        VERIFICADOR_NOTACIONES = verificador_notaciones;
    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ SEMANTICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += VERIFICADOR_DATOS;

        resultado += VERIFICADOR_NOTACIONES;

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE SIMBOLOS COMPLETA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
            resultado += simbolo + "\n";
        }

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE NOTACIONES ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Sentencia notacion : TABLA_DE_NOTACIONES) {
            resultado += notacion + "\n";
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS & SETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que almacena Tokens en la tabla de simbolos por primera vez y actualiza numeros de linea.
     *
     * @param token Token del analizador lexico.
     */
    public void almacenarSimbolo(Token token) {

        // ◂ ◂ ◂ ◂ ¿Existe en la tabla de simbolos? ▸ ▸ ▸ ▸ //
        if (!contieneTablaSimbolos(token.LEXEMA)) {
            TABLA_DE_SIMBOLOS.add(new Simbolo(token));
        } else {
            for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
                if (simbolo.getLEXEMA().equals(token.LEXEMA)) {
                    simbolo.setLineas(token.LINEA_DE_CODIGO);
                    break;
                }
            }
        }
    }

    /**
     * Metodo que almacena Tokens en la tabla de simbolos por primera vez y actualiza numeros de linea.
     *
     * @param token Token del analizador lexico.
     * @param valor Valor que almacena ese simbolo.
     */
    public void almacenarSimbolo(Token token, String valor) {

        // ◂ ◂ ◂ ◂ ¿Existe en la tabla de simbolos? ▸ ▸ ▸ ▸ //
        if (!contieneTablaSimbolos(token.LEXEMA)) {
            TABLA_DE_SIMBOLOS.add(new Simbolo(token, valor));
        } else {
            for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
                if (simbolo.getLEXEMA().equals(token.LEXEMA)) {
                    simbolo.setLineas(token.LINEA_DE_CODIGO);
                    break;
                }
            }
        }
    }

    /**
     * Metodo que actualiza el tipo de dato de la variable en la tabla de simbolos.
     *
     * @param atributo_variable ID de la variable que se encuentra en la tabla de simbolos
     * @param tipo_dato         Tipo de dato que sera asignado a esa variable.
     */
    public void actualizarTipo(int atributo_variable, int tipo_dato) {
        for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
            if (simbolo.getATRIBUTO() == atributo_variable) {
                simbolo.setTipoDeDato(tipo_dato);
                break;
            }
        }
    }

    /**
     * Metodo que almacena sentencias a la tabla de notaciones.
     *
     * @param notacion Sentencia o Instruccion del programa.
     */
    public void almacenarNotacion(Sentencia notacion) {
        TABLA_DE_NOTACIONES.add(notacion);
    }

    /**
     * Metodo que devuelve la tabla de simbolos del analizador semantico.
     *
     * @return Tabla de simbolos actual.
     */
    public LinkedHashSet<Simbolo> getTABLA_DE_SIMBOLOS() {
        return TABLA_DE_SIMBOLOS;
    }

    /**
     * Metodo que devuelve la tabla de notaciones del analizador semantico.
     *
     * @return Tabla de notaciones actual.
     */
    public LinkedList<Sentencia> getTABLA_DE_NOTACIONES() {
        return TABLA_DE_NOTACIONES;
    }

    /**
     * Funcion que busca y devuelve un simbolo existente de la tabla de simbolos.
     *
     * @param token_simbolo Token que puede estar en la tabla de simbolos
     * @return Simbolo de su respectiva tabla, Null = no existe en la tabla.
     */
    public Simbolo getSimbolo(Token token_simbolo) {
        for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
            if (simbolo.getLEXEMA().equals(token_simbolo.LEXEMA)) {
                return simbolo;
            }
        }
        return null;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACIONES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si el lexema se encuentra en la tabla de simbolos.
     *
     * @param lexema Nombre propio de un token que puede esta almacenado en la tabla de simbolos
     * @return ¿Existe en la tabla de simbolos?
     */
    public boolean contieneTablaSimbolos(String lexema) {
        for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
            if (simbolo.getLEXEMA().equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el indice del predict gramatical coincide con el id que establecen las sentencias de la gramatica.
     *
     * @param predict_x_a Indice del Predict[x,a] de la gramatica.
     * @return ¿Es una sentencia o instruccion?
     */
    public boolean esSentencia(int predict_x_a) {
        for (int id : VERIFICADOR_NOTACIONES.getAllTypeStatements()) {
            if (id == predict_x_a) {
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el indice de la gramatica pertenece a una sentencia de declaracion de variables.
     *
     * @param id_gramatica ID de la gramatica.
     * @return ¿Es una sentencia de declaracion?
     */
    public boolean esDeclaracion(int id_gramatica) {
        for (int id : VERIFICADOR_NOTACIONES.DECLARATIONS) {
            return id == id_gramatica;
        }
        return false;
    }

    /**
     * Funcion que verifica si los operandos pueden realizar operaciones entre si.
     *
     * @param operandoA Tipo de dato de la tabla de simbolos de la variante o constante.
     * @param operandoB Tipo de dato de la tabla de simbolos de la variante o constante.
     * @return ¿Es permitido operar con los operandos de entrada?
     */
    public boolean sonTiposCompatibles(int operandoA, int operandoB, Token operador) {

        return sonCompatiblesEnteros(operandoA, operandoB)
                || sonCompatiblesFlotantes(operandoA, operandoB)
                || sonCompatiblesCadenas(operandoA, operandoB, operador);

    }

    /**
     * Funcion que verifica si los operandos pueden realizar operaciones con enteros.
     *
     * @param operandoA Tipo de dato de la tabla de simbolos de la variante o constante.
     * @param operandoB Tipo de dato de la tabla de simbolos de la variante o constante.
     * @return ¿Es permitido operar enteros con los operandos de entrada?
     */
    public boolean sonCompatiblesEnteros(int operandoA, int operandoB) {
        int tipoPermitidoA;
        int tipoPermitidoB;

        // ◂ ◂ ◂ ◂ ¿Son enteros compatibles? ▸ ▸ ▸ ▸ //
        for (int i = 0; i < VERIFICADOR_DATOS.INTEGER.length; i++) {
            tipoPermitidoA = VERIFICADOR_DATOS.INTEGER[i][0];
            tipoPermitidoB = VERIFICADOR_DATOS.INTEGER[i][1];
            if ((tipoPermitidoA == operandoA && tipoPermitidoB == operandoB) || (tipoPermitidoA == operandoB && tipoPermitidoB == operandoA)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion que verifica si los operandos pueden realizar operaciones con flotantes.
     *
     * @param operandoA Tipo de dato de la tabla de simbolos de la variante o constante.
     * @param operandoB Tipo de dato de la tabla de simbolos de la variante o constante.
     * @return ¿Es permitido operar flotantes con los operandos de entrada?
     */
    public boolean sonCompatiblesFlotantes(int operandoA, int operandoB) {
        int tipoPermitidoA;
        int tipoPermitidoB;

        // ◂ ◂ ◂ ◂ ¿Son flotantes compatibles? ▸ ▸ ▸ ▸ //
        for (int i = 0; i < VERIFICADOR_DATOS.FLOAT.length; i++) {
            tipoPermitidoA = VERIFICADOR_DATOS.FLOAT[i][0];
            tipoPermitidoB = VERIFICADOR_DATOS.FLOAT[i][1];
            if ((tipoPermitidoA == operandoA && tipoPermitidoB == operandoB)
                    || (tipoPermitidoA == operandoB && tipoPermitidoB == operandoA)
            ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si los operandos pueden realizar operaciones con cadenas de texto.
     *
     * @param operandoA Tipo de dato de la tabla de simbolos de la variante o constante.
     * @param operandoB Tipo de dato de la tabla de simbolos de la variante o constante.
     * @param operador  Operador aritmetico que realiza dicha operacion entre dos cadenas de texto.
     * @return ¿Es permitido operar cadenas con los operandos de entrada?
     */
    public boolean sonCompatiblesCadenas(int operandoA, int operandoB, Token operador) {
        int tipoPermitidoA;
        int tipoPermitidoB;

        // ◂ ◂ ◂ ◂ TODO: Si no es un operador de adicion, no son compatibles ▸ ▸ ▸ ▸ //

        // ◂ ◂ ◂ ◂ ¿Son cadenas compatibles? ▸ ▸ ▸ ▸ //
        for (int i = 0; i < VERIFICADOR_DATOS.STRING.length; i++) {
            tipoPermitidoA = VERIFICADOR_DATOS.STRING[i][0];
            tipoPermitidoB = VERIFICADOR_DATOS.STRING[i][1];
            if ((tipoPermitidoA == operandoA && tipoPermitidoB == operandoB)
                    || (tipoPermitidoA == operandoB && tipoPermitidoB == operandoA)
            ) {
                return true;
            }
        }
        return false;
    }

}
