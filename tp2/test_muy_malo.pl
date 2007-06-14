enemigo(1,2,1).
enemigo(3,2,1).
%% movi a la izquierda
posicion(1,2,2).
enemigo(4,2,2).
%% lo mate
not(enemigo(1,2,3)).
enemigo(3,2,4).
enemigo(4,2,4).

