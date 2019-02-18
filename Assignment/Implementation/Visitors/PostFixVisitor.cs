﻿using Assignment.Abstraction;
using Assignment.Extensions;
using System;
using System.Linq;

namespace Assignment.Implementation
{
    internal class PostFixVisitor : IASTVisitor<string>
    {
        public string Visit(ProgramNode node)
        {
            return string.Join(Environment.NewLine, node.Children.Select(c => this.Visit(c)));
        }

        private bool ContainsVariable(INode tree, VariableNode variableNode)
        {
            if (tree.GetType() == typeof(VariableNode))
            {
                return ((VariableNode)tree).Value == variableNode.Value;
            }

            if (tree.GetType() == typeof(BinaryExpressionNode))
            {
                return this.ContainsVariable(((BinaryExpressionNode)tree).Left, variableNode) || this.ContainsVariable(((AssignmentNode)tree).Left, variableNode) || this.ContainsVariable(((BinaryExpressionNode)tree).Right, variableNode) || this.ContainsVariable(((AssignmentNode)tree).Right, variableNode);
            }

            return false;
        }

        public string Visit(BinaryExpressionNode node)
        {
            switch(node.Operation)
            {
                case Operations.ADDITION:
                    return $"{this.Visit(node.Left)} {this.Visit(node.Right)} +";
                case Operations.SUBTRACTION:
                    return $"{this.Visit(node.Left)} {this.Visit(node.Right)} -";
                case Operations.MULTIPLICATION:
                    return $"{this.Visit(node.Left)} {this.Visit(node.Right)} *";
                case Operations.DIVISION:
                    return $"{this.Visit(node.Left)} {this.Visit(node.Right)} /";
                case Operations.POWER:
                    return $"{this.Visit(node.Left)} {this.Visit(node.Right)} ^";
                default:
                    return "";
            }
        }

        public string Visit(AssignmentNode node)
        {
            if (node.Right.GetType() == typeof(BinaryExpressionNode))
            {
                if (this.ContainsVariable(node.Right, (VariableNode)node.Left))
                {
                    var exprRight = (BinaryExpressionNode)node.Right;

                    if (exprRight.Left.GetType() == typeof(ValueNode))
                    {
                        return $"{this.Visit(exprRight.Left)} {this.Visit(node.Left)} {exprRight.Operation.GetDisplayName()}!";
                    }

                    if (exprRight.Right.GetType() == typeof(ValueNode))
                    {
                        return $"{this.Visit(exprRight.Right)} {this.Visit(node.Left)} {exprRight.Operation.GetDisplayName()}!";
                    }
                }
            }

            return $"{this.Visit(node.Right)} {this.Visit(node.Left)} !";
        }

        public string Visit(NegateNode node)
        {
            return $"-{this.Visit(node.InnerNode)}";
        }

        public string Visit(FunctionNode node)
        {
            return $"{node.Function}({this.Visit(node.Argument)})";
        }

        public string Visit(ValueNode node)
        {
            return node.Value.ToString();
        }

        public string Visit(VariableNode node)
        {
            return node.Value;
        }

        public string Visit(DeclarationNode node)
        {
            return $"VARIABLE {node.Name}";
        }

        public string Visit(INode node)
        {
            return this.Visit((dynamic)node);
        }
    }
}