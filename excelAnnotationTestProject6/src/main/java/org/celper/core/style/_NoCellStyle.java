package org.celper.core.style;

import org.celper.core.style.builder.CellStyleBuilder;

/**
 * The type No cell style.
 */
public final class _NoCellStyle implements CellStyleConfigurer {
    private _NoCellStyle() {
    }

    @Override
    public void config(CellStyleBuilder builder) {
        // do nothing
    }
}