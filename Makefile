# Auteurs
# Josué Mongan (20290870)
# David Stanescu (20314518)

# Makefile pour compiler et exécuter le projet Java L-système

# Variables communes
JAVAC      = javac
JAR        = jar
JAVA       = java

SRC_DIR    = src
BIN_DIR    = out
LIB_DIR    = lib

JSON_JAR   = $(wildcard $(LIB_DIR)/json-*.jar)

MAIN_CLASS = lindenmayer.Main
JAR_NAME   = lindenmayer.jar

ARGS       ?=

# Détection simple de l’OS
ifeq ($(OS),Windows_NT)
    DETECTED_OS := WINDOWS
else
    DETECTED_OS := UNIX
endif


#################################
# SECTION LINUX / MACOS
#################################
ifeq ($(DETECTED_OS),UNIX)

MKDIR      = mkdir -p
CPSEP      = :
RM         = rm -rf

all: jar

$(BIN_DIR):
	$(MKDIR) $(BIN_DIR)

classes: $(BIN_DIR)
	$(JAVAC) -cp "$(JSON_JAR)" -d $(BIN_DIR) $(SRC_DIR)/lindenmayer/*.java

jar: classes
	$(JAR) cfe $(JAR_NAME) $(MAIN_CLASS) \
		-C $(BIN_DIR) . \
		-C $(LIB_DIR) $(notdir $(JSON_JAR))

run: jar
	$(JAVA) -cp "$(JAR_NAME)$(CPSEP)$(JSON_JAR)" $(MAIN_CLASS) $(ARGS)

clean:
	$(RM) $(BIN_DIR) $(JAR_NAME)

endif



#################################
# SECTION WINDOWS
#################################
ifeq ($(DETECTED_OS),WINDOWS)

MKDIR      = mkdir
CPSEP      = ;
RM         = del /Q

all: jar

$(BIN_DIR):
	@if not exist "$(BIN_DIR)" $(MKDIR) $(BIN_DIR)

classes: $(BIN_DIR)
	$(JAVAC) -cp "$(JSON_JAR)" -d $(BIN_DIR) $(SRC_DIR)/lindenmayer/*.java

jar: classes
	$(JAR) cfe $(JAR_NAME) $(MAIN_CLASS) \
		-C $(BIN_DIR) . \
		-C $(LIB_DIR) $(notdir $(JSON_JAR))

run: jar
	$(JAVA) -cp "$(JAR_NAME)$(CPSEP)$(JSON_JAR)" $(MAIN_CLASS) $(ARGS)

clean:
	@if exist "$(BIN_DIR)" rmdir /S /Q "$(BIN_DIR)"
	@if exist "$(JAR_NAME)" $(RM) "$(JAR_NAME)"

endif
