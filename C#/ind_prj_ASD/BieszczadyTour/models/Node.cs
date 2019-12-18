using System.Collections.Generic;

namespace BieszczadyTour.models
{
    class Node
    {
        public readonly string Id;
        public readonly List<NodeConnection> ConnectedNodes;
        public readonly string Name;
        public readonly string Description;
        public Node PreviouslyVisitedNode;

        public Node(string id, string name, string description)
        {
            Name = name;
            Id = id;
            ConnectedNodes = new List<NodeConnection>();
            Description = description;
        }

        public void ConnectNode(NodeConnection Connection)
        {
            ConnectedNodes.Add(Connection);
        }
    }
}
