# 8 puzzle solver

This project is a solver for the 8-puzzle problem (a variation of the more common [15-puzzle problem](https://en.wikipedia.org/wiki/15_puzzle)),
for the Introduction to Artificial Intelligence course from UFMG Computer Science BSc.

## Compiling

You must have **Java >= 18** installed to compile/run this project. Please refer to your OS-specific installation options. I recommend the [asdf](https://github.com/asdf-vm/asdf) version manager, which conveniently has a [Java](https://github.com/halcyon/asdf-java) plugin.


In order to compile, you can either compile manually using a provided script or use maven if it is available on your system (_tested with mvn 3.6.3_).

**All commands displayed below must be executed in the project's root directory - a.k.a. the directory in which this README.md file is in.**

### Maven

`mvn clean install`

### Manually

Run `./scripts/compile.sh`.

Both of these create `target/tp1.jar`.

## Running

You can either use a _carefully crafted_ wrapper called `TP1` (basically a shell script created to conform with the project's requirement of the executable being named `TP1`), or `java -jar` manually. The wrapper assumes the jar file is located in `target/tp1.jar`.

Execution options:

* Using the wrapper:
`./TP1 <args>`

* Directly: `java -jar target/tp1.jar <args>`
