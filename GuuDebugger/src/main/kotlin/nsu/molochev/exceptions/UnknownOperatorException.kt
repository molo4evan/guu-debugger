package nsu.molochev.exceptions

import java.lang.Exception

class UnknownOperatorException(val line: String): Exception(
    "Unknown operator in line: '$line'"
)