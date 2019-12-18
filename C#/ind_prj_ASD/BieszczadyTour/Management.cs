using System;
using System.Collections.Generic;
using BieszczadyTour.models;
using BieszczadyTour.src;

namespace BieszczadyTour
{
    class Management
    {
        static void Main(string[] args)
        {
            FileValidator fileValidator = new FileValidator();
            List<Tuple<string, NodeConnection>> NodesConnectionsList = fileValidator.ReadFiles(args);

            Graph graph = new Graph(NodesConnectionsList, fileValidator.PointsOnMap, fileValidator.PointsToVisit, args[1]);

            PathOptimiser path_optimiser = new PathOptimiser();
            Tour tour = path_optimiser.OptimiseTour(graph, args[1], fileValidator.PointsToVisit, NodesConnectionsList);

            ResultsWriter results_writer = new ResultsWriter(tour);
            results_writer.PrintResultsToFile();
        }
    }
}
