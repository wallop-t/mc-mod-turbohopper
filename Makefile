SHELL := /bin/zsh
MCV := $(shell zsh -i -c 'mcv')
JAVA_HOME := /Library/Java/JavaVirtualMachines/jdk-26.jdk/Contents/Home
CurseForgePath := $(HOME)/MC/curseforge/minecraft/Instances/$(MCV)
ServerPath := $(HOME)/Workspace/mc-server

cbuild:
	JAVA_HOME=$(JAVA_HOME) ./gradlew clean build
	mkdir -p $(CurseForgePath)/mods
	cp ./build/libs/turbohopper-1.0.0.jar $(CurseForgePath)/mods/turbohopper-1.0.0.jar
	cp ./build/libs/turbohopper-1.0.0.jar $(ServerPath)/mods/turbohopper-1.0.0.jar

uninstall:
	rm -f $(CurseForgePath)/mods/turbohopper-*
	rm -f $(ServerPath)/mods/turbohopper-*
