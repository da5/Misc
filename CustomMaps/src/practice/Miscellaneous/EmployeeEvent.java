package practice.Miscellaneous;

import java.util.*;

/**
 * Created by dasarindam on 5/16/2017.
 *
 You are given N ranges of date offsets when N employees are present in an organization. Something like

 1-4 (i.e. employee will come on 1st, 2nd, 3rd and 4th day )
 2-6
 8-9
 ..


 1-14

 You have to organize an event on minimum number of days such that each employee can attend the event at least twice.
 Write an algorithm (there is apparently an O(n) algorithm for this).
 */
enum ACTION {IN, OUT}
class EmployeeEventOrganizer {

    class EDate{
        int day;
        ACTION action;
        int id;

        public EDate(int id, int day, ACTION action) {
            this.id = id;
            this.day = day;
            this.action = action;
        }

        @Override
        public String toString() {
            return "EDate{" +
                    "day=" + day +
                    ", action=" + action +
                    ", id=" + id +
                    '}';
        }
    }

    public EDate createEDate(int id, int day, ACTION action){
        return new EDate(id, day, action);
    }

    public Set<Set<Integer>> getAttendanceSets(List<EDate> eDates){
        Set<Set<Integer>> setOfSets = new HashSet<>();
        Set<Integer> setOfIds = null;
        Set<Integer> idsEncountered;
        for(EDate eDate: eDates){
            if(setOfIds == null){
                setOfIds = new HashSet<>();
            }
            if(eDate.action.equals(ACTION.IN)){
                setOfIds.add(eDate.id);
            }else{
                if(setOfIds.contains(eDate.id)){
                    setOfSets.add(setOfIds);
                    setOfIds = null;
                }
            }
        }
        if(setOfIds != null && !setOfIds.isEmpty()){
            setOfSets.add(setOfIds);
        }
        return setOfSets;
    }
}

public class EmployeeEvent{
    public static void main(String[] args){
        List<EmployeeEventOrganizer.EDate> eDates = new ArrayList<>();
        EmployeeEventOrganizer employeeEventOrganizer = new EmployeeEventOrganizer();
        eDates.add(employeeEventOrganizer.createEDate(1,1, ACTION.IN));
        eDates.add(employeeEventOrganizer.createEDate(1,4, ACTION.OUT));
        eDates.add(employeeEventOrganizer.createEDate(2,2, ACTION.IN));
        eDates.add(employeeEventOrganizer.createEDate(2,6, ACTION.OUT));
        eDates.add(employeeEventOrganizer.createEDate(3,8, ACTION.IN));
        eDates.add(employeeEventOrganizer.createEDate(3,9, ACTION.OUT));
        eDates.add(employeeEventOrganizer.createEDate(4,1, ACTION.IN));
        eDates.add(employeeEventOrganizer.createEDate(4,14, ACTION.OUT));
        Collections.sort(eDates, new Comparator<EmployeeEventOrganizer.EDate>() {
            @Override
            public int compare(EmployeeEventOrganizer.EDate o1, EmployeeEventOrganizer.EDate o2) {
                if(o1.day == o2.day){
                    if(o1.action.equals(ACTION.IN) && o2.action.equals(ACTION.OUT)){
                        return -1;
                    }else if(o1.action.equals(ACTION.OUT) && o2.action.equals(ACTION.IN)){
                        return 1;
                    }
                }
                return o1.day-o2.day;
            }
        });
        System.out.println(eDates);
        System.out.println(employeeEventOrganizer.getAttendanceSets(eDates));
    }
}
