# Análisis de Temperaturas por Ciudad en Java

Este proyecto es una aplicación desarrollada en Java que permite analizar temperaturas diarias registradas en distintas ciudades del país, utilizando programación funcional con `Streams` y visualización con gráficos de barras.

## Funcionalidades

- Carga de datos desde un archivo CSV con el formato: `Ciudad,Fecha,Temperatura`.
- Cálculo del promedio de temperatura por ciudad en un rango de fechas ingresado por el usuario.
- Visualización gráfica (gráfico de barras) de estos promedios utilizando JFreeChart.
- Identificación de la ciudad más calurosa y la menos calurosa en una fecha específica proporcionada por el usuario.

## Tecnologías utilizadas

- **Java 8+**
- **Librería [JFreeChart](http://www.jfree.org/jfreechart/)** para generar gráficos.

## Instrucciones de ejecución

1. Asegúrate de tener Java instalado (versión 8 o superior).
2. Coloca el archivo `Temperaturas.csv` dentro de la carpeta `Datos/` en la raíz del proyecto.
3. Asegúrate de tener la librería JFreeChart añadida
4. Compila y ejecuta el proyecto desde la terminal
5. Ingresa las fechas solicitadas en el formato dd/MM/yyyy cuando el programa lo indique.

## Formato del CSV
El archivo Temperaturas.csv debe seguir el siguiente formato:

- Ciudad,Fecha,Temperatura
- Bogotá,01/01/2023,17.3
- Bogotá,02/01/2023,16.8
- Medellín,01/01/2023,22.5
- ...

## Ejemplo de uso

Ingrese fecha inicial (dd/MM/yyyy): 01/01/2023  
Ingrese fecha final (dd/MM/yyyy): 05/01/2023

Promedio de temperaturas por ciudad:
Bogotá: 17.0°C  
Medellín: 22.3°C  

Se abre una ventana con gráfico de barras

Ingrese una fecha específica (dd/MM/yyyy): 02/01/2023  

Temperatura extrema para 2023-01-02:  
Ciudad más calurosa: Medellín (23.1°C)  
Ciudad menos calurosa: Bogotá (16.8°C)

# Autores
- Samuel Velásquez Berrio 1192774813
- Max Daniel Pérez Quintero 8028514
- Diego Alejandro Osorio Jimenez 1007291052

