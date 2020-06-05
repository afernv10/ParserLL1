Autor: Ander J. Fernández Vega
Practica: Practica 4 - Analizador Sintáctico Descendente Recursivo

Un fichero de entrada de ejemplo está en la carpeta raiz del proyecto.
Ahora mismo en la carpeta del proyecto está creado el .java del .jflex: Lexer.java

Para ejecutar el ejercicio (obteniendo el estado actual) he seguido estos pasos:
	1. Con el .jflex creo una configuración de ejecución.
		Run>External Tools>External tools Configurations...
	2. En Location pongo la ruta al .bat de jflex (C:\Users\ander\jflex-1.8.1\bin\jflex.bat)
	3. En Working Directory selecciono del workspace el package del ejercicio (lexLL1).
	4. Como argumento pongo el nombre del archivo jflex (analizador_lexico.jflex)
	(5. Le doy a la opción de Refresh para que se actualice el proyecto al Dar a Run)
	6. Hecho esto creo la configuración para el Parser.java que es desde donde ejecutaremos el programa.
		Run>Run Configurations...
	7. Creo una config para el parser, seleccionando el proyecto JFlexPL4
	8. En cada configuración selecciono del workspace la Main class lexLL1.Parser
	9. Como argumento de entrada especifico el .txt del ejercicio (entrada.txt)
	10. Ejecutamos normalmente en el Play seleccionando la config del ejercicio. 
		Podemos probar a cambiar los textos de la entrada.
		
====
Más información: En la limpieza de la gramática, eliminación de recursividad, etc.
Una vez tuve tuve la cual iba a utilizar, añadí una producción de S (entrada) para el tratamiento del
final statement (el punto y coma ";")
Comenzando así por S --> E; y a partir de E se desarrolla la gramática obtenida.

Otra nota: EN cuanto a la espera de un token determinado, no hay ningún caso en el que se sepa con
seguridad cúal vendrá, hay varios posibles, en ese caso se imprime simplemente que se esperaba otro.