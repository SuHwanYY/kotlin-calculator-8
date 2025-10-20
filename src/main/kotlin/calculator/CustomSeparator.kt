package calculator

// 커스텀 구분자 정보
data class CustomInfo(val delimiter: String, val body: String)

class CustomSeparator {

    // 커스텀 구분자 판별 및 검증
    fun isCustomAndExtraction(input: String): CustomInfo? {
        if (!input.startsWith("//")) return null

        // \n이 있는지 판별
        val nl = input.indexOf("\\n")
        if (nl == -1) {
            throw IllegalArgumentException("형식 오류: \\n이 존재하지 않습니다.")
        }

        // 구분자 추출
        val delimiter = input.substring(2, nl)
        if (delimiter.isEmpty()) {
            throw IllegalArgumentException("형식 오류: 커스텀 구분자가 없습니다.")
        }

        // 문자열 추출
        val body = input.substring(nl + 2)
        return CustomInfo(delimiter, body)
    }
}