# rrcBasedClassifiers
The package impements classifiers based on the concept of the Randomized Reference Classifier. 
The original idea was described in:

@article{Woloszynski2011,
  author = {Woloszynski, Tomasz and Kurzynski, Marek},
  doi = {10.1016/j.patcog.2011.03.020},
  issn = {0031-3203},
  journal = {Pattern Recognition},
  month = oct,
  number = {10-11},
  pages = {2656-2668},
  publisher = {Elsevier BV},
  title = {A probabilistic model of classifier competence for dynamic ensemble selection},
  volume = {44},
  year = {2011}
}


## Building

The build system is changed to Maven. To compile the package use:

```console
mvn package
```
To to build the package using ant type:

```console
mvn ant:ant
```
The above generates the ant buildfile. Then just type

```console
ant package
