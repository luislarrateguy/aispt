%% Funcion comprobada.
vacia(X,Y,S1):- S1 > 1,S is S1-1,vacia(X,Y,S),!.
vacia(X,Y,S1):- S1 > 1,S is S1-1,accionEjecutada(comer,S),posicion(X,Y,S),!.
vacia(X,Y,S1):- S1 > 1,S is S1-1,accionEjecutada(pelear,S),posicion(X,Y,S),!.

%% Funcion comprobada.
%% Un razonamiento similar al de datos_energia_mover, se podria aplicar aca. Un cut al final haria que si ya descubrio que en una celda consulta hay comida, no necesita del resto de las reglas. por supuesto que esto hace que consultas como comida(X,Y,1) no funcionen, pero si miramos el tp creo que nunca se hacen consultas de ese estilo, ya que siempre X e Y tienen un valor definido por adyacente o sumarPosicion. Hay que verlo con cuidado. Igualmente anda muy rapido ya todo.
comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),comida(X,Y,S),Y=\=Y1,!.
comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X1,_,S),comida(X,Y,S),X=\=X1,!.
comida(X,Y,S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),comida(X,Y,S),!.


%% Funcion comprobada.
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(_,Y1,S),enemigo(X,Y,S),Y=\=Y1,!.
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(X1,_,S),enemigo(X,Y,S),X=\=X1,!.
enemigo(X,Y,S1):-S1 > 1,S is S1-1,posicion(X,Y,S),accionMover(S),enemigo(X,Y,S),!.
