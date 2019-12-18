using System;

namespace BieszczadyTour.exceptions
{
    class InvalidDataConsistency : Exception
    {
        public new string Message;

        public InvalidDataConsistency(string message)
        {
            Message = message;
        }
    }
}
