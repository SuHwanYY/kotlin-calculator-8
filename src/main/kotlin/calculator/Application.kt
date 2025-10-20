package calculator

import camp.nextstep.edu.missionutils.Console

fun main() {
    println("덧셈할 문자열을 입력해 주세요.")
    val input = Console.readLine()

    val custom = CustomSeparator()
    val default = DefaultSeparator()

    try {
        // 커스텀 구분자 검사
        custom.isCustomAndExtraction(input)?.let { info ->
            println("커스텀 구분자")
            return
        }

        // 기본 구분자 검사
        if (default.isDefault(input)) {
            println("기본 구분자")
            return
        }

        // 그 외
        when {

            // 빈 입력
            input.isBlank() -> {
                println("빈 입력")
            }

            // 구분자 없음
            input.toIntOrNull() != null -> {
                println("구분자 없음")
            }

            // 형식 오류
            else -> {
                throw IllegalArgumentException("형식오류")
            }
        }
    } catch (e: IllegalArgumentException) {
        println("에러메시지 : " + e.message)
    }


}
