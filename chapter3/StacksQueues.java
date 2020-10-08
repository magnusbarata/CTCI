public class StacksQueues {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

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
        assert stackSet.toString().equals("[[2, 1, 0], [5, 4, 3], [7, 6]]") : "pop() implementation is not correct";
        stackSet.popAt(0); stackSet.popAt(1); stackSet.pop();
        assert stackSet.toString().equals("[[3, 1, 0], [5, 4]]") : "popAt() implementation is not correct";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}
