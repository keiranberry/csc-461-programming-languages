def fastEmpty(boatInLock, depth, level): # GRADING: FAST_EMPTY
    """
    Fast empty behavior function for locks

    :return: the amount to change the lock's level by
    :rtype: int
    """
    if boatInLock and depth != level:
        return 1
    elif not boatInLock and level == 1:
        return -1
    elif not boatInLock and level > 0:
        return -2
    return 0


def basic(boatInLock, depth, level): # GRADING: BASIC_FILL
    """
    Basic behavior function for locks

    :return: the amount to change the lock's level by
    :rtype: int
    """
    if boatInLock and depth != level:
        return 1
    elif not boatInLock and level > 0:
        return -1
    return 0


def passThrough(boatInLock, depth, level):
    """
    Pass through behavior function for locks

    :return: the amount to change the lock's level by
    :rtype: int
    """
    return 0
