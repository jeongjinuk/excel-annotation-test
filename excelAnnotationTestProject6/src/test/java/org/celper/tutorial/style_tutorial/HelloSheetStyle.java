package org.celper.tutorial.style_tutorial;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.celper.core.style.SheetStyleConfigurer;
import org.celper.core.style.builder.SheetStyleBuilder;

/**
 * 전체 시트의 기본적인 설정과 스타일을 지정할 수 있습니다.
 *
 */
public class HelloSheetStyle implements SheetStyleConfigurer {
    @Override
    public void config(SheetStyleBuilder builder) {
        builder
                .isVerticallyCenter(true)
                .isFitToPage(true)
                .setDefaultColumnWidth(30)
                .setDefaultRowHeight((short) 500)
        .cellStyleBuilder()
                .setAllOfBorder(BorderStyle.HAIR);

    }
}
