class CallForElevator:
    def __init__(self, data):
        self.name = data[0]
        self.time = float(data[1])
        self.src = int(data[2])
        self.dest = int(data[3])
        self.state = int(data[4])
        self.elevator = int(data[5])
        self.type = 1 if self.dest > self.src else -1



