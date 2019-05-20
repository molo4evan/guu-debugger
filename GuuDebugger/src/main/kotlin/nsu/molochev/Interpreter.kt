package nsu.molochev

import nsu.molochev.exceptions.EndOfScriptException
import nsu.molochev.exceptions.NoFunctionException
import nsu.molochev.exceptions.StackOverflowException
import nsu.molochev.exceptions.UninitializedVariableException
import nsu.molochev.syntax.constructions.*
import java.io.File
import java.lang.UnsupportedOperationException
import java.util.*

class Interpreter(filename: String) {
    private val functionTable = mutableMapOf<String, Int>()
    private val variableTable = mutableMapOf<String, Int>()
    private val stack = Stack<FunctionDeclaration>()

    private val script: List<Parsable>
    private var currentIndex = -1
    private var callIndex = -1
    private var passIndex = -1

    var stackDepth = 1000
    private var currentDepth = 0

    init {
        val file = File(filename)
        val scriptText = file.readLines()
        val parser = Parser()
        script = parser.parseScript(scriptText)
        script.forEachIndexed { index, parsable ->
            if (parsable is FunctionDeclaration){
                functionTable[parsable.name] = index
                if (parsable.name == "main") currentIndex = index
            }
        }
        if (currentIndex < 0) throw NoFunctionException("main")
        stack.push(script[currentIndex] as FunctionDeclaration)
    }

    fun getCurrentLine(): String{
        if (currentIndex >= script.size) throw EndOfScriptException()
        return script[currentIndex].text
    }

    fun getStack(): Stack<String> {
        val out = Stack<String>()
        for (function in stack) {
            val index = functionTable[function.name]
            out.add("${index?.plus(1) ?: 0}: ${function.name}")
        }
        return out
    }

    fun getVariables() = variableTable.toMap()

    fun executeCurrentExpression(passFunctions: Boolean){
        if (currentIndex >= script.size){
            throw EndOfScriptException()
        }
        val expr = script[currentIndex]
        when (expr) {
            is FunctionDeclaration -> {
                 ++currentIndex
            }
            is FunctionCall -> {
                if (!passFunctions){
                    ++currentDepth
                    if (currentDepth > stackDepth){
                        throw StackOverflowException(stackDepth)
                    }
                    callIndex = currentIndex
                    currentIndex = functionTable[expr.name] ?: throw NoFunctionException(expr.name)
                    stack.push(script[currentIndex] as FunctionDeclaration)
                    return
                } else {
                    passIndex = currentIndex
                    while (currentIndex != passIndex + 1){
                        executeCurrentExpression(false)
                    }
                }
            }
            is Print -> {
                val toPrint = variableTable[expr.varName]
                    ?: throw UninitializedVariableException(expr.varName)
                println(toPrint)
                ++currentIndex
            }
            is EmptyLine -> {
                ++currentIndex
            }
            is IntAssignment -> {
                variableTable[expr.variable] = expr.value
                ++currentIndex
            }
            else -> {
                throw UnsupportedOperationException(expr.text)
            }
        }
        if (currentIndex < script.size && script[currentIndex] is EmptyLine) ++currentIndex
        if (currentIndex >= script.size || script[currentIndex] is FunctionDeclaration) {
            if (callIndex >= 0){
                currentIndex = callIndex + 1
                callIndex = -1
                stack.pop()
            } else {
                currentIndex = script.size
                throw EndOfScriptException()
            }
        }
    }

}