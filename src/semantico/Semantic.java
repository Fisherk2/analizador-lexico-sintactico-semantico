package semantico;


import lexico.Token;

import java.util.LinkedHashSet;

/**
 * Clase que almacena las propiedades de un analizador semantico.
 */
public class Semantic {


    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    private final LinkedHashSet<Simbolo> TABLA_DE_SIMBOLOS;

    //TODO: Contructor recibe tabla de precedencia para arboles de expresion.

    /**
     * Clase que almacena las propiedades de un analizador semantico.
     */
    public Semantic() {
        TABLA_DE_SIMBOLOS = new LinkedHashSet<>();
    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ SEMANTICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE SIMBOLOS COMPLETA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
            resultado += simbolo + "\n";
        }

        return resultado;
    }

    /**
     * Metodo que almacena Tokens en la tabla de simbolos por primera vez y actualiza numeros de linea.
     *
     * @param token Token del analizador lexico.
     */
    public void almacenarSimbolo(Token token) {

        // ◂ ◂ ◂ ◂ ¿Existe en la tabla de simbolos? ▸ ▸ ▸ ▸ //
        if (getSimbolo(token) == null) {
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
        if (getSimbolo(token) == null) {
            TABLA_DE_SIMBOLOS.add(new Simbolo(token,valor));
        } else {
            for (Simbolo simbolo : TABLA_DE_SIMBOLOS) {
                if (simbolo.getLEXEMA().equals(token.LEXEMA)) {
                    simbolo.setLineas(token.LINEA_DE_CODIGO);
                    break;
                }
            }
        }
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS & SETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que devuelve la tabla de simbolos del analizador lexico.
     *
     * @return Tabla de simbolos actual.
     */
    public LinkedHashSet<Simbolo> getTABLA_DE_SIMBOLOS() {
        return TABLA_DE_SIMBOLOS;
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

}
