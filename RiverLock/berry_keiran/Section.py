from typing import List
from Boat import Boat
from berry_keiran.RiverPart import RiverPart


class Section(RiverPart):
    __length = 0
    __flow = 0
    __subsections: List["Subsection"] = []

    def __init__(self, length, flow):
        """
        Initialize instance of section class

        :param length: length of section
        :param flow: flow of section
        """
        self.__length = length
        self.__flow = flow
        self.__subsections = [self.Subsection() for i in range(length)]

    class Subsection:
        __boatInSubSection = False

        def __init__(self):
            """
            Initialize instance of Subsection class
            """
            self.__boatInSubSection = False

        def getBoatInSubSection(self):
            """
            Get whether there is a boat in the subsection

            :return: whether there is a boat in the subsection
            :rtype: bool
            """
            return self.__boatInSubSection

        def removeBoatFromSubSection(self):
            """
            Remove boat from subsection

            :return: boat from subsection
            :rtype: Boat
            """
            self.__boatInSubSection = False
            b = self.__currentBoat
            return b

        def addBoatToSubSection(self, boat):
            """
            Adds boat to subsection

            :param boat: boat to add to subsection
            """
            self.__currentBoat = boat
            self.__boatInSubSection = True

        def getCurrentBoat(self):
            """
            Gets current boat in subsection

            :return: boat in subsection
            :rtype: Boat
            """
            return self.__currentBoat

        def __str__(self):
            """
            Converts subsection to a string which can be displayed

            :return: string of subsection
            :rtype: string
            """
            if self.__boatInSubSection:
                return "⛴〜〜"
            else:
                return "〜〜〜"

        def getSubSectionVisualWithId(self):
            """
            Gets the visual of subsection with id, for the bottom of the system

            :return: string of subsection for bottom
            :rtype: string
            """
            if self.__boatInSubSection:
                return str(self.__currentBoat.getId()).ljust(3, '〜')

            else:
                return "〜〜〜"

    def addSubsection(self, subsection):
        """
        Adds subsection to the end of the list of subsections

        :param subsection: subsection to add to list
        """
        self.__subsections.append(subsection)

    def addBoat(self, boat):
        """
        Adds boat to section by adding it to the first subsection

        :param boat: Boat to add to section
        """
        self.__subsections[0].addBoatToSubSection(boat)

    def getLength(self):
        """
        Gets length of the section

        :return: length of section
        :rtype: int
        """
        return self.__length

    def getFlow(self):
        """
        Gets flow of section

        :return: flow of section
        :rtype: int
        """
        return self.__flow

    def hasBoatInFirstSlot(self):
        """
        Whether there is a boat in the first subsection, can be used to see if the section
        can accept a boat

        :return: status of boat in first subsection
        :rtype: bool
        """
        if self.__subsections[0].getBoatInSubSection():
            return True
        else:
            return False

    def getNumberOfBoatsInSection(self):
        """
        Gets number of boats in the whole section for section information

        :return: number of boats in section
        :rtype: int
        """
        i = 0
        for sub in self.__subsections:
            if sub.getBoatInSubSection():
                i += 1
        return i

    def getVisualWithId(self):
        """
        Gets visual for whole section with ids. Used for the bottom display of the system

        :return: output of section with id of boats
        :rtype: string
        """
        output = ""
        for i in range(len(self.__subsections)):
            output += self.__subsections[i].getSubSectionVisualWithId()
        return output

    def update(self, roomAhead):
        """
        Updates the entire section by going through each subsection backwards and moving the
        corresponding boats, if applicable

        :param roomAhead: whether a boat can leave the section and move on to the next

        :return: boat that left the section, if applicable, and whether the section can accept another boat
        :rtype: Boat/bool, bool
        """
        b = False
        lastBoatFound = False
        self.__subsections.reverse()

        for i in range(len(self.__subsections)):
            # if it is the subsection, there is a boat, and it can move on, then let the boat leave
            if i == 0 and self.__subsections[i].getBoatInSubSection() and roomAhead:
                b = self.__subsections[i].removeBoatFromSubSection()
                continue
            # if we find the final boat in the section
            if not lastBoatFound and self.__subsections[i].getBoatInSubSection():
                lastBoatFound = True
                # if the boat would go further than the end of the section
                if i < self.__subsections[i].getCurrentBoat().TravelDistance(self.__flow,
                        self.__subsections[i].getCurrentBoat().getEngine()):
                    # put it at the end of the section
                    self.__subsections[0].addBoatToSubSection(self.__subsections[i].removeBoatFromSubSection())
                    continue

            # if there is a boat in the subsection
            if i > 0 and self.__subsections[i].getBoatInSubSection():
                j = i - 1

                # increment j while there is not a boat in the way, the boat still has travel distance, and
                # the boat is not trying to go off the edge of the section
                while j >= 0 and not self.__subsections[j].getBoatInSubSection() \
                        and i - j < self.__subsections[i].getCurrentBoat()\
                        .TravelDistance(self.__flow, self.__subsections[i].getCurrentBoat().getEngine()):
                    j -= 1

                # if there is a boat at j, we need to put it in the spot behind, it cannot pass
                if self.__subsections[j].getBoatInSubSection():
                    self.__subsections[j + 1].addBoatToSubSection(self.__subsections[i].removeBoatFromSubSection())
                else:
                    # otherwise it can go to the original spot it wanted to
                    self.__subsections[j].addBoatToSubSection(self.__subsections[i].removeBoatFromSubSection())
        self.__subsections.reverse()

        if self.__subsections[0].getBoatInSubSection():
            return b, False
        return b, True

    def __str__(self):
        """
        Returns the section as a string, which can be used for things such as printing
        a visualization of the section

        :return: output string of the section
        :rtype: string
        """
        output = ""
        for sub in self.__subsections:
            output += str(sub)
        return output
