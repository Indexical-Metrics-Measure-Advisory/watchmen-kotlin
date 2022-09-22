package com.matrdata.watchmen.storage

/**
 * literal statement of entity operation
 */
sealed interface LiteralStatement

/**
 * criteria statement of find entities
 */
sealed interface CriteriaStatement

enum class CriteriaJointConjunction(val code: String) {
	AND("and"),
	OR("or")
}

class EntityCriteriaJoint(
	var conjunction: CriteriaJointConjunction = CriteriaJointConjunction.AND,
	var children: List<CriteriaStatement>
) : CriteriaStatement

enum class CriteriaExpressionOperator(val code: String) {
	IS_EMPTY("is-empty"),
	IS_NOT_EMPTY("is-not-empty"),
	IS_BLANK("is-blank"),
	IS_NOT_BLANK("is-not-blank"),
	EQUALS("equals"),
	NOT_EQUALS("not-equals"),
	LESS_THAN("less-than"),
	LESS_THAN_OR_EQUALS("less-than-or-equals"),
	GREATER_THAN("greater-than"),
	GREATER_THAN_OR_EQUALS("greater-than-or-equals"),
	IN("in"),
	NOT_IN("not-in"),
	LIKE("like"),
	NOT_LIKE("not-like")
}

class EntityCriteriaExpression(
	var left: LiteralStatement,
	var operator: CriteriaExpressionOperator = CriteriaExpressionOperator.EQUALS,
	var right: LiteralStatement? = null
) : CriteriaStatement, LiteralStatement

enum class ComputedLiteralOperator(val code: String) {
	ADD("add"),
	SUBTRACT("subtract"),
	MULTIPLY("multiply"),
	DIVIDE("divide"),
	MODULUS("modulus"),
	YEAR_OF("year-of"),
	HALF_YEAR_OF("half-year-of"),
	QUARTER_OF("quarter-of"),
	MONTH_OF("month-of"),
	WEEK_OF_YEAR("week-of-year"),
	WEEK_OF_MONTH("week-of-month"),
	DAY_OF_MONTH("day-of-month"),
	DAY_OF_WEEK("day-of-week"),
	CASE_THEN("case-then"),

	CONCAT("concat"),
	YEAR_DIFF("year-diff"),
	MONTH_DIFF("month-diff"),
	DAY_DIFF("day-diff"),
	FORMAT_DATE("format-date"),

	CHAR_LENGTH("char-length")
}

class ComputedLiteral(
	var operator: ComputedLiteralOperator,
	var elements: List<LiteralStatement>
) : LiteralStatement

class ColumnNameLiteral(
	var synonym: Boolean = false,
	var entityName: EntityName?,
	var columnName: EntityColumnName
) : LiteralStatement