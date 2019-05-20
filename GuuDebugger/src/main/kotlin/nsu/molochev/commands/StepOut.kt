package nsu.molochev.commands

import nsu.molochev.Interpreter

class StepOut: Command(true) {
    override fun execute(interpreter: Interpreter, args: List<String>) {
        interpreter.executeCurrentExpression(true)
    }
}