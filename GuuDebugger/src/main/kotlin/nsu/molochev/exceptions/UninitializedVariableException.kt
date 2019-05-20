package nsu.molochev.exceptions

import java.lang.Exception

class UninitializedVariableException(val varName: String): Exception()