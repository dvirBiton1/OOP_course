package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Collections;

public class Ex0 implements ElevatorAlgo {
    private Building building;
    private ArrayList<Integer>[] elevators;
    int[] dir;
    int counter = 0;

    //constructor
    public Ex0(Building building) {
        this.building = building;
        this.elevators = new ArrayList[building.numberOfElevetors()];
        dir = new int[building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            elevators[i] = new ArrayList<>();
        }
    }

    /*
    return the index of the most suitable elevator
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(0);
        temp = candidateElevators(c);
        double relevant = calculateTime(temp.get(0), c.getSrc());
        int index = temp.get(0);
        for (int i = 0; i < temp.size(); i++) {
            double min = calculateTime(temp.get(i), c.getSrc());
            if (min < relevant) {
                relevant = min;
                index = temp.get(i);
            }
        }
        elevators[index].add(c.getSrc());
        elevators[index].add(c.getDest());
        dir[index] = c.getType();
        sort(c.getType(), index);
        temp.clear();
        return index;
    }
    //sort the arrayList if up sort from low to high, if down sort from high to down
    public void sort(int type, int index) {
        if (type == 1) Collections.sort(elevators[index]);
        else Collections.sort(elevators[index], Collections.reverseOrder());
    }

    /*
    the cmd responsible about the routine elevators
    tell the elevator where are next station

     */
    @Override
    public void cmdElevator(int elev) {
        if (building.getElevetor(elev).getState() == Elevator.LEVEL) {
            if (!elevators[elev].isEmpty()) {
                if (elevators[elev].get(0) == building.getElevetor(elev).getPos()) {
                    elevators[elev].remove(0);
                    cmdElevator(elev);
                } else {
                    building.getElevetor(elev).goTo(elevators[elev].get(0));
                    elevators[elev].remove(0);
                }
            }
            if (elevators[elev].isEmpty()) {
                dir[elev] = 0;
            }
        }
    }

    /*
    return arrayList with the candidate elevators
    parameters to calculate which is the best candidate: empty, same direction.
     */
    public ArrayList candidateElevators(CallForElevator c) {
        ArrayList<Integer> temp = new ArrayList<>();
        if (c.getType() == 1) {
            for (int i = 0; i < building.numberOfElevetors(); i++) {
                if (dir[i] == c.getType() && building.getElevetor(i).getPos() < c.getSrc()) {
                    temp.add(i);
                }
            }
        } else {
            if (c.getType() == -1) {
                for (int i = 0; i < building.numberOfElevetors(); i++) {
                    if (dir[i] == c.getType() && building.getElevetor(i).getPos() > c.getSrc()) {
                        temp.add(i);
                    }
                }
            }
        }
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            if (elevators[i].isEmpty()) {
                temp.add(i);
            }
        }
        if (temp.isEmpty()) {
            if (c.getType() == 1) {
                for (int i = 0; i < dir.length; i++) {
                    if (dir[i] == 1) temp.add(i);
                }
            }
            if (c.getType() == -1) {
                for (int i = 0; i < dir.length; i++) {
                    if (dir[i] == -1) temp.add(i);
                }
            }
        }

        if (temp.isEmpty()) {
            for (int i = 0; i < dir.length; i++) {
                if (dir[i] == c.getType()) temp.add(i);
            }
            if (temp.isEmpty()) {
                do {
                    counter = counter % building.numberOfElevetors();
                } while (dir[counter] == c.getType());
                temp.add(counter);
                counter++;
            }
        }
        return temp;

    }

    /*
    calculate how much time is adding to elevator if we add the call
     */
    public double calculateTime(int elev, int src) {
        double distance = Math.abs(building.getElevetor(elev).getPos() - src);
        double time = (distance / building.getElevetor(elev).getSpeed()) + building.getElevetor(elev).getTimeForOpen() + building.getElevetor(elev).getTimeForClose() +
                building.getElevetor(elev).getStartTime() + building.getElevetor(elev).getStopTime();
        return time;
    }

    /*
    return the building
     */
    @Override
    public Building getBuilding() {
        return building;
    }

    /*
    return the name of the algorithm
     */
    @Override
    public String algoName() {
        return "algo";
    }
}
