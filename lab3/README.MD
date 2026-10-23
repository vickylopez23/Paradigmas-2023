## Laboratorio 3: Programación distribuida en Spark (Parte 2)
- Victoria Lopez
- Franco Molina
- Tomás Ponce Gessi


## Eleccion 

Basandonos en las tres versiones de las soluciones individuales de la primera parte del laboratorio, elegimos la que para nosotros era la mas compacta,  si bien todas son muy similares elegimos la que se basaba en ChatGPT en Español (correspondiente a Franco) y fue la que usamos para implementar la segunda parte del laboratorio.

En cuanto a las consultas a la IA, estas no fueron necesarias. Por un lado para el Makefile utilizamos la linea de comando que el IDE que utilizamos usaba para ejecutar el proyecto, donde colocamos las dependencias en la carpeta lib del proyecto y por eso pesa 250MB el lab, pero es por comodidad :D

Para la implementación tampoco fueron necesarias consultas a ChatGPT, particularmente porque las funciones que utilizamos son practicamente descriptivas por su nombre y el IDE extrae de las dependencias las funciones disponibles para cada clase. Digamos que la programación fue asisitada por el IDE mas que por ChatGPT.

## Implementación 

Para recuperar los documentos que contengan la palabra Google lo que hicimos fue un índice invertido de entidad nombrada a articulo para el cual posteriormente buscamos aquellos articulos cuya entidad nombrada se corresponda con la entidad nombrada que se quiere buscar (la cual solicitamos por el Standard Input)

Con dichos articulos, los ordenamos por la frecuencia de la entidad nombrada buscada e imprimos por pantalla los titulos de dichos articulos junto a las ocurrencias de la entidad.
Cabe aclarar que todos los calculos pesados fueron realizados sobre los RDD de Spark, lo cual hace que el programa escale horizontalmente a otras maquinas gracias a la capacidad de distribución de calculo de Spark.

__Aclaración:__ Algo que lamentablemente no pudimos resolver (y eso que probamos varias alternativas) es lograr que no se muestren los logs de ejecucion de Spark, setear el LogLevel del SparkContext y de Log4J en ERROR por ejemplo no funciono y se siguen mostrando los logs Info en adelante.

## Conclusiones generales:

Principalmente, Spark esta muy bueno, con un poco de analisis del problema y pensando la estructura de lo que se quiere obtener es muy sencillo distribuir el calculo complejo y aumentar la velocidad de procesamiento por grandes magnitudes. De esto quedo bastante claro cuando hicimos el indice invertido, dado que si bien no hay tantas entidades nombradas en nuestro laboratorio se aprecia el hecho de que si hubiesen del orden de millones entonces distribuir el calculo hace que se pueda resolver el problema en un tiempo bastante razonable.

 