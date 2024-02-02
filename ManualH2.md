# Comandos H2 - Migración

[CSVWRITE](http://www.h2database.com/html/functions.html#csvwrite) Exportar datos de una tabla a .CSV

```sql
-- Path por defecto 'C:\Users\usrgesdoc\Documents\data.csv'
CALL CSVWRITE(PATH, QUERY, CSVOPTIONS)
-- Exportar sin " como delimitador y los datos separados por comas
CALL CSVWRITE('C:\Users\usrgesdoc\Documents\<FILE_NAME>.csv',
                'SELECT * FROM <TABLE_NAME>',
                'charset=UTF-8 fieldSeparator=, fieldDelimiter=');

CALL CSVWRITE('C:\Users\usrgesdoc\Documents\contsan20.csv',
                'SELECT RECID FROM CLARO_MIGRACION.METADATOS',
                'charset=UTF-8 fieldSeparator=, fieldDelimiter=');
/*
    charset - Codificación de caracteres del documento, por defecto es UTF-8
    fieldSeparator - Simbolo para separar los datos, por defecto es <<,>>
    fieldDelimiter - Simbolo para delimitar el fin de un dato, por defecto es <<">>.
*/

CALL CSVWRITE('C:\Users\usrgesdoc\Documents\fallidos-sactodos.csv',
                'SELECT RECID,XD_NUMERO_RADICADO,XD_EMPRESA,XD_NOMBRE_PROVEEDOR_ENTIDAD,XD_SERIE_DOCUMENTAL FROM CLARO_MIGRACION.METADATOS WHERE ESTADO_ULTIMO_PROCESO = ''FALLIDO''',
                'charset=UTF-8 fieldSeparator=, fieldDelimiter=');

```

[CSVREAD](https://www.h2database.com/html/tutorial.html?highlight=CSVREAD&search=csvread#firstFound) Leer datos de un archivo CSV

```sql
-- Path por defecto 'C:\Users\usrgesdoc\Documents\data.csv'
-- Ver los datos en el fichero
SELECT * FROM CSVREAD('<PATH>', 'COL_1,COL_N', CSVOPTIONS);
/*
    Crear una base de datos a partir de un fichero CSV
    Todas las columnas serán de tipo VARCHAR
*/
CREATE TABLE SCHEMA.TEST AS SELECT * FROM CSVREAD('test.csv');
-- Personalizar el tipo de datos de las columnas
CREATE TABLE SCHEMA.TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))
    AS SELECT * FROM CSVREAD('test.csv');
-- Par[a]metros personalizados. El segundo par[a]metro es nulo si el nombre de las columnas est[a] en el fichero.
SELECT * FROM CSVREAD('C:\Users\usrgesdoc\Documents\test.csv',
                        null,
                        'charset=UTF-8  fieldSeparator=, fieldDelimiter=');

```

[SCRIPT](https://www.h2database.com/html/commands.html#script) Generar el script SQL de TODA la base de datos

```sql
-- Path por defecto 'C:\Users\usrgesdoc\Documents\script.sql' también soporta .csv
/*
    NODATA - Solo la estructura de la base de datos, sin los datos
    SIMPLE - No optimiza la inserción de filas
    COLUMNS - Exportar las columnas seleccionadas
*/
-- Obtener el script como un result set. Es mejor dejarlo vacío
SCRIPT <NODATA | SIMPLE | COLUMNS>
-- Para exportarlo a un archivo
SCRIPT <NODATA | SIMPLE | COLUMNS> TO '<PATH>'
-- Para exportar solo una tabla en especifico
SCRIPT TABLE <SCHEMA_NAME | TABLE_NAME>
SCRIPT TABLE <SCHEMA_NAME | TABLE_NAME> TO '<PATH>'
-- Ejemplo
SCRIPT TO 'C:\Users\usrgesdoc\Documents\script.sql'
```

[RUNSCRIPT](https://www.h2database.com/html/commands.html#runscript) Ejecutar el script previamente generado

```sql
-- Path por defecto 'C:\Users\usrgesdoc\Documents\script.sql'
RUNSCRIPT FROM '<PATH>'
-- Ejemplo
RUNSCRIPT FROM 'C:\Users\usrgesdoc\Documents\script.sql'
```
