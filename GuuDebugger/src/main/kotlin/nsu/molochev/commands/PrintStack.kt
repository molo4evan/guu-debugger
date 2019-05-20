package nsu.molochev.commands

import nsu.molochev.Interpreter

class PrintStack: Command(false) {
    override fun execute(interpreter: Interpreter, args: List<String>) {
        val stack = interpreter.getStack()
        var tabs = 0
        println("Stack:")
        for (s in stack) {
            for (i in 0..tabs) print("\t")
            println("-> $s")
            ++tabs
        }
    }
}