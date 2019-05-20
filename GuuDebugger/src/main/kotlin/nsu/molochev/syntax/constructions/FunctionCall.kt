package nsu.molochev.syntax.constructions

class FunctionCall(
    val name: String,
    rawText: String
): Parsable(rawText)