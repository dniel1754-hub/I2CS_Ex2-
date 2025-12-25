Map Class Overview
This class implements the Map2D interface, representing a 2D grid (raster matrix) of integers that can function as a canvas, a map, or a maze.

Initialization & Management
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

האם תרצה שאוסיף הסבר על אלגוריתם ה-BFS הספציפי שהשתמשת בו ב-shortestPath?
