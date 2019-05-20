import nsu.molochev.Debugger
import java.lang.NumberFormatException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: 'script name ['-s n' where n is stack depth]' / '-h'")
        return
    }
    if (args[0] == "-h"){
        Debugger.printHelp()
    } else {
        val d = Debugger(args[0])
        if (args.size >= 3 && args[1] == "-s"){
            try {
                val depth = args[2].toInt()
                if (depth < 1) throw NumberFormatException()
                d.start(depth)
            } catch (e: NumberFormatException){
                println("ERROR: stack depth must be integer (>= 1)")
                return
            }
        } else {
            d.start()
        }
    }
}