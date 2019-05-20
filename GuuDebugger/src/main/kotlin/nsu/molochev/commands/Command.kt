package nsu.molochev.commands

import nsu.molochev.Interpreter

abstract class Command(val printLine: Boolean) {
    abstract fun execute(interpreter: Interpreter, args: List<String>)
}