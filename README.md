# starcraft-optimiser
starcraft-optimiser is a program that generates an optimised build order for Starcraft II:
WoL for the Terran race.

This folder consists of two folders called basic and extensions, which contain the program for 
the basic deliverable and two programs that has extensions implemented respectively.

# Compilation
Program is to be compiled within the source folder accordingly:

javac *.java

# Usage-basic

java StarcraftOptimiser <goal>

some examples of how <goal> should look are:
- "6 marines"
- "6 marines, 1 hellion, 9 medivacs"

# Usage-delay

javac RealisticTimings <goal>

# Usage-intermediate

java IntermediateStarcraftOptimiser <goal>
