package org.example;

public class Flow {
    /**
     * 예외처리 관련
     *
     * 프로파일 관련
     * - local일때 workbook 생성 테스트 실제 워크북 생성
     *
     * 단일 네거티브
     * column 관련
     * - 아무 어노테이션도 없는 DTO를 사용하면 NotFoundExcelColumnAnnotationException
     *
     * formula 관련
     * - 없는 fieldName일때 NoSuchFieldNameException
     *
     * style 관련
     * - @ExcelColumn은 없는데 @ExcelStyle 은 존재할때 이건 아무런 예외가 안나야함 그냥 적용이 아예안되어야함
     *
     * DTO 하나만 있으면될듯
     * formula exprssion 말고 이름을 좀 다르게 해야할듯?
     * @Formula(expression = )
     * @ExcelStyle(header , body) = [헤더 = 색은 녹색, 일반 테두리] [바디 = 중앙정렬, bold 체]
     * TESTDTO{
     *      @ExcelStyle(header = 빨강, 테두리 일반)
     *      @ExcelColumn(headerName = "이름")
     *      private String name;
     *
     *      @ExcelColumn(headerName = "주소")
     *      private String address;
     *
     *      @ExcelStyle(header, body)
     *      private String email;
     *
     *      @ExcelStyle(body = 파랑)
     *      @ExcelColumn(headerName = 나이")
     *      private int age;
     *
     *     @ExcelStyle(body = 파랑)
     *     @ExcelColumn(headerName = "여부")
     *     private boolean is;
     *}
     * --- 만들꺼
     * MySXXFSworkbook class -> 기본적 설정 여기서 내가 무슨 워크북 쓸지 결정
     *
     * Style
     *  - BlueBackgroundStyle
     *  - greenBackgroundBorderThinStyle
     *  - defaultHeaderStyle
     *  - defaultBodyStyle
     *
     * formula
     *  - Counter -> countif(is, "*true")
     *  - Average -> avg(age) 두개 이상이 정상적으로 들어가는지 확인하는 목적
     *
     *  - nonFieldNameFormula 없는 필드네임으로 만드는 formula class 이건 오류 확인용
     *
     *
     *
     *
     * 통합 포지티브
     * - 정상적으로 formula가 생성되는지
     * - 정상적으로 Style이 적용되어 있는지
     *
     *
     * 02-09
     * TODO
     * 테스트 완성
     * 1. 백그라운드 설정완료
     * 2. 전반적으로 정상적으로 들어가는지 테스트 완성
     *      - 아무것도 없는 DTO 일때 예외 테스트 NotFoundExcelColumnAnnotationException
     *      - 컬럼 인덱스 (확인)
     *      - 컬럼 스타일 (확인)
     *      - 수식 (확인)
     *
     * 3. 세분화 테스트
     *      - formula 예외 field 이름 없을 경우 예외 / DTO 만들기
     *      - @ExcleColumn이 없는데 @ExcelStyle만 존재하면 아예 resource를 안만들어야함 / 통합 DTO사용
     *      - @ExcelStyle 만 존재할때 resource에 안들어가는지 확인 맵에 아예 존재하면 안된다. / 통합 DTO사용
     *
     * 4. dataformat 만들기
     *
     *
     * 포멧 적용
     * 1 방식
     * @Style(dataformat = "yyyy-mm-dd")
     * -> 전체 스타일의 해당되는 서식 적용
     *  문제점
     *  1. 해당 스타일이 전체적으로 적용되는 문제가 있음
     *  2. styleResource에서 작업을 해야하는데 이친구가 해당 스타일이 존재할때만 필드에 있는 스타일을 만드는데 포멧만 존재하면 안만듦
     *  3. 이걸해결하려고 여러개를 지정해야하는데 문제가 있음
     *
     * @ExcelColumn(dataformat = "yyyy-dd-mm")
     * 이게 좋아보이긴한데
     * 1. 스타일을 또 만들어 넣어줘야함
     *
     *TODO 해결이 안된다 format
     *
     *
     *
     */
}
