using System;
using System.Collections.Generic;

using BieszczadyTour.models;

namespace BieszczadyTour.src
{
    class PathOptimiser
    {
        public Tour OptimiseTour(Graph graph, string StarterPointId, List<string> PointsToVisit, List<Tuple<string, NodeConnection>> ConnectionsList)
        {
            Node StarterNode = graph.NodesList.Find(x => x.Id == StarterPointId);

            NearestNeighbour nearestNeighbour = new NearestNeighbour(graph, PointsToVisit);
            Tour official_tour = nearestNeighbour.GenerateTour(StarterNode, ConnectionsList);

            return official_tour;
        }
    }
}
