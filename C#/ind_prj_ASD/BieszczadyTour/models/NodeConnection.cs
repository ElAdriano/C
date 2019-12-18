
namespace BieszczadyTour.models
{
    class NodeConnection
    {
        public Node ConnectedNode;
        public float Time;
        public float Cost;

        public NodeConnection(Node connNode, float time, float cost)
        {
            ConnectedNode = connNode;
            Time = time;
            Cost = cost;
        }
    }
}
