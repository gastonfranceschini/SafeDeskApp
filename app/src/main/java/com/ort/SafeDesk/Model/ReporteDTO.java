package com.ort.SafeDesk.Model;

import java.util.List;

public class ReporteDTO {

    private List<String> campos;
    private List<String> valores;

    public ReporteDTO(List<String> campos, List<String> valores){
        this.campos = campos;
        this.valores = valores;
    }


}
