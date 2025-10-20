package calculator

class customSeparator {

    // '//'로 시작하는 커스텀 구분자를 가지는 문자열인지 확인
    fun isCustom(input: String): Boolean {
        return input.startsWith("//")
    }

    // 나머지 조건들이 커스텀 구분자를 가지는 올바른 문자열인지 검사
    fun checkCustom(input: String) {

        // '\n'의 위치 반환
        val secondIndex = input.indexOf("\\n")

        // '\n'이 존재하지 않을 시 잘못된 형식
        if (secondIndex == -1) {
            println("잘못된 형식")
            return
        }

        // 커스텀 구분자 반환
        val delimiter = input.substring(2, secondIndex)

        // 커스텀 구분자가 없을 시 잘못된 형식
        if (delimiter.isEmpty()) {
            println("잘못된 형식")
            return
        }

        else {
            println("커스텀 구분자를 가지는 문자열")
        }

    }
}