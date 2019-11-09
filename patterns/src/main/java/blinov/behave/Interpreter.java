package blinov.behave;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class Context {
	private ArrayDeque<Integer> contextValues = new ArrayDeque<>();

	public Integer popValue() {
		return contextValues.pop();
	}

	public void pushValue(Integer value) {
		this.contextValues.push(value);
	}
}

abstract class AbstractMathExpression {
	public abstract void interpret(Context context);
}

class NonterminalExpressionNumber extends AbstractMathExpression {
	private int number;

	public NonterminalExpressionNumber(int number) {
		this.number = number;
	}

	@Override
	public void interpret(Context c) {
		c.pushValue(number);
	}
}

class TerminalExpressionDivide extends AbstractMathExpression {
	@Override
	public void interpret(Context c) {
		c.pushValue((c.popValue() / c.popValue()));
	}
}

class TerminalExpressionMinus extends AbstractMathExpression {
	@Override
	public void interpret(Context c) {
		c.pushValue(c.popValue() - c.popValue());
	}
}

class TerminalExpressionMultiply extends AbstractMathExpression {
	@Override
	public void interpret(Context c) {
		c.pushValue(c.popValue() * c.popValue());
	}
}

class TerminalExpressionPlus extends AbstractMathExpression {
	@Override
	public void interpret(Context c) {
		c.pushValue(c.popValue() + c.popValue());
	}
}

class Client4 {
	private ArrayList<AbstractMathExpression> listExpression;

	public Client4(String expression) {
		listExpression = new ArrayList<>();
		parse(expression);
	}

	private void parse(String expression) {
		for (String lexeme : expression.split("\\p{Blank}+")) {
			if (lexeme.isEmpty()) {
				continue;
			}
			char temp = lexeme.charAt(0);
			switch (temp) {
			case '+':
				listExpression.add(new TerminalExpressionPlus());
				break;
			case '-':
				listExpression.add(new TerminalExpressionMinus());
				break;
			case '*':
				listExpression.add(new TerminalExpressionMultiply());
				break;
			case '/':
				listExpression.add(new TerminalExpressionDivide());
				break;
			default:
				Scanner scan = new Scanner(lexeme);
				if (scan.hasNextInt()) {
					listExpression.add(new NonterminalExpressionNumber(scan
							.nextInt()));
				}
			}
		}
	}

	public Number calculate() {
		Context context = new Context();
		for (AbstractMathExpression terminal : listExpression) {
			terminal.interpret(context);
		}
		return context.popValue();
	}
}

class InterpreterRunner {
	public static void main(String[] args) {
		String expression = "8 2 7 4 5 + * -";
		Client4 interpreter = new Client4(expression);
		System.out.println("[ " + expression + " ] = "
				+ interpreter.calculate());
	}
}