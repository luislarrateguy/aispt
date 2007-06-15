:- dynamic percepcion/5,accionEjecutada/2,posicion/3,comida/3,enemigo/3,vacia/3,
energia/2,convieneMoverse/2,convienePelear/2.

%% Funcion probada.
sumarPosicion(P,O,P1):-O=:=(-1),P=:=1,P1 is 4,!.
sumarPosicion(P,O,P1):-O=:=(-1),P=\=1,P1 is P-1,!.
sumarPosicion(P,O,P1):-O=:=1,P=:=4,P1 is 1,!.
sumarPosicion(P,O,P1):-O=:=1,P=\=4,P1 is P+1,!.

%%hace la sumatoria de una lista
%% Funcion probada.
sumatoria([],0):-!.
sumatoria([L|Ls],C):-sumatoria(Ls,T),C is T+L.

%%retorna la cantidad de elementos de una lista
cantidad([],0):-!.
cantidad([_|Ls],C):-cantidad(Ls,T),C is T+1.

%%calcula el promedio de una lista
%% Funcion probada.
promedio(L,P):-cantidad(L,C),C=:=0,P is 0,!.
promedio(L,P):-sumatoria(L,S), cantidad(L,C), P is (S/C).

%% Funcion probada.
posicion(X,Y,1):-percepcion([_,_,_,_],X,Y,_,1),!.

%% PERCEPCIONES
%% infiriendo de las percepciones
percibir(S):-percepcion([enemigo,_,_,_],_,_,_,S),adyacente(X,Y,izquierda,S),asserta(enemigo(X,Y,S):-!).
percibir(S):-percepcion([_,enemigo,_,_],_,_,_,S),adyacente(X,Y,derecha,S),asserta(enemigo(X,Y,S):-!).
percibir(S):-percepcion([_,_,enemigo,_],_,_,_,S),adyacente(X,Y,arriba,S),asserta(enemigo(X,Y,S):-!).
percibir(S):-percepcion([_,_,_,enemigo],_,_,_,S),adyacente(X,Y,abajo,S),asserta(enemigo(X,Y,S):-!).

percibir(S):-percepcion([comida,_,_,_],_,_,_,S),adyacente(X,Y,izquierda,S),asserta(comida(X,Y,S):-!).
percibir(S):-percepcion([_,comida,_,_],_,_,_,S),adyacente(X,Y,derecha,S),asserta(comida(X,Y,S):-!).
percibir(S):-percepcion([_,_,comida,_],_,_,_,S),adyacente(X,Y,arriba,S),asserta(comida(X,Y,S):-!).
percibir(S):-percepcion([_,_,_,comida],_,_,_,S),adyacente(X,Y,abajo,S),asserta(comida(X,Y,S):-!).

percibir(S):-percepcion([vacia,_,_,_],_,_,_,S),adyacente(X,Y,izquierda,S),asserta(vacia(X,Y,S):-!).
percibir(S):-percepcion([_,vacia,_,_],_,_,_,S),adyacente(X,Y,derecha,S),asserta(vacia(X,Y,S):-!).
percibir(S):-percepcion([_,_,vacia,_],_,_,_,S),adyacente(X,Y,arriba,S),asserta(vacia(X,Y,S):-!).
percibir(S):-percepcion([_,_,_,vacia],_,_,_,S),adyacente(X,Y,abajo,S),asserta(vacia(X,Y,S):-!).


%% Funcion probada.
percibir(S):-percepcion([_,_,_,_],_,_,E,S),asserta(energia(E,S):-!).


%%reglas causales
convieneMoverse(si,S):- energia(E,S), promedioPorMoverse(P,S), (E + P) > 0.
convieneMoverse(no,S):- energia(E,S), promedioPorMoverse(P,S), (E + P) =< 0.
convienePelear(si,S) :- energia(E,S), promedioPorPelear(P,S), (E + P) > 0.
convienePelear(no,S) :- energia(E,S), promedioPorPelear(P,S), (E + P) =< 0.


%% Funcion probada.
%% Los CUTs que puse, hace que no de todas las soluciones, pero anda mas rapido y nos sirve.
conoce(X,Y,S) :- vacia(X,Y,S),!.
conoce(X,Y,S) :- comida(X,Y,S),!.
conoce(X,Y,S) :- enemigo(X,Y,S),!.


adyacenteCelda(X,Y,X1,Y):-sumarPosicion(X,-1,X1).
adyacenteCelda(X,Y,X1,Y):-sumarPosicion(X,1,X1).
adyacenteCelda(X,Y,X,Y1):-sumarPosicion(Y,-1,Y1).
adyacenteCelda(X,Y,X,Y1):-sumarPosicion(Y,1,Y1).

adyacente(X1,Y,izquierda,S):- posicion(X,Y,S),sumarPosicion(X,-1,X1).
adyacente(X1,Y,derecha,S)  :- posicion(X,Y,S),sumarPosicion(X,1,X1).
adyacente(X,Y1,arriba,S)   :- posicion(X,Y,S),sumarPosicion(Y,-1,Y1).
adyacente(X,Y1,abajo,S)    :- posicion(X,Y,S),sumarPosicion(Y,1,Y1).

%% Funcion probada.
%% Ojo. Se estan afectando unos a otros las variaciones de energia.
%% Lo mejor seria agregarlos desde Java o no?
datos_energia_pelear([],1):-!.
datos_energia_pelear([D|Ds],S1):-S0 is S1-1,accionEjecutada(pelear,S0),energia(E0,S0),energia(E1,S1),D is E1-E0,datos_energia_pelear(Ds,S0),!.
datos_energia_pelear(D,S1):-S0 is S1-1,datos_energia_pelear(D,S0),!.

datos_energia_mover([],1):-!.
datos_energia_mover([D|Ds],S1):-S0 is S1-1,accionMover(S0),energia(E0,S0),energia(E1,S1),D is E1-E0,datos_energia_mover(Ds,S0),!. %%este CUT aca hace que si tuvo exito la regla completa, no se ejecute la regla de abajo. Si la ccion no fue del tipo mover, entonces los datos de energia seran los mismos, por lo tanto nunca llega a ese cut, y se ejecuta la regla de abajo.
datos_energia_mover(D,S1):-S0 is S1-1,datos_energia_mover(D,S0),!.


%% todo esto se podria hacer en java
%% el llevar las energias y promedios
%% y solo cargar
%% promedioPorPelear(-2,3).
%% para indicar que en promedio perdio 2 a la situacion 3
%% Habia un error: los promedios eran positivos.

promedioPorPelear(0,1):-!.
promedioPorPelear(P,S):-datos_energia_pelear(Es,S),promedio(Es,P).

promedioPorMoverse(0,1):-!.
promedioPorMoverse(P,S):-datos_energia_mover(Es,S),promedio(Es,P).

%% Para cumplir el objetivo, se me ocurre que basta unicamente
%% con tableroVacio(S)
conoceTodo(S):-conoce(1,1,S),conoce(1,2,S),conoce(1,3,S),conoce(1,4,S),
               conoce(2,1,S),conoce(2,2,S),conoce(2,3,S),conoce(2,4,S),
               conoce(3,1,S),conoce(3,2,S),conoce(3,3,S),conoce(3,4,S),
               conoce(4,1,S),conoce(4,2,S),conoce(4,3,S),conoce(4,4,S).
               
tableroVacio(S):-vacia(1,1,S),vacia(1,2,S),vacia(1,3,S),vacia(1,4,S),
                 vacia(2,1,S),vacia(2,2,S),vacia(2,3,S),vacia(2,4,S),
                 vacia(3,1,S),vacia(3,2,S),vacia(3,3,S),vacia(3,4,S),
                 vacia(4,1,S),vacia(4,2,S),vacia(4,3,S),vacia(4,4,S).
                 
noHayEnemigosVivos(S):-tableroVacio(S).
noHayComida(S):-tableroVacio(S).

condicionUnoObjetivo(S):-conoceTodo(S).
condicionUnoObjetivo(S):-convieneMoverse(no,S).

condicionDosObjetivo(S):-noHayComida(S).

condicionTresObejtivo(S):-noHayEnemigosVivos(S).
condicionTresObejtivo(S):-convienePelear(no,S).

cumplioObjetivo(S):-condicionUnoObjetivo(S),condicionDosObjetivo(S),condicionTresObejtivo(S).

%%reglita comoda que nos ayudaría a seguir rastro de todas las acciones
%% si es que no las vamos borrando a las situaciones
acciones([],0):-!.
acciones([A|As],S):-accionEjecutada(A,S),S1 is S-1,acciones(As,S1).
accionMover(S):-accionEjecutada(arriba,S).
accionMover(S):-accionEjecutada(abajo,S).
accionMover(S):-accionEjecutada(derecha,S).
accionMover(S):-accionEjecutada(izquierda,S).

%%ESTADO SUCESOR
%% Funcion comprobada.
posicion(X,Y,S1):-S is S1-1,accionEjecutada(comer,S),posicion(X,Y,S),asserta(posicion(X,Y,S1):-!).
posicion(X,Y,S1):-S is S1-1,accionEjecutada(pelear,S),posicion(X,Y,S),asserta(posicion(X,Y,S1):-!).

%% Funcion comprobada.
posicion(X,Y1,S1):-S is S1-1,accionEjecutada(arriba,S),posicion(X,Y,S),sumarPosicion(Y,-1,Y1),asserta(posicion(X,Y,S1):-!).
posicion(X,Y1,S1):-S is S1-1,accionEjecutada(abajo,S),posicion(X,Y,S),sumarPosicion(Y,1,Y1),asserta(posicion(X,Y,S1):-!).
posicion(X1,Y,S1):-S is S1-1,accionEjecutada(derecha,S),posicion(X,Y,S),sumarPosicion(X,1,X1),asserta(posicion(X,Y,S1):-!).
posicion(X1,Y,S1):-S is S1-1,accionEjecutada(izquierda,S),posicion(X,Y,S),sumarPosicion(X,-1,X1),asserta(posicion(X,Y,S1):-!).

%% Funcion comprobada.
vacia(X,Y,S1):- S1 > 1,S is S1-1,vacia(X,Y,S),asserta(vacia(X,Y,S1):-!).
vacia(X,Y,S1):- S1 > 1,S is S1-1,accionEjecutada(comer,S),posicion(X,Y,S),asserta(vacia(X,Y,S1):-!).
vacia(X,Y,S1):- S1 > 1,S is S1-1,accionEjecutada(pelear,S),posicion(X,Y,S),asserta(vacia(X,Y,S1):-!).

%% Funcion comprobada.
%% Un razonamiento similar al de datos_energia_mover, se podria aplicar aca. Un cut al final haria que si ya descubrio que en una celda consulta hay comida, no necesita del resto de las reglas. por supuesto que esto hace que consultas como comida(X,Y,1) no funcionen, pero si miramos el tp creo que nunca se hacen consultas de ese estilo, ya que siempre X e Y tienen un valor definido por adyacente o sumarPosicion. Hay que verlo con cuidado. Igualmente anda muy rapido ya todo.
%%comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),comida(X,Y,S),Y=\=Y1.
%%comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X1,_,S),comida(X,Y,S),X=\=X1.
%%comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),comida(X,Y,S).

%%estado_sucesor(S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),comida(X,Y,S),Y=\=Y1->assert(comida(X,Y,S1)),fail.
%%estado_sucesor(S1):-S1 > 1,S is S1-1,posicion(X1,_,S),comida(X,Y,S),X=\=X1->assert(comida(X,Y,S1)),fail.
%%estado_sucesor(S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),comida(X,Y,S)->assert(comida(X,Y,S1)),fail.

comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),comida(X,Y,S),Y=\=Y1,asserta(comida(X,Y,S1):-!).
comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X1,_,S),comida(X,Y,S),X=\=X1,asserta(comida(X,Y,S1):-!).
comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),comida(X,Y,S),asserta(comida(X,Y,S1):-!).


%% Funcion comprobada.
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),enemigo(X,Y,S),Y=\=Y1,asserta(enemigo(X,Y,S1):-!).
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(X1,_,S),enemigo(X,Y,S),X=\=X1,asserta(enemigo(X,Y,S1):-!).
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),enemigo(X,Y,S),asserta(enemigo(X,Y,S1):-!).

direccionDeDescubrimiento(D,S):-adyacente(Xa,Ya,D,S),adyacenteCelda(Xa,Ya,Xaa,Yaa),not(conoce(Xaa,Yaa,S)).

%% valoracion de las acciones
%% Funcion comprobada.
%% TODO: Optimizar con el "conviene" primero
excelente(comer,S):-posicion(X,Y,S),comida(X,Y,S).
excelente(pelear,S):-posicion(X,Y,S),enemigo(X,Y,S),convienePelear(si,S).

%% Funcion comprobada.
muy_bueno(D,S):-adyacente(Xa,Ya,D,S),comida(Xa,Ya,S),convieneMoverse(si,S).
muy_bueno(D,S):-adyacente(Xa,Ya,D,S),enemigo(Xa,Ya,S),convieneMoverse(si,S),
	convienePelear(si,S).

%% Funcion comprobada.
bueno(D,S):-convieneMoverse(si,S),direccionDeDescubrimiento(D,S).

%% Funcion comprobada.
regular(D,S):-adyacente(Xa,Ya,D,S),vacia(Xa,Ya,S),convieneMoverse(si,S).

%% Funcion comprobada. Pero.. hay una situacion, en la que le conviene descubrir
%% el mundo que deberia estar sobre esta.
%% Los conviene estan fallando debido a que si pelea, la diferencia de energia
%% le afecta. Voy a probar resolverlo en prolog. Sino lo javeamos.
%%podria necesitar pasar, quiza es mas corto
malo(D,S):-adyacente(Xa,Ya,D,S),enemigo(Xa,Ya,S),convienePelear(no,S).

%% Funcion comprobada. Anda muy bien.
%% lo lleva al muere
muy_malo(pelear,S):-posicion(X,Y,S),enemigo(X,Y,S),convienePelear(no,S).
muy_malo(D,S):-adyacente(Xa,Ya,D,S),vacia(Xa,Ya,S),convieneMoverse(no,S).


mejorAccion(X,S):-excelente(X,S).
mejorAccion(X,S):-muy_bueno(X,S).
mejorAccion(X,S):-bueno(X,S).
mejorAccion(X,S):-regular(X,S).
mejorAccion(X,S):-malo(X,S).
mejorAccion(X,S):-muy_malo(X,S),fail.



