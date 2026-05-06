package hn.com.tigo.comodatos.soap.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsRequest {

    @NotBlank(message = "precio_promo is required")
    private String precio_promo;

    @NotBlank(message = "meses_permanencia is required")
    private String meses_permanencia;

    @NotBlank(message = "codigo_modelo is required")
    private String codigo_modelo;

    private String tipo_cliente;

    private String current_date;

    private Integer financiado;

    private Integer gross;
}