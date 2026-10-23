# Laboratorio 1: Laboratorio de programación funcional


## Nombres de los integrantes 
- Tomas Ponce Gessi
- Franco Molina 
- Victoria Lopez

## ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo.


Que las funcionalidades esten separadas en los modulos, hace que sea mas entendible y legible ver de donde trabaja cada modulo, ademas es una manera de dividir un problema grande, en problemas mas chicos.
Las funcionalidades estan separadas en modulos porque es una manera de dividir un problema grande, en problemas mas chicos y poder ir testeando cada uno de estos modulos por separado, ademas facilita la organizacion y representacion mental del programa.
Responsabilidades de cada modulo:
- Dibujo.hs: Se hace la declaración de las figuras de nuestro lenguaje, donde definimos los constructores de Dibujo y funciones que utilizamos con ellas. Basicamente construimos nuestro propio DSL y exportamos funciones para manipularlo.
- Interp.hs:  Interpretación geométrica de las figuras. Se encarga de la semantica de nuestro lenguaje
- Pred.hs: Funciones de predicados sobre el lenguaje que definimos. Basicamente utilidades.
- Escher.hs: Aca es donde construimos el Dibujo de Escher, basicamente utilizamos el DSL que construimos para especificar una semantica que va a interpretarse enventualmente como el dibujo de Escher.
- Main.hs: Programa principal que nos permite realizar llamados a otros modulos y configurarlos para realizar dibujos mediante Gloss



## ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez es un parámetro del tipo?

La razón por la que las figuras básicas no están incluidas en la definición del lenguaje, y en cambio se pasan como un parámetro del tipo, es para permitir la flexibilidad y la extensibilidad del lenguaje. Si las figuras básicas fueran parte de la definición del lenguaje, el lenguaje estaría limitado a un conjunto fijo de formas. Sin embargo, al permitir que las figuras básicas sean especificadas por el usuario, el lenguaje se vuelve más general y puede adaptarse a diferentes necesidades. En simples palabras, al utilizar parametros de tipo lo que estamos haciendo es definir un lenguaje que opera sobre "figuras básicas", donde dichas figuras basicas son definidas por el usuario del lenguaje a traves del parametro de tipo.



## ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?


Utilizar una función de fold puede tener ventajas en términos de modularidad, abstracción y claridad, dependiendo del contexto en el que se esté trabajando.

- Modularidad porque al utilizar fold, se pueden separar las operaciones de recorrer la estructura y aplicar una operación sobre cada elemento. Esto facilita la creación de módulos

- Abstraccion porque el uso de fold permite abstraer el proceso de recorrer una estructura de datos y realizar una operación en cada elemento, lo que hace que el código sea más general y reutilizable. En cambio, el pattern-matching directo está más enfocado en una operación específica sobre una estructura de datos particula

- Claridad porque en algunos casos, el uso de fold puede hacer que el código sea más claro y fácil de entender, ya que permite separar la lógica de recorrer la estructura de datos de la lógica de la operación sobre cada elemento, lo que puede hacer que el código sea más fácil de leer y mantener.



## Comentarios generales sobre la experiencia haciendo el laboratorio:


Particularmente el resultado del laboratorio estuvo muy bueno, una vez que el dibujo de Escher quedó formado se apreció mucho y se logro entender el laboratorio en si. Lamentablemente lo bueno radicó en el "resultado" dado que particularmente el desarollo en si del laboratorio fue, a nuestro criterio, innecesariamente dificil.
La dificultad que hablamos no estuvo en Haskell en si, ni en el paradigma funcional, sino en el hecho de que la division de los componentes y como se supone que debian conectarse en si no quedo para nada clara a medida que se fue desarollando el laboratorio, es como que implementamos un programa que recien al implementarlo supimos en grandes rasgos su especificacion, no antes, lo cual claramente no tiene sentido. De hecho, creemos que si hubieran dado algunos ejemplos de como funciona la interconexion de los distintos modulos (Dibujo, Interp y Escher) hubiera servido muchisimo, o en su defecto escribir la especificacion de cada uno, la razon de su existencia en el programa y como esta pensado que se interconecte con el resto de modulos.
Puesto en otras palabras, al final del laboratorio obtuvimos la idea de la especificacion de cada modulo porque justamente lo implementamos, lo cual no creemos que se deba programar asi. A su vez, hubo muchas confusiones relacionadas a lo que es una "figura basica", esto lo notamos incluso entre algunos profesores que no sabian como era realmente la especificacion de Dibujo.hs, generando mucha mas confusion de la que ya habia.
De cualquier modo, esperamos que estos comentarios sirvan para mejorar el laboratorio, para focalizar el aprendizaje en el paradigma funcional que es el objetivo. 