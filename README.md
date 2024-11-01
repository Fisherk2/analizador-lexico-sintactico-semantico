
# Analizador de codigo lexico - sintactico - semantico.

## Descripción

Programa que analiza lexica, sintacticamente y semanticamente un archivo de texto que contenga un codigo que perteneciente a un lenguaje.

## Uso

El codigo a analizar debe estar en un archivo de texto plano, este se encuentra en el fichero `programaSimple.txt` ubicada en la carpeta `src/archivos`.

El analizador funciona dependiendo del lenguaje en que debe ser escrito el codigo, este lenguaje esta conformado por:

* El automata que acepta las palabras del lenguaje, este AFN se encuentra configurado en un fichero JSON ubicado en `src/archivos/automata.json`.

* La tabla de clasificacion lexica, igualmente configurable en un archivo JSON ubicada en `src/archivos/tabla_categoria_lexica.json`.

* La gramatica, en este caso esta en la forma de JSON ubicado en la misma ruta que las anteriores `src/archivos/gramatica.json`.
Los simbolos terminales que representan algun tipo de dato, deben ser los mismos que los de la clasificacion lexica. como en siguiente ejemplo:

_Tabla de clasificacion lexica_
```
  "IDENTIFIERS": {"NAME": "Identificadores", "REGEX": "^[a-z](_*([a-z]|[0-9]))*", "ATTRIBUTE": 777}
```
_Gramatica_
```
{
  "N":
  [ <No terminales> ],
  "T":
  [ <Terminales>, "Identificadores", <Terminales> ],
  "S": <Simbolo inicial>,
  "P":
  [
    <Producciones>,
    {"ID": 33,"N": "sentencia", "NUT": ["Identificadores",<Simbolo NuT>]},
    <Producciones>
  ]
}
```

* La identificacion de notaciones/sentencias se puede configurar en el fichero `src/archivos/notaciones.json`, en cada espacio del arreglo debe esta el **ID de la gramatica** que identifica 
todos los tipos de sentencias del programa.

NOTA: Si en tu gramatica, no incluye un tipo de sentencia (ya sea condicionales, ciclos, manejo de excepciones, etc...), **DEBES ESTABLECER EL ARREGLO EN CEROS**

_Tabla de notaciones_
```
{
"LOOPS": [0],
"HANDLER_EXCEPTION": [24,39]
}
```
* El verificador de datos se encuentra configurable en `src/archivos/verificador.json`, debes establece par de enteros de los atributos de la tabla de clasificacion lexica
que consideres deben coincidir para que sean tipo de datos compatibles, para ello, previamente, debes establecer su respectiva clasificacion en el archivo configurable
ubicado en `src/archivos/tabla_clasificacion_lexica.json` como se ven en el siguiente ejemplo:

_Tabla de clasificacion lexica_
```
  "INTEGER_NUMBERS": {"NAME": "Integer", "REGEX": "^[1-9][0-9]*|[0]", "ATTRIBUTE": 777},
```

NOTA: Al igual que el identificador de notaciones, **DEBES ESTABLECER EN CEROS LOS ARREGLOS**
en caso de que no tenga cierto tipo de dato en tu programa.

_Tabla de verificacion de tipos de datos_
```
{
"STRING": [[0,0]],
"INTEGER":
    [
      [777,777],
      [999,777],
    ]
}
```

Una vez configurando estos ficheros, el analizador debe funcionar apropiadamente si el lenguaje el cual el codigo pertenece, esta correctamente establecido.

### CONSIDERACIONES
* El programa se puede ciclar en caso de que la gramatica no se establezca correctamente, esto sucede cuando un simbolo del lado derecho la produccion no se encuentra del lado izquierdo de las producciones.
* Por el momento, el analizador solo funciona con sentencias simples, como declaracion de variables y expresiones aritmeticas.
* No hay manejo de errores, solo se muestra en consola que errores tiene de escritura en el codigo de ejemplo.

## Resultados

El programa lanzara en consola todas las configuraciones realizadas, ademas de la generacion de tabla de simbolos y de notaciones. Se detiene una vez que encuentra un error lexico/sintactico o termine de leer todo el fichero, los errores semanticos se imprimiran en consola una vez finalizado
la ejecucion del analizador.
