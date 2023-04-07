package com.test.calculator;

import java.math.BigDecimal;

/**
 * 节点信息
 */
public class Node {

    // 序列id
    private Long seqId;

    // 运算符
    private OperatorEnum operator;

    // 运算符：左侧数据
    private BigDecimal leftNumber;

    // 运算符：右侧数据
    private BigDecimal rightNumber;

    /**
     * 计算结果
     */
    private BigDecimal resultNumber;

    /**
     * @return
     */
    public Node clone() {
        return new Node().setSeqId(seqId)
                .setOperator(operator)
                .setLeftNumber(leftNumber)
                .setRightNumber(rightNumber)
                .setResultNumber(resultNumber);
    }

    public long getSeqId() {
        return seqId;
    }

    public Node setSeqId(final Long seqId) {
        this.seqId = seqId;
        return this;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    public Node setOperator(final OperatorEnum operator) {
        this.operator = operator;
        return this;
    }

    public BigDecimal getLeftNumber() {
        return leftNumber;
    }

    public Node setLeftNumber(final BigDecimal leftNumber) {
        this.leftNumber = leftNumber;
        return this;
    }

    public BigDecimal getRightNumber() {
        return rightNumber;
    }

    public Node setRightNumber(final BigDecimal rightNumber) {
        this.rightNumber = rightNumber;
        return this;
    }

    public BigDecimal getResultNumber() {
        return resultNumber;
    }

    public Node setResultNumber(final BigDecimal resultNumber) {
        this.resultNumber = resultNumber;
        return this;
    }

    @Override
    public String toString() {
        return "seqId = " + seqId + ", " + leftNumber + " " + operator.getDesc() + " " + rightNumber + " = " + resultNumber;
    }

    enum OperatorEnum {

        ADD(1, "+"),
        SUBTRACT(2, "-"),
        MULTIPlY(3, "*"),
        DIVIDE(4, "/");

        private int value;
        private String desc;

        OperatorEnum(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return value + ":" + desc;
        }
    }
}
