using System.Collections.Generic;
using BieszczadyTour.models;

namespace BieszczadyTour.src
{

    class Tour
    {
        public List<Node> Nodes;
        public float Time;
        public float Cost;

        public Tour()
        {
            Nodes = new List<Node>();
            Time = 0;
            Cost = 0;
        }
    }
}
