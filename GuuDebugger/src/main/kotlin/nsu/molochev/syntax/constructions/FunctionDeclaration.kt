package nsu.molochev.syntax.constructions

class FunctionDeclaration(
    val name: String,
    rawText: String
): Parsable(rawText)