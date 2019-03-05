package ce305.abstraction.expressions;

import java.util.UUID;

import ce305.abstraction.ArithmeticOperation;
import ce305.abstraction.INode;

public class BinaryExpressionNode extends INode {
    public final INode left;
    public final INode right;
    public final ArithmeticOperation operation;

    public BinaryExpressionNode(INode left, INode right, ArithmeticOperation operation) {
        super();
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public BinaryExpressionNode(INode left, INode right, ArithmeticOperation operation, UUID id) {
        super(id);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public String getDisplayName() {
        return String.format("%s(operation: %s)", BinaryExpressionNode.class.getSimpleName(), this.operation);
    }
}