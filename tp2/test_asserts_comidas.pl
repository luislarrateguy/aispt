%% aca esta medio raro. Iria en la iteracion
percibir(1).
posicion(2,2,1).
comida(1,2,1).
comida(3,2,1).
comida(2,1,1).
%% movi a la izquierda













percibir(2).
estado_sucesor(2).
posicion(1,2,2).
comida(1,2,2).
comida(3,2,2).
comida(2,1,2).

%% se la comio

percibir(4).
estado_sucesor(3).


not(comida(1,2,3)).

percibir(4).
estado_sucesor(4).

not(comida(1,2,4)).
comida(3,2,4).
comida(2,1,4).

