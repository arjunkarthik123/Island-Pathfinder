# Assignment A2: Mesh Generator

  - Kyle Hagerman [hagermak@mcmaster.ca]
  - Arjun Karthik [kartha4@mcmaster.ca]
  - Derron Li [li1578@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with the feature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To visualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tools like `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

-- Each feature compiles properly on maven, functioning both on the generation side and the visualizing side, following all necessary business requirements.  --

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
|  F01  |  Draw segments between vertices to visualize squares  |  Everyone  |  02/03/23  |  02/05/23  |  D  |
|  F02  |  Mesh generates at a precision model of 2 decimal places  |  Derron  |  02/06/23  |  02/08/23  |  D  |
|  F03  |  Mesh has minimal vertices  |  Kyle  |  02/03/23  |  02/07/23  |  D  |
|  F04  |  Mesh has minimal segments  |  Kyle  |  02/05/23  |  02/09/23  |  D  |
|  F05  |  Mesh has minimal polygons  |  Arjun/Derron  |  02/09/23  |  02/14/23  |  D  |
|  F06  |  Polygon has changeable colour transparency  |  Arjun  |  02/09/23  |  02/14/23  |  D  |
|  F07  |  Vertex has changeable colour transparency  |  Kyle  |  02/14/23  |  02/15/23  |  D  |
|  F08  |  Segment has changeable colour transparency  |  Kyle  |  02/15/23  |  02/15/23  |  D  |
|  F09  |  Polygon has changeable thickness  | Arjun |  02/09/23  |  02/14/23  |  D  |
|  F10  |  Vertex has changeable thickness  |  Kyle  |  02/13/23  |  02/13/23  |  D  |
|  F11  |  Segment has changeable thickness  |  Kyle  |  02/13/23  |  02/13/23  |  D  |
|  F12  |  User can switch into debug mode for different colour display  |  Derron  |  02/20/23  |  02/22/23  |  D  |
|  F13  |  Debug mode displays the centroids in a red colour  |  Derron/Arjun  |  02/20/23  |  02/22/23  |  D  |
|  F14  |  Debug mode displays the neighbourhood relations in grey |  Derron/Arjun  |  02/20/23  |  02/22/23  |  D  |
|  F15  |  Debug mode displays the polygon, vertices, and segments in black and purple |  Derron  |  02/20/23  |  02/22/23  |  D  |
|  F16  |  User can switch between grid meshes or irregular mesh |  P  |  02/22/23 |  |  P  |
|  F17  |  Points are randomly generated to construct the irregular meshes  |  P  |  02/22/23  |  |  P  |
|  F18  |  Voronoi diagram for each point is calculated  |  P  |  02/22/23  |    |  P  |
|  F19  |  Each randomly generated point is converted to a centroid for each polygon  |  P  |  02/22/23  |   |  P  |
|  F20  |  User can select to relax the mesh or not  |  P  |  02/22/23  |   |  P  |
|  F21  |  Neighbourhood relations are computed using Delaunay's triangulation |  P  |  02/22/23  |   |  P  |
|  F22  |  User can enter "help" mode if a -h or --help argument is inputted when running generator |  P  |  02/22/23  |   |  P  |
|  F23  |  User can select the number of polygons they wish to generate through the command line  |  P  |  02/22/23  |   |  P  |


|  F  |  Template  |  S  |  .  |  .  |  B  |


## ideas
new class to handle user input (is given dotgen and string args[], returns a mesh)
call dotgen, which accepts all the arguments, but new class sets values to default for ones that are not provided

to do:
1. new class to do all this
2. modify dotgen and subsequent classes to use these new values passed in rather than hard coded


# Documentation
To get thickness of vertices and lines, extract the thickness from the properties list using the key 'thickness'.

## Format for user input when running generator main file
- Different arguments accepted depending on if second arg is for a grid or an irregular mesh.  
- Arguments in '' specify exact input required to work (eg. must input 'g' as second arg to use a grid).  
- For help mode, enter '-h' or '--help' as the first argument (and it will be the only one considered if used).  
- For changing transparency or thickness from default value, consult legend for command to use, and then insert value wanted after a space.  
- For relaxation level, enter '0' for not relaxed, and '1' for relaxed.

## Legend
- -pa = polygon transparency (0 to 255) (default = 255)
- -sa = segment transparency (0 to 255) (default = 255)
- -va = vertex transparency (0 to 255) (default = 255)
- -pt = polygon thickness (default = 0.5)
- -st = segment thickness (default = 0.5)
- -vt = vertex thickness (default = 3)

## Grid
outputFile 'g' (extra commands from legend)

### Example
User wants output file sample.mesh, polygon transparency of 200, and segment thickness of 10
sample.mesh g -pa 200 -st 10

## Irregular
outputFile 'i' numOfPolygons relaxationLevel (extra commands from legend)

### Example
User wants output file sample.mesh, 200 polygons, relaxed mesh, polygon transparency of 200, and segment thickness of 10
sample.mesh i 200 1 -pa 200 -st 10

## Format for user input when running visualizer main file
Add '-X' after first 2 arguments to enter debug mode. Anything else is taken as default visualization.
