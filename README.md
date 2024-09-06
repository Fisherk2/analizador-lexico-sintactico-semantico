
# Analizador de codigo lexico - sintactico - semantico (PROXIMO A IMPLEMENTAR).

## Descripción

Programa que analiza lexica, sintacticamente y semanticamente un archivo de texto que contenga un codigo que perteneciente a un lenguaje.

## Uso

El codigo a analizar debe estar en un archivo de texto plano, este se encuentra en el fichero `programa.txt` ubicada en la carpeta `src/archivos`.

El analizador funciona dependiendo del lenguaje en que debe ser escrito el codigo, este lenguaje esta conformado por:

- El automata que acepta las palabras del lenguaje, este AFN se encuentra configurado en un fichero JSON ubicado en `src/archivos/automata.json`.

- La tabla de clasificacion lexica, igualmente configurable en un archivo JSON ubicada en `src/archivos/tabla_clasificacion_lexica.json`.

- Por ultimo la gramatica, hay dos formas distintas de configurarlo, en este caso esta en la forma de JSON ubicado en la misma ruta que las anteriores `src/archivos/gramatica.json`.

**(NO RECOMENDABLE, OMITE ESTE PASO YA QUE TIENDE A PROVOCAR ERRORES)**
Pero existe otra manera de configurarla, es por medio del texto plano igualmente alojado en `src/archivos/gramaticaDeAliexpress.txt`.

Esta ultima forma de configurar se hace de la siguiente manera: `N.- No terminal -> Conjunto NuT`
Donde:
- `N` es el numero de produccion.
- `No terminal` es simbolo no terminal que representa la produccion.
- `Conjunto NuT` es el conjunto de simbolos terminales y no terminales de la producion.
- El numero de producion debe separarse con `.-`
- Las producciones deben separarse de su derivacion, ya sea con `->` o `→`.
- Cada produccion se estara leyendo por salto de linea, como en la gramatica de ejemplo.
- Los simbolos no terminales y terminales solo deben contener `_` como se muestra en el fichero `gramaticaDeAliexpress.txt` 

En cualquier caso, las dos maneras de establecer la gramatica ya estan listas para configurar, solo es cuestion de descomentar y comentar el estilo de gramatica que desea utilizar, este se implementa cada que se instancia un nuevo objeto de tipo Syntax como se ve en este ejemplo:
```
Syntax sintactico = new Syntax(
                (Gramatica) new Fichero("gramatica.json").deserializar_json(Gramatica.class)
                //new Fichero("gramaticaDeAliexpress.txt").deserializar_gramatica()
        );
```
Una vez configurando estos ficheros, el analizador debe funcionar apropiadamente si el lenguaje el cual el codigo pertenece, esta correctamente establecido.

### NOTA IMPORTANTE DE LA GRAMATICA
El programa se puede ciclar en caso de que la gramatica no se establezca correctamente, esto sucede cuando un simbolo del lado derecho la produccion no se encuentra del lado izquierdo de las producciones.

## Resultados

El programa lanzara en consola paso por paso de como el analizador va evaluando cada token del codigo, se detiene una vez que encuentra un error sintactico o termine de leer todo el fichero.
