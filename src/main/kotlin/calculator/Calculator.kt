package calculator

class Calculator(
    private val custom: CustomSeparator = CustomSeparator(),
    private val default: DefaultSeparator = DefaultSeparator()
) {
    fun add(input: String): Int {
        // 입력이 비어있을 경우
        if (input.isBlank()) return 0

        // 커스텀 구분자 시도
        //  - CustomSeparator.isCustomAndExtraction(input) 내부에서
        //    "//{구분자}\n{본문}" (실제 \n) 우선, 없으면 "\\n"도 허용하도록 처리되어 있어야 함.
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
        toIntsOrThrow(                       // 문자열 토큰을 Int로 바꾸고 유효성 검사
            body.split(Regex("[,:]"))        // 쉼표와 콜론으로 문자열 분리
                .map { it.trim() }           // 각 토큰 공백 제거
        ).sum()

    // 커스텀 구분자 합계
    private fun sumCustom(body: String, delimiter: String): Int =
        toIntsOrThrow(
            body.split(delimiter)            // 전달된 커스텀 구분자 문자열로 분리
                .map { it.trim() }
        ).sum()

    // 문자열 토큰 검증
    private fun toIntsOrThrow(tokens: List<String>): List<Int> {

        // "//;\n" (또는 "//;\\n") 처럼 본문이 비어 있으면 합 0
        if (tokens.isEmpty()) return emptyList()

        // 구분자 사이에 비어있는 곳이 있을 경우 → 빈 숫자 토큰으로 간주
        if (tokens.any { it.isEmpty() }) {
            // ※ 테스트 호환을 위해 메시지를 "비어 있는 숫자 토큰" 표현으로 통일
            throw IllegalArgumentException("형식 오류: 비어 있는 숫자 토큰이 있습니다.")
        }

        // 구분자 사이에 숫자가 아닌 토큰이 있을 경우
        val ints = tokens.map { s ->
            s.toIntOrNull() ?: throw IllegalArgumentException(
                // ※ 테스트 호환: "숫자가 아닌 토큰이 포함되어 있습니다: '...'"
                "형식 오류: 숫자가 아닌 토큰이 포함되어 있습니다: '$s'"
            )
        }

        // 음수가 있을 경우
        val negatives = ints.filter { it < 0 }
        if (negatives.isNotEmpty()) {
            // ※ 테스트 호환: "음수는 허용되지 않습니다: -1, -2"
            throw IllegalArgumentException(
                "형식 오류: 음수는 허용되지 않습니다: ${negatives.joinToString(", ")}"
            )
        }

        // 모든 검증을 통과한 경우 Int 리스트 반환
        return ints
    }
}