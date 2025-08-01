package principal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Moneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean salir = true;
        while (salir) {
            String msj = """
                    ************************************************************
                    Sea bienvenido/a al conversor de Moneda =]
                    
                    1) Dolar =>> Peso Argentino
                    2) Peso argentino =>> Dolar
                    3) Dolar =>> Real brasileño
                    4) Real brasileño =>> Dolar
                    5) Dolar =>> Peso colombiano
                    6) Peso colombiano =>> Dolar
                    7) Salir
                    Elija una opcion valida:
                    ************************************************************
                    """;

            ArrayList<Moneda> monedas = new ArrayList<>();
            Scanner sc = new Scanner(System.in);
            double resultado = 0;


            Moneda monedaUSD = null;
            Moneda monedaARS = null;
            Moneda monedaBRL = null;
            Moneda monedaCOP = null;


            System.out.println(msj);
            String eleccion = sc.nextLine();

            switch (eleccion) {
                case "1":// Dolar =>> Peso Argentino
                    if (!verificarCreado(monedas, "USD")) {
                        monedaUSD = RecogerValores("USD");
                        monedas.add(monedaUSD);
                    }
                    convertirMoneda(monedaUSD, "ARS");
                    break;
                case "2": // Peso argentino =>> Dolar
                    if (!verificarCreado(monedas, "ARS")) {
                        monedaARS = RecogerValores("ARS");
                        System.out.println("holaaa");
                        monedas.add(monedaARS);
                    }
                    convertirMoneda(monedaARS, "USD");
                    break;

                case "3": //Dolar =>> Real brasileño
                    if (!verificarCreado(monedas, "USD")) {
                        monedaUSD = RecogerValores("USD");
                        monedas.add(monedaUSD);
                    }

                    convertirMoneda(monedaUSD, "BRL");
                    break;
                case "4": //Real brasileño =>> Dolar
                    if (!verificarCreado(monedas, "BRL")) {
                        monedaBRL = RecogerValores("BRL");
                        monedas.add(monedaBRL);
                    }

                    convertirMoneda(monedaBRL, "USD");
                    break;
                case "5": //Dolar =>> Peso colombiano
                    if (!verificarCreado(monedas, "USD")) {
                        monedaUSD = RecogerValores("USD");
                        monedas.add(monedaUSD);
                    }

                    convertirMoneda(monedaUSD, "COP");
                    break;
                case "6": //Peso colombiano =>> Dolar
                    if (!verificarCreado(monedas, "COP")) {
                        monedaCOP = RecogerValores("COP");
                        monedas.add(monedaCOP);
                    }

                    convertirMoneda(monedaCOP, "USD");
                    break;

                case "7":
                    System.out.println("Hasta luego");
                    salir = false;
                    break;
            }
        }
    }

    public static boolean verificarCreado(ArrayList<Moneda> monedas, String nombre) {
        for (Moneda moneda : monedas) {
            if (moneda.getNombre().equals(nombre)) {
                System.out.println(moneda.getNombre());
                return true;

            }
        }
        return false;
    }

    public static Moneda RecogerValores(String nombre) throws IOException, InterruptedException {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();

        String direccion = "https://v6.exchangerate-api.com/v6/7c47fcb7788ffcf23b5021da/latest/"+nombre;
        //System.out.println(direccion);
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response = cliente
                .send(peticion, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        Moneda conversiones = gson.fromJson(json, Moneda.class);
        return conversiones;
    }

    public static void convertirMoneda(Moneda moneda, String nombre){
        Scanner sc = new Scanner(System.in);
        double valor = moneda.getConversiones().get(nombre);
        System.out.println("Ingresa el valor que deseas convertir: ");
        double cantidad = sc.nextDouble();
        double resultado = valor * cantidad;
        System.out.printf("""
                        1 %s = %.6f %s
                        El resultado es: %.6f %s
                        """,moneda.getNombre(), valor ,nombre, resultado,nombre);

    }
}