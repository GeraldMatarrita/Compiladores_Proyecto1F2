# Proyecto_Compiladores
Estudiantes:
Jeaustin Obando 2020067573
Gerald Matarrita 2020144486
Emmanuel López 2018077125

Analizador léxico, sintáctico y semántico

Este analizador léxico, sintáctico y semántico se puede ejecutar por medio del .JAR en la carpeta
comprimida de este proyecto.
(Se necesita tener instalado Java 18 o superior, dado que el programa está compilador en esta versión).

Para ser ejecutado es necesario descomprimir el .zip en una carpeta a gusto, 
después abrir esa carpeta en el terminal o CMD y ejecutar el siguiente comando:

-- java -jar miCompilador.jar input.txt output.txt

Después de ejecutar el jar debe de salir este mensaje (En caso de éxito):

-- Input is valid.

Si se encuentra algún error en el archivo input.txt se mostrará un mensaje de error y se creará un archivo
llamado error.txt con el(los) error(es) encontrado(s).
Si hay uno o más errores léxicos debe de salir el mensaje:

-- "Error [Fase Léxica]: El archivo contiene errores léxicos, revisa el archivo error.txt"

Si no hay errores léxicos, pero hay un error sintáctico debe de salir el mensaje:
-- "Error [Fase Sintáctica]: El archivo contiene errores sintácticos. Revisa el archivo error.txt"

Si no hay errores léxicos ni sintácticos, pero hay un error semántico debe de salir el mensaje:
-- "Error [Fase Semántica]: El archivo contiene errores semánticos. Revisa el archivo error.txt"

Por último, revise los archivos output.txt, tablaDeSimbolos.txt y error.txt en la carpeta para verificar
si la lectura del archivo input es correcta.


Si desea ver el código en IntelliJ puede seleccionar la opción de abrir proyecto y seleccionar la carpeta llamada
miCompilador y dirigirse a las carpetas src para ver las diferentes clases.

