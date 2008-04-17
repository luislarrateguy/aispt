import os

for a in range(100):
	os.system("java -jar simulador.jar logger.config configDesbalanceado.xml | tail -n 1 >> tp1_ejec_aestrella.csv")
