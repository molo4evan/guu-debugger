package nsu.molochev.syntax.factories

import nsu.molochev.exceptions.ParserException
import nsu.molochev.syntax.constructions.FunctionCall
import nsu.molochev.syntax.constructions.Parsable

class FunctionCallFactory: ParsableFactory {
    override fun provide(args: List<String>, rawText: String): Parsable {
        if (args.size != 1) throw ParserException()
        return FunctionCall(args[0], rawText)
    }
}