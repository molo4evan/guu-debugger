package nsu.molochev.syntax.factories

import nsu.molochev.exceptions.ParserException
import nsu.molochev.syntax.constructions.IntAssignment
import nsu.molochev.syntax.constructions.Parsable
import java.lang.NumberFormatException

class IntAssignmentFactory: ParsableFactory {
    override fun provide(args: List<String>, rawText: String): Parsable {
        if (args.size != 2) throw ParserException()
        return try {
            IntAssignment(args[0], args[1].toInt(), rawText)
        } catch (ex: NumberFormatException) {
            throw ParserException()
        }
    }
}