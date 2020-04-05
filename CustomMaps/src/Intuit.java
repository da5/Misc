import java.util.ArrayList;
import java.util.List;
/*
{3, 34, 4, 12, 5, 2}

f(0),           f(3)
f(34), f(0)     f()

 */
public class Intuit {
    void print(List<Integer> list) {

    }

    boolean _find(List<Integer> list, int tempSum, int[] arr, int target, int idx) {
        if(target==tempSum) {
            print(list);
        }
        if(idx==arr.length) {
            return tempSum==target;
        }

        boolean meetTarget = false;
        list.add(arr[idx]);
        meetTarget = _find(list, tempSum+arr[idx], arr, target, idx+1);
        list.remove(list.size()-1);

        if(!meetTarget) {
            meetTarget = _find(list, tempSum, arr, target, idx+1);
        }

        return meetTarget;
    }

    boolean _find(int tempSum, int[] arr, int target, int idx) {
        if(idx==arr.length) {
            return tempSum==target;
        }
        if(tempSum+arr[idx]==target) {
            return true;
        }
        return _find(tempSum, arr, target, idx+1) || _find(tempSum+arr[idx], arr, target, idx+1);
    }

    //{3, 34, 4, 12, 5, 2}


    boolean find(int[] arr, int target) {
        List<Integer> list = new ArrayList<>();
        return _find(list, 0, arr, target, 0);
    }
}


