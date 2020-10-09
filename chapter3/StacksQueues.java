import java.sql.Timestamp;

/* 3-6 */
abstract class Animal {
    protected String name;
    private Timestamp ts;

    public Animal(String name) { this.name = name; }
    public void setTs(Timestamp ts) { this.ts = ts; }
    public Timestamp getTs() { return ts; }
    public boolean isOlderThan(Animal a) { return this.ts.compareTo(a.ts) < 0; }
    
    @Override
    public String toString() { return name; }
}

class Dog extends Animal {
    public Dog(String name) { super(name); }
}

class Cat extends Animal {
    public Cat(String name) { super(name); }
}

class AnimalShelter {
    private Queue<Dog> dogs = new Queue<>();
    private Queue<Cat> cats = new Queue<>();

    public void deposit(Animal a) {
        a.setTs(new Timestamp(System.currentTimeMillis()));
        if (a instanceof Dog) dogs.add((Dog) a);
        else if (a instanceof Cat) cats.add((Cat) a);
    }

    public Animal adoptAny() {
        if (dogs.isEmpty() && cats.isEmpty()) throw new java.util.NoSuchElementException();
        else if (dogs.isEmpty()) return adoptCat();
        else if (cats.isEmpty()) return adoptDog();
        else return dogs.peek().isOlderThan(cats.peek()) ? adoptDog() : adoptCat();
    }

    public Dog adoptDog() { return dogs.remove(); }
    public Cat adoptCat() { return cats.remove(); }

    public Queue<Cat> getCats() { return cats; }
    public Queue<Dog> getDogs() { return dogs; }
}

public class StacksQueues {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /* 3-5 */
    public static <T extends Comparable<T>> void sort(Stack<T> s) {
        if (s.isEmpty()) return;
        Stack<T> aux = new Stack<>();
        while(!s.isEmpty()) {
            T temp = s.pop();
            while(!aux.isEmpty() && temp.compareTo(aux.peek()) < 0)
                s.push(aux.pop());
            aux.push(temp);
        }
        
        while (!aux.isEmpty()) s.push(aux.pop());
    }
    
    public static void main(String[] args) throws Exception {
        System.out.print("3-1: ");
        MultiStack stacks = new MultiStack(3, 4);
        assert stacks.toString().equals("[], [], []") : "Stacks must be empty upon initialization";
        stacks.push(0, 10); stacks.push(0, 11); stacks.push(0, 12); stacks.pop(0); stacks.push(0, 13);
        stacks.push(1, 20); stacks.push(1, 21); stacks.push(1, 22);
        stacks.push(2, 30); stacks.push(2, 31); stacks.push(2, 31); stacks.push(2, 32); stacks.push(2, 33); stacks.push(2, 34);
        assert stacks.toString().equals("[10, 11, 13], [20, 21, 22], [30, 31, 31, 32, 33, 34]") :
                        "Stacks must be: [10, 11, 13], [20, 21, 22], [30, 31, 31, 32, 33, 34]";
        stacks.pop(1); stacks.push(2, 35);
        assert stacks.peek(0) == 13 : "Last element on stack 0 is 13";
        assert stacks.peek(1) == 21 : "Last element on stack 1 is 21";
        assert stacks.peek(2) == 35 : "Last element on stack 2 is 35";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("3-2: ");
        StackMin<Integer> stackMin = new StackMin<>();
        stackMin.push(2);
        assert stackMin.min() == 2 : "Minimum is 2";
        assert stackMin.peek() == 2 : "Last item is 2";
        stackMin.push(1); stackMin.push(3); stackMin.push(1);
        assert stackMin.min() == 1 : "Minimum is 1";
        stackMin.pop();
        assert stackMin.peek() == 3 : "Last item is 3";
        stackMin.pop(); stackMin.pop();
        assert stackMin.min() == 2 : "Minimum is 2";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("3-3: ");
        SetOfStacks<Integer> stackSet = new SetOfStacks<>(3);
        for (int i = 0; i < 10; i++) stackSet.push(i);
        stackSet.pop(); stackSet.pop();
        assert stackSet.toString().equals("[[2, 1, 0], [5, 4, 3], [7, 6]]") : "pop() implementation is incorrect";
        stackSet.popAt(0); stackSet.push(8); stackSet.popAt(1); stackSet.pop();
        assert stackSet.toString().equals("[[3, 1, 0], [7, 5, 4]]") : "popAt() implementation is incorrect";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("3-4: ");
        Queue<Integer> queue = new Queue<>();
        MyQueue<Integer> myQueue = new MyQueue<>();
        for (int i = 0; i < 100; i++) {
            double rand = Math.random();
            if (rand < 0.5) {
                queue.add(i);
                myQueue.add(i);
            } else if (!queue.isEmpty()) {
                assert myQueue.isEmpty() == queue.isEmpty() : "isEmpty() implementation is incorrect";
                assert myQueue.peek().equals(queue.peek()) : "MyQueue implementation is incorrect";
                queue.remove();
                myQueue.remove();
            }
        }
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("3-5: ");
        Stack<Integer> intStack = new Stack<>();
        int[] truth = new int[100];
        for (int i = 0; i < 100; i++) {
            int rand = (int)(Math.random() * 1000);
            intStack.push(rand);
            truth[i] = rand;
        }
        java.util.Arrays.sort(truth); sort(intStack);
        for (int v: truth) assert intStack.pop() == v : "sort() implementation is incorrect";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("3-6: ");
        AnimalShelter shelter = new AnimalShelter();
        shelter.deposit(new Cat("Callie"));
		shelter.deposit(new Cat("Kiki"));
		shelter.deposit(new Dog("Fido"));
		shelter.deposit(new Dog("Dora"));
		shelter.deposit(new Cat("Kari"));
		shelter.deposit(new Dog("Dexter"));
		shelter.deposit(new Dog("Dobo"));
        shelter.deposit(new Cat("Copa"));
        shelter.adoptAny();
        shelter.deposit(new Dog("Dapa"));
        assert shelter.adoptAny().name.equals("Kiki") : "adoptAny() implementation is incorrect";
        shelter.deposit(new Cat("Kilo"));
        assert shelter.adoptCat().name.equals("Kari") : "adoptCat() implementation is incorrect";
        assert shelter.adoptDog().name.equals("Fido") : "adoptDog() implementation is incorrect";
        assert shelter.getCats().toString().equals("[Copa, Kilo]") :
               shelter.getCats() + " AnimalShelter implementation is incorrect";
        assert shelter.getDogs().toString().equals("[Dora, Dexter, Dobo, Dapa]") :
               shelter.getDogs() + " AnimalShelter implementation is incorrect";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}
