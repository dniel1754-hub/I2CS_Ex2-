<img width="1411" height="1006" alt="צילום מסך 2025-12-25 154418" src="https://github.com/user-attachments/assets/2612668e-6fcb-4699-bb87-5339120a2cc7" />
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
- Initialization & Management
Map(w, h, v) / init: Initializes a map with specified width and height, filling every pixel with a default value.

getMap: Returns a deep copy of the underlying 2D integer array.

getWidth / getHeight: Retrieve the dimensions of the map.

isInside: Checks if a given coordinate or Pixel2D point is within the map's boundaries.

Pixel Operations
getPixel / setPixel: Access or modify the color/value of a specific pixel at given coordinates.

sameDimensions: Compares the current map's size with another Map2D object.

equals: Determines if two maps are identical in both dimensions and pixel content.

Mathematical & Transformation Tools
addMap2D: Performs element-wise addition of another map's values to the current map.

mul: Multiplies every pixel value in the map by a given scalar.

rescale: Adjusts the map's dimensions by a scale factor, preserving existing data where possible.

Drawing Tools
drawCircle: Colors all pixels within a specified radius from a center point.

drawLine: Draws a line between two points using a linear interpolation algorithm.

drawRect: Fills a rectangular area defined by two corner pixels.

Algorithms & Pathfinding
getNeighbers: Identifies the adjacent pixels (up, down, left, right), with an option for "cyclic" (wrap-around) connectivity.

fill: Implements the Flood-fill algorithm to change the color of a connected area starting from a specific pixel.

allDistance: Calculates a distance map where each pixel represents its shortest path distance from a starting point, avoiding obstacles.

shortestPath: Computes the shortest path between two pixels as an array of points, utilizing a BFS-based approach.

## How to Run
1. Ensure you have the `StdDraw` library in your classpath.
2. Run the `Ex2_GUI` class to visualize maps.
3. Use `MapTest` to run the suite of JUnit tests.

## Visualizations
![Map Visualization](images/map_example.png)
*adding a photo of the output.*

## Authors
- Daniel REUVEN - 212750947<img width="1411" height="1006" alt="צילום מסך 2025-12-25 154418" src="https://github.com/user-attachments/assets/5483a63c-733f-44c4-9bd9-0c85763df1cf" />

