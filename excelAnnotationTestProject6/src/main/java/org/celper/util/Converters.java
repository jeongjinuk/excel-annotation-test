package org.celper.util;

import org.apache.poi.ss.usermodel.DateUtil;
import org.modelmapper.AbstractConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * The type Converters.
 */
public final class Converters {
    private Converters(){
        throw new IllegalStateException();
    }

    /**
     * The type String to boolean.
     */
    static final class StringToBoolean extends AbstractConverter<String, Boolean>{
        @Override
        protected Boolean convert(String s) {
            return Boolean.valueOf(s);
        }
    }

    /**
     * The type String to double.
     */
    static final class StringToDouble extends AbstractConverter<String, Double>{
        @Override
        protected Double convert(String s) {
            try {
                return Double.parseDouble(s);
            }catch (NumberFormatException ignored){
                // 변환할 수 없음
            }
            return 0.0;
        }
    }

    /**
     * The type Double to date.
     */
    static final class DoubleToDate extends AbstractConverter<Double, Date>{
        @Override
        protected Date convert(Double d) {
            return DateUtil.getJavaDate(d);
        }
    }

    /**
     * The type Double to local date time.
     */
    static final class DoubleToLocalDateTime extends AbstractConverter<Double, LocalDateTime> {
        @Override
        protected LocalDateTime convert(Double d) {
            return DateUtil.getLocalDateTime(d);
        }
    }

    /**
     * The type Double to local date.
     */
    static final class DoubleToLocalDate extends AbstractConverter<Double, LocalDate> {
        @Override
        protected LocalDate convert(Double d) {
            return DateUtil.getLocalDateTime(d).toLocalDate();
        }
    }

    /**
     * The type Double to local time.
     */
    static final class DoubleToLocalTime extends AbstractConverter<Double, LocalTime> {
        @Override
        protected LocalTime convert(Double d) {
            return DateUtil.getLocalDateTime(d).toLocalTime();
        }
    }
}
