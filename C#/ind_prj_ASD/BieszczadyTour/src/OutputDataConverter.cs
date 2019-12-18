using System;
using System.Collections.Generic;
using System.Linq;

namespace BieszczadyTour.src
{
    class OutputDataConverter
    {
        private readonly Tour data;

        public OutputDataConverter(Tour tour)
        {
            data = tour;
        }

        public List<string> ConvertData()
        {
            Console.WriteLine(data.Time);
            List<string> OutputData = new List<string>();
            OutputData.Add(data.Nodes[0].Name);
            for (int i = 1; i < data.Nodes.Count(); i++)
            {
                OutputData.Add("-> " + data.Nodes[i].Name);
            }

            int hours = (int)Math.Round(data.Time, 0);
            int minutes = (int)(Math.Ceiling((data.Time - Math.Floor(data.Time)) * 60));

            string time = "Czas: " + hours + " godzin " + minutes + " minut";
            OutputData.Add(time);

            string cost = "Koszt: " + data.Cost + " zł";
            OutputData.Add(cost);
            return OutputData;
        }
    }
}
