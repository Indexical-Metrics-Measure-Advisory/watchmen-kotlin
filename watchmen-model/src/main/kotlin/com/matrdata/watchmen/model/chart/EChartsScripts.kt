package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

typealias EChartsScriptsVars = Map<String, String?>

sealed interface EchartsScriptHolder {
	var script: String?

	/**
	 * javascript which can be parsed to an array of {@see DefItem}
	 */
	var scriptVarsDefs: String?
	var scriptVars: EChartsScriptsVars?
}

enum class ItemType(val code: String) {
	SECTION("section"),
	NUMBER("number"),
	PERCENTAGE("percentage"),
	BOOLEAN("boolean"),
	TEXT("text"),
	COLOR("color"),
	DROPDOWN("dropdown")
}

sealed interface DefItem : DataModel {
	var type: ItemType
	var label: String?
}

data class SectionItem(
	override var label: String? = null
) : DefItem {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.SECTION
		set(value) {}
}

sealed class InputItem(
	override var label: String? = null,
	open var key: String? = null
) : DefItem

data class NumberItem(
	override var label: String? = null,
	override var key: String? = null,
	var placeholder: String? = null,
	var unit: String? = null,
	var defaultValue: BigDecimal? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.NUMBER
		set(value) {}
}

data class PercentageItem(
	override var label: String? = null,
	override var key: String? = null,
	var placeholder: String? = null,
	var defaultValue: BigDecimal? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.PERCENTAGE
		set(value) {}
}

data class BooleanItem(
	override var label: String? = null,
	override var key: String? = null,
	var defaultValue: Boolean? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.BOOLEAN
		set(value) {}
}

data class TextItem(
	override var label: String? = null,
	override var key: String? = null,
	var placeholder: String? = null,
	var defaultValue: String? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.TEXT
		set(value) {}
}

data class ColorItem(
	override var label: String? = null,
	override var key: String? = null,
	var defaultValue: ChartColor? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.COLOR
		set(value) {}
}

sealed interface DropdownItemOption<V : Any> : DataModel {
	var value: V?
	var label: String?
}

data class TextDropdownItemOption(
	override var value: String? = null,
	override var label: String? = null
) : DropdownItemOption<String>

data class NumberDropdownItemOption(
	override var value: BigDecimal? = null,
	override var label: String? = null
) : DropdownItemOption<BigDecimal>

data class BooleanDropdownItemOption(
	override var value: Boolean? = null,
	override var label: String? = null
) : DropdownItemOption<Boolean>

data class DropdownItem(
	override var label: String? = null,
	override var key: String? = null,
	var placeholder: String? = null,
	var options: List<DropdownItemOption<out Any>>? = null,
	var defaultValue: ChartColor? = null
) : InputItem(label = label, key = key) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ItemType
		get() = ItemType.DROPDOWN
		set(value) {}
}
