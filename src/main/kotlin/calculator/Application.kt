package calculator

import camp.nextstep.edu.missionutils.Console

fun main() {
    println("덧셈할 문자열을 입력해 주세요.")
    val input = Console.readLine()

    val CustomSeparator = customSeparator()
    val DefaultSeparator = defaultSeparator()

    when {
        CustomSeparator.isCustom(input) -> CustomSeparator.checkCustom(input)
        DefaultSeparator.isDefault(input) -> println("기본 구분자를 가지는 문자열")
    }

}
