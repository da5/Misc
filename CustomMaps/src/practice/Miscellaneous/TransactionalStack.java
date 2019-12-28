package practice.Miscellaneous;

import java.util.Deque;
import java.util.LinkedList;

public class TransactionalStack {
    static class Element {
        boolean isSpecial;
        int value;
        Element prev;
        Element next;

        public Element(boolean isSpecial) {
            this.isSpecial = isSpecial;
        }

        public Element(int value) {
            isSpecial = false;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return (isSpecial)? "T" : String.valueOf(value);
        }
    }

    Deque<Element> stack;
    Element head;
    Element tail;
    Element top;
    boolean verbose;

    public TransactionalStack() {
        stack = new LinkedList<>();
        head = null;
        tail = null;
        verbose = true;
    }

    private void _push(Element element) {
        if(head==null) {
            head = tail = element;
        } else {
            tail.next = element;
            element.prev = tail;
            tail = element;
        }
    }

    private void print(String header) {
        StringBuilder builder = new StringBuilder();
        builder.append(header + " :: ");
        for(Element curr=head; curr!=null; curr=curr.next) {
            builder.append(curr).append(" ");
        }
        System.out.println(builder.toString());
    }

    public void push(int value) {
        Element element = new Element(value);
        _push(element);
        top = element;
        if(verbose) {
            print("Push " + value);
        }
    }

    public int top() {
        if(top==null) {
            return 0;
        }
        return top.value;
    }

    public int pop() {
        if(top==null) {
            return 0;
        }
        Element element = top;
        if(head==top) {
            head = tail = null;
        } else {
            element.prev.next = null;
            tail = element.prev;
            element.prev = null;
        }
        updateTop();
        if(verbose) {
            print("Pop");
        }
        return element.value;
    }

    public void begin() {
        Element element = new Element(true);
        _push(element);
        stack.push(element);
        if(verbose) {
            print("Begin");
        }
    }

    public boolean rollback() {
        if(stack.isEmpty()) {
           return false;
        }
        Element element = stack.pop();
        if(element==head) {
            head = tail = null;
        } else {
            element.prev.next = null;
            tail = element.prev;
            element.prev = null;
        }
        updateTop();
        if(verbose) {
            print("Rollback");
        }
        return true;
    }

    private void updateTop() {
        top = null;
        for(Element curr=tail; curr!=null; curr=curr.prev) {
            if(!curr.isSpecial) {
                top = curr;
                break;
            }
        }
    }

    public boolean commit() {
        if(stack.isEmpty()) {
            return false;
        }
        Element element = stack.pop();
        if(element==head) {
            if(element==tail) {
                head = tail = null;
            } else {
                head = element.next;
            }
        } else if(element==tail){
            tail = element.prev;
            element.prev.next = null;
            element.prev = null;
        } else {
            Element prev = element.prev;
            Element next = element.next;
            prev.next = next;
            next.prev = prev;
        }
        if(verbose) {
            print("Commit");
        }
        return true;
    }
}

class TransactionalStackDriver {

    private static void test1() {
        TransactionalStack transactionalStack = new TransactionalStack();
        System.out.println( transactionalStack.top()==0 );
        transactionalStack.pop();
        transactionalStack.push(5);
        transactionalStack.push(2);
        System.out.println( transactionalStack.top()==2);
        transactionalStack.pop();
        System.out.println( transactionalStack.top()==5);
    }

    private static void test2() {
        TransactionalStack transactionalStack = new TransactionalStack();
        transactionalStack.push(4);
        transactionalStack.begin();
        transactionalStack.push(7);
        transactionalStack.begin();
        transactionalStack.push(2);

        System.out.println(  transactionalStack.rollback());
        System.out.println(  transactionalStack.top()==7);

        transactionalStack.begin();
        transactionalStack.push(10);
        System.out.println(  transactionalStack.commit());
        System.out.println(  transactionalStack.top()==10);
        System.out.println(  transactionalStack.rollback());
        System.out.println(  transactionalStack.top()==4);
        System.out.println(  !transactionalStack.commit());
    }

    private static void test3() {
        TransactionalStack transactionalStack = new TransactionalStack();
        transactionalStack.push(4);
        transactionalStack.begin();
        transactionalStack.begin();
        System.out.println(  transactionalStack.pop()==4);
        System.out.println(  transactionalStack.top()==0);
    }

    public static void main(String[] args) {
        test1();
        System.out.println("------------------------------");
        test2();
        System.out.println("------------------------------");
        test3();

    }
}
