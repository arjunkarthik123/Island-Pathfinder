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
|  F15  |  Debug mode displays the polygon, vertices, and segments in black and white |  Derron  |  02/20/23  |  02/22/23  |  D  |
|  F  |  Template  |  S  |  .  |  .  |  B  |


## ideas
empty for now



## stuff to remember from teams

- You can organize your code "as you wish". The generator and visualizer are yours now, the starting pack was just here to demonstrate how to read/write the Mesh structure.
  As you need to work with ordered indexes, and a Set is by definition not ordered, you'll have to "do something about it", eventually.
- the centroid of a polygon is not a “random” point but its… centroid. It should be defined as a vertex (like any other point), but will not be used as a corner of any polygon.
- You are not allowed to modify anything in the “it” package. This part of the source code is off limit, and you should consider it a black box. It is up to you to find the right way to _develop_ your generator and visualizer, taking this assumption as a fixed point.
- Polygons do have indexes, based on the order that are added into the mesh (like segments and vertices). That being said, contrarily to vertices and segment, their indexes are not used as references elsewhere.
- 


## Documentation
To get thickness of vertices and lines, extract the thickness from the properties list using the key 'thickness'. 
Change the thickness in the generator code itself if you would like.

Transparency values are from 0 to 255.

