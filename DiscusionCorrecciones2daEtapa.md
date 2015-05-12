# Discución sobre las correcciones de las 2da Etapa #
Podemos discutir acá lo que nos corrigió Milagros.

_Milagros_

**Nacho**

_**Milton**_



> _El estado deberían especificarlo en término de estructuras de datos._

**Estado es una Clase. Una clase no sería una estructura de datos? Pregunto porque quizá se refiere a otra cosa**

> ~~_En el estado inicial el pacman puede tener una energía menor o igual a 50._~~

**Corregido**

> ~~_Los operadores de búsquedas no están definidos solo aparece el nombre. Estos deben representar transiciones de estado. Que significa cada uno de estos operadores??_~~

**Corregido** Revisar por favor.
_**Revisado**_

**Tenemos que discutir esto y ver si hacemos una máquina de estados de última. Igual no se si entendió la parte de los operadores. Está sólo el nombre (que es auto descriptivo), pero nos falta explayarlos (se escribe así) y agregar sus precondiciones**
_**Creo que así esta bien (revisión 68 del código). Lo que sí en el draft define las precondiciones y resultados de un operador con pseudocódigo.**_

> ~~_Las acciones de búsqueda son operadores ATÓMICOS. Representan transición de estado. La forma de representarlo es: si  la precondición se cumple en el estado  entonces el nuevo estado es este._~~

**Corregido**

> ~~_La búsqueda es una prueba que hace el agente para ver que tan bien le va aplicando acciones en el mundo que conoce en ese momento.
> La prueba de meta debe evaluar un estado y su estructura es:
> Si este estado cumple estas condiciones entonces  llegué a la meta (retorno true)_~~

**Corregido** ~~**Bien, esto tenemos que corregirlo en la DOC del TP, porque ya lo charlamos y lo entendimos bien ambos (creo) en la clase esa**~~

> _Ud. definen esto:_
```
Si visionAmbiente.ConocimientoTotal entonces
convienePelear = energiaPromedioPelea < energiaActual
convieneMoverse = energiaPromedioMover < energiaActual
```
> ~~_¿qué significa? El agente mientras desarrolla la búsqueda no percibe nada._~~

**Corregida la prueba de meta. Ahora es más entendible. Deberiamos dejar la aclaracion que el agente no percibe nada, pero podemos simular que percibe, como deciamos aplicandole una ganancia o perdida de energia. Quizá convenga pensar esto y ver si no es parte de la función heurística**
_**Si, es verdad. Lo vemos hoy en la clase.**_

> ~~_Conoce una porción del mundo y con eso se tiene que arreglar para tomar la decisión._~~

**Resuelto** ~~**Entonces primero tendría que cumplir el objetivo "Descubrir mapa"?**~~

> _Esta parte de la prueba:_
```
Si ( (No visionAmbiente.HayAlimentosSinComer) OR
   (visionAmbiente.HayAlimentosSinComer AND No convieneMoverse) )
   AND ( (No visionAmbiente.HayEnemigos) OR
   (visionAmbiente.HayEnemigos AND No convienePelear) )
   retornar EXITO
Sino
   Si energiaInsuficienteParaMover retornar EXITO
   retornar FRACASO
```

> ~~_¿qué pasaría si en el estado inicial el agente percibe que no hay nada a su alrededor? Entonces la prueba de meta daría éxito y ninguna acción sería propuesta por el agente pacman al simulador del entorno, es decir se queda indefinidamente en la posición inicial._~~

**nueva prueba de meta**
```
Si ConoceTodoElAmbiente entonces
  ConvienePelear =  energiaActual > energiaPromedioPelea
  ConvieneMoverse = energiaActual > energiaPromedioMover
  Si (ConvieneMoverse AND (HayAlimentosSinComer OR 
                          (HayEnemigos AND ConvienePelear) 
     )  
     retornar FRACASO
  Sino
     retornar EXITO
Sino
  Si convieneMoverse entonces
     retornar FRACASO  // Se da por vencido. Asume éxito en su objetivo, porque quiere sobrevivir!
  Sino
     retornar EXITO
```
**Corregido. Bah, no hacia falta corregir. En caso de que no perciba nada la secuencia de "verdades" sería:
```
ConoceTodoElAmbiente -> NO
entonces
convieneMoverse -> veamos: energiaActual > (EnergiaPromedioMoverse = 0) SI
entonces retonrar FRACASO
Por lo tanto sigue buscando. No se traba.
```**

_**Milagros no entendió la prueba de meta, creo. Hoy se lo hacemos entender (que feo que suena... tiene olor a violencia física) o nos explica que estamos totalmente equivocados con nuestros pensamientos :)**_

> _Ojo cómo plantean el estado, los operadores y la prueba de meta, estas tres cosas deben ser coherentes.
> Vuelvan a mandar el práctico modificado antes de implementarlo._