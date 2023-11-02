"""
Grading tags in for all lines marked with *			_X_

Tierless str meets D in SOLID (hidden test)*			_X_
Check if above is done, but its test was not reached	___

1. Initial Show system\Got it compiling
Menu\initial system working					_X_
Bad input handled						_X_

2. Add Default
Added and shown properly					_X_
Second+ item ignored						_X_

3. Basic Update (single)
Moves along section						_X_
String format correct						_X_
Iterator used*							_X_

4. Basic Update (multiple)					_X_

5. Multi Update
Updates correctly						_X_
Bad input handled						_X_

6. Show details
Shows details properly 						_X_
Iterator used*							_X_

6. Add user specified item
Basic movement still works					_X_
Powered works							_X_
No passing							_X_

7. Tester part 1
Boats works up to second lock 					_X_
Formatting correct 						_X_

8. Tester part 2
Boats works up to end						_X_
Strategy pattern for basic fill*				_X_
Strategy pattern for fast empty*				_X_

9. Custom belt **
String formatting correct					_X_
Everything still works 						_X_
Bad input handled 						_X_
"""
from berry_keiran.Boat import Boat
from berry_keiran.Lock import Lock
from berry_keiran.RiverSystem import RiverSystem
from berry_keiran.Section import Section

nextBoatId = 1


def cleanInput(prompt):
    result = input(prompt)
    # strips out blank lines in input
    while result == '':
        result = input()

    return result


def main():
    """
    Main function for the RiverLock program

    :return: 0 at end of run
    :rtype: int
    """
    menu = "\n" \
           "1) Add Default Boat\n" \
           "2) Update One Tick\n" \
           "3) Update X Ticks\n" \
           "4) Show Section Details\n" \
           "5) Add Boat\n" \
           "6) Make Tester\n" \
           "7) Make New Simulator\n" \
           "0) Quit\n"

    choice = -1
    system = RiverSystem()
    global nextBoatId
    print(system)
    print(system.bottom())
    while choice != 0:
        try:

            print(menu)
            choice = int(cleanInput("Choice:> "))

            # add default box
            if choice == 1:
                boat = Boat(nextBoatId, 1, 1)
                system.addBoat(boat)
                nextBoatId += 1
                print(system)
                print(system.bottom())

            # update one time
            elif choice == 2:
                system.update()
                print(system)
                print(system.bottom())

            # update X number of times
            elif choice == 3:
                updates = int(cleanInput("How many updates:> "))
                for i in range(updates):
                    system.update()
                    print(system)
                    print(system.bottom())

            # print out station details
            elif choice == 4:
                print(system.getRiverSectionDetails())

            # make a new box of any size
            elif choice == 5:
                enginePower = int(cleanInput("What engine power:> "))
                travelMethod = int(cleanInput("What travel method. (1) Steady or (2) Max :> "))

                if travelMethod == 1 or travelMethod == 2:
                    boat = Boat(nextBoatId, enginePower, travelMethod)
                    system.addBoat(boat)
                    nextBoatId += 1
                else:
                    print("Input an option in the range 1-2")

                print(system)
                print(system.bottom())

            # make new system
            elif choice == 6:
                system.clearRiverParts()
                system.addRiverPart(Section(5, 0))
                system.addRiverPart(Lock(0, 1))
                system.addRiverPart(Section(6, 2))
                system.addRiverPart(Lock(2, 2))
                system.addRiverPart(Section(3, 3))
                system.addRiverPart(Lock(5, 3))
                print(system)
                print(system.bottom())

            # make new system
            elif choice == 7:
                system.clearRiverParts()
                addMore = 'y'
                while not addMore == 'n':
                    try:
                        riverPart = int(cleanInput("Section (1) or Lock (2):> "))
                        if riverPart == 1:
                            length = int(cleanInput("Length:> "))
                            flow = int(cleanInput("Flow:> "))
                            system.addRiverPart(Section(length, flow))
                        elif riverPart == 2:
                            behavior = int(cleanInput("Fill behavior: None (1), Basic (2), or Fast Empty (3):> "))
                            depth = int(cleanInput("Depth:> "))
                            system.addRiverPart(Lock(depth, behavior))
                        else:
                            print("Input an option in the range 1-2")
                    except ValueError:
                        print("Cannot accept value")

                    addMore = cleanInput("Add another component (n to stop):> ")

                print(system)
                print(system.bottom())


            # debug/check for D in SOLID in __str__
            elif choice == -1:
                boat1 = Boat(nextBoatId, 1, 1)
                nextBoatId += 1
                boat2 = Boat(nextBoatId, 1, 1)
                nextBoatId += 1
                boat3 = Boat(nextBoatId, 1, 1)
                nextBoatId += 1
                section = Section(1, 1)
                lock = Lock(0, 1)
                section.addBoat(boat1)
                lock.addBoat(boat2)
                system.addBoat(boat3)

                # GRADING: TO_STR
                print(boat1)
                print(section)
                print(lock)
                print(system)
                print(system.bottom())


            elif choice == 0:
                choice = 0
            else:
                print("Input an option in the range 0-7")

        except ValueError:
            print("Please, input a positive integer")


if __name__ == '__main__':
    main()
