using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASD
{
    class Program
    {
        static void Main(string[] args)
        {
            List< Tuple<int, int, int> > edges = new List< Tuple<int, int, int> >();
            edges.Add(Tuple.Create<int, int, int>(1, 2, 1));
            edges.Add(Tuple.Create<int, int, int>(2, 3, 1));
            edges.Add(Tuple.Create<int, int, int>(2, 5, 3));
            edges.Add(Tuple.Create<int, int, int>(3, 4, 2));
            edges.Add(Tuple.Create<int, int, int>(4, 5, 1));

            Graph g = new Graph(5,edges);
            g.showGraph();

            List<int> l = g.dijkstra(5);

            g.showDistances(l);

            Console.ReadKey();
        }
    }
}
