# Ex2 - Map2D Analysis and Visualization

## Project Overview
This project implements a grid-based map analysis system. It provides tools for calculating distances, finding shortest paths between points, and performing area fills, while supporting both standard and cyclic (wrap-around) map behaviors.

## Features
- **Map Representation:** Efficient 2D array-based map implementation.
- **BFS Algorithms:** - `allDistance`: Calculates the shortest distance from a start point to all reachable areas using Breadth-First Search.
  - `shortestPath`: Computes the actual path between two pixels.
- ** Fill:** A classic algorithm to fill connected areas with a new color.
- **GUI:** Visual representation of the map and calculated paths using the `StdDraw` library.
- **File I/O:** Save and load maps from simple text files.

## How to Run
1. Ensure you have the `StdDraw` library in your classpath.
2. Run the `Ex2_GUI` class to visualize maps.
3. Use `MapTest` to run the suite of JUnit tests.

## Visualizations
![Map Visualization](images/map_example.png)
*adding a photo of the output.*

## Authors
- Daniel REUVEN - 212750947
