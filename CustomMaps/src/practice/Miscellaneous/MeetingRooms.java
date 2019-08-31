package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.List;

public class MeetingRooms {
    static class Time {
        int val;
        int type;
        public Time(int v, int t) {
            val = v;
            type = t;
        }
    }

    public boolean canAttendMeetings(int[][] intervals) {
        int n = intervals.length;
        List<Time> timeList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            timeList.add(new Time(intervals[i][0], 1));
            timeList.add(new Time(intervals[i][1], -1));
        }
        timeList.sort(
                (Time t1, Time t2) -> ((t1.val==t2.val)? t2.type-t1.type : t1.val-t2.val)
        );
        boolean canAttend = true;
        int ctr = 0;
        for(Time time: timeList) {
            ctr += time.type;
            if(ctr>1) {
                canAttend = false;
                break;
            }
        }
        return canAttend;
    }
}

class MeetingRoomsDriver {
    public static void main(String[] args){
        MeetingRooms meetingRooms = new MeetingRooms();
        int[][] intervals = {{1,3},{4,5},{6,7}};
        System.out.println(meetingRooms.canAttendMeetings(intervals));
    }
}
