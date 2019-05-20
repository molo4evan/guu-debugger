package nsu.molochev.commands

import nsu.molochev.Interpreter

class StepInto: Command(true) {
    override fun execute(interpreter: Interpreter, args: List<String>) {
        interpreter.executeCurrentExpression(false)
    }
}