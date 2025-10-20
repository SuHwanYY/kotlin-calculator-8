package calculator

import camp.nextstep.edu.missionutils.Console

fun main() {
    println("덧셈할 문자열을 입력해 주세요.")
    val input = Console.readLine()

    val calculator = Calculator()

    try {
        val result = calculator.add(input)
        println(result)
    } catch (e: IllegalArgumentException) {
        println("에러메시지 : " + e.message)
    }

}
