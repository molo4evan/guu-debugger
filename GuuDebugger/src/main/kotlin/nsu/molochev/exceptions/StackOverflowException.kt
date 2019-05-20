package nsu.molochev.exceptions

class StackOverflowException(depth: Int): Exception(
    "Stack depth ($depth) overflow!"
)