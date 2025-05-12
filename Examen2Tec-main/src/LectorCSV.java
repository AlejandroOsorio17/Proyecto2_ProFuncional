import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

public class LectorCSV {

    public static List<RegistroTemperatura> cargarDatos(String rutaArchivo) {
        List<RegistroTemperatura> registros = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {
            br.readLine(); // Saltar encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String ciudad = datos[0].trim();
                LocalDate fecha = LocalDate.parse(datos[1].trim(), formatter);
                double temperatura = Double.parseDouble(datos[2].trim());
                registros.add(new RegistroTemperatura(ciudad, fecha, temperatura));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public static List<String> obtenerCiudadesUnicas(String rutaArchivo) {
        Set<String> ciudadesSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {
            br.readLine(); 
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                ciudadesSet.add(datos[0].trim()); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> ciudades = new ArrayList<>(ciudadesSet);
        Collections.sort(ciudades); 
        return ciudades;
    }

    public static void mostrarPromediosPorCiudad(List<RegistroTemperatura> registros, LocalDate desde, LocalDate hasta) {
        Map<String, Double> promedios = registros.stream()
                .filter(r -> !r.getFecha().isBefore(desde) && !r.getFecha().isAfter(hasta))
                .collect(Collectors.groupingBy(
                        RegistroTemperatura::getCiudad,
                        Collectors.averagingDouble(RegistroTemperatura::getTemperatura)
                ));

        System.out.println("\nPromedio de temperaturas por ciudad:");
        promedios.forEach((ciudad, promedio) -> 
            System.out.printf("%s: %.2f°C%n", ciudad, promedio));

        mostrarGraficaPromedios(promedios);
    }

    public static void mostrarGraficaPromedios(Map<String, Double> promedios) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        promedios.forEach((ciudad, promedio) -> {
            dataset.addValue(promedio, "Temperatura", ciudad);
        });

        JFreeChart grafico = ChartFactory.createBarChart(
                "Promedio de Temperatura por Ciudad",
                "Ciudad",
                "Temperatura (°C)",
                dataset
        );

        ChartPanel panel = new ChartPanel(grafico);
        JFrame ventana = new JFrame("Gráfico");
        ventana.setContentPane(panel);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void mostrarExtremosPorFecha(List<RegistroTemperatura> registros, LocalDate fecha) {
        List<RegistroTemperatura> filtrados = registros.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .collect(Collectors.toList());

        if (filtrados.isEmpty()) {
            System.out.println("No hay datos para la fecha " + fecha);
            return;
        }

        RegistroTemperatura max = filtrados.stream()
                .max(Comparator.comparing(RegistroTemperatura::getTemperatura)).orElse(null);
        RegistroTemperatura min = filtrados.stream()
                .min(Comparator.comparing(RegistroTemperatura::getTemperatura)).orElse(null);

        System.out.println("\nTemperatura extrema para " + fecha + ":");
        System.out.printf("Ciudad más calurosa: %s (%.2f°C)%n", max.getCiudad(), max.getTemperatura());
        System.out.printf("Ciudad menos calurosa: %s (%.2f°C)%n", min.getCiudad(), min.getTemperatura());
    }

    public static void main(String[] args) {
        String rutaArchivo = "Datos/Temperaturas.csv";
        List<RegistroTemperatura> registros = cargarDatos(rutaArchivo);

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Ingrese fecha inicial (dd/MM/yyyy): ");
        LocalDate desde = LocalDate.parse(scanner.nextLine(), formato);

        System.out.print("Ingrese fecha final (dd/MM/yyyy): ");
        LocalDate hasta = LocalDate.parse(scanner.nextLine(), formato);

        mostrarPromediosPorCiudad(registros, desde, hasta);

        System.out.print("\nIngrese una fecha específica (dd/MM/yyyy): ");
        LocalDate fechaConsulta = LocalDate.parse(scanner.nextLine(), formato);

        mostrarExtremosPorFecha(registros, fechaConsulta);
    }
}
