package nsu.molochev.exceptions

class NoFunctionException(val functionName: String): Exception(
    "No function with name '$functionName'"
)