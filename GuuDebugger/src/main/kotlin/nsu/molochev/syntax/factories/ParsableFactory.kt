package nsu.molochev.syntax.factories

import nsu.molochev.syntax.constructions.Parsable

interface ParsableFactory {
    fun provide(args: List<String>, rawText: String): Parsable
}