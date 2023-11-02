import LockBehavior
from berry_keiran.RiverPart import RiverPart


class Lock(RiverPart):
    __depth = 0
    __level = 0
    __boatInLock = False
    __currentBoat = None
    Behave = None

    def __init__(self, depth, behavior):
        """
        Initialize the Lock class

        :param depth: the depth of the lock
        :param behavior: the behavior of the lock, pass through, basic, or fast empty
        """
        self.__depth = depth
        self.setBehavior(behavior)

    def setBehavior(self, behavior):
        """
        Sets behavior of lock

        :param behavior: the behavior type of the lock
        """
        if behavior == 1:
            self.Behave = LockBehavior.passThrough
        elif behavior == 2:
            self.Behave = LockBehavior.basic
        else:
            self.Behave = LockBehavior.fastEmpty

    def getDepth(self):
        """
        Gets depth of lock

        :return: the depth of the lock
        :rtype: int
        """
        return self.__depth

    def getLevel(self):
        """
        Gets level of lock

        :return: the level of the lock
        :rtype: int
        """
        return self.__level

    def setLevel(self, level):
        """
        Sets level of lock

        :param level: the level of the lock
        """
        self.__level = level

    def changeLevel(self, difference):
        """
        Changes level of lock

        :param difference: the change in lock level
        """
        self.setLevel(self.getLevel() + difference)

    def setBoatInLock(self, isBoat):
        """
        Sets whether a boat is in the lock

        :param isBoat: whether there is a boat in the lock
        """
        self.__boatInLock = isBoat

    def getCurrentBoat(self):
        """
        Gets boat currently in lock

        :return: the boat in the lock
        :rtype: Boat
        """
        return self.__currentBoat

    def setCurrentBoat(self, boat):
        """
        Sets a boat object in the lock

        :param boat: the boat to put in the lock
        """
        self.__currentBoat = boat

    def getLevelString(self):
        """
        Gets the level of the lock in string format

        :return: level of lock
        :rtype: string
        """
        if len(str(self.__level)) < 2:
            return " " + str(self.__level)
        else:
            return str(self.__level)

    def getVisualWithId(self):
        """
        Gets a visual of the lock

        :return: visual of lock
        :rtype: string
        """
        if self.__boatInLock:
            return str(self.__currentBoat.getId()).ljust(7, '.')
        else:
            return "......."

    def moveBoatOn(self):
        """
        Moves the boat in the lock on within the system

        :return: the boat which exited, or False
        :rtype: Boat or boolean
        """
        if self.getLevel() == self.getDepth():
            self.setBoatInLock(False)
            b = self.getCurrentBoat()
            return b
        else:
            return False

    def addBoat(self, boat):
        """
        Adds a boat to the lock

        :param boat: the boat to put in the lock
        :return: False, only if the boat could not be put in
        :rtype: bool or nothing
        """
        if self.getLevel() == 0:
            self.setCurrentBoat(boat)
            self.setBoatInLock(True)
        else:
            return False

    def update(self, roomAhead):
        """
        Updates lock
        This could mean changing the level of the lock, letting a boat in,
        or letting a boat out

        :param roomAhead: whether the next river part in the system can accept a boat
        :return: the boat which is leaving, and whether the lock can accept a boat
        :rtype: Boat/bool, bool
        """
        b = False
        # if there is a boat in the lock or the level isn't 0
        if self.__boatInLock or not self.__level == 0:
            # change the level of the lock based on the behavior of the lock
            self.changeLevel(self.Behave(self.__boatInLock, self.__depth, self.__level))
        # if there is a boat in the lock and it can leave
        if self.__level == self.__depth and roomAhead and self.__boatInLock:
            # let it leave
            b = self.moveBoatOn()
        # if there is no boat and the level is 0
        if not self.__boatInLock and self.__level == 0:
            # it can accept a boat
            return b, True
        # otherwise it cant
        return b, False

    def __str__(self):
        """
        Convert Lock class to a string
        Makes lock class a string which can be called with functions such as print(l)
        where l is a Lock

        :return: the string of the lock
        :rtype: string
        """
        if self.__boatInLock:
            return "_⛴(" + self.getLevelString() + ")_"
        else:
            return "_X(" + self.getLevelString() + ")_"
