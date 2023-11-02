def steadyTravel(flow, engine): # GRADING: STEADY_TRAVEL
    """
    Returns distance boat travels in steadyTravel mode

    :param flow: the flow of the section the boat is in
    :param engine: the engine power of the boat
    :return: the travel distance of the boat, always 1 in steady
    :rtype: int
    """
    return 1


def maxTravel(flow, engine): # GRADING: MAX_TRAVEL
    """
    Returns distance boat travels in maxTravel mode

    :param flow: the flow of the section the boat is in
    :param engine: the engine power of the boat
    :return: the travel distance of the boat
    :rtype: int
    """
    if (engine - flow) > 1:
        return engine - flow
    else:
        return 1
