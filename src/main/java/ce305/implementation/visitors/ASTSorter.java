package ce305.implementation.visitors;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import ce305.abstraction.INode;
import ce305.abstraction.INodeComparator;
import ce305.abstraction.dependency.Dependency;
import ce305.abstraction.dependency.DependencyGraph;
import ce305.abstraction.expressions.AssignmentNode;
import ce305.abstraction.expressions.BinaryExpressionNode;
import ce305.abstraction.expressions.BooleanExpressionNode;
import ce305.abstraction.expressions.DeclarationNode;
import ce305.abstraction.expressions.FunctionCallNode;
import ce305.abstraction.expressions.NegateNode;
import ce305.abstraction.expressions.ParenthesesExpressionNode;
import ce305.abstraction.expressions.ProgramNode;
import ce305.abstraction.expressions.ValueNode;
import ce305.abstraction.expressions.VariableNode;
import ce305.abstraction.functions.FunctionCallParamNode;
import ce305.abstraction.functions.FunctionNode;
import ce305.abstraction.functions.FunctionParamNode;
import ce305.abstraction.statements.ElseIfStatementNode;
import ce305.abstraction.statements.ElseStatementNode;
import ce305.abstraction.statements.FunctionReturnStatementNode;
import ce305.abstraction.statements.IfStatementNode;
import ce305.abstraction.statements.WhileStatementNode;
import ce305.implementation.binders.BinderType;
import ce305.implementation.binders.DependencyGraphBinder;

public class ASTSorter extends ASTVisitor<INode> {
    private final INodeComparator _comparator;

    public ASTSorter(INode tree) {
        DependencyGraphBinder dependencyGraphBinder = new DependencyGraphBinder();
        dependencyGraphBinder.bind(tree, BinderType.DeclarationOnly);
        DependencyGraph dependencyGraph = dependencyGraphBinder.bind(tree, BinderType.SyntaxOnly);

        StringBuilder sb = new StringBuilder();

        sb.append("digraph g {\r\n\trankdir=lr\r\n");
        Set<Map.Entry<Dependency, Set<Dependency>>> entries = dependencyGraph._dependencies.entrySet();
        for (Map.Entry<Dependency, Set<Dependency>> entry : entries) {
            
        }

        _comparator = new INodeComparator(dependencyGraph);
    }

    @Override
    public INode visit(ProgramNode node) {
        ArrayList<INode> body = new ArrayList<>(node.children);
        body.sort(_comparator);

        return new ProgramNode(body);
    }

    @Override
    public INode visit(BinaryExpressionNode node) {
        return node;
    }

    @Override
    public INode visit(AssignmentNode node) {
        return node;
    }

    @Override
    public INode visit(NegateNode node) {
        return node;
    }

    @Override
    public INode visit(FunctionNode node) {
        ArrayList<INode> body = new ArrayList<>(node.body);
        body.sort(_comparator);

        return new FunctionNode(node.dataType, node.name, body, node.params);
    }

    @Override
    public INode visit(ValueNode node) {
        return null;
    }

    @Override
    public INode visit(VariableNode node) {
        return null;
    }

    @Override
    public INode visit(DeclarationNode node) {
        return null;
    }

    @Override
    public INode visit(IfStatementNode node) {
        return null;
    }

    @Override
    public INode visit(ElseStatementNode node) {
        return null;
    }

    @Override
    public INode visit(ElseIfStatementNode node) {
        return null;
    }

    @Override
    public INode visit(BooleanExpressionNode node) {
        return null;
    }

    @Override
    public INode visit(WhileStatementNode node) {
        ArrayList<INode> body = new ArrayList<>(node.body);
        body.sort(_comparator);

        return new WhileStatementNode(body, node.expression);
    }

    @Override
    public INode visit(FunctionParamNode node) {
        return null;
    }

    @Override
    public INode visit(FunctionReturnStatementNode node) {
        return null;
    }

    @Override
    public INode visit(FunctionCallNode node) {
        return null;
    }

    @Override
    public INode visit(FunctionCallParamNode node) {
        return null;
    }

    @Override
    public INode visit(ParenthesesExpressionNode node) {
        return null;
    }

}