using System.Collections.Generic;
using System.Linq;

namespace BieszczadyTour.src
{
    class ResultsWriter
    {
        private Tour tour;

        public ResultsWriter(Tour tour)
        {
            this.tour = tour;
        }

        public void PrintResultsToFile()
        {
            System.IO.StreamWriter ResultsFile = new System.IO.StreamWriter(@"tour.txt");
            OutputDataConverter outputDataConverter = new OutputDataConverter(tour);

            List<string> DataToWrite = outputDataConverter.ConvertData();

            for (int i = 0; i < DataToWrite.Count(); i++)
            {
                ResultsFile.WriteLine(DataToWrite[i]);
            }

            ResultsFile.Close();
        }
    }
}
