using System;

namespace BieszczadyTour.exceptions
{
    class InvalidStartPlaceIdException : Exception
    {
        public new string Message;

        public InvalidStartPlaceIdException(string message)
        {
            Message = message;
        }
    }
}
