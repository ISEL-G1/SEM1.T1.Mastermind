/**
 * Instituto Superior de Engenharia de Lisboa
 * 16/10/2024
 *
 * Trabalho realizado por:
 * - Alexandre Silva (52718)
 * - Daniel Viegas (52885)
 * - Duarte Rodrigues (52599)
 *
 * Em âmbito da unidade curricular de Programação da turma LEIC12D.
 */

const val MAX_TRIES = 10 // in 5..20
const val SIZE_POSITIONS = 4 // in 2..6
const val SIZE_COLORS = 6 // in 2..10 and >= SIZE_POSITIONS
const val FIRST_COLOR = 'A' // ‘A’ or ‘a’ or ‘0’
val COLORS = FIRST_COLOR ..< FIRST_COLOR + SIZE_COLORS

/**
 * Main program entrypoint; Asks for
 */
fun main()	{
    val secret: String = generateSecret()

    println("Descubra o código em $MAX_TRIES tentativas.")
    println("$SIZE_POSITIONS posições e $SIZE_COLORS cores $COLORS")

    for (numTries in 1..MAX_TRIES) {
        val guess = readGuess(numTries)

        if (guess == secret) {
            println("Parabéns!\nAcertou à ${numTries}ª tentativa.")
            return
        }

        val corrects = getCorrects(guess, secret)
        val swapped = getSwapped(guess, secret)
        printTry(numTries, guess, corrects, swapped)
    }

    println("Não acertou em $MAX_TRIES tentativas.")
}

/**
 * Creates a 4-Character secret code in the A-F range. All these
 * characters will be different.
 *
 * @return A 4-Character code made up of different components
 */
fun generateSecret() : String {

    var result = ""  // The string that will be used to create the secret code

    while (result.length < SIZE_POSITIONS) {

        // Generates the different characters while appending it to "result"
        while (true) {
            val generated = COLORS.random()

            if (!result.contains(generated)) {
                result += generated
                break
            }
        }
    }

    return result
}

/**
 * Allows character inputs set in the COLORS constant, with the set size of
 * SIZE_POSITIONS.
 *
 * @param input The message typed in by the user
 * @return Whether the inputs are valid or not
 */
fun validateInputs(input: String): Boolean {

    if (input.length != SIZE_POSITIONS) return false

    val inputElements = input.toList()

    // Checks if every character exists within the COLORS range.
    for (i in inputElements) {
        if (!COLORS.toList().contains(i.uppercaseChar())) return false
    }

    return true
}

/**
 * Prompts the user asking for their guess and returns it in the form of a string
 *
 * @param numTries The current amount of tries performed
 * @return The string read from the user input
 */
fun readGuess(numTries: Int) : String {

    while (true) {

        print("${numTries}ª Tentativa: ")
        val input = readln().uppercase()

        // Checks if the inputs are valid and requests new ones until they are
        if (!validateInputs(input)) {
            println("Entrada inválida. Insira um valor com $SIZE_POSITIONS caracteres de ${COLORS.first} a ${COLORS.last}")
            continue
        }

        return input
    }
}

/**
 * After reading the guess, search for the correct characters on the right spot
 *
 * @param guess Code with 4 characters that the user inputs
 * @param secret Secret code with 4 different characters, auto-generated
 * @return The number of correct characters on the right spot
 */
fun getCorrects(guess: String, secret: String) : String {

    // Split both strings on different chars
    val secretValues = secret.toList()
    val guessValues = guess.toList()
    var corrects = 0

    for (i in secretValues.indices) {

        if (guessValues[i] == secretValues[i]) {
            corrects += 1
        }
    }

    return corrects.toString()
}

/**
 * After reading the guess, search for the correct characters
 *
 * @param guess Code with 4 characters that the user inputs
 * @param secret Secret code with 4 different characters, auto-generated
 * @return The number of correct characters
 */
fun getSwapped(guess: String, secret: String) : String {

    // Split both strings on different chars
    val guessValues = guess.toList()
    var alreadySwapped = ""
    var swapped = 0

    // Search for elements in guessValues that secret contains and add the number to swapped
    for (e in guessValues) {

        if (alreadySwapped.contains(e))
            continue

        if (secret.contains(e)) {
            swapped += 1
            alreadySwapped += e
        }
    }

    return swapped.toString()
}

/**
 * After reading the guess, search for the correct characters on the wrong spot
 *
 * @param numTries The current amount of tries performed
 * @param guess Code with 4 characters that the user inputs
 * @param corrects The number of correct characters on the right spot
 * @param swapped The number of correct characters on the wrong spot
 *
*/
fun printTry(numTries: Int, guess: String, corrects: String, swapped: String) {

    println("${numTries}ª : $guess -> ${corrects}C + ${swapped.toInt() - corrects.toInt()}T")
}



