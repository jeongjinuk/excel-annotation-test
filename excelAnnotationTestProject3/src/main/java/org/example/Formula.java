package org.example;

import org.example.templates.formula.FormulaTemplate;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Repeatable(value = Formulas.class)
public @interface Formula {
    Class<? extends FormulaTemplate>[] expression();

}
