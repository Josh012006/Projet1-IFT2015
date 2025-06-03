# Auteurs
# Josué Mongan (20290870)
# David Stanescu (20314518)

# Makefile pour compiler et exécuter le projet Java L-système

# Variables
JAVAC      = javac
JAR        = jar
JAVA       = java

SRC_DIR    = src
BIN_DIR    = out
LIB_DIR    = lib

# Récupère automatiquement le .jar JSON dans lib/
JSON_JAR   = $(wildcard $(LIB_DIR)/json-*.jar)

MAIN_CLASS = lindenmayer.Main
JAR_NAME   = lindenmayer.jar

# Arguments pour la cible run :
ARGS       ?=

.PHONY: all classes jar run clean

all: jar

# Création du répertoire de classes compilées
$(BIN_DIR):
	mkdir -p $(BIN_DIR)

# Compilation des sources Java dans BIN_DIR
classes: $(BIN_DIR)
	$(JAVAC) -cp "$(JSON_JAR)" -d $(BIN_DIR) $(SRC_DIR)/lindenmayer/*.java

# Assemblage du JAR exécutable lindenmayer.jar
jar: classes
	$(JAR) cfe $(JAR_NAME) $(MAIN_CLASS) \
		-C $(BIN_DIR) . \
		-C $(LIB_DIR) $(notdir $(JSON_JAR))

# Exécution du programme
# Exemple d'utilisation :
#   make run ARGS="test/buisson.json 5 > test/buisson.eps"
run: jar
	$(JAVA) -cp "$(JAR_NAME):$(JSON_JAR)" $(MAIN_CLASS) $(ARGS)

# Suppression des fichiers compilés et du JAR
clean:
	rm -rf $(BIN_DIR) $(JAR_NAME)
