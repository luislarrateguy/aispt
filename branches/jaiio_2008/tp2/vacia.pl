:- dynamic vacia/3.
vacia(1,1,1).
estado_sucesor(S1):- (S1 > 1,S is S1-1,vacia(X,Y,S))->assert(vacia(X,Y,S1)).
