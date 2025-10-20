package calculator

class DefaultSeparator {

    // 기본 구분자를 가지는 문자열인지 판별
    fun isDefault(input: String): Boolean {
        return input.contains(",") || input.contains(":")
    }

}