import os

for a in range(100):
	os.system("./simulador.sh | tail -n 1 >> tp2.csv")
