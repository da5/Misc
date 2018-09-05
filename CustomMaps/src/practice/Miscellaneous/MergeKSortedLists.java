package practice.Miscellaneous;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
/*
Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6

 */
public class MergeKSortedLists {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    static class NodeEntry{
        ListNode node;
        int queueId;
        NodeEntry(int id, ListNode n){queueId = id; node = n;}
    }

    public static ListNode addToQueueFromList(Queue queue, ListNode list, int id){
        if(list != null){
            ListNode node = list;
            list = list.next;
            queue.add(new NodeEntry(id,node));
        }
        return list;
    }
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode resultHead = null, resultTail = null;
        Comparator<NodeEntry> comparator =
                (NodeEntry n1, NodeEntry n2) -> n1.node.val - n2.node.val;
        Queue<NodeEntry> queue = new PriorityQueue<>(comparator);
        for(int i=0; i<lists.length;i++){
            lists[i] = addToQueueFromList(queue, lists[i], i);
        }
        while (!queue.isEmpty()){
            NodeEntry nodeEntry = queue.poll();

            if(resultHead == null){
                resultHead = nodeEntry.node;
                resultTail = nodeEntry.node;
            }else{
                resultTail.next = nodeEntry.node;
                resultTail = resultTail.next;
            }

            lists[nodeEntry.queueId] = addToQueueFromList(queue, lists[nodeEntry.queueId], nodeEntry.queueId);
        }
        return resultHead;
    }

    public static void main(String[] args){
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1);
        lists[0].next = new ListNode(4);
        lists[0].next.next = new ListNode(5);

        lists[1] = new ListNode(1);
        lists[1].next = new ListNode(3);
        lists[1].next.next = new ListNode(4);

        lists[2] = new ListNode(2);
        lists[2].next = new ListNode(6);

        ListNode result = mergeKLists(lists);

        while(result!=null){
            System.out.print(result.val+"->");
            result = result.next;
        }


    }
}
