using System;

namespace BieszczadyTour.exceptions
{
    class LackOfDataException : Exception
    {
        public new string Message;

        public LackOfDataException(string message)
        {
            Message = message;
        }
    }
}
