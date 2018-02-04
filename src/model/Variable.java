package model;

import java.util.Set;

public class Variable<type> {
	private static Set<Variable> list;
	
	private String name;
	private type value;
	private String expression;
	
	private boolean evaluationMode; // 
	private boolean readOnly;
	
	@SuppressWarnings("unchecked")
	public Variable(String name, Object value_expression, boolean evaluationMode, boolean readOnly) {
		this.name = name;
		this.evaluationMode = evaluationMode;
		this.readOnly = readOnly;
		if(evaluationMode) {
			expression = (String) value_expression;
			list.add(this);
		}	
		else
			value = (type) value_expression;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public type getValue() {
		return value;
	}
	public boolean setValue(type value) {
		if(!readOnly) {
			this.value = value;
			return true;
		}
		return false;
	}
}
