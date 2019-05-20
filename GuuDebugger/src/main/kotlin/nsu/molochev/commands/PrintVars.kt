package nsu.molochev.commands

import nsu.molochev.Interpreter

class PrintVars: Command(false) {
    override fun execute(interpreter: Interpreter, args: List<String>) {
        val vars = interpreter.getVariables()
        println("%-10s|\t%s".format("Variable", "Value"))
        for (pair in vars) {
            println("%-10s|\t%d".format(pair.key, pair.value))
        }
    }
}