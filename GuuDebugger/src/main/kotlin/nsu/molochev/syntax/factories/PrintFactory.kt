package nsu.molochev.syntax.factories

import nsu.molochev.exceptions.ParserException
import nsu.molochev.syntax.constructions.Parsable
import nsu.molochev.syntax.constructions.Print

class PrintFactory: ParsableFactory {
    override fun provide(args: List<String>, rawText: String): Parsable {
        if (args.size != 1) throw ParserException()
        return Print(args[0], rawText)
    }
}