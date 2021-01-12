package calcy

class InsufficientParametersException(message: String?) : Throwable(message)

class IllegalParametersException(message: String?) : Throwable(message)

class LimitReachedException(message: String?) : Throwable(message)
