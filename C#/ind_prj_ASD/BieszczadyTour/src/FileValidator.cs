using System;
using System.Collections.Generic;
using System.Linq;
using BieszczadyTour.models;
using BieszczadyTour.exceptions;
using System.Text.RegularExpressions;

namespace BieszczadyTour.src
{
    class FileValidator
    {
        public List<Node> PointsOnMap;
        public List<string> PointsToVisit;

        public FileValidator()
        {
            PointsOnMap = new List<Node>();
            PointsToVisit = new List<string>();
        }

        public List<Tuple<string, NodeConnection>> ReadFiles(string[] files)
        {
            List<Tuple<string, string, string, float>> Connections = new List<Tuple<string, string, string, float>>();

            try
            {
                if (files.Count() < 2)
                {
                    throw new LackOfDataException("Too less input files specified. Please enter input files to program work correctly.");
                }
            }
            catch (LackOfDataException e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(2);
            }

            ValidateFirstInputFile(files, Connections);
            ValidateStarterPointIdentifier(files);
            ValidateOptionalArgument(files);

            List<Tuple<string, NodeConnection>> NodesConnections = adaptData(Connections);
            return NodesConnections;
        }

        private List<Tuple<string, NodeConnection>> adaptData(List<Tuple<string, string, string, float>> data)
        {
            List<Tuple<string, NodeConnection>> Connections = new List<Tuple<string, NodeConnection>>();
            for (int i = 0; i < data.Count(); i++)
            {
                Node ConnectedNode = PointsOnMap.Find(x => x.Id == data[i].Item2);
                float time = float.Parse(data[i].Item3.Split(':')[0]) + (float.Parse(data[i].Item3.Split(':')[1]) / 60);
                float cost = data[i].Item4;
                Connections.Add(Tuple.Create(data[i].Item1, new NodeConnection(ConnectedNode, time, cost)));
            }
            return Connections;
        }

        private void ValidateFirstInputFile(string[] files, List<Tuple<string, string, string, float>> Connections)
        {
            try
            {
                string path = files[0];
                string text = System.IO.File.ReadAllText(path);
                char[] separators = { '\n' };
                string[] lines = text.Split(separators);

                if (!text.Contains("### Miejsca podróży") || !text.Contains("### Czas przejścia"))
                {
                    throw new InvalidDataConsistency("Lack of mark for list in first input file detected");
                }

                if (text.Split(new string[] { "###" }, StringSplitOptions.None).Count() != 3)
                {
                    throw new InvalidDataConsistency("Invalid number of marks for data sections. There should be exactly 2 marks");
                }

                bool firstPartOfFile = false;

                string line;

                for (int j = 0; j < lines.Count(); j++)
                {
                    line = lines[j];
                    if (lines[j].Length <= 1)
                    {
                        continue;
                    }
                    if (lines[j].Contains("### Miejsca podróży"))
                    {
                        firstPartOfFile = true;
                        j++;
                        continue;
                    }
                    if (lines[j].Contains("### Czas przejścia"))
                    {
                        firstPartOfFile = false;
                        j++;
                        continue;
                    }

                    string[] lineSections = line.Split(new string[] { " | ", " |", "| ", "|" }, StringSplitOptions.None);
                    if (firstPartOfFile)
                    {
                        if (line.Count(c => c == '|') != 4)
                        {
                            throw new FormatException("Incorrect data format at line " + (j + 1) + " in first input file. Please enter data correctly.");
                        }

                        if (lineSections[1].Length == 0)
                        {
                            throw new LackOfDataException("Invalid place identifier at line " + (j + 1) + " int first input file.");
                        }

                        int indexID = PointsOnMap.FindIndex(x => x.Id == lineSections[1]);
                        int indexName = PointsOnMap.FindIndex(x => x.Name == lineSections[2]);
                        if (indexID == -1)
                        {
                            PointsOnMap.Add(new Node(lineSections[1], lineSections[2], lineSections[3]));
                        }
                        else if (indexID != -1)
                        {
                            throw new InvalidDataConsistency("Duplicated identifier detected at lines " + indexID.ToString() + " and " + PointsOnMap.Count().ToString() + " in first input file.");
                        }
                        if (indexName != -1)
                        {
                            if (PointsOnMap[indexName].Description == lineSections[3])
                            {
                                throw new InvalidDataConsistency("Duplicated place with two different identifiers detected at lines " + (indexName + 1).ToString() + " and " + (PointsOnMap.Count() + 1).ToString() + " in first input file.");
                            }
                        }
                    }
                    else
                    {
                        if (line.Count(c => c == '|') != 6)
                        {
                            throw new FormatException("Incorrect data format at line " + (j + 1) + " in first input file. Please enter data correctly.");
                        }

                        Match m = Regex.Match(lineSections[3], @"^[0-9]{1,}[:][0-5][0-9]$");
                        if (!m.Success)
                        {
                            throw new FormatException("Invalid time format at line " + (j + 1) + " in first input file.");
                        }
                        if (lineSections[3] == "0:00")
                        {
                            throw new InvalidDataConsistency("Invalid time value detected at line " + (j + 1) + ". Time of travel cannot be equal zero. Please enter correct value.");
                        }

                        m = Regex.Match(lineSections[4], @"^[0-9]{1,}[:][0-5][0-9]$");
                        if (!m.Success)
                        {
                            throw new FormatException("Invalid time format at line " + (j + 1) + " in first input file.");
                        }
                        if (lineSections[4] == "0:00")
                        {
                            throw new InvalidDataConsistency("Invalid time value detected at line " + (j + 1) + ". Time of travel cannot be equal zero. Please enter correct value.");
                        }

                        if (lineSections[5] != "–" && lineSections[5] != "--")
                        {
                            float cost;
                            try
                            {
                                cost = float.Parse(lineSections[5]);
                            }
                            catch (FormatException)
                            {
                                throw new FormatException("Invalid cost at line " + (j + 1) + " in first input file.");
                            }

                            if (cost < 0)
                            {
                                throw new InvalidDataConsistency("Negative cost at line " + (j + 1) + " in first input file.");
                            }
                        }
                        else
                        {
                            lineSections[5] = "0";
                        }

                        if (PointsOnMap.FindIndex(x => x.Id == lineSections[1]) == -1)
                        {
                            throw new InvalidDataConsistency("Invalid identifier detected at line " + (j + 1) + " in column 2 in first input file.");
                        }
                        if (PointsOnMap.FindIndex(x => x.Id == lineSections[2]) == -1)
                        {
                            throw new InvalidDataConsistency("Invalid identifier detected at line " + (j + 1) + " in column 3 in first input file.");
                        }

                        if (lineSections[1] == lineSections[2])
                        {
                            throw new InvalidDataConsistency("The same identifier for start and end point detected at line " + (j + 1) + ". Cannot use path starting and ending in the same place.");
                        }

                        Connections.Add(Tuple.Create(lineSections[1], lineSections[2], lineSections[3], float.Parse(lineSections[5])));
                        Connections.Add(Tuple.Create(lineSections[2], lineSections[1], lineSections[4], float.Parse(lineSections[5])));
                    }
                }
                if (Connections.Count() == 0)
                {
                    throw new LackOfDataException("No given input data, first input file is empty.");
                }
            }
            catch (InvalidDataConsistency e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(2);
            }
            catch (LackOfDataException e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(2);
            }
            catch (FormatException e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(1);
            }
        }

        private void ValidateStarterPointIdentifier(string[] files)
        {
            try
            {
                if (PointsOnMap.FindIndex(p => p.Id == files[1]) == -1)
                {
                    throw new InvalidStartPlaceIdException("Invalid start place identifier entered, please enter id that exists for your data.");
                }
            }
            catch (InvalidStartPlaceIdException e)
            {
                Console.WriteLine(e.Message);
                System.Environment.Exit(3);
            }
        }

        private void ValidateOptionalArgument(string[] files)
        {

            if (files.Length > 2)
            {
                try
                {
                    string path = files[2];
                    string text = System.IO.File.ReadAllText(path);
                    char[] separators = { '\n' };
                    string[] lines = text.Split(separators);
                    string number_field_id = "LP.";

                    for (int i = 0; i < lines.Length; i++)
                    {
                        string line = lines[i];
                        if (lines[i].Length == 0 || lines[i].Contains("###") || line.ToUpper().Contains(number_field_id))
                        {
                            continue;
                        }
                        string[] lineSections = lines[i].Split(new string[] { " | ", " |", "| ", "|" }, StringSplitOptions.None);
                        if (lineSections[1].Length == 0)
                        {
                            throw new LackOfDataException("Detected lack of identifier at line " + (i + 1) + " in third input file. Please enter data.");
                        }
                        else if (PointsOnMap.FindIndex(x => x.Id == lineSections[1]) == -1)
                        {
                            throw new InvalidDataConsistency("Detected invalid place identifier at line " + (i + 1) + " in third input file. Please enter correct id.");
                        }
                        PointsToVisit.Add(lineSections[1]);
                    }
                }
                catch (LackOfDataException e)
                {
                    Console.WriteLine(e.Message);
                    System.Environment.Exit(2);
                }
                catch (InvalidDataConsistency e)
                {
                    Console.WriteLine(e.Message);
                    System.Environment.Exit(2);
                }
            }
        }
    }
}