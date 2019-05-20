package nsu.molochev.syntax.constructions

class Print(
    val varName: String,
    rawText: String
): Parsable(rawText)