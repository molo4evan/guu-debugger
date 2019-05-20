package nsu.molochev.syntax.constructions

class IntAssignment(
    val variable: String,
    val value: Int,
    rawText: String
): Parsable(rawText)