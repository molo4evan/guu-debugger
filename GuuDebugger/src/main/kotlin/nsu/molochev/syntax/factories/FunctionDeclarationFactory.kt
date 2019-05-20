package nsu.molochev.syntax.factories

import nsu.molochev.exceptions.ParserException
import nsu.molochev.syntax.constructions.FunctionDeclaration
import nsu.molochev.syntax.constructions.Parsable

class FunctionDeclarationFactory: ParsableFactory {
    override fun provide(args: List<String>, rawText: String): Parsable {
        if (args.size != 1) throw ParserException()
        return FunctionDeclaration(args[0], rawText)
    }
}