from typing import List
from berry_keiran.Iterator import Iterator
from berry_keiran.RiverPart import RiverPart
from berry_keiran.Section import Section
from berry_keiran.Lock import Lock


class RiverSystem:
    __riverParts : List[RiverPart] = []
    __iteratorType = 0
    __iterator = None

    def __init__(self):
        """
        Initialize the RiverSystem class

        Initializes river parts to the default river system
        """
        self.__riverParts = [Section(6, 0), Lock(0, 1), Section(3, 1)]

    def __iter__(self):
        """
        Define iterator for river system

        If iterator type is 0, returns a normal iterator.
        Otherwise, returns a section iterator

        :return: iterator
        :rtype: Iterator or SectionIterator
        """
        if self.__iteratorType == 0:
            self.__iterator = Iterator(self.__riverParts)
        else:
            self.__iterator = Iterator.SectionIterator(self.__riverParts)
        return self.__iterator

    def clearRiverParts(self):
        """
        Clears river parts list, can be used to make a custom river system
        instead of the default
        """
        self.__riverParts.clear()

    def addRiverPart(self, riverPart):
        """
        Adds river part to the list of river parts

        :param riverPart: river part to add to the system
        """
        self.__riverParts.append(riverPart)

    def addBoat(self, boat):
        """
        Adds boat to river system if there is space

        :param boat: boat to add to the system
        """
        if len(self.__riverParts) < 1:
            return
        if not self.__riverParts[0].hasBoatInFirstSlot():
            self.__riverParts[0].addBoat(boat)

    def update(self):
        """
        Updates the river system by one tick
        Iterates through the whole system and updates each part
        """
        roomAhead = True
        i = 0
        self.__iteratorType = 0
        for part in self: # GRADING: LOOP_ALL
            result = part.update(roomAhead)
            current, roomAhead = result
            if current and i < 0:
                self.__riverParts[i].addBoat(current)
            i -= 1

    def getRiverSectionDetails(self):
        """
        Gets section details for the river.
        Iterates through each section of the river and builds
        an output string

        :return: output string of section details
        :rtype: string
        """
        output = ""
        i = 1
        self.__iteratorType = 1
        for part in self: # GRADING: LOOP_RESTRICT
            output += "Section " + str(i) + '\n' + \
                      "Boats: " + str(part.getNumberOfBoatsInSection()) + \
                      " Flow: " + str(part.getFlow()) + '\n' + '\n'
            i += 1
        return output

    def bottom(self):
        """
        Creates the bottom string of the system to output to the console

        :return: the bottom string of the system visual
        :rtype: string
        """
        output = ""
        for i in range(len(self.__riverParts)):
            output += self.__riverParts[i].getVisualWithId()
        return output

    def __str__(self):
        """
        Converts the whole system to a string which can be printed

        :return: output string of whole system
        :rtype: string
        """
        output = ""
        for i in range(len(self.__riverParts)):
            output += str(self.__riverParts[i])

        return output
