import BoatBehavior


class Boat:
    __id = 1
    __engine = 1
    __symbol = '⛴'
    TravelDistance = None

    def __init__(self, id, engine, travel):
        """
        Initialize the Boat class

        :param id: the id of the boat
        :param engine: the engine power of the boat
        :param travel: the travel behavior of the boat, either max or steady
        """
        self.__id = id
        self.__engine = engine
        self.setBehavior(travel)

    def __str__(self):
        """
        Convert Boat class to a string
        Makes boat class a string which can be called with functions such as print(b)
        where b is a Boat

        :return: the symbol of the boat
        :rtype: char
        """
        return self.__symbol

    def getId(self):
        """
        Gets id of boat

        :return: the id of the boat
        :rtype: int
        """
        return self.__id

    def getEngine(self):
        """
        Gets engine power of boat

        :return: the engine power of the boat
        :rtype: int
        """
        return self.__engine

    def setBehavior(self, arg):
        """
        Sets behavior of boat

        :param arg: the behavior type of the boat
        """
        if arg == 1:
            self.TravelDistance = BoatBehavior.steadyTravel
        else:
            self.TravelDistance = BoatBehavior.maxTravel
