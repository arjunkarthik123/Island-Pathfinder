# Assignment A4: Evolution

Arjun Karthik [kartha4@mcmaster.ca]

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A4`

To install the different tooling on your computer, simply run:

```
mosser@azrael A4 % mvn install
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

#### Backlog A4

### Definition of Done

-- Each feature compiles properly on maven, functioning both on the generation side and the visualizing side, following all necessary business requirements.  --

## Documentation

## Legend
- -i = input file path to use
- -o = output file path to use
- -mode = (optional, defaults to circle) either 'lagoon' for lagoon mode or valid shape
- -heatmap = (optional) heatmap type to use for visualizing properties (see list of valid heatmaps for more details)
- -elevation = (defaults to plains) elevation profile to use (see list of valid profiles for more details)
- -soil = (defaults to wet profile) soil absorption profile to use, determines how much moisture is absorbed from water sources
- -aquifer = maximum number of aquifers to create (default = 0)
- -seed = seed to use which will always generate the same map for the same seed
- -biome = Whittaker diagram to take biomes from when visualizing (defaults to only visualizing with general green land tiles)
- -lake = maximum number of lakes to generate (default = 0)
- -river = maximum number of rivers to generate (default = 0)
- -city = maximum number of cities to generate and visualize (default = 0)

### Important Pathfinding Notes
1. This new release brings a pathfinding algorithm, such that the shortest path between two polygon centroids can be 
   computed and displayed as a star network. 
2. Cities are visualized on screen in a yellow colour, with red lines indicating the road network that connects these 
   cities together. 
3. Below is a sample command that you can use to generate and visualize cities using the island generator. Note that 
   you may add different features that are listed above or remove features, along with being able to customize the 
   number of cities that you wish to generate.

Prior to running the program: run mvn clean install in your terminal. 
cd island
java -jar island.jar -o island.mesh -i ../generator/input.mesh -mode circle -elevation volcano -city 10 -river 4 -lake 3
cd ..
cd visualizer
java -jar visualizer.jar -i ../island/island.mesh -o island.svg

One thing to note in this release is that with some island generations, there may be roads that are generated that 
cross rivers and/or lakes. This can be accounted for by urban planners by the use of bridges to ensure travel between
cities. 

The backlog of features can be found on my github repository through the use of a Kanban board. 

If you would still like to run the basic lagoon from the previous release, the commands for it are noted below:

cd island
java -jar island.jar -o lagoon.mesh -i ../generator/input.mesh -mode lagoon
cd ..
cd visualizer
java -jar visualizer.jar -i ../island/lagoon.mesh -o lagoon.svg

Below are the noted features from the previous release and their associated details: 

### List of valid shapes
1. 'circle' (default)
2. 'hexagon'

### List of valid heatmaps
1. 'elevation' : Shows elevation of polygons (default if non-valid heatmap is input when heatmap command is used)
2. 'moisture' : Shows moisture of polygons
3. 'vertexelevation' : Shows elevation of vertices

### List of valid elevation profiles
1. 'plains' (default) : Randomly generated elevations per tile in low range
2. 'volcano' : Largest elevation in middle of island, slopes down

### List of valid soil Whittaker profiles
1. 'arctic' : contains Dry, Moist, Wet, and Rain tundra tiles
2. 'warmtemperate' : contains Desert, Desert Scrub, Woodland, Dry forest, Moist Forest, Wet Forest, and Rain forest tiles

### List of valid soil absorption profiles
1. 'wet' (default) : Polygons within a large range gain moisture from surrounding water sources
2. 'dry' : Has 1/4 the range of wet

### Information regarding lakes and rivers
1. Endorheic lakes do not count towards the maximum number of lakes
2. Rivers which randomly generate at a vertex where they cannot move any further just begin and end there
