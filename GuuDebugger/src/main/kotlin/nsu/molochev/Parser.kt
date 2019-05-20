package nsu.molochev

import nsu.molochev.exceptions.ParserException
import nsu.molochev.syntax.constructions.EmptyLine
import nsu.molochev.syntax.constructions.Parsable
import nsu.molochev.syntax.factories.*
import kotlin.streams.toList

class Parser {
    private val factories = mapOf(
        "sub" to FunctionDeclarationFactory(),
        "set" to IntAssignmentFactory(),
        "call" to FunctionCallFactory(),
        "print" to PrintFactory()
    )

    private fun parseLine(line: String): Parsable {
        if (line.isBlank()) return EmptyLine()
        val words = line.trim().split(Regex("[ \t]+"))
        val fac = factories[words[0]]
        return factories[words[0]]?.provide(
            words.stream().skip(1).toList(),
            line
        ) ?: throw ParserException()
    }

    fun parseScript(lines: List<String>): List<Parsable> {
        val constructions = mutableListOf<Parsable>()
        for (line in lines) {
            val constr = parseLine(line)
            constructions.add(constr)
        }
        return constructions
    }
}