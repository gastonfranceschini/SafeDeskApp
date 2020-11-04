package com.ort.SafeDesk.Model;

import java.util.List;

public class ReporteDTO {

    private List<String> campos;
    private List<String> valores;
    private boolean formatoAlternativo;
    public ReporteDTO(List<String> campos, List<String> valores, boolean formatoAlternativo){
        this.campos = campos;
        this.valores = valores;
        this.formatoAlternativo = formatoAlternativo;
    }


}
