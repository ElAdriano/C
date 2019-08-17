using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASD
{
    class Graph : GraphAlgorithms // class className : interfaceName means that class implements interfaceName
    {
        private int vertices;
        private List< Tuple<int,int> >[] connections;

        public Graph(int v, List< Tuple<int,int,int> > conn)
        {
            connections = new List< Tuple<int,int> >[v+1];
            for(int i=1; i<=v; i++)
            {
                connections[i] = new List<Tuple<int, int>>();
            }
            vertices = v;

            for(int i=0; i < conn.Count; i++)
            {
                int cityA = conn[i].Item1;
                int cityB = conn[i].Item2;
                int dist = conn[i].Item3;

                connections[cityA].Add(Tuple.Create<int, int>(cityB, dist));
                connections[cityB].Add(Tuple.Create<int, int>(cityA, dist));
            }
        }

        public List<int> dijkstra(int v)
        {
            int[] distances = new int[vertices+1];
            for(int i=1; i<=vertices; i++)
            {
                if (i != v) distances[i] = int.MaxValue;    //represents infinity for int
                else distances[i] = 0;
            }

            HashSet<Tuple<int, int>> s = new HashSet<Tuple<int, int>>();
            s.Add( Tuple.Create(0, v) );

            int nextCity, dist;
            while (s.Count > 0)
            {
                int city = s.ElementAt(0).Item2;
                s.Remove( s.ElementAt(0) );

                for(int i=0; i<connections[city].Count; i++)
                {
                    nextCity = connections[city].ElementAt(i).Item1;
                    dist = connections[city].ElementAt(i).Item2;
              
                    if (distances[city] + dist < distances[nextCity])
                    {
                        s.Remove( Tuple.Create<int, int>(distances[nextCity], nextCity) );
                        distances[nextCity] = distances[city] + dist;
                        s.Add( Tuple.Create<int, int>(distances[nextCity], nextCity) );
                    }
                }
            }
            return distances.ToList();
        }

        public void showDistances(List<int> l)
        {
            for(int i=1; i<l.Count; i++)
            {
                Console.WriteLine( "{0} -> {1}", i, l.ElementAt(i) );
            }
        }

        public void showGraph()
        {
            for (int i = 1; i <= vertices; i++)
            {
                Console.Write("{0}:",i);
                for (int j = 0; j < connections[i].Count; j++)
                {
                    try
                    {
                        Console.Write("({0},{1}), ",connections[i].ElementAt(j).Item1, connections[i].ElementAt(j).Item2);
                    }
                    catch (ArgumentNullException e) { }
                }
                Console.WriteLine();
            }
        }

    }
}
