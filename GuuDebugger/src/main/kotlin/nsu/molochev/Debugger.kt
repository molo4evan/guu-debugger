package nsu.molochev

import nsu.molochev.commands.*

import nsu.molochev.exceptions.EndOfScriptException
import nsu.molochev.exceptions.NoFunctionException
import nsu.molochev.exceptions.ParserException
import kotlin.streams.toList
class Debugger(private val filename: String) {
    companion object {
        fun printHelp(){
            println("%-8s|\t%s".format("Command", "Definition"))
            println("%-8s|\t%s".format("i", "Step into"))
            println("%-8s|\t%s".format("o", "Step out"))
            println("%-8s|\t%s".format("s", "Print stack"))
            println("%-8s|\t%s".format("v", "Print variables"))
            println("%-8s|\t%s".format("e", "Exit"))
        }
    }

    private lateinit var interpreter: Interpreter
    var stackDepth: Int
    get() = interpreter.stackDepth
    set(value){
        interpreter.stackDepth = value
    }

    private val commands = mapOf(
        "i" to StepInto(),
        "o" to StepOut(),
        "s" to PrintStack(),
        "v" to PrintVars(),
        "e" to Exit()
    )

    fun start(){
        printGreeting()
        try {
            interpreter = Interpreter(filename)
        } catch (ex: ParserException){
            println("Sorry, there is syntax exception in script")
            return
        } catch (ex: NoFunctionException){
            if (ex.functionName == "main"){
                println("Sorry, but there is no 'main' function in script")
                return
            } else {
                throw ex
            }
        }
        printHelp()
        printInfo()
        println()
        printCurrentLine()
        while (true){
            print("> ")
            val comLine = readLine()
            if (comLine == null){
                printGoodbye()
                return
            }
            val cmd = try {
                parseCommand(comLine)
            } catch (ex: Exception){
                println("ERROR: wrong command syntax")
                continue
            }
            if (cmd.first is Exit){
                printGoodbye()
                return
            }
            try {
                cmd.first.execute(interpreter, cmd.second)
                if (cmd.first.printLine) printCurrentLine()
            } catch (ex: EndOfScriptException){
                printGoodbye()
                return
            } catch (ex: Exception) {
                println("Exception occurred: $ex")
                println("--> ${ex.message}")
                return
            }
        }
    }

    private fun printGreeting(){
        println("Hello, user, thank you for using FGD (Friendly Goo Debugger)!")
    }

    private fun printInfo(){
        println("Current stack depth is $stackDepth")
    }

    private fun printCurrentLine(){
        println("|- ${interpreter.getCurrentLine()}")
    }

    private fun printGoodbye(){
        println("There is all, good luck, have fun")
    }

    fun parseCommand(line: String): Pair<Command, List<String>> {
        if (line.isBlank()) throw Exception()
        val words = line.split(' ', '\t')
        val cmd = commands[words[0]] ?: throw Exception()
        return Pair(cmd, words.stream().skip(1).toList())
    }
}