package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Call_A;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex0Test {

    Building building1;
    Building building2;
    Building building3;
    Building building4;
    Ex0 algoBuilding1;
    Ex0 algoBuilding2;
    Ex0 algoBuilding3;
    Ex0 algoBuilding4;

    /*
    a simple test just to know the algo work on the building
     */
    @Test
    void getBuilding(){
        Simulator_A.initData(1,null);
        building1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        building2 = Simulator_A.getBuilding();
        Simulator_A.initData(3,null);
        building3 = Simulator_A.getBuilding();
        Simulator_A.initData(4,null);
        building4 = Simulator_A.getBuilding();
        algoBuilding1 = new Ex0(building1);
        algoBuilding2 = new Ex0(building2);
        algoBuilding3 = new Ex0(building3);
        algoBuilding4 = new Ex0(building4);
        assertEquals(building1, algoBuilding1.getBuilding());
        assertEquals(building2, algoBuilding2.getBuilding());
        assertEquals(building3, algoBuilding3.getBuilding());
        assertEquals(building4, algoBuilding4.getBuilding());

    }
    /*
    check if the allocateAnElevator return the elevator we want
     */
    @Test
    void allocateAnElevator() {
        Simulator_A.initData(1,null);
        building1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        building2 = Simulator_A.getBuilding();
        Simulator_A.initData(3,null);
        building3 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        building4 = Simulator_A.getBuilding();
        algoBuilding1 = new Ex0(building1);
        algoBuilding2 = new Ex0(building2);
        algoBuilding3 = new Ex0(building3);
        algoBuilding4 = new Ex0(building4);
        CallForElevator call1 =  new Call_A(10, 0 ,1);
        CallForElevator call2 =  new Call_A(3,10,-1);
        CallForElevator call3 =  new Call_A(5,20,1);
        CallForElevator call4 =  new Call_A(7,-4,0);
        CallForElevator call5 =  new Call_A(8,24,1);
        CallForElevator call6 =  new Call_A(9,24,1);

        assertEquals(0,algoBuilding1.allocateAnElevator(call1));
        assertEquals(1,algoBuilding2.allocateAnElevator(call2));
        assertEquals(1,algoBuilding3.allocateAnElevator(call3));
        assertEquals(0,algoBuilding4.allocateAnElevator(call4));
        assertEquals(0,algoBuilding4.allocateAnElevator(call5));
    }


    /*
    check if the cmd work like we want
    example in the first cmd all the elevator go to the minimum floor
     */
    @Test
    void cmdElevator() {
        Simulator_A.initData(1,null);
        building1 = Simulator_A.getBuilding();
        Simulator_A.initData(2,null);
        building2 = Simulator_A.getBuilding();
        Simulator_A.initData(3,null);
        building3 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        building4 = Simulator_A.getBuilding();
        algoBuilding1 = new Ex0(building1);
        algoBuilding2 = new Ex0(building2);
        algoBuilding3 = new Ex0(building3);
        algoBuilding4 = new Ex0(building4);
        algoBuilding1.cmdElevator(0);
        algoBuilding2.cmdElevator(1);
        algoBuilding3.cmdElevator(2);
        algoBuilding4.cmdElevator(3);
        assertEquals(building1.minFloor(),algoBuilding1.getBuilding().getElevetor(0).getPos());
        assertEquals(building2.minFloor(),algoBuilding2.getBuilding().getElevetor(1).getPos());
        assertEquals(building3.minFloor(),algoBuilding3.getBuilding().getElevetor(0).getPos());
        assertEquals(building4.minFloor(),algoBuilding4.getBuilding().getElevetor(1).getPos());
    }
}