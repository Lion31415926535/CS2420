public class Queue <E>{
    private class Node <E> {
        E data;
        Node next;

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> front;
    private Node<E> back;

    public Queue() {
        this.front = null;
        this.back = null;
    }

    public void add(E elem) {
        /*
        Adds the given element to the back of the queue
         */
        if (this.front == null) {
            this.front = new Node<>(elem, null);
            this.back = this.front;
        } else {
            this.back.next = new Node<>(elem, null);
            this.back = this.back.next;
        }
    }

    public E remove() {
        /*
        Removes the element at the beginning of the queue and returns the data
         */
        if (this.front != null) {
            E elem = this.front.data;
            this.front = this.front.next;
            return elem;
        }
        System.out.println("Queue is empty");
        return null;
    }

    public void printContents() {
        /*
        Prints the contents of the queue in order
         */
        Node<E> current = this.front;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public boolean isEmpty() {
        /*
        Returns true if the queue is empty
         */
        return this.front == null;
    }


    public static void main(String[] args) {
        /*
        Creates a new queue and runs through its methods
         */
        Queue<Dwarf> queue = new Queue<>();
        queue.add(new Dwarf("Happy"));
        queue.add(new Dwarf("Grumpy"));
        queue.add(new Dwarf("Dopey"));
        queue.printContents();
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        queue.add(new Dwarf("Jimmy"));
        System.out.println(queue.remove());
        System.out.println(queue.remove());

    }



}
