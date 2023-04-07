package com.test.calculator;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 1、计算器类
 * 2、undo、redo本身可以认为是前进或者后退操作，优先考虑使用Stack实现；复杂场景可考虑双向链表处理
 * 3、日志输出暂时使用System.out.println
 */
public class Calculator {

    // 小数点保留位数（当前对除法操作有效）
    private static final Integer DEFAULT_SCALE = 2;

    // 序列号id生成器
    private static AtomicLong seqIdBuilder = new AtomicLong(0);

    // undo节点信息
    private Stack<Node> undoNodeStack = new Stack<>();

    // redo节点信息
    private Stack<Node> redoNodeStack = new Stack<>();

    /**
     * 计算方法
     *
     * @param node
     * @return
     */
    public BigDecimal calculate(Node node) {


        node.setSeqId(seqIdBuilder.addAndGet(1));
        if (Objects.nonNull(node.getResultNumber())) {
            node.setLeftNumber(node.getResultNumber());
        }

        BigDecimal resultNumber;

        //todo 业务上如果类似mysql处理，此处可加上undo相关操作
        switch (node.getOperator()) {
            case ADD:
                resultNumber = node.getLeftNumber().add(node.getRightNumber());
                break;
            case SUBTRACT:
                resultNumber = node.getLeftNumber().subtract(node.getRightNumber());
                break;
            case MULTIPlY:
                resultNumber = node.getLeftNumber().multiply(node.getRightNumber());
                break;
            case DIVIDE:
                resultNumber = node.getLeftNumber().divide(node.getRightNumber(), DEFAULT_SCALE);
                break;
            default:
                throw new IllegalArgumentException("运算符非法");
        }


        node.setResultNumber(resultNumber);
        redo(node.clone());
        System.out.println("[calculate.redoNode] " + node);
        return resultNumber;
    }


    /**
     * redo操作
     *
     * @param node
     */
    private void redo(Node node) {

        if (node == null) {
            throw new IllegalArgumentException("node is invalid");
        }
        redoNodeStack.push(node);
    }

    /**
     * @return
     */
    public Node undo() {
        if (redoNodeStack.isEmpty()) {
            return null;
        }

        Node node = redoNodeStack.pop();
        undoNodeStack.push(node);
        System.out.println("[undoNode] " + node);
        return node;
    }

    public Node redo() {

        if (undoNodeStack.isEmpty()) {
            return null;
        }

        Node node = undoNodeStack.pop();
        redoNodeStack.push(node);
        System.out.println("[redoNode] " + node);
        return node;
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();

        Node node = new Node().setLeftNumber(BigDecimal.TEN)
                .setOperator(Node.OperatorEnum.ADD)
                .setRightNumber(BigDecimal.ONE);

        calculator.calculate(node);
        calculator.calculate(node);
        calculator.calculate(node);
        calculator.calculate(node);
        calculator.calculate(node);
        calculator.calculate(node);

        calculator.undo();
        System.out.println("[after-undo][redo-peek]: " + calculator.redoNodeStack.peek());
        calculator.redo();
        System.out.println("[after-redo][redo-peek]: " + calculator.redoNodeStack.peek());
    }
}
