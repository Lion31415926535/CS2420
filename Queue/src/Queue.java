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

    public void add(E elem) {
        if (front == null) {
            front = new Node<>(elem, null);
            back = front;
        } else {
            back.next = new Node<>(elem, null);
            back = back.next;
        }
    }

    public E remove() {
        if (front != null) {
            E elem = front.data;
            front = front.next;
            return elem;
        }
        System.out.println("Queue is empty");
        return null;
    }

    public void printContents() {
        Node<E> current = front;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }


    public static void main(String[] args) {
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
        System.out.println(queue.remove());

    }



}
