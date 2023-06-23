from CallForElevator import CallForElevator
from queue import PriorityQueue

class Elevators:
    def __init__(self, di, index):
        self.id = int(di["_id"])
        self.speed = float(di["_speed"])
        self.minFloor = int(di["_minFloor"])
        self.maxFloor = int(di["_maxFloor"])
        self.closeTime = float(di["_closeTime"])
        self.openTime = float(di["_openTime"])
        self.startTime = float(di["_startTime"])
        self.stopTime = float(di["_stopTime"])

        self.currentFloor = 0
        self.startTime = 0
        self.type = 0

        self.req = PriorityQueue()
        self.req.put(0)  # [floor]
        self.dest = self.req.get()

    def updetDest(self):
        if not self.req.empty():
            if self.type == -1:
                self.dest = -1 * self.req.get()
            else:
                self.dest = self.req.get()
        else:
            self.type = 0
        return self.dest

    def sortDestList(self):
        for i in self.req.queue:
            i *= self.type

    def calculateTime(self, call: CallForElevator):
        """
        check how much time take to the elevator to get to the call
        :param elev:
        :param call:
        :return: how much time take for the elevator to get the call
        """
        dis_call = abs(call.src - call.dest)
        dis_current = abs(self.currentFloor - call.src)
        time = (dis_current / self.speed) + self.openTime*5 + self.closeTime + self.startTime + self.stopTime + (dis_call / self.speed)
        return time

    def __str__(self):
        return f"id: {self.id}, state: {self.type};;"

    def __repr__(self):
        return f"id: {self.id}, state: {self.type};;"
