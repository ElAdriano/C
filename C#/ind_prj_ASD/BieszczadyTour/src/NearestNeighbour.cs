using System;
using System.Collections.Generic;
using System.Linq;
using BieszczadyTour.models;

namespace BieszczadyTour.src
{
    class NearestNeighbour
    {
        private Dictionary<Node, bool> VisitedNodes;
        private List<Tuple<string, string>> PaidConnections;
        private Graph graph;
        private List<string> PointsIdToVisit;

        public NearestNeighbour(Graph graph, List<string> ListOfPointsIdToVisit)
        {
            VisitedNodes = new Dictionary<Node, bool>();
            foreach (Node node in graph.NodesList)
            {
                VisitedNodes.Add(node, false);
            }

            PaidConnections = new List<Tuple<string, string>>();
            this.graph = graph;
            PointsIdToVisit = ListOfPointsIdToVisit;
        }

        public Tour GenerateTour(Node TourStarterNode, List<Tuple<string, NodeConnection>> ConnectionsList)
        {
            Node CurrentlyKeptNode = getNearestNeighbourNode(TourStarterNode);
            CurrentlyKeptNode.PreviouslyVisitedNode = TourStarterNode;

            Tour tour = new Tour();
            VisitedNodes[TourStarterNode] = true;
            tour.Nodes.Add(TourStarterNode);

            while (ConditionFulfilled())
            {
                Node nearest_neighbour_node = getNearestNeighbourNode(CurrentlyKeptNode);

                if (nearest_neighbour_node.Equals(CurrentlyKeptNode))
                {
                    nearest_neighbour_node = CurrentlyKeptNode.PreviouslyVisitedNode;
                }
                else if (nearest_neighbour_node.PreviouslyVisitedNode == null)
                {
                    nearest_neighbour_node.PreviouslyVisitedNode = CurrentlyKeptNode;
                }

                tour.Nodes.Add(CurrentlyKeptNode);
                VisitedNodes[CurrentlyKeptNode] = true;
                if (!ConditionFulfilled())
                {
                    break;
                }

                if (!PaidConnections.Exists(x => x.Item1 == CurrentlyKeptNode.Id && x.Item2 == nearest_neighbour_node.Id)
                    && !PaidConnections.Exists(x => x.Item1 == nearest_neighbour_node.Id && x.Item2 == CurrentlyKeptNode.Id))
                {
                    tour.Cost += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == nearest_neighbour_node.Id).Cost;
                    PaidConnections.Add(Tuple.Create(CurrentlyKeptNode.Id, nearest_neighbour_node.Id));
                }

                tour.Time += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == nearest_neighbour_node.Id).Time;
                CurrentlyKeptNode = nearest_neighbour_node;
            }

            ComeBackToStartPoint(tour, CurrentlyKeptNode, TourStarterNode, ConnectionsList);
            return tour;
        }

        private bool ConditionFulfilled()
        {
            if (PointsIdToVisit.Count() > 0)
            {
                foreach (string id in PointsIdToVisit)
                {
                    if (!VisitedNodes[graph.NodesList.Find(x => x.Id == id)])
                    {
                        return true;
                    }
                }
                return false;
            }
            else
            {
                return VisitedNodes.ContainsValue(false);
            }
        }

        private void ComeBackToStartPoint(Tour tour, Node CurrentlyKeptNode, Node TourStarterNode, List<Tuple<string, NodeConnection>> ConnectionsList)
        {
            List<Node> NearestPath = FindNearestWayToStarterPoint(CurrentlyKeptNode, TourStarterNode, ConnectionsList);

            Node NextVisitedNode = NearestPath[NearestPath.Count() - 1];

            tour.Time += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == NextVisitedNode.Id).Time;
            if (!PaidConnections.Exists(x => x.Item1 == CurrentlyKeptNode.Id && x.Item2 == NextVisitedNode.Id)
                && !PaidConnections.Exists(x => x.Item1 == NextVisitedNode.Id && x.Item2 == CurrentlyKeptNode.Id))
            {
                PaidConnections.Add(Tuple.Create(CurrentlyKeptNode.Id, NextVisitedNode.Id));
                tour.Cost += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == NextVisitedNode.Id).Cost;
            }

            for (int i = NearestPath.Count() - 1; i > 0; i--)
            {
                CurrentlyKeptNode = NearestPath[i];
                NextVisitedNode = NearestPath[i - 1];

                tour.Nodes.Add(CurrentlyKeptNode);
                tour.Time += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == NextVisitedNode.Id).Time;
                if (!PaidConnections.Exists(x => x.Item1 == CurrentlyKeptNode.Id && x.Item2 == NextVisitedNode.Id)
                    && !PaidConnections.Exists(x => x.Item1 == NextVisitedNode.Id && x.Item2 == CurrentlyKeptNode.Id))
                {
                    PaidConnections.Add(Tuple.Create(CurrentlyKeptNode.Id, NextVisitedNode.Id));
                    tour.Cost += CurrentlyKeptNode.ConnectedNodes.Find(x => x.ConnectedNode.Id == NextVisitedNode.Id).Cost;
                }
            }
            tour.Nodes.Add(TourStarterNode);
        }

        private List<Node> FindNearestWayToStarterPoint(Node LastVisitedNode, Node StarterNode, List<Tuple<string, NodeConnection>> Connections)
        {
            Dictionary<Node, float> TimesOfJourneyToOtherNodes = new Dictionary<Node, float>();
            Dictionary<Node, Node> MapWhereICameFromForNodes = new Dictionary<Node, Node>();

            foreach (Node node in graph.NodesList)
            {
                TimesOfJourneyToOtherNodes.Add(node, float.MaxValue);
                MapWhereICameFromForNodes.Add(node, node);
            }

            TimesOfJourneyToOtherNodes[LastVisitedNode] = 0;
            MapWhereICameFromForNodes[LastVisitedNode] = LastVisitedNode;

            for (int i = 0; i < graph.NodesList.Count(); i++)
            {
                for (int j = 0; j < Connections.Count(); j++)
                {
                    Node CityA = graph.NodesList.Find(x => x.Id == Connections[j].Item1);
                    Node CityB = graph.NodesList.Find(x => x.Id == Connections[j].Item2.ConnectedNode.Id);
                    float Time = Connections[i].Item2.Time;
                    if (TimesOfJourneyToOtherNodes[CityA] + Time < TimesOfJourneyToOtherNodes[CityB])
                    {
                        TimesOfJourneyToOtherNodes[CityB] = TimesOfJourneyToOtherNodes[CityA] + Time;
                        MapWhereICameFromForNodes[CityB] = CityA;
                    }
                }
            }

            Node CurrentLocation = StarterNode;
            List<Node> list = new List<Node>();

            while (!CurrentLocation.Equals(LastVisitedNode))
            {
                list.Add(CurrentLocation);
                CurrentLocation = MapWhereICameFromForNodes[CurrentLocation];
            }
            return list;
        }

        private Node getNearestNeighbourNode(Node CurrentNode)
        {
            NodeConnection NodeInInfinityConnectionWith = new NodeConnection(new Node("INF", "INF", "INF"), float.MaxValue, float.MaxValue);
            NodeConnection NodeConnectionWithNodeToReturn = NodeInInfinityConnectionWith;

            foreach (NodeConnection node_connection in CurrentNode.ConnectedNodes)
            {
                if (!VisitedNodes[node_connection.ConnectedNode])
                {
                    if (node_connection.Time < NodeConnectionWithNodeToReturn.Time)
                    {
                        NodeConnectionWithNodeToReturn = node_connection;
                    }
                }
            }

            if (NodeConnectionWithNodeToReturn.Equals(NodeInInfinityConnectionWith))
            {
                return CurrentNode;
            }
            return NodeConnectionWithNodeToReturn.ConnectedNode;
        }
    }
}
