package modelos;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Moneda {
    @SerializedName("base_code")
    private String nombre;
    @SerializedName("conversion_rates")
    private Map<String, Double> conversiones;

    public Map<String, Double> getConversiones() {
        return conversiones;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Moneda{" +
                "conversion=" + conversiones +
                '}';
    }
}