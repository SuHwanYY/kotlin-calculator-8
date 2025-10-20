package calculator

class Calculator(
    private val custom: CustomSeparator = CustomSeparator(),
    private val default: DefaultSeparator = DefaultSeparator()
) {
    fun add(input: String): Int {
        // 입력이 비어있을 경우
        if (input.isBlank()) return 0

        // 커스텀 구분자 시도
        custom.isCustomAndExtraction(input)?.let { info ->
            return sumCustom(info.body, info.delimiter)
        }

        // 기본 구분자 시도
        if (default.isDefault(input)) {
            return sumDefault(input)
        }

        // 구분자가 없을 경우 단일 숫자이거나 형식 오류
        input.toIntOrNull()?.let { return it }
        throw IllegalArgumentException("형식 오류: 구분자를 찾을 수 없고 숫자도 아닙니다.")
    }

    // 기본 구분자 합계 계산
    private fun sumDefault(body: String): Int =
        toIntsOrThrow(     // 문자열 토큰을 Int로 바꾸고 유효성 검사
            body.split(Regex("[,:]"))   // 쉼표와 콜론으로 문자열 분리
                .map { it.trim() }                      // 각 토큰 공백 제거
        ).sum()

    // 커스텀 구분자 합계
    private fun sumCustom(body: String, delimiter: String): Int =
        toIntsOrThrow(
            body.split(delimiter)   // 전달된 커스텀 구분자 문자열로 분리
                .map { it.trim() }
        ).sum()

    // 문자열 토큰 검증
    private fun toIntsOrThrow(tokens: List<String>): List<Int> {

        // "//;\\n" 처럼 본문이 비어 있으면 합 0
        if (tokens.isEmpty()) return emptyList()

        // 구분자 사이에 비어있는 곳이 있을 경우
        if (tokens.any { it.isEmpty() }) {
            throw IllegalArgumentException("형식 오류: 구분자 사이에 비어 있는 곳이 있습니다.")
        }

        // 구분자 사이에 숫자가 아닌 토큰이 있을 경우
        val ints = tokens.map { s ->
            s.toIntOrNull() ?: throw IllegalArgumentException("형식 오류: 숫자가 아닌 토큰이 있습니다: '$s'")
        }

        // 음수가 있을 경우
        val negatives = ints.filter { it < 0 }
        if (negatives.isNotEmpty()) {
            throw IllegalArgumentException("형식 오류: 음수가 있습니다 : ${negatives.joinToString(", ")}")
        }

        // 모든 검증을 통과한 경우 Int 리스트 반환
        return ints
    }

}