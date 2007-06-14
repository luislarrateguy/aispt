%% Percepciones (logran tablero vacio, cumple objetivo en 6)
percepcion([enemigo,enemigo,vacia,vacia],2,2,10,1).
accionEjecutada(izquierda,1).
percepcion([enemigo,vacia,vacia,vacia],0,0,9,2).
accionEjecutada(pelear,2).
percepcion([enemigo,vacia,vacia,vacia],0,0,3,3).
accionEjecutada(izquierda,3).
percepcion([enemigo,vacia,vacia,vacia],0,0,2,4).

