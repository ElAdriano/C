using System;
using System.Collections.Generic;
using System.Linq;
using BieszczadyTour.exceptions;

namespace BieszczadyTour.models
{
    class Graph
    {
        public readonly List<Node> NodesList;
        private Dictionary<Node, Node> NodeRepresentantsInCoherentComponent;
        private Dictionary<Node, int> Depth;

        public Graph(List<Tuple<string, NodeConnection>> Connections, List<Node> PointsOnMap, List<string> PointsToVisit, string StarterPointId)
        {
            NodeRepresentantsInCoherentComponent = new Dictionary<Node, Node>();

            foreach (Node node in PointsOnMap)
            {
                NodeRepresentantsInCoherentComponent[node] = node;
            }

            NodesList = new List<Node>();
            for (int i = 0; i < Connections.Count(); i++)
            {
                Node node = PointsOnMap.Find(x => x.Id == Connections[i].Item1);

                if (!NodesList.Contains(node))
                {
                    NodesList.Add(node);
                }
                node.ConnectNode(Connections[i].Item2);
                UnionNodesToCoherentComponent(node, Connections[i].Item2.ConnectedNode);
            }

            try
            {
                if (PointsToVisit.Count() > 0)
                {
                    Node GraphRepresentant = FindRepresentantInCoherentComponent(PointsOnMap.Find(x => x.Id == StarterPointId));

                    for (int i = 0; i < PointsToVisit.Count(); i++)
                    {
                        Node node = PointsOnMap.Find(x => x.Id == PointsToVisit[i]);
                        Node representant = FindRepresentantInCoherentComponent(node);
                        if (!GraphRepresentant.Equals(representant))
                        {
                            throw new InvalidDataConsistency("Non consistent data in files. Cannot go to all the places mentioned in optional file.");
                        }
                    }
                }
            }
            catch (InvalidDataConsistency e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(2);
            }

        }

        private void UnionNodesToCoherentComponent(Node node1, Node node2)
        {
            Node pointer1 = node1;
            Node pointer2 = node2;

            int depth_node1 = 0;
            int depth_node2 = 0;
            while (!pointer1.Equals(NodeRepresentantsInCoherentComponent[pointer1]))
            {
                depth_node1++;
                pointer1 = NodeRepresentantsInCoherentComponent[pointer1];
            }

            while (!pointer2.Equals(NodeRepresentantsInCoherentComponent[pointer2]))
            {
                depth_node2++;
                pointer2 = NodeRepresentantsInCoherentComponent[pointer2];
            }

            if (depth_node1 > depth_node2)
            {
                NodeRepresentantsInCoherentComponent[FindRepresentantInCoherentComponent(node2)] = FindRepresentantInCoherentComponent(node1);
            }
            else
            {
                NodeRepresentantsInCoherentComponent[FindRepresentantInCoherentComponent(node1)] = FindRepresentantInCoherentComponent(node2);
            }
        }

        private Node FindRepresentantInCoherentComponent(Node node)
        {
            if (node != NodeRepresentantsInCoherentComponent[node])
            {
                NodeRepresentantsInCoherentComponent[node] = FindRepresentantInCoherentComponent(NodeRepresentantsInCoherentComponent[node]);
            }
            return NodeRepresentantsInCoherentComponent[node];
        }
    }
}
