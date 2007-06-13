percepcion([enemigo,enemigo,vacia,comida],2,2,10,1).

%%Estos son los statements que no infieren

%% "Funcion" para sumar posiciones en forma de lista circular
sumarPosicion(P,O,P1):-O<=0,P=:=1,P1 is 4.
sumarPosicion(P,O,P1):-O<=0,P=\=1,P1 is P-1.
sumarPosicion(P,O,P1):-O=:=1,P=:=4,P1 is 1.
sumarPosicion(P,O,P1):-O=:=1,P=\=4,P1 is P+1.

%%hace la sumatoria de una lista
sumatoria([],0):-!.
sumatoria([L|Ls],C):-sumatoria(Ls,T),C is T+L.

%%retorna la cantidad de elementos de una lista
cantidad([],0):-!.
cantidad([L|Ls],C):-cantidad(Ls,T),C is T+1.

%%calcula el promedio de una lista
promedio(L,P):-sumatoria(L,Z), cantidad(L,C), P is (S/C).

%% infiriendo de las percepciones
enemigo(X1,Y,S):-percepcion([enemigo,_,_,_],X,Y,_,S),sumarPosicion(X,-1,X1).
enemigo(X1,Y,S):-percepcion([_,enemigo,_,_],X,Y,_,S),sumarPosicion(X,1,X1).
enemigo(X,Y1,S):-percepcion([_,_,enemigo,_],X,Y,_,S),sumarPosicion(Y,-1,Y1).
enemigo(X,Y1,S):-percepcion([_,_,_,enemigo],X,Y,_,S),sumarPosicion(Y,1,Y1).

comida(X1,Y,S):-percepcion([comida,_,_,_],X,Y,_,S),sumarPosicion(X,-1,X1).
comida(X1,Y,S):-percepcion([_,comida,_,_],X,Y,_,S),sumarPosicion(X,1,X1).
comida(X,Y1,S):-percepcion([_,_,comida,_],X,Y,_,S),sumarPosicion(Y,-1,Y1).
comida(X,Y1,S):-percepcion([_,_,_,comida],X,Y,_,S),sumarPosicion(Y,1,Y1).

vacia(X1,Y,S):-percepcion([vacia,_,_,_],X,Y,_,S),sumarPosicion(X,-1,X1).
vacia(X1,Y,S):-percepcion([_,vacia,_,_],X,Y,_,S),sumarPosicion(X,1,X1).
vacia(X,Y1,S):-percepcion([_,_,vacia,_],X,Y,_,S),sumarPosicion(Y,-1,Y1).
vacia(X,Y1,S):-percepcion([_,_,_,vacia],X,Y,_,S),sumarPosicion(Y,1,Y1).

posicion(X,Y,S):-percepcion([_,_,_,_],X,Y,_,S).

energia(E,S):-percepcion([_,_,_,_],_,_,E,S).

%%reglas causales
convieneMoverse(si,S):- energia(E,S), promedioPorMoverse(P,S), (E + P) > 0.
convieneMoverse(no,S):- energia(E,S), promedioPorMoverse(P,S), (E + P) <= 0.
convienePelear(si,S) :- energia(E,S), promedioPorPelear(P,S), (E + P) > 0.
convienePelear(no,S) :- energia(E,S), promedioPorPelear(P,S), (E + P) <= 0.

conoce(X,Y,S) :- vacia(X,Y,S).
conoce(X,Y,S) :- comida(X,Y,S).
conoce(X,Y,S) :- enemigo(X,Y,S).


adyacente(X1,y,derecha,S)  :- posicion(X,Y,S),sumarPosicion(X,1,X1).
adyacente(X1,y,izquierda,S):- posicion(X,Y,S),sumarPosicion(X,-1,X1).
adyacente(X,Y1,arriba,S)   :- posicion(X,Y,S),sumarPosicion(Y,1,Y1).
adyacente(X,Y1,abajo,S)    :- posicion(X,Y,S),sumarPosicion(Y,-1,Y1).

datosEnergia(_,[],0):-!.
datosEnergia(A,[E2|Es],S):-accionEjecutada(A,S), energia(E1,S), S1 is S-1,energia(E0,S1),E2 is (E0 - E1).

%% todo esto se podria hacer en java
%% el llevar las energias y promedios
%%y solo cargar
%% promedioPorPelear(-2,3).
%% para indicar que en promedio perdio 2 a la situacion 3

promedioPorPelear(P,S):-datosEnergia(pelear,Es),promedio(Es,P).
promedioPorMoverse(P,S):-datosEnergia(mover,Es),promedio(Es,P).

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
                 
%%defino por las dudas cosas de las que hablamos
%% esta seria una clausula positiva para decir
%% que no hay enemigo en XY si la conozco
%% aunque dudo que no este de más poner lo de vacia y comida
noHayEnemigo(X,Y,S):-vacia(X,Y,S).
noHayEnemigo(X,Y,S):-comida(X,Y,S).
noHayEnemigo(X,Y,S):-enemigo(X,Y,S),!,fail.
noHayEnemigo(X,Y,S).

%%mismo para comida
noHayComida(X,Y,S):-vacia(X,Y,S).
noHayComida(X,Y,S):-enemigo(X,Y,S).
noHayComida(X,Y,S):-comida(X,Y,S),!,fail.
noHayComida(X,Y,S).

%% Esto sería util debido a lo explicado en la documentacion,
%% pero insisto:
%% no estaria mal redefinir el objetivo a tableroVacio(S)

noHayEnemigosVivos(S):-tableroVacio(S).
noHayComida(S):-tableroVacio(S).

%% usando las reglas de noHayEnemigo(X,Y,S)
noHayEnemigosVivos(S):-noHayEnemigo(1,1,S),noHayEnemigo(1,2,S),noHayEnemigo(1,3,S),noHayEnemigo(1,4,S),
                       noHayEnemigo(2,1,S),noHayEnemigo(2,2,S),noHayEnemigo(2,3,S),noHayEnemigo(2,4,S),
                       noHayEnemigo(3,1,S),noHayEnemigo(3,2,S),noHayEnemigo(3,3,S),noHayEnemigo(3,4,S),
                       noHayEnemigo(4,1,S),noHayEnemigo(4,2,S),noHayEnemigo(4,3,S),noHayEnemigo(4,4,S).

noHayComida(S):-noHayComida(1,1,S),noHayComida(1,2,S),noHayComida(1,3,S),noHayComida(1,4,S),
                noHayComida(2,1,S),noHayComida(2,2,S),noHayComida(2,3,S),noHayComida(2,4,S),
                noHayComida(3,1,S),noHayComida(3,2,S),noHayComida(3,3,S),noHayComida(3,4,S),
                noHayComida(4,1,S),noHayComida(4,2,S),noHayComida(4,3,S),noHayComida(4,4,S).


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

%%ESTADO SUCESOR

%% El estado sucesor para la posicion no haría falta
%% ya que viene como percepcion cada vez.

vacia(X,Y,S1):-vacia(X,Y,S),!.
vacia(X,Y,S1):-comida(X,Y,S),posicion(X,Y,S),accionEjecutada(comer,S1),S1 is S + 1,!.
vacia(X,Y,S1):-enemigo(X,Y,S),posicion(X,Y,S),accionEjecutada(pelear,S1),S1 is S + 1,!.

comida(X,Y,S1):-comida(X,Y,S),posicion(X1,Y1,S),X=\=X1,Y=\=Y1,!.
comida(X,Y,S1):-comida(X,Y,S),posicion(X,Y,S),accionEjecutada(mover,S1),S1 is S + 1,!.


enemigo(X,Y,S1):-enemigo(X,Y,S),posicion(X1,Y1,S),X=\=X1,Y=\=Y1,S1 is S + 1,!.
enemigo(X,Y,S1):-enemigo(X,Y,S),posicion(X,Y,S),accionEjecutada(mover,S1),S1 is S + 1,!.


%%valoracion de las acciones
excelente(comer,S):-posicion(X,Y,S),comida(X,Y,S).

muy_bueno(pelear,S):-posicion(X,Y,S),enemigo(X,Y,S),convienePelear(si,S).
muy_bueno(D,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),comida(Xa,Ya,S),convieneMoverse(S).

bueno(D,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),enemigo(Xa,Ya,S),convienePelear(si,S).

regular(D,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),vacia(Xa,Ya,S),convieneMoverse(si,S).

%%podria necesitar pasar, quiza es mas corto
malo(D,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),enemigo(Xa,Ya,S),convienePelear(no,S).

%% lo lleva al muere
muy_malo(pelear,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),enemigo(Xa,Ya,S),convienePelear(no,S).
muy_malo(D,S):-posicion(X,Y,S),adyacente(Xa,Ya,D,S),vacia(Xa,Ya,S),convieneMoverse(no,S).


mejorAccion(X,S):-excelente(X,S).
mejorAccion(X,S):-muy_bueno(X,S).
mejorAccion(X,S):-bueno(X,S).
mejorAccion(X,S):-regular(X,S).
mejorAccion(X,S):-malo(X,S).
mejorAccion(X,S):-muy_malo(X,S).
