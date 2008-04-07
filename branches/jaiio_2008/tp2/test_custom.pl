%% aca esta medio raro. Iria en la iteracion
asserta(percepcion([vacia,comida,vacia,enemigo],1,1,20,1)).
cumplioObjetivo(1).
findall(X,est(1),L).

mejorAccion(X,1).


asserta(accionEjecutada(derecha,1)).
asserta(percepcion([vacia,vacia,vacia,vacia],0,0,19,2)).

findall(X,est(2),L).

cumplioObjetivo(2).


mejorAccion(X,2).


asserta(accionEjecutada(comer,2)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,8,3)).


findall(X,est(3),L).


cumplioObjetivo(3).


mejorAccion(X,3).


asserta(accionEjecutada(izquierda,3)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,7,4)).

est(4).


cumplioObjetivo(4).


mejorAccion(X,4).


asserta(accionEjecutada(arriba,4)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,6,5)).

est(5).


cumplioObjetivo(5).


mejorAccion(X,5).


asserta(accionEjecutada(izquierda,5)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,6,6)).

est(6).


cumplioObjetivo(6).


mejorAccion(X,6).


asserta(accionEjecutada(izquierda,6)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,5,7)).

est(7).


cumplioObjetivo(7).


mejorAccion(X,7).


asserta(accionEjecutada(izquierda,7)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,5,8)).

est(8).


cumplioObjetivo(8).


mejorAccion(X,8).


asserta(accionEjecutada(izquierda,8)).
asserta(percepcion([vacia,vacia,vacia,vacia],2,1,5,9)).

est(9).


cumplioObjetivo(9).


mejorAccion(X,9).



