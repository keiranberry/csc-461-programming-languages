from berry_keiran.Section import Section


class Iterator:

    def __init__(self, riverParts):
        """
        Initialize the normal iterator for the river system

        :param riverParts: the list of river parts to iterate through
        """
        self.__i = 0
        self.__list = riverParts

    def __iter__(self): # GRADING: ITER_ALL
        """
        Returns iterator for all river parts

        :return: the iterator for the river system
        :rtype: iterator
        """
        self.__i = 0
        return self

    def __next__(self):
        """
        Returns next river part in system

        :return: the next river part in the system
        :rtype: RiverPart
        :raises StopIteration: if list end is reached
        """
        try:
            self.__i -= 1
            return self.__list[self.__i]
        except:
            raise StopIteration()

    class SectionIterator:
        def __init__(self, riverParts):
            """
            Initialize the iterator for sections in river system

            :param riverParts: the list of river parts to iterate through
            """
            self.__i = -1
            self.__list = riverParts

        def __iter__(self): # GRADING: ITER_RESTRICT
            """
            Returns iterator for sections in river system

            :return: the iterator for the river system
            :rtype: iterator
            """
            self.__i = -1
            return self

        def __next__(self):
            """
            Returns next section in system

            :return: the next section in the system
            :rtype: Section
            :raises StopIteration: if list end is reached
            """
            try:
                self.__i += 1
                while type(self.__list[self.__i]) is not Section:
                    self.__i += 1

                return self.__list[self.__i]

            except:
                raise StopIteration()
