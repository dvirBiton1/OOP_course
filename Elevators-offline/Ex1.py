import sys
from CallForElevator import CallForElevator
from Building import Building
from Elevators import Elevators
import csv
import subprocess

# a simple global counter, if we did not find elevator to take the call is give that call for the elevator in the
# counter
optional = 0


# for run: python Ex1.py input\Ex1_Buildings\B2.json input\Ex1_Calls\Calls_a.csv out.csv
# ^^the function to write from the terminal just change the building and calls


def inputs():
    """
    input the building and the calls from the terminal
    :return: dictionary
    """
    if len(sys.argv) == 4:
        di = {
            "buildingName": sys.argv[1],
            "callsName": sys.argv[2],
            "outputName": sys.argv[3]
        }
    else:
        di = {
            "buildingName": "input\Ex1_Buildings\B1.json",
            "callsName": "input\Ex1_Calls\Calls_a.csv",
            "outputName": "out.csv"
        }
    return di


def readCalls(file_name):
    """
    read the calls from the file
    :param file_name:
    :return: void
    """
    calls = []
    with open(file_name) as fp:
        data = csv.reader(fp)
        for k in data:
            if (int(k[2]) < building.minFloor or int(k[2]) > building.maxFloor) or (
                    int(k[3]) < building.minFloor and int(k[3]) > building.maxFloor):
                raise Exception("the call doesnt freat")

            calls.append(CallForElevator(k))
    return calls


def writeCalls():
    """
    write to the file the new lines
    :return: void
    """
    dataCalls = []
    for k in calls:
        dataCalls.append(k.__dict__.values())
    with open(path["outputName"], 'w', newline="") as fu:
        csvwriter = csv.writer(fu)
        csvwriter.writerows(dataCalls)


def runTester():
    """
    run the tester
    :return:void
    """
    subprocess.Popen(["powershell.exe", "java -jar lib\Ex1_checker_V1.2_obf.jar 318765856,316080514 " +
                      path["buildingName"] + "  " + path["outputName"] + " out.log"])


def optionalElevators(call):
    """
    check candidate elevators that can take the call
    :param call:
    :return: list of the candidate elevators, their are the baste optional elevators
    """
    global optional
    optional_list = []
    if call.type == 1:
        for e in building.elevators:
            if e.type == call.type and e.currentFloor < call.src:
                optional_list.append(e)
    elif call.type == -1:
        for e in building.elevators:
            if e.type == call.type and e.currentFloor > call.src:
                optional_list.append(e)
    for e in building.elevators:
        if e.req.empty():
            optional_list.append(e)
    if not optional_list:
        for e in building.elevators:
            if e.type == call.type:
                optional_list.append(e)
    if not optional_list:
        optional = optional % len(building.elevators)
        optional_list.append(building.elevators[optional])
        optional += 1
    return optional_list


def cmd(time: int):
    """
    the cmd charge for the routine of the elevators
    :param time:
    :return: void
    """
    for e in building.elevators:
        if e.type == 1 and e.startTime <= time:
            e.currentFloor += e.speed
            if e.currentFloor >= e.dest:
                e.currntFloor = e.updetDest()
                e.startTime = time + e.stopTime
        elif e.type == -1 and e.startTime <= time:
            e.currentFloor -= e.speed
            if e.currentFloor <= e.dest:
                e.currntFloor = e.updetDest()
                e.startTime = time + e.stopTime
        if e.req.empty():
            e.dest = 0


def best_elevator(call):
    """
    allocate the elevator for the call
    :param call:
    :return: void
    """
    optional_list = []
    optional_list = optionalElevators(call)
    best = optional_list[0].calculateTime(call)
    elev = optional_list[0]
    for e in optional_list:
        min = e.calculateTime(call)
        if best > min:
            best = min
            elev = e
    elev.req.put(call.src)
    elev.req.put(call.dest)
    elev.type = call.type
    elev.sortDestList()
    call.elevator = elev.id

def offline_algorithm():
    """
    charge for the algorithm work, get call and allocate for it an elevator
    :return: void
    """
    num_of_calls = 0
    endTime = int(calls[-1].time) + 2
    for time in range(endTime):
        cmd(time)
        while int(calls[num_of_calls].time) + 1 == time:
            best_elevator(calls[num_of_calls])
            num_of_calls += 1
            if num_of_calls == len(calls):
                break

if __name__ == "__main__":
    path = inputs()
    ###if you want change the building and calls go to line 30-31###
    building = Building(path["buildingName"])
    calls = readCalls(path["callsName"])
    offline_algorithm()
    writeCalls()
    runTester()
