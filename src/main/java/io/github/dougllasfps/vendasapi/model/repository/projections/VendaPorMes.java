package io.github.dougllasfps.vendasapi.model.repository.projections;

import com.ibm.icu.math.BigDecimal;

public interface VendaPorMes {
    Integer getMes();

    BigDecimal getValor();

}
