# starcraft-optimiser

starcraft-optimiser is a program that generates an optimised build order for
    Starcraft II: WoL for the Terran race.

This folder consists of two folders called basic and extensions, which contain the program for 
the basic deliverable and two programs that have extensions implemented respectively.

## Compilation
Program is to be compiled within the source folder accordingly:

javac *.java

## Usage-basic

java StarcraftOptimiser \<goal\>

Some examples of how \<goal\> should look like are:
- "6 marines"
- "6 marines, 1 hellion, 9 medivacs"

## Usage-delay

java RealisticTimings \<goal\>

## Usage-intermediate

java IntermediateStarcraftOptimiser \<goal\>

Note: when attempting to build siege tanks, they need to be passed in as "tanks" and not "siege tanks"

## The Team 🚀

Built with ❤ by [A.C.](https://github.com/AdrianaCucu) and [meills](https://github.com/meills)
