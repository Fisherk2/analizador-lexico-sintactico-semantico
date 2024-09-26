package semantico;


import lenguaje.Operadores;
import lenguaje.Procedencia;
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
    public final Operadores OPERADORES;
    public final Procedencia VERIFICADOR;

    /**
     * Clase que almacena las propiedades de un analizador semantico.
     *
     * @param signos_operacionales   Simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
     *                               de una sentencia que pertenezcan al lenguaje.
     * @param verificador_sentencias Objeto que almacena la de verificacion de datos, sentencias y produccion inicial de la gramatica.
     */
    public Semantic(Operadores signos_operacionales, Procedencia verificador_sentencias) {
        TABLA_DE_SIMBOLOS = new LinkedHashSet<>();
        TABLA_DE_NOTACIONES = new LinkedList<>();
        this.OPERADORES = signos_operacionales;
        this.VERIFICADOR = verificador_sentencias;
    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ SEMANTICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

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
     * Metodo que almacena sentencias infijas a la tabla de notaciones.
     *
     * @param notacion_infija Sentencia infija.
     */
    public void almacenarNotacion(LinkedList<Token> notacion_infija) {
        TABLA_DE_NOTACIONES.add(new Sentencia(notacion_infija.toArray(new Token[0])));
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
        for (int id : VERIFICADOR.SENTENCES) {
            if (id == predict_x_a) {
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el indice del predict gramatical coincide con el id que establece el inicio del programa de la gramatica.
     *
     * @param predict_x_a Indice del Predict[x,a] de la gramatica.
     * @return ¿Es la produccion que inicia el programa?
     */
    public boolean esInicio(int predict_x_a) {
        return VERIFICADOR.INIT == predict_x_a;
    }

    /**
     * Funcion que verifica que el token sea un operador.
     * @param atributo Atributo de su clasificacion lexica del token.
     * @return ¿Es un operador?
     */
    public boolean esOperador(int atributo) {
        return OPERADORES.esSignoAritmetico(atributo)
                || OPERADORES.esSignoAsignacion(atributo)
                || OPERADORES.esSignoComparacion(atributo)
                || OPERADORES.esSignoLogico(atributo);
    }

}
