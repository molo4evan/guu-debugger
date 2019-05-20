package nsu.molochev.commands

import nsu.molochev.Interpreter

class Exit: Command(false) {
    override fun execute(interpreter: Interpreter, args: List<String>) {
    }
}